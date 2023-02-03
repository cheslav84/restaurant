package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BundleManager bundleManager = BundleManager.valueOf(((Locale) request.getSession().getAttribute(LOCALE)).getCountry());
        HttpSession session = request.getSession();
        String redirectionPage;
        Part part = request.getPart(RequestParameters.DISH_IMAGE);
        String imageFileName = part.getSubmittedFileName();
        String path = AppPagesPath.DISH_IMAGE_PATH + imageFileName;
        Dish dish = mapDish(request, imageFileName);
        //todo validate

        try {
            dishService.addNewDish(dish);
            path = request.getServletContext().getRealPath(path);
            InputStream is = part.getInputStream();
            Files.copy(is, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
            session.removeAttribute(ERROR_MESSAGE);
            redirectionPage = AppPagesPath.REDIRECT_INDEX;

            LOG.debug("List of dishes received by servlet and going to be sending to client side.");
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.GLOBAL_ERROR));
            redirectionPage = AppPagesPath.REDIRECT_ERROR;
            LOG.error(e);
        }
        response.sendRedirect(redirectionPage);
    }

    private Dish mapDish(HttpServletRequest request, String imageFileName) {
        final String name = request.getParameter(RequestParameters.DISH_NAME);
        final String description = request.getParameter(RequestParameters.DISH_DESCRIPTION);
        final int weight = Integer.parseInt(request.getParameter(RequestParameters.DISH_WEIGHT));
        String priceStr = request.getParameter(RequestParameters.DISH_PRICE).replaceAll(",", ".");
        final BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceStr));
        final boolean alcohol = request.getParameter(RequestParameters.DISH_ALCOHOL) != null;
        final boolean special = request.getParameter(RequestParameters.DISH_SPECIAL) != null;
        final Category category = Category.valueOf(request.getParameter(RequestParameters.DISH_CATEGORY).toUpperCase());
        return Dish.getInstance(name, description, weight,
         price, imageFileName, alcohol, special, category);
    }


}