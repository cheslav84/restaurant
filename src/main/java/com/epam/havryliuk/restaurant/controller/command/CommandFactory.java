package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.IndexCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {
    private static final Logger LOG = LogManager.getLogger(CommandFactory.class);

    public Command defineCommand(HttpServletRequest request) {
        Command defaultCommand = new IndexCommand();
        String referer = request.getRequestURI();
        String action = referer.substring(referer.lastIndexOf('/') + 1);
        LOG.debug(action + " action was received.");
        if (action.isEmpty()) {
            return defaultCommand;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            defaultCommand = currentEnum.getCurrentCommand();
            LOG.debug(action.toUpperCase() + " command is going to be performed.");
        } catch (IllegalArgumentException e) {
            LOG.error("Wrong action.", e);
            request.setAttribute(RequestAttributes.WRONG_ACTION, action
                    + BundleManager.EN.getProperty(ResponseMessages.GLOBAL_ERROR));
        }
        return defaultCommand;
    }
}
