package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.model.resource.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EmptyCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(ConfigurationManager.getProperty("path.page.index"));
    }
}