package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;

import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.service.UserService;

import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

/**
 * Command that validates user data, encrypts user password and saves that user in storage
 * in case that all data are correct, or processes registration error if any occurs.
 */
public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private UserService userService;

    public RegisterCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        userService = appContext.getInstance(UserService.class);
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
        HttpSession session = request.getSession();
        String redirectionPage;
        BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
        User user;
        try {
            user = mapUser(request);
            encryptUserPassword(user);
            userService.addNewUser(user);
            session.setAttribute(LOGGED_USER, user);
            //        Cookie cookie = new Cookie("sessionId", session.getId());
            //        resp.addCookie(cookie);
            session.removeAttribute(REGISTRATION_ERROR_MESSAGE);
            session.removeAttribute(USER_IN_LOGGING);
            session.removeAttribute(REGISTRATION_PROCESS);
            redirectionPage = AppPagesPath.REDIRECT_INDEX;
            LOG.info("The user \"" + user.getName() + "\" has been successfully registered.");
        } catch (ValidationException e) {
            LOG.error("Some credentials are not correct." + e);
            redirectionPage = getRegistrationPage(session);
        } catch (DuplicatedEntityException e) {
            LOG.error("The user with such login is already exists. Try to use another one." + e);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.REGISTRATION_USER_EXISTS));
            redirectionPage = getRegistrationPage(session);
        } catch (ServiceException e) {
            LOG.error("User hasn't been registered. " + e);
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.REGISTRATION_ERROR));
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

//    private String getRedirectionPage(HttpSession session) {
//        String pageFromBeingRedirected = (String) session.getAttribute(PAGE_FROM_BEING_REDIRECTED);//todo set from security filter
//        String redirectionPage;
//        if (pageFromBeingRedirected != null) {
//            redirectionPage = pageFromBeingRedirected;
//        } else {
//            redirectionPage = AppPagesPath.REDIRECT_INDEX;
//        }
//        session.removeAttribute(PAGE_FROM_BEING_REDIRECTED);
//        return redirectionPage;
//    }

    /**
     * Method maps User from data entered by User During registration process, and validates that data.
     * If some data is invalid, method gets ValidationException, removes User password and sets that User
     * to session under the flag "loggingUser", and throws ValidationException again to the method above.
     * The User that is set to Session while ValidationException occurs, can contain in his Entity fields
     * messages of incorrect data. The fields that is valid will be displayed in proper input page fields
     * preventing user to enter correct data again.
     * @return correct User mapped from data entered by user.
     * @throws ValidationException when some data entered by user is incorrect.
     */
    private User mapUser(HttpServletRequest req) throws ValidationException {//todo можливо винести метод, подумати...
        final String password = req.getParameter(RequestParameters.PASSWORD);
        final String email = req.getParameter(RequestParameters.EMAIL).trim();
        final String name = req.getParameter(RequestParameters.NAME).trim();
        final String surname = req.getParameter(RequestParameters.SURNAME).trim();
        final String gender = req.getParameter(RequestParameters.GENDER).trim();
        final boolean isOverEighteen = req.getParameter(RequestParameters.OVER_EIGHTEEN_AGE) != null;
        User user = User.getInstance(email, password, name, surname, gender, isOverEighteen);
        user.setRole(Role.CLIENT);
        try {
            Validator.validateUserData(user, req);
//            new Validator().validateUserData(user, req);
        } catch (ValidationException e){
            user.setPassword(null);
            req.getSession().setAttribute(USER_IN_LOGGING, user);
            throw new ValidationException();
        }
        return user;
    }

    /**
     * Method gets raw password from User, encrypts it and sets it back to user.
     * If on some reason password isn't encrypted, ServiceException will be thrown.
     * @param user whose password should be encrypted.
     */
    private void encryptUserPassword(User user) throws ServiceException {
        try {
            String password = user.getPassword();
            user.setPassword(PassEncryptor.encrypt(password));
        } catch (GeneralSecurityException e) {
            String errorMessage = "Failed to encrypt password.";
            LOG.error(errorMessage, e);
            throw new ServiceException(errorMessage, e);
        }
    }
}