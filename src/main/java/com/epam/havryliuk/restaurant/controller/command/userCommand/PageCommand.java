package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

public class PageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(PageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Optional<String> requestedPath = Optional.of(request.getParameter("path"));
        String path = "/WEB-INF/jsp/" + requestedPath.orElse("index") + ".jsp";//todo move to constants
        log.debug("Going move to: " + path);
        request.getRequestDispatcher(path).forward(request, response);
    }

}