package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.requestMapper.DishRequestMapper;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class AddDishCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddDishCommand.class);

    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public AddDishCommand() {
        dishService = ApplicationServiceContext.getInstance(DishService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        BundleManager bundleManager = BundleManager.valueOf(((Locale) request.getSession()
                .getAttribute(LOCALE)).getCountry());
        HttpSession session = request.getSession();
        String redirectionPage;
        try {
            Part part = request.getPart(RequestParameters.DISH_IMAGE);
            String imageFileName = part.getSubmittedFileName();
            Dish dish = DishRequestMapper.mapDish(request, imageFileName);
            session.setAttribute(CURRENT_DISH, dish);

            String realPath = request.getServletContext()
                    .getRealPath(AppPagesPath.DISH_IMAGE_PATH + imageFileName);
            if (Validator.isDishCreatingDataValid(dish, realPath, session, bundleManager)) {
                dishService.addNewDish(dish);
                InputStream is = part.getInputStream();
                Files.copy(is, Paths.get(realPath), StandardCopyOption.REPLACE_EXISTING);
                session.removeAttribute(CURRENT_DISH);
                session.removeAttribute(WRONG_DISH_FIELD_MESSAGE);
                session.removeAttribute(ERROR_MESSAGE);
                redirectionPage = AppPagesPath.REDIRECT_MENU;
                LOG.debug("List of dishes received by servlet and going to be sending to client side.");
            } else {
                redirectionPage = AppPagesPath.REDIRECT_ADD_DISH_PAGE;
            }
        } catch (DuplicatedEntityException e) {
            redirectionPage = AppPagesPath.REDIRECT_ADD_DISH_PAGE;
            session.setAttribute(WRONG_DISH_FIELD_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.SUCH_DISH_NAME_EXISTS));
            LOG.info(e);
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.GLOBAL_ERROR));
            redirectionPage = AppPagesPath.REDIRECT_ERROR;
            LOG.error(e);
        }
        response.sendRedirect(redirectionPage);
    }

}