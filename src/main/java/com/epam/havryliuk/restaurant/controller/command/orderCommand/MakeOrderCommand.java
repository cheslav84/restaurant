package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.IrrelevantDataException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ERROR_MESSAGE;

/**
 * Command that manages creating or getting the order from session or storage,
 * and saving dishes and dishes amount in that order.
 */
public class MakeOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(MakeOrderCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;

    public MakeOrderCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        orderService = appContext.getInstance(OrderService.class);
    }

    /**
     * Method executes command that gets order and saves the dishes to it. Firstly method tries to get an
     * Order from HttpSession, if it doesn't in session then method asks for order in storage or a new one.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(RequestAttributes.LOGGED_USER);
        Order order = (Order) session.getAttribute(CURRENT_ORDER);
        if (order == null) {
            order = getFromStorageOrCreateOrder(request, user);
            session.setAttribute(CURRENT_ORDER, order);
            LOG.debug("Created new order: " + order);
        } else {
            session.removeAttribute(ERROR_MESSAGE);
            LOG.debug("Order in session: " + order);
        }
        if (order != null) {
            saveDishToOrder(request, order);
        }
        String redirectionPage = getRedirectionPage(request);
        response.sendRedirect(redirectionPage);
    }

    /**
     * Methods requests an order from service. It can be the order that already exist in database
     * or the new one.
     */
    private Order getFromStorageOrCreateOrder(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        Order order = null;
        try {
            String deliveryAddress = request.getParameter(RequestParameters.DELIVERY_ADDRESS);
            String deliveryPhone = request.getParameter(RequestParameters.DELIVERY_PHONE);
            Validator.validateDeliveryData(deliveryAddress, deliveryPhone, request);
            order = orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone);
            session.removeAttribute(ERROR_MESSAGE);
            session.removeAttribute(ORDER_MESSAGE);
            session.removeAttribute(DELIVERY_ADDRESS);
            session.removeAttribute(DELIVERY_PHONE);
            LOG.debug(order);
        } catch (ServiceException | ValidationException e) {
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
     * Catches ServiceException when impossible to get data from storage or to write data to it.
     * Catches DuplicatedEntityException when dish is already exists in that order.
     * Catches IrrelevantDataException when the amount of requested dishes exceed available ones in menu.
     */
    private void saveDishToOrder(HttpServletRequest request, Order order) {
            HttpSession session = request.getSession();
            BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
            try {
                Dish dish = getCurrentDish(request);
                int dishesAmount = getDishesAmount(request);
                orderService.addDishToOrder(order, dish, dishesAmount);
                session.removeAttribute(CURRENT_DISH);
            } catch (IrrelevantDataException e) {
                LOG.error(e);
                session.setAttribute(ORDER_MESSAGE,
                        bundleManager.getProperty(ResponseMessages.UNAVAILABLE_DISHES_AMOUNT));
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            } catch (DuplicatedEntityException e) {
                LOG.error(e);
                session.setAttribute(ORDER_MESSAGE,
                        bundleManager.getProperty(ResponseMessages.DISH_ALREADY_IN_ORDER));
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            } catch (ServiceException | ValidationException e) {
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                LOG.error(e);
            }
    }

    /**
     * Method obtains a dish saved in session. If there is no dish in it, the ServiceException will be
     * thrown and the corresponding message will be set to session for informing user. Dish had to be set
     * in session while user press "Order" button and performing "show_dish_info" command preceding the current
     * command.
     * @return Dish that need to be saved to order.
     * @throws ServiceException if there is no Dish present in session.
     */
    private Dish getCurrentDish(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        Dish dish = (Dish) session.getAttribute(CURRENT_DISH);
        if (dish == null) {
            BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
            session.setAttribute(ORDER_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.ORDER_DISH_NOT_FOUND));
            LOG.error(ResponseMessages.ORDER_DISH_NOT_FOUND);
            throw new ServiceException(ResponseMessages.ORDER_DISH_NOT_FOUND);
        }
        return dish;
    }

    /**
     * Method extracts dishes amount from HttpServletRequest and validates that data. If number of dishes is
     * not valid, the ValidationException will be thrown, and corresponding message will be set to session
     * for informing user.
     * @return amount of the same dishes that has to be saved to order.
     * @throws ValidationException if the dishes amount data is not valid.
     */
    private int getDishesAmount(HttpServletRequest request) throws ValidationException {
        int dishesAmount;
        HttpSession session = request.getSession();
        BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
        try {
            dishesAmount = Integer.parseInt(request.getParameter(RequestParameters.ORDER_DISHES_AMOUNT).trim());
            Validator.validateDishesAmount(dishesAmount, request);
            LOG.debug("Request for \"" + dishesAmount + "\" has been received.");
        } catch (NumberFormatException | NullPointerException e) {
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.NUMBER_OF_DISHES_IS_EMPTY_ERROR));
            throw new ValidationException("Enter amount of dishes you want to order please.");
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
            redirectionPage = URLUtil.getRefererPage(request);
        }
        LOG.debug("redirectionPage " + redirectionPage);
        return redirectionPage;
    }

}