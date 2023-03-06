package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.entity.mapper.UserMapper;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.UserService;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command that validates user data, encrypts user password and saves that user in storage
 * in case that all data are correct, or processes registration error if any occurs.
 */
public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private UserService userService;

    public RegisterCommand() {
        userService = ApplicationProcessor.getInstance(UserService.class);
    }

    /**
     * Executes the command that receives registration data from user, maps User Entity from
     * that data, encrypts the password and adds the user to storage.
     * If all described above are successfully done, then User will be set to HttpSession and
     * will be removed redundant attributes from it.
     * If ValidationException (some data is not valid) or DuplicatedEntityException (User with
     * such email is already exists in storage) have been caught by method, response will
     * redirect to the same registration page and registration part of page will be shown to user.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.trace("RegisterCommand.");
        HttpSession session = request.getSession();
        String redirectionPage;
        User user;
        try {
            user = UserMapper.mapUser(request);
            if (Validator.validateUserData(user, request)){
                encryptUserPassword(user);
                userService.addNewUser(user);
                session.setAttribute(LOGGED_USER, user);
                session.removeAttribute(REGISTRATION_ERROR_MESSAGE);
                session.removeAttribute(USER_IN_LOGGING);
                session.removeAttribute(REGISTRATION_PROCESS);
                redirectionPage = AppPagesPath.REDIRECT_INDEX;
                LOG.info("The user \"{}\" has been successfully registered.", user.getName());
            } else {
                user.setPassword(null);
                request.getSession().setAttribute(USER_IN_LOGGING, user);
                LOG.debug("Some of user data is not correct.");
                redirectionPage = getRegistrationPage(session);
            }
        } catch (DuplicatedEntityException e) {
            LOG.debug("The user with such login is already exists. Try to use another one.", e);
            MessageDispatcher.setToSession(request, REGISTRATION_ERROR_MESSAGE, ResponseMessages.REGISTRATION_USER_EXISTS);
            redirectionPage = getRegistrationPage(session);
        } catch (ServiceException e) {
            LOG.error("User hasn't been registered. ", e);
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.REGISTRATION_ERROR);
            redirectionPage = AppPagesPath.REDIRECT_ERROR;
        }
        response.sendRedirect(redirectionPage);
    }

    /**
     * Method receives the Login/Registration page, and sets to HttpSession
     * flag that registration part of that page should be displayed for user
     * (REGISTRATION_PROCESS).
     * @return String representation of registration page.
     */
    private String getRegistrationPage(HttpSession session) {
        String redirectionPage = AppPagesPath.REDIRECT_REGISTRATION;
        session.setAttribute(REGISTRATION_PROCESS, REGISTRATION_PROCESS);
        return redirectionPage;
    }

    /**
     * Method gets raw password from User, encrypts it and sets it back to user.
     * @param user whose password should be encrypted.
     */
    private void encryptUserPassword(User user) throws ServiceException {
        String password = user.getPassword();
        user.setPassword(PassEncryptor.encrypt(password));
    }
}