package com.epam.havryliuk.restaurant.controller.dispatchers;

import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

public class DishDispatcher {
    private static final Logger LOG = LogManager.getLogger(DishDispatcher.class);

    /**
     * Method obtains a dish saved in session. If there is no dish in it, the ServiceException will be
     * thrown and the corresponding message will be set to session for informing user. Dish had to be set
     * in session while user press "Order" button and performing "show_dish_info" command preceding the current
     * command.
     * @return Dish that need to be saved to order.
     * @throws ServiceException if there is no Dish present in session.
     */
    public static Dish getCurrentDish(HttpServletRequest request) throws ServiceException {
        Dish dish = (Dish) request.getSession().getAttribute(CURRENT_DISH);
        if (dish == null) {
            MessageDispatcher.setToSession(request, ORDER_MESSAGE, ResponseMessages.ORDER_DISH_NOT_FOUND);
            LOG.error(ResponseMessages.ORDER_DISH_NOT_FOUND);
            throw new ServiceException(ResponseMessages.ORDER_DISH_NOT_FOUND);
        }
        return dish;
    }


    /**
     * If dishes are absent in menu Category, shows the massage about it.
     * @param dishes list of Dishes in menu Category that needs to be checked.
     */
    public static void setMessageIfDishesAbsent(HttpServletRequest request, List<Dish> dishes) {
        if (dishes.isEmpty()) {
            MessageDispatcher.setToRequest(request, MENU_MESSAGE, ResponseMessages.MENU_EMPTY);
        }
    }

}
