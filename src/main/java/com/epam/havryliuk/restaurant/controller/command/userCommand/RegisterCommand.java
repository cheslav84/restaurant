package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.ConfigurationManager;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.UserService;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import com.epam.havryliuk.restaurant.model.util.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class RegisterCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        User user;
        String redirectionPage;

        try {
            UserService service = new UserService();
            user = service.addNewUser(request);
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
            redirectionPage = "registration"; //todo
            setErrorMessage(request, e.getMessage());
            session.setAttribute(REGISTRATION_PROCESS, REGISTRATION_PROCESS);
        }
        response.sendRedirect(redirectionPage);
    }



    private String getRedirectionPage(HttpSession session) {
        String pageFromBeingRedirected = (String) session.getAttribute(PAGE_FROM_BEING_REDIRECTED);//todo set from security filter
        String redirectionPage;
        if (pageFromBeingRedirected != null) {
            redirectionPage = pageFromBeingRedirected;
        } else {
            redirectionPage = "index";//todo
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