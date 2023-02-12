package com.epam.havryliuk.restaurant.model.requestMapper;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class DishRequestMapper {
    private static final Logger LOG = LogManager.getLogger(DishRequestMapper.class);

    public static Dish mapDish(HttpServletRequest request, String imageFileName) {
        String name = request.getParameter(RequestParameters.DISH_NAME);
        String description = request.getParameter(RequestParameters.DISH_DESCRIPTION);
        boolean alcohol = request.getParameter(RequestParameters.DISH_ALCOHOL) != null;
        boolean special = request.getParameter(RequestParameters.DISH_SPECIAL) != null;
        Category category = null;
        int weight = 0;
        int amount = 0;
        BigDecimal price = BigDecimal.valueOf(0);
        try {
            weight = Integer.parseInt(request.getParameter(RequestParameters.DISH_WEIGHT).trim());
        } catch (IllegalArgumentException e) {
            LOG.info("The weight received from user Dish data isn't correct.");
        }
        try {
            amount = Integer.parseInt(request.getParameter(RequestParameters.DISH_AMOUNT).trim());
        } catch (IllegalArgumentException e) {
            LOG.info("The weight received from user Dish data isn't correct.");
        }
        try {
            String priceStr = request.getParameter(RequestParameters.DISH_PRICE)
                    .replaceAll(",", ".").trim();
            price = BigDecimal.valueOf(Double.parseDouble(priceStr));
        } catch (IllegalArgumentException e) {
            LOG.info("The price received from user Dish data isn't correct.");
        }
        try {
            category = Category.valueOf(request.getParameter(RequestParameters.DISH_CATEGORY).toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            LOG.info("User has not chose Dish category.");
        }
        return Dish.getInstance(name, description, weight, price, imageFileName, alcohol, amount, special, category);
    }

}
