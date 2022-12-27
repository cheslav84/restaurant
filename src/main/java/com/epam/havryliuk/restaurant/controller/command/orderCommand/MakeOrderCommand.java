package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import com.epam.havryliuk.restaurant.model.util.Validator;
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
//        if (user == null) {//todo код дублюється (виконати через фільтр)
//            log.error("The user has been logged out.");
//            session.setAttribute(ERROR_MESSAGE, "You should log in to make an order.");
//            response.sendRedirect(AppPagesPath.REDIRECT_REGISTRATION);//todo
//        }
        OrderService orderService = new OrderService();
        Order order = (Order) session.getAttribute(CURRENT_ORDER);
        if (order != null) {
            saveDishToOrder(request, orderService, order);
            session.removeAttribute(ERROR_MESSAGE);
            log.debug("Order in session: " + order);
        } else {
            makeNewOrder(request, session, user, orderService, order);
        }
        String redirectionPage = getRedirectionPage(request);
        response.sendRedirect(redirectionPage);
    }

    private void makeNewOrder(HttpServletRequest request, HttpSession session, User user, OrderService orderService, Order order) {
        try {
            String deliveryAddress = request.getParameter("deliveryAddress");
            String deliveryPhone = request.getParameter("deliveryPhone");
            checkDeliveryCredentials(deliveryAddress, deliveryPhone, session);
            order = orderService.getOrder(user, deliveryAddress, deliveryPhone);
            log.debug(order);
            session.setAttribute(CURRENT_ORDER, order);
            session.removeAttribute(ERROR_MESSAGE);
            session.removeAttribute(ORDER_MESSAGE);
            session.removeAttribute(DELIVERY_ADDRESS);
            session.removeAttribute(DELIVERY_PHONE);
        } catch (ServiceException | BadCredentialsException e) {
            String errorMessage = e.getMessage();
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(errorMessage, e);
        }
        if (order != null) {// todo think of refactoring
            saveDishToOrder(request, orderService, order);
        }
    }


    private void checkDeliveryCredentials(String deliveryAddress, String deliveryPhone, HttpSession session)
            throws BadCredentialsException {
        if(!Validator.isAddressCorrect(deliveryAddress)) {
            String error = "The address is incorrect.";
            throw new BadCredentialsException(error);
        }
        session.setAttribute(DELIVERY_ADDRESS, deliveryAddress);
        if(!Validator.isPhoneCorrect(deliveryPhone)) {
            String error = "The phone is incorrect.";
            throw new BadCredentialsException(error);
        }
        session.setAttribute(DELIVERY_PHONE, deliveryPhone);
    }


    private void saveDishToOrder(HttpServletRequest req, OrderService orderService, Order order) {
        HttpSession session = req.getSession();
        try {
            Dish dish = getCurrentDish(req);
            int dishesAmount = getDishesAmount(req);
            orderService.addDishToOrder(order, dish, dishesAmount);
            session.removeAttribute(CURRENT_DISH);

        } catch (DuplicatedEntityException e) {
            String errorMessage = "The dish is already in your order.";
            log.error(errorMessage, e);
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            session.setAttribute(ORDER_MESSAGE, errorMessage);
        } catch (ServiceException | BadCredentialsException e) {
            String errorMessage = e.getMessage();
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(errorMessage, e);
        }
    }


    private Dish getCurrentDish(HttpServletRequest req) throws ServiceException {
        HttpSession session = req.getSession();
        Dish dish = (Dish) session.getAttribute(CURRENT_DISH);
        if (dish == null) {
            String errorMessage = "Choose the dish for adding it to basket.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
        return dish;
    }

    //    private User getCurrentUser(HttpServletRequest req) throws ServiceException {// todo move to User service
//        HttpSession session = req.getSession();
//        User user = (User) session.getAttribute(LOGGED_USER);
//        if (user == null) {
//            String errorMessage = "The user has been logged out.";
//            log.error(errorMessage);
//            throw new ServiceException(errorMessage);
//        }
//        return user;
//    }


    private int getDishesAmount(HttpServletRequest req) throws BadCredentialsException {
        int dishesAmount;

        try {
            dishesAmount = Integer.parseInt(req.getParameter("amount").trim());
            if(!Validator.isDishesAmountCorrect(dishesAmount)) {
                throw new BadCredentialsException("The the number of dishes is incorrect.");
            }
            log.debug("Request for \"" + dishesAmount + "\" has been received.");
        } catch (NumberFormatException e) {
            throw new BadCredentialsException("Enter amount of dishes you want to order please.");
        }
        return dishesAmount;
    }





    /**
     * Returns String page that is going to be redirected to.
     * If user press "continue ordering" button, then request will
     * receive such parameter, and response will be redirected to the
     * page the request have been done from. Otherwise, user will be
     * redirected to his basket page.
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
//            redirectionPage = "basket";
        } else {
            redirectionPage = URLUtil.getRefererPage(req);
        }
        log.debug("redirectionPage " + redirectionPage);
        return redirectionPage;
    }


}