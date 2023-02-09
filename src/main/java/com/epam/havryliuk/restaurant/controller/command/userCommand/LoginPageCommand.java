package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.paths.AppPagesPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Command forwards to "Login" page while User press the login menu button.
 */
public class LoginPageCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginPageCommand.class);

    /**
     * Method executes the command that forward user to "Login" page on correspondent button click.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Login page request.");
        request.getRequestDispatcher(AppPagesPath.FORWARD_REGISTRATION).forward(request, response);
    }
}