package com.epam.havryliuk.restaurant.controller.orderServlets;

import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import com.epam.havryliuk.restaurant.model.util.Validator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;


@WebServlet("/addToOrder")
public class AddToOrderController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AddToOrderController.class);// todo add logs for class

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/BasketController\" request doGet.");
//
//
//        req.getRequestDispatcher("view/jsp/user/index.jsp").forward(req, resp);
//    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        OrderService orderService = new OrderService();

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute(CURRENT_ORDER);

        if (order != null) {
            saveDishToOrder(req, orderService, order);
            log.debug("Order in session: " + order);
        } else {
            try {
                checkDeliveryCredentials(req, orderService);// todo move to service
                order = orderService.getOrder(req);

                log.debug(order);
                session.setAttribute(CURRENT_ORDER, order);
                session.removeAttribute(ERROR_MESSAGE);
                session.removeAttribute(DELIVERY_ADDRESS);
                session.removeAttribute(DELIVERY_PHONE);
            } catch (ServiceException | BadCredentialsException e) {
                String errorMessage = e.getMessage();
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(errorMessage, e);
            }
                if (order != null) {// think of refactoring
                saveDishToOrder(req, orderService, order);
            }
        }

        String redirectionPage = getRedirectionPage(req);
        resp.sendRedirect(redirectionPage);
    }

    private void checkDeliveryCredentials(HttpServletRequest req, OrderService orderService) throws BadCredentialsException {

        String deliveryAddress = req.getParameter("deliveryAddress");
        String deliveryPhone = req.getParameter("deliveryPhone");

        HttpSession session = req.getSession();

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
            orderService.addDishToOrder(req, order);
            session.removeAttribute(CURRENT_DISH);
        } catch (ServiceException | BadCredentialsException e) {
            String errorMessage = e.getMessage();
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(errorMessage, e);
        }
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
        if (continueOrder == null && errorMessage == null) {
            redirectionPage = "basket";
        } else {
            redirectionPage = URLUtil.getRefererPage(req);
        }
            log.debug("redirectionPage " + redirectionPage);
        return redirectionPage;
    }

//    @Nullable
//    private Order getOrder(HttpServletRequest req, OrderService orderService) {
//        HttpSession session = req.getSession();
//        Order order = (Order) session.getAttribute(ORDER);
//        log.debug("Order in session: " + order);
//        if (order == null) {
//            try {
//                order = orderService.getOrder(req);
//                log.debug(order);
//                session.setAttribute(ORDER, order);
//                session.removeAttribute(ERROR_MESSAGE);
//            } catch (NoSuchEntityException | BadCredentialsException e) {
//                String errorMessage = e.getMessage();
//                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
//                session.setAttribute(ERROR_MESSAGE, errorMessage);
//                log.error(errorMessage, e);
//            }
//        }
//        return order;
//    }




}


//    System.out.println("ContextPath(): " + req.getContextPath());
//        System.out.println("HttpServletMapping(): " +req.getHttpServletMapping());
//        System.out.println("getAuthType(): " +req.getAuthType());
//        System.out.println("req.getPathInfo(): " +req.getPathInfo());
//        System.out.println("req.getPathTranslated(): " +req.getPathTranslated());
//        System.out.println("req.getQueryString(): " +req.getQueryString());
//        System.out.println("req.getRemoteUser(): " +req.getRemoteUser());
//        System.out.println("req.getRequestURI(): " +req.getRequestURI());
//        System.out.println("req.getServletPath(): " +req.getServletPath());
//        System.out.println("req.getLocalAddr(): " +req.getLocalAddr());
//        System.out.println("req.getRemoteAddr(): " +req.getRemoteAddr());
//        System.out.println("req.getRemoteHost(): " +req.getRemoteHost());
//        System.out.println("req.getLocale(): " +req.getLocale());
//        System.out.println("req.getHeaderNames(): " +req.getHeaderNames());
//        System.out.println("req.getHeaderNames(): " +req.getHeader("Referer"));



