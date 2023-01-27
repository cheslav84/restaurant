package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.IndexCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.LOCALE;

/**
 * Choose the necessary command to execution depends on received user request.
 */
public class CommandFactory {
    private static final Logger LOG = LogManager.getLogger(CommandFactory.class);
    private Command defaultCommand = new IndexCommand();

    /**
     * Method obtains the representation of command in String as the last past of
     * request URL, then converts it to the Command instance that has to be performed.
     * If method receives the empty part of URL, the default Command will be assigned.
     * In case of wrong command that is not in the Enum list, method set in session
     * message about wrong action and throws IllegalArgumentException.
     *
     * @param request HttpServletRequest user side.
     * @return Command instance that has to be performed.
     * @throws IllegalArgumentException when the command has to be executed doesn't exist.
     */
    public Command defineCommand(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String command = requestURI.substring(requestURI.lastIndexOf('/') + 1);
        LOG.debug(command + " action was received.");
        if (command.equals(requestURI.substring(requestURI.indexOf('/') + 1))) {
            return defaultCommand;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(command.toUpperCase());
            defaultCommand = currentEnum.getCurrentCommand();
            LOG.debug(command.toUpperCase() + " command is going to be performed.");
        } catch (IllegalArgumentException e) {
            LOG.error("Wrong action.", e);
            BundleManager bundleManager = BundleManager.valueOf((
                    (Locale) request.getSession().getAttribute(LOCALE)).getCountry());
            request.setAttribute(RequestAttributes.WRONG_ACTION, command
                    + bundleManager.getProperty(ResponseMessages.GLOBAL_ERROR));
            throw e;
        }
        return defaultCommand;
    }
}
