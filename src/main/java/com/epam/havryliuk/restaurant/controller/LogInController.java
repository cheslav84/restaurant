package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


//todo read about PRG pattern

@WebServlet("/login")
public class LogInController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LogInController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/login\" request doGet in LoginController");


        //todo ask from which page user came
        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);//todo userPage
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/login\" request doPost in LoginController");


        User user = null;
        try {
            UserService service = new UserService();
            user = service.getUserByLogin(req);
            //todo set userId
            log.info("The user \"" + user.getName() + "\" has been successfully added to DataBase.");
        } catch (DBException e) {
            log.error("Adding user to DataBase failed.");
            //todo inform cause
        }

        req.getSession().setAttribute("loggedUser", user);


        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
        resp.sendRedirect("registration");
        //todo redirect
    }

}

