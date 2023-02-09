package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.paths.AppPagesPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AddDishPageCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddDishPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Add dish page.");
        request.getRequestDispatcher(AppPagesPath.FORWARD_ADD_DISH_PAGE).forward(request, response);
    }
}