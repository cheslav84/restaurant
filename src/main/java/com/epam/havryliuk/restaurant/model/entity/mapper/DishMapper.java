package com.epam.havryliuk.restaurant.model.entity.mapper;

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


    public static Dish mapDish(ResultSet rs) throws SQLException {
        long id = rs.getLong(DishFields.DISH_ID);
        String name = rs.getString(DishFields.DISH_NAME);
        String description = rs.getString(DishFields.DISH_DESCRIPTION);
        int weight = rs.getInt(DishFields.DISH_WEIGHT);
        BigDecimal price = rs.getBigDecimal(DishFields.DISH_PRICE);
        int amount = rs.getInt(DishFields.DISH_AMOUNT);
        String image = rs.getString(DishFields.DISH_IMAGE);
        boolean alcohol = rs.getBoolean(DishFields.DISH_ALCOHOL);
        return new Dish.DishBuilder()
                .withId(id)
                .withName(name)
                .withDescription(description)
                .withWeight(weight)
                .withPrice(price)
                .withImage(image)
                .withAlcohol(alcohol)
                .withAmount(amount)
                .build();
    }

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
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.debug("The weight received from user Dish data isn't correct.");
        }
        try {
            amount = Integer.parseInt(request.getParameter(RequestParameters.DISH_AMOUNT).trim());
        } catch (IllegalArgumentException | NullPointerException  e) {
            LOG.debug("The amount of dishes will not be set.");
        }
        try {
            String priceStr = request.getParameter(RequestParameters.DISH_PRICE)
                    .replaceAll(",", ".").trim();
            price = BigDecimal.valueOf(Double.parseDouble(priceStr));
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.debug("The price received from user Dish data isn't correct.");
        }
        try {
            category = Category.valueOf(request.getParameter(RequestParameters.DISH_CATEGORY).toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            LOG.debug("User has not chose Dish category.");
        }
        return new Dish.DishBuilder()
                .withName(name)
                .withDescription(description)
                .withWeight(weight)
                .withPrice(price)
                .withImage(imageFileName)
                .withAlcohol(alcohol)
                .withAmount(amount)
                .withSpecial(special)
                .withCategory(category)
                .build();
    }


}
