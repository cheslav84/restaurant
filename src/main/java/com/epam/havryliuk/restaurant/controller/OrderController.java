package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import com.epam.havryliuk.restaurant.model.services.OrderService;
import com.epam.havryliuk.restaurant.model.utils.URLUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;


@WebServlet("/addToOrder")
public class OrderController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(OrderController.class);// todo add logs for class

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/BasketController\" request doGet.");
//
//
//        req.getRequestDispatcher("view/jsp/user/index.jsp").forward(req, resp);
//    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//        OrderService orderService = new OrderService();
//        Order order = getOrder(req, orderService);
//        saveDishToOrder(req, orderService, order);

        String redirectionPage = getRedirectionPage(req);
        resp.sendRedirect(redirectionPage);
    }

    @NotNull
    private String getRedirectionPage(HttpServletRequest req) {
        String redirectionPage;
        String continueOrder = req.getParameter("continue");
        log.debug("continueOrder: " + continueOrder);
        if (continueOrder == null) {
            redirectionPage = "basket";
        } else {
            redirectionPage = URLUtil.getRefererPage(req);
        }
            log.debug("redirectionPage " + redirectionPage);
        return redirectionPage;
    }

    private void saveDishToOrder(HttpServletRequest req, OrderService orderService, Order order) {
        HttpSession session = req.getSession();
        try {
            orderService.addDishToOrder(req, order);
            session.removeAttribute(CURRENT_DISH);
        } catch (NoSuchEntityException e) {
            String errorMessage = e.getMessage();
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(errorMessage, e);
        }
    }

    @Nullable
    private Order getOrder(HttpServletRequest req, OrderService orderService) {
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute(ORDER);
        log.debug("Order in session: " + order);
        if (order == null) {
            try {
                order = orderService.getOrder(req);
                log.debug(order);
                session.setAttribute(ORDER, order);
                session.removeAttribute(ERROR_MESSAGE);
            } catch (NoSuchEntityException | BadCredentialsException e) {
                String errorMessage = e.getMessage();
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(errorMessage, e);
            }
        }
        return order;
    }


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



