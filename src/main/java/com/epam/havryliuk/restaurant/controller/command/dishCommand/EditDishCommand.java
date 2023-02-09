package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.requestMapper.DishRequestMapper;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ORDER_MESSAGE;

public class EditDishCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditDishCommand.class);

    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public EditDishCommand() {
        dishService = ApplicationServiceContext.getInstance(DishService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        BundleManager bundleManager = BundleManager.valueOf(((Locale) request.getSession()
                .getAttribute(LOCALE)).getCountry());
        HttpSession session = request.getSession();
        String redirectionPage = AppPagesPath.REDIRECT_MENU;
        try {
            Dish dish = getCurrentDish(session);
            dish = DishRequestMapper.mapDish(request, dish.getImage());
            if (Validator.isDishEditingDataValid(dish, session, bundleManager)) {
                dishService.updateDish(dish);
                session.removeAttribute(ERROR_MESSAGE);
                LOG.debug("List of dishes received by servlet and going to be sending to client side.");
            } else {
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            }
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.GLOBAL_ERROR));
            redirectionPage = AppPagesPath.REDIRECT_ERROR;
            LOG.error(e);
        }
        response.sendRedirect(redirectionPage);
    }


    /**
     * Method obtains a dish saved in session. If there is no dish in it, the ServiceException will be
     * thrown and the corresponding message will be set to session for informing user. Dish had to be set
     * in session while user press "Order" button and performing "show_dish_info" command preceding the current
     * command.
     * @return Dish that need to be saved to order.
     * @throws ServiceException if there is no Dish present in session.
     */
    private Dish getCurrentDish(HttpSession session) throws ServiceException {
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