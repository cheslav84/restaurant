package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import jakarta.servlet.http.HttpServletRequest;

public class CommandFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("action");
        System.err.println("action++++++++++++++++++++++++" + action);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + MessageManager.EN.getProperty("message.wrongAction"));
        }
        return current;
    }
}
