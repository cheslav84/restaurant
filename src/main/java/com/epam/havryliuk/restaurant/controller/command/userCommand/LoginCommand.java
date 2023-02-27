package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.UserService;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command that verifies user and redirects him to the "Index" page if
 * entered email/login and password are correct.
 */
public class LoginCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private UserService userService;

    public LoginCommand() {
        userService = ApplicationProcessor.getInstance(UserService.class);
    }

    /**
     * Method obtains User email and password from HttpServletRequest. Then, receives User from storage
     * by User email. If password that User entered coincides the password in storage, saves User
     * in HttpSession and redirects him to the "Index" Page.
     * If entered email is absent it the storage, method get EntityNotFoundException and user will be
     * informed that "User with such email doesn't exist".
     * If entered password doesn't coincide with the password in the storage, then GeneralSecurityException
     * method catch and user will receive message that entered password is wrong.
     * In case of ServiceException, User will be redirected to "Error" page.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.trace("LoginCommand.");
        String email = request.getParameter(RequestParameters.EMAIL);
        String password = request.getParameter(RequestParameters.PASSWORD);
        HttpSession session = request.getSession();
        String page;
        try {
            User user = userService.getUserFromDatabase(email);
            PassEncryptor.verify(user.getPassword(), password);
            session.setAttribute(LOGGED_USER, user);
            session.removeAttribute(ERROR_MESSAGE);
            page = AppPagesPath.REDIRECT_INDEX;
            LOG.info("User \"{}\" logged in.", user.getEmail());
        } catch (EntityNotFoundException e) {
            page = AppPagesPath.REDIRECT_REGISTRATION;
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.LOGIN_ERROR);
            LOG.debug(e.getMessage());
        } catch (GeneralSecurityException e) {
            page = AppPagesPath.REDIRECT_REGISTRATION;
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.PASSWORD_ERROR);
            LOG.debug(e.getMessage());
        } catch (ServiceException e) {
            page = AppPagesPath.REDIRECT_ERROR;
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.GLOBAL_ERROR);
            LOG.error(e.getMessage());
        }
        response.sendRedirect(page);
    }
}