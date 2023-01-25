package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class LogoutCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

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