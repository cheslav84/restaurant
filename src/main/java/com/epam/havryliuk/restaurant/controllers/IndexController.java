package com.epam.havryliuk.restaurant.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


//todo read about PRG pattern

@WebServlet("/")
public class IndexController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(IndexController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/\" req doGet IndexController");
        resp.sendRedirect("index");
    }

    
}















