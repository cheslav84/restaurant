package com.epam.havryliuk.restaurant.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


@WebServlet("/basket")
public class BasketController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BasketController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/BasketController\" request doGet.");

        req.getRequestDispatcher("view/jsp/user/basket.jsp").forward(req, resp);
    }


}















