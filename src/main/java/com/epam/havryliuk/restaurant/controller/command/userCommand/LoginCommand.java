package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.UserService;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;


import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.PAGE_FROM_BEING_REDIRECTED;
import static com.epam.havryliuk.restaurant.model.constants.RequestParameters.EMAIL;
import static com.epam.havryliuk.restaurant.model.constants.RequestParameters.PARAM_NAME_PASSWORD;

public class LoginCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter(EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        HttpSession session = request.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));

        String page;
        try {
            //todo подумати про верифікацію даних
            User user = new UserService().getUserFromDatabase(login);
            PassEncryptor.verify(user.getPassword(), password);
            session.setAttribute(LOGGED_USER, user);
            session.removeAttribute(ERROR_MESSAGE);
            page = getRedirectionPage(session);//todo
            log.debug("User logged in.");
        } catch (ServiceException e) {
            page = AppPagesPath.REDIRECT_REGISTRATION;
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.LOGIN_ERROR));
            log.error(e.getMessage());
        } catch (GeneralSecurityException e) {
            page = AppPagesPath.REDIRECT_REGISTRATION;
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.PASSWORD_ERROR));
            log.error(e.getMessage());
        }
        response.sendRedirect(page);
    }


    private String getRedirectionPage(HttpSession session) {//todo, think maybe delete this
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

}