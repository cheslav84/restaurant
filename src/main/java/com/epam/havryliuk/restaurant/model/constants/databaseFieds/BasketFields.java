package com.epam.havryliuk.restaurant.model.constants.databaseFieds;

import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class BasketFields {
    private static final Logger log = LogManager.getLogger(BasketFields.class);

    private static final Properties properties;
    public static String ORDER_ID;
    public static String DISH_ID;
    public static String DISH_PRICE;
    public static String DISH_AMOUNT;

    static {
        properties = PropertiesLoader.getProperties(ResourcePath.DB_FIELDS_SETTING_FILE);
        initialiseVariable();
        log.debug("Database fields for \"order_has_dish\" table have been initialised successfully.");
    }

    private static void initialiseVariable() {
        ORDER_ID = (String) properties.get("basket.orderId");
        DISH_ID = (String) properties.get("basket.dishId");
        DISH_PRICE = (String) properties.get("basket.price");
        DISH_AMOUNT = (String) properties.get("basket.amount");
    }

}
