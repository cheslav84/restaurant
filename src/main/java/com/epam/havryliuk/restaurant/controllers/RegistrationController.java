package com.epam.havryliuk.restaurant.controllers;

import com.epam.havryliuk.restaurant.model.db.entity.User;
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

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RegistrationController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/registration\" request doGet in RegistrationController");


        //todo ask from which page user came
        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/registration\" request doPost in RegistrationController");

        //todo create Session
        User user = null;
        try {
            UserService service = new UserService();
            user = service.getUser(req);
            //todo set userId
            log.info("The user \"" + user.getName() + "\" has been successfully added to DataBase.");

            System.out.println("The user \"" + user.getName() + "\" has been successfully added to DataBase. SYSTEM.OUT");
        } catch (DBException e) {
            log.error("Adding user to DataBase failed.");
            //todo inform cause
        }

        req.getSession().setAttribute("user", user);

        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
        resp.sendRedirect("registration");
        //todo redirect
    }

}

