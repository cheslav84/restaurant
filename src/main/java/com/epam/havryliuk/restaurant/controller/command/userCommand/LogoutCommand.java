package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.resource.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(ConfigurationManager.getProperty(AppPagesPath.INDEX));
    }
}