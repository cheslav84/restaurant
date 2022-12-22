//package com.epam.havryliuk.restaurant.controller.userServlets;
//
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.IOException;
//
//
//
//@WebServlet("/logout")
//public class LogOutController extends HttpServlet {
//    private static final Logger log = LogManager.getLogger(LogOutController.class);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        log.debug("\"/logout\" request doGet in LoginController");
//
//        req.getSession().invalidate();
//        resp.sendRedirect("index");
//    }
//}
//
