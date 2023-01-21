package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.IndexCommand;
import com.epam.havryliuk.restaurant.model.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;

public class CommandFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new IndexCommand();
        String referer = request.getRequestURI();
        String action = referer.substring(referer.lastIndexOf('/') + 1);

        if (action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + MessageManager.EN.getProperty("message.wrongAction"));//todo
        }
        return current;
    }
}
