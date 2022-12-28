package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;

import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class RegisterCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        User user;
        String redirectionPage;

        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));

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
        }catch (DuplicatedEntityException e) {
            log.error("The user with such login is already exists. Try to use another one." + e);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.REGISTRATION_USER_EXISTS));
            redirectionPage = getErrorPage(session);
        } catch (ServiceException e) {
            log.error("User hasn't been registered. " + e);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.REGISTRATION_ERROR));
            redirectionPage = getErrorPage(session);
        }
        response.sendRedirect(redirectionPage);
    }

    private String getErrorPage(HttpSession session) {
        String redirectionPage;
        redirectionPage = AppPagesPath.REDIRECT_REGISTRATION;
        session.setAttribute(REGISTRATION_PROCESS, REGISTRATION_PROCESS);//TODO
        return redirectionPage;
    }


    private String getRedirectionPage(HttpSession session) {
        String pageFromBeingRedirected = (String) session.getAttribute(PAGE_FROM_BEING_REDIRECTED);//todo set from security filter
        String redirectionPage;
        if (pageFromBeingRedirected != null) {
            redirectionPage = pageFromBeingRedirected;
        } else {
            redirectionPage = AppPagesPath.REDIRECT_INDEX;
        }
        session.removeAttribute(PAGE_FROM_BEING_REDIRECTED);
        return redirectionPage;
    }



//    private void setErrorMessage(HttpServletRequest req, String errorMassage) {
//        if (errorMassage != null){
//            req.getSession().setAttribute(REGISTRATION_ERROR_MESSAGE, errorMassage);
//        }
//    }
}