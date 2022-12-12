package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import com.epam.havryliuk.restaurant.model.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;


//todo read about PRG pattern

@WebServlet("/login")
public class LogInController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LogInController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/login\" request doGet in LoginController");

        HttpSession session = req.getSession();

        //todo ask from which page user came
        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/login\" request doPost in LoginController");

        User user;
        String redirectionPage;

        try {
            UserService service = new UserService();
            user = service.getLoggedInUser(req);
            HttpSession session = req.getSession();
            session.setAttribute("loggedUser", user);
//            session.setAttribute("userRole", user.getRole().getUserRole());
            session.removeAttribute("logInErrorMessage");
            redirectionPage = getRedirectionPage(session);
            log.debug("User logged in.");// todo rename
        } catch (NoSuchEntityException e) {
            redirectionPage = "registration";
            setErrorMessage(req, e.getMessage());
            log.error(e.getMessage());// todo rename
        } catch (GeneralSecurityException e) {
            redirectionPage = "errorPage";
            setErrorMessage(req, e.getMessage());
            log.error(e.getMessage());
        }

//        req.getRequestDispatcher(redirectionPage).forward(req, resp);
        resp.sendRedirect(redirectionPage);
    }


    private String getRedirectionPage(HttpSession session) {
        String pageFromBeingRedirected = (String) session.getAttribute("pageFromBeingRedirected");//todo set from security filter
        String redirectionPage;
        if (pageFromBeingRedirected != null) {
            redirectionPage = pageFromBeingRedirected;
        } else {
            redirectionPage = "index";
        }
        session.removeAttribute("pageFromBeingRedirected");
        return redirectionPage;
    }

    private void setErrorMessage(HttpServletRequest req, String errorMassage) {
        if (errorMassage != null){
            req.getSession().setAttribute("logInErrorMessage", errorMassage);
        }
    }


}

