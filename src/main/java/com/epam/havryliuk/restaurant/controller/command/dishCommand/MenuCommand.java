package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.responseManager.MenuResponseManager;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.MessageManager;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class MenuCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(MenuCommand.class);
    private static final String DEFAULT_SORTING = "name";
    private MenuResponseManager menuResponseManager;
    private DishService dishService;

    public MenuCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        dishService = appContext.getInstance(DishService.class);
        menuResponseManager = new MenuResponseManager();
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Category currentMenu = menuResponseManager.getCurrentMenu(request);
        MessageManager messageManager = MessageManager.valueOf((String) request.getSession().getAttribute(LOCALE));
        List<Dish> dishes = null;
        List<Dish> specials = null;
        try {
            if (currentMenu.equals(Category.ALL)) {
                String sortParameter = getSortParameter(request);
                dishes = dishService.getAllMenuSortedBy(sortParameter);
            } else {
                dishes = dishService.getMenuByCategory(menuResponseManager.getCurrentMenu(request));
            }
            if (dishes.isEmpty()) {
                request.setAttribute(MENU_MESSAGE,
                        messageManager.getProperty(ResponseMessages.MENU_EMPTY));
            }
            specials = dishService.getMenuByCategory(Category.SPECIALS);
            LOG.debug("List of dishes received by servlet and going to be sending to client side.");
        } catch (ServiceException e) {
            request.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.MENU_UNAVAILABLE));
            LOG.error(e);
        }
        menuResponseManager.setOrderInfoAttribute(request);
        request.setAttribute(DISH_LIST, dishes);
        request.setAttribute(SPECIALS_DISH_LIST, specials);
        request.getRequestDispatcher(AppPagesPath.FORWARD_MENU_PAGE).forward(request, response);
    }

    private String getSortParameter(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter(RequestParameters.MENU_SORTING_OPTION)).orElse(DEFAULT_SORTING);
    }

}