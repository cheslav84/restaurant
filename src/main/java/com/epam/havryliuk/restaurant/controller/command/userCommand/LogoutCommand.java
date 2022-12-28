package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LogoutCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("LogoutCommand");
        request.getSession().invalidate();
        response.sendRedirect(AppPagesPath.REDIRECT_INDEX);
    }
}