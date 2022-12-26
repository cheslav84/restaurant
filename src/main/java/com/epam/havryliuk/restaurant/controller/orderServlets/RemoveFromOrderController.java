package com.epam.havryliuk.restaurant.controller.orderServlets;

import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;


@WebServlet("/removeFromOrder")
public class RemoveFromOrderController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RemoveFromOrderController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        OrderService orderService = new OrderService();

        HttpSession session = req.getSession();
            try {
                orderService.removeDishFromOrder(req);
                log.debug("Dish has been removed from order.");
                session.removeAttribute(ERROR_MESSAGE);
            } catch (ServiceException e) {
                String errorMessage = e.getMessage();
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(errorMessage, e);
            }
        resp.sendRedirect("basket");
    }

}


