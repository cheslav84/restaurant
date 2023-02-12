package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.controller.responseDispatcher.DishDispatcher;
import com.epam.havryliuk.restaurant.controller.responseDispatcher.MenuDispatcher;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.responseDispatcher.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command to show the welcome page and list of dishes in it.
 */
public class IndexCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(IndexCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private MenuDispatcher menuDispatcher;
    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public IndexCommand() {
        dishService = ApplicationServiceContext.getInstance(DishService.class);
        menuDispatcher = new MenuDispatcher();
    }

    /**
     * Method executes the "index" command, it gets list of dishes that is dependent on
     * Category (menu) which user asks, and sets that list as attribute to HttpServletRequest.
     * If dishes list is empty then correspondent message will be showed to user. Method also
     * asks the Response Manager to show or hide part of html code in index page that allows
     * to display dish order information on that page.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Category currentMenu = menuDispatcher.getCurrentMenu(request);
        User user = (User) request.getSession().getAttribute(LOGGED_USER);
        List<Dish> dishes = null;
        try {
            dishes = dishService.getMenuByCategory(currentMenu, user);
            DishDispatcher.setMessageIfDishesAbsent(request, dishes);
            LOG.debug("List of dishes received by servlet and going to be sending to client side.");
        } catch (ServiceException e) {
            MessageDispatcher.setToRequest(request, ERROR_MESSAGE, ResponseMessages.MENU_UNAVAILABLE);
            LOG.error(e);
        }
        menuDispatcher.setOrderInfoAttribute(request);
        request.setAttribute(DISH_LIST, dishes);
        request.getRequestDispatcher(AppPagesPath.FORWARD_INDEX).forward(request, response);
    }

}