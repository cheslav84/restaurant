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


@WebServlet("/basket")
public class BasketController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BasketController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/BasketController\" request doGet.");


        req.getRequestDispatcher("view/jsp/user/basket.jsp").forward(req, resp);
    }





}

