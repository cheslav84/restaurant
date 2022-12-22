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
import java.util.Locale;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.controller.RequestAttributes.PAGE_FROM_BEING_REDIRECTED;

public class LoginCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);

    private static final String PARAM_NAME_LOGIN = "email";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        HttpSession session = request.getSession();
        try {
            User user = new UserService().getUserFromDatabase(login);
            Validator.checkIfPasswordsCoincide(PassEncryptor.encrypt(pass), user.getPassword());// todo при вводі одного і того ж паролю до енкриптора різні результати. З'ясувати
            session.setAttribute(LOGGED_USER, user);
            session.removeAttribute(ERROR_MESSAGE);
            page = getRedirectionPage(session);//todo
            log.debug("User logged in.");
        } catch (ServiceException e) {
            page = ConfigurationManager.getProperty(AppPagesPath.REGISTRATION);
            session.setAttribute(ERROR_MESSAGE,
                    MessageManager.UA.getProperty("message.loginError"));//todo change local
            log.error(e.getMessage());
        } catch (GeneralSecurityException e) {
            page = ConfigurationManager.getProperty(AppPagesPath.REGISTRATION);
            session.setAttribute(ERROR_MESSAGE,
                    MessageManager.UA.getProperty("message.passwordError"));//todo change local
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
            redirectionPage = "index";
        }
        session.removeAttribute(PAGE_FROM_BEING_REDIRECTED);
        return redirectionPage;
    }

}