//package com.epam.havryliuk.restaurant.controller.orderServlets;
//
//import com.epam.havryliuk.restaurant.model.entity.Order;
//import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
//import com.epam.havryliuk.restaurant.model.service.OrderService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.IOException;
//import java.util.List;
//
//import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ERROR_MESSAGE;
//import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ORDER_LIST;
//
//
//@WebServlet("/basket")
//public class BasketController extends HttpServlet {
//    private static final Logger log = LogManager.getLogger(BasketController.class);// todo add logs for class
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/BasketController\" request doGet.");
//
//        OrderService orderService = new OrderService();
//        HttpSession session = req.getSession();
//
//        try {
//            List<Order> orders = orderService.getAllUserOrders(req);
//
//            session.setAttribute(ORDER_LIST, orders);
//            session.removeAttribute(ERROR_MESSAGE);
//
//        } catch (ServiceException e) {
//            log.error(e.getMessage());
//            session.setAttribute(ERROR_MESSAGE, e.getMessage());
//        }
//
//        req.getRequestDispatcher("view/jsp/user/basket.jsp").forward(req, resp);
//    }
//
//
//
//
//}
//
