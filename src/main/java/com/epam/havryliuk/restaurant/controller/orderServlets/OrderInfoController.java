package com.epam.havryliuk.restaurant.controller.orderServlets;

import com.epam.havryliuk.restaurant.model.services.DishService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/showOrderInfo")
public class OrderInfoController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(OrderInfoController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("\"/login\" request doGet in OrderController");

        DishService dishService = new DishService();
        dishService.getDishInfo(req);

        resp.sendRedirect("index");
    }

}

