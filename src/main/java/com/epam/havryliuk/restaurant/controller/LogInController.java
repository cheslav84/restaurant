package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
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
import java.security.GeneralSecurityException;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;


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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("\"/login\" request doPost in LoginController");

        User user;
        String redirectionPage;

        try {
            UserService service = new UserService();
            user = service.getLoggedInUser(req);
            HttpSession session = req.getSession();
            session.setAttribute(LOGGED_USER, user);
            session.removeAttribute(LOGIN_ERROR_MESSAGE);
            redirectionPage = getRedirectionPage(session);
            log.debug("User logged in.");// todo rename
        } catch (ServiceException e) {
            redirectionPage = "registration";
            setErrorMessage(req, e.getMessage());
            log.error(e.getMessage());// todo rename
        } catch (GeneralSecurityException e) {
            redirectionPage = "errorPage";
            setErrorMessage(req, e.getMessage());
            log.error(e.getMessage());
        }
        System.out.println("log in req.getHeaderNames(): " +req.getHeader("Referer"));

        resp.sendRedirect(redirectionPage);
    }


    private String getRedirectionPage(HttpSession session) {
        String pageFromBeingRedirected = (String) session.getAttribute(PAGE_FROM_BEING_REDIRECTED);//todo set from security filter
        String redirectionPage;
        if (pageFromBeingRedirected != null) {
            redirectionPage = pageFromBeingRedirected;
        } else {
            redirectionPage = "index";
        }
        session.removeAttribute(PAGE_FROM_BEING_REDIRECTED);
        return redirectionPage;
    }

    private void setErrorMessage(HttpServletRequest req, String errorMassage) {
        if (errorMassage != null){
            req.getSession().setAttribute(LOGIN_ERROR_MESSAGE, errorMassage);
        }
    }


}

