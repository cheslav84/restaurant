package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.controller.dispatchers.DishDispatcher;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.IrrelevantDataException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.controller.dispatchers.URLDispatcher;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationProcessor;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.ERROR_MESSAGE;

/**
 * Command that manages creating or getting the order from session or storage,
 * and saving dishes and dishes amount in that order.
 */
public class MakeOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(MakeOrderCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;

    public MakeOrderCommand() {
        orderService = ApplicationProcessor.getInstance(OrderService.class);
    }

    /**
     * Method executes command that gets order and saves the dishes to it. Firstly method tries to get an
     * Order from HttpSession, if it doesn't in session then method asks for order in storage or a new one.
     * If User entered invalid data and Order doesn't exist it still will be null, in such case User will be
     * redirected to the same page and proper message will be shown for him.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOG.trace("MakeOrderCommand.");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(RequestAttributes.LOGGED_USER);
        if (isUserUnderEighteenNotOrderAlcohol(request, user)) {
            Order order = (Order) session.getAttribute(CURRENT_ORDER);
            int dishesAmount = getDishesAmount(request);
            if (order == null) {
                getOrder(request, user, dishesAmount);
            } else {
                saveOrder(request, order, dishesAmount);
            }
        }
        String redirectionPage = getRedirectionPage(request);
        response.sendRedirect(redirectionPage);
    }


    private boolean isUserUnderEighteenNotOrderAlcohol(HttpServletRequest request, User user) {
        try {
            Dish dish = DishDispatcher.getCurrentDish(request);
            if (dish.isAlcohol() && !user.isOverEighteen()) {
                LOG.info("User under 18 trying to order alcohol.");
                MessageDispatcher.setToSession(request, ORDER_MESSAGE, ResponseMessages.USER_UNDER_EIGHTEEN_ORDER_ALCOHOL);
                request.getSession().setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                return false;
            }
        } catch (ServiceException e) {
            LOG.debug(ResponseMessages.ORDER_DISH_NOT_FOUND);
            return false;
        }
        return true;
    }


    private void getOrder(HttpServletRequest request, User user, int dishesAmount) {
        if (Validator.validateDeliveryData(dishesAmount, request)) {
            Order order = getOrCreateOrder(request, user);
            request.getSession().setAttribute(CURRENT_ORDER, order);
            saveDishToOrder(request, order, dishesAmount);
            LOG.debug("Created new order: {}", order);
        } else {
            request.getSession().setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            LOG.debug("Some of delivery data is incorrect.");
        }
    }


    private void saveOrder(HttpServletRequest request, Order order, int dishesAmount) {
        if (Validator.validateDishesAmount(dishesAmount, request)) {
            saveDishToOrder(request, order, dishesAmount);
            request.getSession().removeAttribute(ERROR_MESSAGE);
            LOG.debug("Order in session: {}", order);
        } else {
            request.getSession().setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            LOG.debug("Dishes amount is incorrect.");
        }
    }


    /**
     * Methods requests an order from service. It can be the order that already exist in database
     * or the new one.
     */
    private Order getOrCreateOrder(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        Order order = null;
        try {
            String deliveryAddress = request.getParameter(RequestParameters.DELIVERY_ADDRESS);
            String deliveryPhone = request.getParameter(RequestParameters.DELIVERY_PHONE);
            order = orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone);
            session.removeAttribute(ERROR_MESSAGE);
            session.removeAttribute(ORDER_MESSAGE);
            session.removeAttribute(DELIVERY_ADDRESS);
            session.removeAttribute(DELIVERY_PHONE);
            LOG.debug("User made order: " + order);
        } catch (ServiceException e) {
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            LOG.error(e.getMessage(), e);
        }
        return order;
    }


    /**
     * Method saves dish and its amount to an Order. Method also removes Dish entity from session,
     * as it is going to order in that session, and that dish in order.
     * @param request HttpServletRequest from user.
     * @param order that dish needs to be saved in.
     * @param dishesAmount amount of dishes that user wants to order.
     * Catches ServiceException when impossible to get data from storage or to write data to it.
     * Catches DuplicatedEntityException when dish is already exists in that order.
     * Catches IrrelevantDataException when the amount of requested dishes exceed available ones in menu.
     */
    private void saveDishToOrder(HttpServletRequest request, Order order, int dishesAmount) {
            HttpSession session = request.getSession();
            try {
                Dish dish = DishDispatcher.getCurrentDish(request);
                orderService.addDishToOrder(order, dish, dishesAmount);
                session.removeAttribute(CURRENT_DISH);
            } catch (IrrelevantDataException e) {
                LOG.error(e);
                MessageDispatcher.setToSession(request, ORDER_MESSAGE, ResponseMessages.UNAVAILABLE_DISHES_AMOUNT);
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            } catch (DuplicatedEntityException e) {
                LOG.error(e);
                MessageDispatcher.setToSession(request, ORDER_MESSAGE, ResponseMessages.DISH_ALREADY_IN_ORDER);
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            } catch (ServiceException e) {
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                LOG.error(e);
            }
    }


    /**
     * Method extracts dishes amount from HttpServletRequest and validates that data. If number of dishes is
     * not valid, the ValidationException will be thrown, and corresponding message will be set to session
     * for informing user.
     * @return amount of the same dishes that has to be saved to order.
     */
    private int getDishesAmount(HttpServletRequest request) {
        int dishesAmount = 0;
        try {
            dishesAmount = Integer.parseInt(request.getParameter(RequestParameters.ORDER_DISHES_AMOUNT).trim());
            LOG.debug("Request for \"" + dishesAmount + "\" has been received.");
        } catch (NumberFormatException | NullPointerException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.NUMBER_OF_DISHES_IS_EMPTY_ERROR);
        }
        return dishesAmount;
    }

    /**
     * Returns String page that is going to be redirected to. If user press "continue ordering" button,
     * then request will receive such parameter, and response will be redirected to the page the request
     * have been done from. Otherwise, user will be redirected to his basket page. If any error situation
     * occurs, (any messages should be displayed to user) then user will get the same page with the proper messages.
     * @return redirection page
     */
    private String getRedirectionPage(HttpServletRequest request) {
        String redirectionPage;
        String continueOrder = request.getParameter(RequestParameters.CONTINUE_ORDERING);
        LOG.debug("continueOrder: " + continueOrder);
        String errorMessage = (String) request.getSession().getAttribute(ERROR_MESSAGE);
        String orderMessage = (String) request.getSession().getAttribute(ORDER_MESSAGE);
        if (continueOrder == null && errorMessage == null && orderMessage == null) {
            redirectionPage = AppPagesPath.REDIRECT_BASKET;
        } else {
            redirectionPage = URLDispatcher.getRefererPage(request);
        }
        LOG.debug("redirectionPage " + redirectionPage);
        return redirectionPage;
    }

}