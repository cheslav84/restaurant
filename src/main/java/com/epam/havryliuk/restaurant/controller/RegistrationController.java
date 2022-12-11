package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


//todo read about PRG pattern

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RegistrationController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo ask from which page user came
        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\"RegistrationController\", doPost.");//todo move down

        User user = null;
        try {
            UserService service = new UserService();
            user = service.addNewUser(req);
            //todo set userId
            log.info("The user \"" + user.getName() + "\" has been successfully registered.");//todo move down
            HttpSession session = req.getSession();
            session.setAttribute("loggedUser", user);
            session.setAttribute("userRole", user.getRole().getUserRole().name());
    //        Cookie cookie = new Cookie("sessionId", session.getId());
    //        resp.addCookie(cookie);
    //        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);

        } catch (DBException e) {
            log.error("User hasn't been registered. " + e);
            req.getSession().setAttribute("registrationError", e.getMessage());
            resp.sendRedirect("registration");
        }
        req.getSession().removeAttribute("registrationError");
        resp.sendRedirect("index");
    }

}

