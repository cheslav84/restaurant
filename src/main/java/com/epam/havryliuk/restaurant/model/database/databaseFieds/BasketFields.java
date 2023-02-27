package com.epam.havryliuk.restaurant.model.database.databaseFieds;

import com.epam.havryliuk.restaurant.model.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class BasketFields {
    private static final Logger LOG = LogManager.getLogger(BasketFields.class);
    private static final Properties PROPERTIES;
    public static final String ORDER_ID;
    public static final String DISH_ID;
    public static final String DISH_PRICE;
    public static final String DISH_AMOUNT;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_FIELDS_SETTING_FILE);
        ORDER_ID = (String) PROPERTIES.get("basket.orderId");
        DISH_ID = (String) PROPERTIES.get("basket.dishId");
        DISH_PRICE = (String) PROPERTIES.get("basket.price");
        DISH_AMOUNT = (String) PROPERTIES.get("basket.amount");
        LOG.info("Database fields for \"order_has_dish\" table have been initialised successfully.");
    }
}
