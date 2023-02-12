package com.epam.havryliuk.restaurant.model.database.databaseFieds;

import com.epam.havryliuk.restaurant.model.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class DishFields {
    private static final Logger LOG = LogManager.getLogger(DishFields.class);
    private static final Properties PROPERTIES;
    public static final String DISH_ID;
    public static final String DISH_NAME;
    public static final String DISH_DESCRIPTION;
    public static final String DISH_WEIGHT;
    public static final String DISH_PRICE;
    public static final String DISH_AMOUNT;
    public static final String DISH_SPECIAL;
    public static final String DISH_IMAGE;
    public static final String DISH_ALCOHOL;
    public static final String DISH_CATEGORY_ID;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_FIELDS_SETTING_FILE);
        DISH_ID = (String) PROPERTIES.get("dish.id");
        DISH_NAME = (String) PROPERTIES.get("dish.name");
        DISH_DESCRIPTION = (String) PROPERTIES.get("dish.description");
        DISH_WEIGHT = (String) PROPERTIES.get("dish.weight");
        DISH_PRICE = (String) PROPERTIES.get("dish.price");
        DISH_AMOUNT = (String) PROPERTIES.get("dish.amount");
        DISH_SPECIAL = (String) PROPERTIES.get("dish.special");
        DISH_IMAGE = (String) PROPERTIES.get("dish.image");
        DISH_ALCOHOL = (String) PROPERTIES.get("dish.spirits");
        DISH_CATEGORY_ID = (String) PROPERTIES.get("dish.categoryId");
        LOG.debug("Database fields for \"Dish\" table have been initialised successfully.");
    }

}
