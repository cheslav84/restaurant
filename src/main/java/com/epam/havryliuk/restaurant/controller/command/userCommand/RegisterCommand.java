package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;

import com.epam.havryliuk.restaurant.model.util.MessageManager;
import com.epam.havryliuk.restaurant.model.service.UserService;

import com.epam.havryliuk.restaurant.model.service.validation.Validator;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class RegisterCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);
    private UserService userService;

    public RegisterCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        userService = appContext.getInstance(UserService.class);
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String redirectionPage;
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
        User user;
        try {
            user = mapUser(request);

            //todo check user if exists
            userService.addNewUser(user);
            encryptUserPassword(user);
            session.setAttribute(LOGGED_USER, user);
            //        Cookie cookie = new Cookie("sessionId", session.getId());
            //        resp.addCookie(cookie);
            //        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
            session.removeAttribute(REGISTRATION_ERROR_MESSAGE);
            session.removeAttribute(USER_IN_LOGGING);
            session.removeAttribute(REGISTRATION_PROCESS);
            redirectionPage = getRedirectionPage(session);
            log.info("The user \"" + user.getName() + "\" has been successfully registered.");
        } catch (ValidationException e) {
            log.error("Some credentials are not correct." + e);//todo rename
            redirectionPage = getErrorPage(session);
        } catch (DuplicatedEntityException e) {
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

    @NotNull
    private User mapUser(HttpServletRequest req) throws ValidationException {//todo можливо винести метод, подумати...
        final String password = req.getParameter(RequestParameters.PASSWORD);
        final String email = req.getParameter(RequestParameters.EMAIL).trim();
        final String name = req.getParameter(RequestParameters.NAME).trim();
        final String surname = req.getParameter(RequestParameters.SURNAME).trim();
        final String gender = req.getParameter(RequestParameters.GENDER).trim();
        final boolean isOverEighteen = req.getParameter(RequestParameters.OVER_EIGHTEEN_AGE) != null;
        User user =  User.getInstance(email, password, name, surname, gender, isOverEighteen);
        try {
            new Validator().validateUserData(user, req);
        } catch (ValidationException e){//todo rename validation
            user.setPassword(null);
            req.getSession().setAttribute(USER_IN_LOGGING, user);
            throw new ValidationException();
        }
        return user;
    }

    private void encryptUserPassword(User user) {
        try {
            String password = user.getPassword();
            user.setPassword(PassEncryptor.encrypt(password));
        } catch (GeneralSecurityException e) {
            log.error("Failed to encrypt password. ", e);
            //todo redirect to error page...
        }
    }
}