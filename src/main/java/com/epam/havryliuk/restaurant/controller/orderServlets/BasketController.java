package com.epam.havryliuk.restaurant.controller.orderServlets;

import com.epam.havryliuk.restaurant.controller.RequestAttributes;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.ERROR_MESSAGE;


@WebServlet("/basket")
public class BasketController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BasketController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/BasketController\" request doGet.");

        OrderService orderService = new OrderService();
        HttpSession session = req.getSession();

        try {
            List<Order> orders = orderService.getAllUserOrders(req);
            session.setAttribute("orders", orders);
            session.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            session.setAttribute(ERROR_MESSAGE, e.getMessage());
        }


//        List<Order> orders = new ArrayList<>();
//        Order order = (Order) req.getSession().getAttribute(RequestAttributes.ORDER);
//        orders.add(order);
//        req.getSession().setAttribute("orders", orders);
//        if (order == null) {
//            orderService.getAllUserOrders(req);
//        }
//
//        System.out.println(order);
//        try {
//            List<Map<Dish, Integer>> orders = orderService.getAllUserOrders(req);
//        } catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }

        req.getRequestDispatcher("view/jsp/user/basket.jsp").forward(req, resp);
    }





}

