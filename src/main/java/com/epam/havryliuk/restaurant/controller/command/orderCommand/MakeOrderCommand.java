package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.IrrelevantDataException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ERROR_MESSAGE;

public class MakeOrderCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(MakeOrderCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(RequestAttributes.LOGGED_USER);
        OrderService orderService = new OrderService();
        Order order = (Order) session.getAttribute(CURRENT_ORDER);
        if (order != null) {
            saveDishToOrder(request, orderService, order);
            session.removeAttribute(ERROR_MESSAGE);
            log.debug("Order in session: " + order);
        } else {
            makeNewOrder(request, session, user, orderService);
        }
        String redirectionPage = getRedirectionPage(request);
        response.sendRedirect(redirectionPage);
    }

    /**
     * Methods requests an order from service. It can be the order that already exist in database
     * or the new one.
     * @param request
     * @param session
     * @param user
     * @param orderService
     */
    private void makeNewOrder(HttpServletRequest request, HttpSession session, User user, OrderService orderService) {
        Order order = null;
        try {
            String deliveryAddress = request.getParameter(RequestParameters.DELIVERY_ADDRESS);
            String deliveryPhone = request.getParameter(RequestParameters.DELIVERY_PHONE);
            checkDeliveryCredentials(deliveryAddress, deliveryPhone, session);
            order = orderService.getOrder(user, deliveryAddress, deliveryPhone);
            session.setAttribute(CURRENT_ORDER, order);
            session.removeAttribute(ERROR_MESSAGE);
            session.removeAttribute(ORDER_MESSAGE);
            session.removeAttribute(DELIVERY_ADDRESS);
            session.removeAttribute(DELIVERY_PHONE);
            log.debug(order);
        } catch (ServiceException | BadCredentialsException e) {
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            log.error(e.getMessage(), e);
        }
        if (order != null) {// todo think of refactoring
            saveDishToOrder(request, orderService, order);
        }
    }


    private void checkDeliveryCredentials(String deliveryAddress, String deliveryPhone, HttpSession session)
            throws BadCredentialsException {
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));
        if(!Validator.isAddressCorrect(deliveryAddress)) {
            session.setAttribute(ORDER_MESSAGE,
                    messageManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_ADDRESS));
            String error = "The address is incorrect.";
            throw new BadCredentialsException(error);
        }
        session.setAttribute(DELIVERY_ADDRESS, deliveryAddress);
        if(!Validator.isPhoneCorrect(deliveryPhone)) {
            session.setAttribute(ORDER_MESSAGE,
                    messageManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_PHONE));
            String error = "The phone is incorrect.";
            throw new BadCredentialsException(error);
        }
        session.setAttribute(DELIVERY_PHONE, deliveryPhone);
    }


    private void saveDishToOrder(HttpServletRequest req, OrderService orderService, Order order) {
        HttpSession session = req.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));
        try {
            Dish dish = getCurrentDish(req);
            int dishesAmount = getDishesAmount(req);
            orderService.addDishToOrder(order, dish, dishesAmount);
            session.removeAttribute(CURRENT_DISH);
        } catch (IrrelevantDataException e) {
            log.error(e);
            session.setAttribute(ORDER_MESSAGE,
                    messageManager.getProperty(ResponseMessages.UNAVAILABLE_DISHES_AMOUNT));
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
        } catch (DuplicatedEntityException e) {
            log.error(e);
            session.setAttribute(ORDER_MESSAGE,
                    messageManager.getProperty(ResponseMessages.DISH_ALREADY_IN_ORDER));
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
        } catch (ServiceException | BadCredentialsException e) {
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            log.error(e);
        }
    }


    private Dish getCurrentDish(HttpServletRequest req) throws ServiceException {
        HttpSession session = req.getSession();
        Dish dish = (Dish) session.getAttribute(CURRENT_DISH);
        if (dish == null) {
            MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));
            session.setAttribute(ORDER_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ORDER_DISH_NOT_FOUND));
            log.error("Choose the dish for adding it to basket.");
            throw new ServiceException();
        }
        return dish;
    }


    private int getDishesAmount(HttpServletRequest req) throws BadCredentialsException {
        int dishesAmount;
        HttpSession session = req.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));
        try {
            dishesAmount = Integer.parseInt(req.getParameter(RequestParameters.ORDER_DISHES_AMOUNT).trim());
            if(!Validator.isDishesAmountCorrect(dishesAmount)) {
                session.setAttribute(ERROR_MESSAGE,
                        messageManager.getProperty(ResponseMessages.INCORRECT_NUMBER_OF_DISHES_ERROR));
                throw new BadCredentialsException("The number of dishes is incorrect.");
            }
            log.debug("Request for \"" + dishesAmount + "\" has been received.");
        } catch (NumberFormatException e) {
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.NUMBER_OF_DISHES_IS_EMPTY_ERROR));
            throw new BadCredentialsException("Enter amount of dishes you want to order please.");
        }
        return dishesAmount;
    }





    /**
     * Returns String page that is going to be redirected to.
     * If user press "continue ordering" button, then request will
     * receive such parameter, and response will be redirected to the
     * page the request have been done from. Otherwise, user will be
     * redirected to his basket page. If any error situation occurs,
     * (any messages should be displayed to user) then user will get
     * the same page with the proper messages.
     * @param req
     * @return
     */
    private String getRedirectionPage(HttpServletRequest req) {
        String redirectionPage;
        String continueOrder = req.getParameter("continue");
        log.debug("continueOrder: " + continueOrder);
        String errorMessage = (String) req.getSession().getAttribute(ERROR_MESSAGE);
        String orderMessage = (String) req.getSession().getAttribute(ORDER_MESSAGE);
        if (continueOrder == null && errorMessage == null && orderMessage == null) {
            redirectionPage = AppPagesPath.REDIRECT_BASKET;
        } else {
            redirectionPage = URLUtil.getRefererPage(req);
        }
        log.debug("redirectionPage " + redirectionPage);
        return redirectionPage;
    }


}