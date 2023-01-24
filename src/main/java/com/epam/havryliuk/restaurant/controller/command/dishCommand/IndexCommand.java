package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.responseManager.MenuResponseManager;
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

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class IndexCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(IndexCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private MenuResponseManager menuResponseManager;
    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;
    public IndexCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        dishService = appContext.getInstance(DishService.class);
        menuResponseManager = new MenuResponseManager();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Category currentMenu = menuResponseManager.getCurrentMenu(request);
        MessageManager messageManager = MessageManager.valueOf((String) request.getSession().getAttribute(LOCALE));
        List<Dish> dishes = null;
        try {
            dishes = dishService.getMenuByCategory(currentMenu);
            if (dishes.isEmpty()) {
                request.setAttribute(MENU_MESSAGE,
                        messageManager.getProperty(ResponseMessages.MENU_EMPTY));
            }
            LOG.debug("List of dishes received by servlet and going to be sending to client side.");
        } catch (ServiceException e) {
            request.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.MENU_UNAVAILABLE));
            LOG.error(e);
        }
        menuResponseManager.setOrderInfoAttribute(request);
        request.setAttribute(DISH_LIST, dishes);
        request.getRequestDispatcher(AppPagesPath.FORWARD_INDEX).forward(request, response);
    }

}