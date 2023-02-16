package com.epam.havryliuk.restaurant.model.entityMappers;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.database.databaseFieds.DishFields;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DishMapper {
    private static final Logger LOG = LogManager.getLogger(DishMapper.class);


    public static synchronized Dish mapDish(ResultSet rs) throws SQLException {
        long id = rs.getLong(DishFields.DISH_ID);
        String name = rs.getString(DishFields.DISH_NAME);
        String description = rs.getString(DishFields.DISH_DESCRIPTION);
        int weight = rs.getInt(DishFields.DISH_WEIGHT);
        BigDecimal price = rs.getBigDecimal(DishFields.DISH_PRICE);
        int amount = rs.getInt(DishFields.DISH_AMOUNT);
        String image = rs.getString(DishFields.DISH_IMAGE);
        boolean alcohol = rs.getBoolean(DishFields.DISH_ALCOHOL);
        return Dish.getInstance(id, name, description, weight, price, amount, image, alcohol);
    }

    public synchronized static Dish mapDish(HttpServletRequest request, String imageFileName) {
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
