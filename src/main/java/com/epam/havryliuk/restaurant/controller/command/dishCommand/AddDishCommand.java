package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.dispatchers.ImageDispatcher;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.mapper.DishMapper;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

public class AddDishCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddDishCommand.class);

    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public AddDishCommand() {
        dishService = ApplicationProcessor.getInstance(DishService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.trace("AddDishCommand.");
        HttpSession session = request.getSession();
        String redirectionPage;
        try {
            ImageDispatcher imageDispatcher =
                    new ImageDispatcher(request, RequestParameters.DISH_IMAGE, AppPagesPath.DISH_IMAGE_PATH);
            Dish dish = DishMapper.mapDish(request, imageDispatcher.getImageFileName());
            session.setAttribute(CURRENT_DISH, dish);
            if (Validator.isCreatingDishDataValid(dish, imageDispatcher, request)) {
                saveDish(dish, imageDispatcher, session);
                redirectionPage = AppPagesPath.REDIRECT_MENU;
            } else {
                redirectionPage = AppPagesPath.REDIRECT_ADD_DISH_PAGE;
            }
        } catch (DuplicatedEntityException e) {
            redirectionPage = AppPagesPath.REDIRECT_ADD_DISH_PAGE;
            MessageDispatcher.setToSession(request, WRONG_DISH_FIELD_MESSAGE, ResponseMessages.SUCH_DISH_NAME_EXISTS);
            LOG.debug(e);
        } catch (ServiceException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.GLOBAL_ERROR);
            redirectionPage = AppPagesPath.REDIRECT_ERROR;
            LOG.error(e);
        }
        response.sendRedirect(redirectionPage);
    }

    private void saveDish(Dish dish, ImageDispatcher imageDispatcher, HttpSession session)
            throws ServiceException, IOException {
        dishService.addNewDish(dish);
        saveImage(imageDispatcher);
        manageAttributes(session);
        LOG.debug("List of dishes received by servlet and going to be sending to client side.");
    }

    private void saveImage(ImageDispatcher imageDispatcher) throws IOException {
        InputStream is = imageDispatcher.getPart().getInputStream();
        Files.copy(is, Paths.get(imageDispatcher.getRealPath()), StandardCopyOption.REPLACE_EXISTING);
    }

    private void manageAttributes(HttpSession session) {
        session.removeAttribute(CURRENT_DISH);
        session.removeAttribute(WRONG_DISH_FIELD_MESSAGE);
        session.removeAttribute(ERROR_MESSAGE);
    }

}