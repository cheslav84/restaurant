package com.epam.havryliuk.restaurant.model.constants.queries;

import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class BasketQuery {
    private static final Logger log = LogManager.getLogger(BasketQuery.class);
    private static final Properties properties;

    public static String ADD_DISH_TO_BASKET;
    public static String GET_DISH_FR0M_BASKET;
    public static String GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER;


    static {
        properties = PropertiesLoader.getProperties(ResourcePath.DB_QUERIES_FILE);
        initialiseVariable();
        log.debug("Database queries for \"order_has_dishes\" table have been initialised successfully.");
    }

    private static void initialiseVariable() {
        ADD_DISH_TO_BASKET = (String) properties.get("basket.ADD_DISH_TO_BASKET");
        GET_DISH_FR0M_BASKET = (String) properties.get("basket.GET_DISH_FR0M_BASKET");
        GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER = (String) properties.get("basket.GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER");

    }
}
