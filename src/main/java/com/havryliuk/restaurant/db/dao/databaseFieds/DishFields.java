package com.havryliuk.restaurant.db.dao.databaseFieds;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class DishFields {
    private static final Logger log = LogManager.getLogger(DishFields.class);

    private static final Properties properties;
    public static String DISH_ID;
    public static String DISH_NAME;
    public static String DISH_DESCRIPTION;
    public static String DISH_WEIGHT;
    public static String DISH_PRICE;
    public static String DISH_AMOUNT;
    public static String DISH_SPECIAL;
    public static String DISH_IMAGE;
    public static String DISH_CATEGORY_ID;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_FIELDS_SETTING_FILE);
        initialiseVariable();
        log.debug("Database fields for \"Dish\" table have been initialised successfully.");
    }

    private static void initialiseVariable() {
        DISH_ID = (String) properties.get("dish.id");
        DISH_NAME = (String) properties.get("dish.name");
        DISH_DESCRIPTION = (String) properties.get("dish.description");
        DISH_WEIGHT = (String) properties.get("dish.weight");
        DISH_PRICE = (String) properties.get("dish.price");
        DISH_AMOUNT = (String) properties.get("dish.amount");
        DISH_SPECIAL = (String) properties.get("dish.special");
        DISH_IMAGE = (String) properties.get("dish.image");
        DISH_CATEGORY_ID = (String) properties.get("dish.categoryId");
    }

}
