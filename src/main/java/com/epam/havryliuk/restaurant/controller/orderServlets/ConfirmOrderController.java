package com.epam.havryliuk.restaurant.controller.orderServlets;

import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;


@WebServlet("/confirmOrder")
public class ConfirmOrderController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ConfirmOrderController.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderService orderService = new OrderService();
        HttpSession session = req.getSession();
        try {
            orderService.confirmOrder(req);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            session.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
        resp.sendRedirect("basket");
    }
}




