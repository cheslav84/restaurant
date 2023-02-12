package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command logs out User (invalidates HttpSession).
 */
public class LogoutCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

    /**
     * Method executes the "LogoutCommand", that is checks if User is present in
     * HttpSession, and if it is invalidates that session. Method also sets "Local"
     * back to Session to prevent setting locale to default after invalidating session.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(LOGGED_USER) != null) {
            Locale locale = (Locale) session.getAttribute(LOCALE);
            session.invalidate();
            request.getSession(true).setAttribute(LOCALE, locale);
            LOG.debug("User logged out.");
        }
        response.sendRedirect(AppPagesPath.REDIRECT_INDEX);
    }
}