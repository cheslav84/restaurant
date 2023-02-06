package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Category;
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
import java.math.BigDecimal;
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
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        dishService = appContext.getInstance(DishService.class);
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
            Dish dish = mapDish(request, imageFileName);
            session.setAttribute(CURRENT_DISH, dish);

            String realPath = request.getServletContext()
                    .getRealPath(AppPagesPath.DISH_IMAGE_PATH + imageFileName);
            if (!Validator.isDishDataValid(dish, request, realPath)) {
                redirectionPage = AppPagesPath.REDIRECT_ADD_DISH_PAGE;
            } else {
                dishService.addNewDish(dish);
                InputStream is = part.getInputStream();
                Files.copy(is, Paths.get(realPath), StandardCopyOption.REPLACE_EXISTING);
                session.removeAttribute(CURRENT_DISH);
                session.removeAttribute(WRONG_DISH_FIELD_MESSAGE);
                session.removeAttribute(ERROR_MESSAGE);
                redirectionPage = AppPagesPath.REDIRECT_MENU;
                LOG.debug("List of dishes received by servlet and going to be sending to client side.");
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


    private Dish mapDish(HttpServletRequest request, String imageFileName) {
        String name = request.getParameter(RequestParameters.DISH_NAME);
        String description = request.getParameter(RequestParameters.DISH_DESCRIPTION);
        boolean alcohol = request.getParameter(RequestParameters.DISH_ALCOHOL) != null;
        boolean special = request.getParameter(RequestParameters.DISH_SPECIAL) != null;
        Category category = null;
        int weight = 0;
        BigDecimal price = BigDecimal.valueOf(0);
        try {
            weight = Integer.parseInt(request.getParameter(RequestParameters.DISH_WEIGHT));
        } catch (IllegalArgumentException e) {
            LOG.info("The weight received from user Dish data isn't correct.");
        }
        try {
            String priceStr = request.getParameter(RequestParameters.DISH_PRICE).replaceAll(",", ".");
            price = BigDecimal.valueOf(Double.parseDouble(priceStr));
        } catch (IllegalArgumentException e) {
            LOG.info("The price received from user Dish data isn't correct.");
        }
        try {
            category = Category.valueOf(request.getParameter(RequestParameters.DISH_CATEGORY).toUpperCase());
        } catch (IllegalArgumentException e) {
            LOG.info("User has not chose Dish category.");
        }
        return Dish.getInstance(name, description, weight, price, imageFileName, alcohol, special, category);
    }

}