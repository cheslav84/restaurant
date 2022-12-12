package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


//todo read about PRG pattern

@WebServlet("/logout")
public class LogOutController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LogOutController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("\"/logout\" request doGet in LoginController");

        req.getSession().invalidate();
//        req.getSession().removeAttribute("loggedUser");
        resp.sendRedirect("index");
    }
}

