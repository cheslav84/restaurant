package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.resource.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LogoutCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("log out command");

        request.getSession().invalidate();
//        System.err.println(AppPagesPath.FORWARD_INDEX);
//        System.err.println(AppPagesPath.REDIRECT_INDEX);
        response.sendRedirect("index");

//        response.sendRedirect(ConfigurationManager.getProperty(AppPagesPath.REDIRECT_INDEX));
    }
}