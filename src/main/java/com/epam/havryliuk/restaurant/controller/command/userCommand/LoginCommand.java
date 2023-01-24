package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.MessageManager;
import com.epam.havryliuk.restaurant.model.service.UserService;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class LoginCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private UserService userService;

    public LoginCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        userService = appContext.getInstance(UserService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter(RequestParameters.EMAIL);//todo перейменувати скрізь на email
        String password = request.getParameter(RequestParameters.PASSWORD);
        HttpSession session = request.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
        String page;
        try {
            User user = userService.getUserFromDatabase(email);
            PassEncryptor.verify(user.getPassword(), password);
            session.setAttribute(LOGGED_USER, user);
            session.removeAttribute(ERROR_MESSAGE);
            page = AppPagesPath.REDIRECT_INDEX;
            LOG.debug("User logged in.");
        } catch (EntityNotFoundException e) {
            page = AppPagesPath.REDIRECT_REGISTRATION;
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.LOGIN_ERROR));
            LOG.error(e.getMessage());
        } catch (GeneralSecurityException e) {
            page = AppPagesPath.REDIRECT_REGISTRATION;
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.PASSWORD_ERROR));
            LOG.error(e.getMessage());
        } catch (ServiceException e) {
            page = AppPagesPath.REDIRECT_ERROR;
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.GLOBAL_ERROR));
            LOG.error(e.getMessage());
        }
        response.sendRedirect(page);
    }
}