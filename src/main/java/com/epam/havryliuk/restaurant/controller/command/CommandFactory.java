package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Choose the necessary command to execution depends on received user request.
 */
public class CommandFactory {
    private static final Logger LOG = LogManager.getLogger(CommandFactory.class);
    private Command defaultCommand = CommandsHolder.valueOf("INDEX").getCurrentCommand();


    /**
     * Method obtains the representation of command in String as the last past of
     * request URL, then converts it to the Command instance that has to be performed.
     * If method receives the empty part of URL, the default Command will be assigned.
     * In case of wrong command that is not in the Enum list, method set in session
     * message about wrong action and throws IllegalArgumentException.
     * @param request HttpServletRequest user side.
     * @return Command instance that has to be performed.
     * @throws IllegalArgumentException when the command has to be executed doesn't exist.
     */
    public Command defineCommand(HttpServletRequest request) {
        LOG.trace("CommandFactory. DefineCommand.");
        String requestURI = request.getRequestURI();
        String command = requestURI.substring(requestURI.lastIndexOf('/') + 1);
        LOG.debug("{} action was received.", command);
        if (command.equals(requestURI.substring(requestURI.indexOf('/') + 1))) {
            return defaultCommand;
        }
        try {
            CommandsHolder currentEnum = CommandsHolder.valueOf(command.toUpperCase());
            defaultCommand = currentEnum.getCurrentCommand();
            LOG.debug( "{} command is going to be performed.", command.toUpperCase());
        } catch (IllegalArgumentException e) {
            MessageDispatcher.setToRequest(request, RequestAttributes.WRONG_ACTION, ResponseMessages.GLOBAL_ERROR);
            LOG.error("Wrong action.", e);
            throw e;
        }
        return defaultCommand;
    }

}
