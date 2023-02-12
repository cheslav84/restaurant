package com.epam.havryliuk.restaurant.model.database.queries;

import com.epam.havryliuk.restaurant.model.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class BasketQuery {
    private static final Logger LOG = LogManager.getLogger(BasketQuery.class);
    private static final Properties PROPERTIES;
    public static final String ADD_DISH_TO_BASKET;
//    public static final String GET_DISH_FR0M_BASKET;
    public static final String GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_QUERIES_FILE);
        ADD_DISH_TO_BASKET = (String) PROPERTIES.get("basket.ADD_DISH_TO_BASKET");
//        GET_DISH_FR0M_BASKET = (String) PROPERTIES.get("basket.GET_DISH_FR0M_BASKET");
        GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER = (String) PROPERTIES.get("basket.GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER");
        LOG.debug("Database queries for \"order_has_dishes\" table have been initialised successfully.");
    }
}
