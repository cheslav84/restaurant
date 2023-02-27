package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.controller.dispatchers.URLDispatcher;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

//todo edit description
/**
 * Command displays to user order info panel on his click to "order" button
 * with following information: dish details, fields for entering amount of dishes,
 * delivery address and phone with buttons that sends dish to basket on press of them.
 */
public class DishInfoCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(DishInfoCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public DishInfoCommand() {
        dishService = ApplicationProcessor.getInstance(DishService.class);
    }
//todo edit description
    /**
     * Method executes command that receives dish by its id, sets it to HttpSession, and set to session
     * attribute flag that informs user page to show ordering menu of concrete dish. If, on some reason,
     * ServiceException occurs while getting a Dish, user will be informed with correspondent message.
     * All above can be done from several places, and which of pages is going to be redirected to, decides
     * "getRefererPage" method on "URLDispatcher" class. When user got logged out, after some period of time doing
     * nothing, execution this command will lead him to the login page.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.trace("DishInfoCommand.");
        long dishId = Long.parseLong(request.getParameter(RequestParameters.DISH_ID));
        LOG.debug("\"/dishId\" {}} has been received from user.", dishId);
        Dish dish;
        try {
            dish = dishService.getDish(dishId);
            manageAttributes(request, dish);
        } catch (ServiceException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.DISH_IN_MENU_NOT_FOUND);
            LOG.error(e);
        }
        response.sendRedirect(getRedirectingPage(request));
    }

    private String getRedirectingPage(HttpServletRequest request) {
        return request.getSession().getAttribute(LOGGED_USER) != null
                ? URLDispatcher.getRefererPage(request)
                : AppPagesPath.FORWARD_REGISTRATION;
    }

    private void manageAttributes(HttpServletRequest request, Dish dish) {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_DISH, dish);
        session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
        session.removeAttribute(ORDER_MESSAGE);
    }
}