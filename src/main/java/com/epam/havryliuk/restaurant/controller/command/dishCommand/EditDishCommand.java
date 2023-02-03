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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ORDER_MESSAGE;

public class EditDishCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditDishCommand.class);

    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public EditDishCommand() {
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
            Dish dish = getCurrentDish(request);

            updateDishData(request, dish);
        //todo validate
            dishService.updateDish(dish);
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

    private void updateDishData(HttpServletRequest request, Dish dish) {
        dish.setName(request.getParameter(RequestParameters.DISH_NAME));
        dish.setDescription(request.getParameter(RequestParameters.DISH_DESCRIPTION));
        dish.setWeight(Integer.parseInt(request.getParameter(RequestParameters.DISH_WEIGHT)));
        dish.setAmount(Integer.parseInt(request.getParameter(RequestParameters.DISH_AMOUNT)));
        String priceStr = request.getParameter(RequestParameters.DISH_PRICE).replaceAll(",", ".");
        dish.setPrice(BigDecimal.valueOf(Double.parseDouble(priceStr)));
        dish.setAlcohol(request.getParameter(RequestParameters.DISH_ALCOHOL) != null);
        dish.setSpecial(request.getParameter(RequestParameters.DISH_SPECIAL) != null);
        dish.setCategory(Category.valueOf(request.getParameter(RequestParameters.DISH_CATEGORY).toUpperCase()));
    }



    /**
     * Method obtains a dish saved in session. If there is no dish in it, the ServiceException will be
     * thrown and the corresponding message will be set to session for informing user. Dish had to be set
     * in session while user press "Order" button and performing "show_dish_info" command preceding the current
     * command.
     * @return Dish that need to be saved to order.
     * @throws ServiceException if there is no Dish present in session.
     */
    private Dish getCurrentDish(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        Dish dish = (Dish) session.getAttribute(CURRENT_DISH);
        if (dish == null) {
            BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
            session.setAttribute(ORDER_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.ORDER_DISH_NOT_FOUND));
            LOG.error(ResponseMessages.ORDER_DISH_NOT_FOUND);
            throw new ServiceException(ResponseMessages.ORDER_DISH_NOT_FOUND);
        }
        return dish;
    }


}