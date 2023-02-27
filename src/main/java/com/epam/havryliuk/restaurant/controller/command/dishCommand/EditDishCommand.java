package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.dispatchers.DishDispatcher;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.mapper.DishMapper;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationProcessor;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

public class EditDishCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditDishCommand.class);

    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public EditDishCommand() {
        dishService = ApplicationProcessor.getInstance(DishService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.trace("EditDishCommand.");
        HttpSession session = request.getSession();
        String redirectionPage = AppPagesPath.REDIRECT_MENU;
        try {
            Dish dish = DishDispatcher.getCurrentDish(request);
            Dish newDish = DishMapper.mapDish(request, dish.getImage());
            newDish.setId(dish.getId());
            if (Validator.isEditingDishDataValid(newDish, request)) {
                dishService.updateDish(newDish);
                session.removeAttribute(ERROR_MESSAGE);
                LOG.debug("List of dishes received by servlet and going to be sending to client side.");
            } else {
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            }
        } catch (ServiceException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.GLOBAL_ERROR);
            redirectionPage = AppPagesPath.REDIRECT_ERROR;
            LOG.error(e);
        }
        response.sendRedirect(redirectionPage);
    }

}