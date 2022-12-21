package com.epam.havryliuk.restaurant.controller.userServlets;

import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RegistrationController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo ask from which page user came
        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("\"RegistrationController\", doPost.");

        HttpSession session = req.getSession();
        User user;
        String redirectionPage;

        try {
            UserService service = new UserService();
            user = service.addNewUser(req);
            session.setAttribute(LOGGED_USER, user);
    //        Cookie cookie = new Cookie("sessionId", session.getId());
    //        resp.addCookie(cookie);
    //        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
            session.removeAttribute(REGISTRATION_ERROR_MESSAGE);
            session.removeAttribute(REGISTRATION_PROCESS);//todo if that attribute is set - hide login menu in jsp
            redirectionPage = getRedirectionPage(session);
            log.info("The user \"" + user.getName() + "\" has been successfully registered.");
        } catch (ServiceException e) {
            log.error("User hasn't been registered. " + e);
            redirectionPage = "registration";
            setErrorMessage(req, e.getMessage());
            session.setAttribute(REGISTRATION_PROCESS, REGISTRATION_PROCESS);
        }
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
            req.getSession().setAttribute(REGISTRATION_ERROR_MESSAGE, errorMassage);
        }
    }
}

