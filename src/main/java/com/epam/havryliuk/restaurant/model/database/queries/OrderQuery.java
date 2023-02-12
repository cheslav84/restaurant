package com.epam.havryliuk.restaurant.model.database.queries;

import com.epam.havryliuk.restaurant.model.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class OrderQuery {
    private static final Logger LOG = LogManager.getLogger(OrderQuery.class);
    private static final Properties PROPERTIES;
    public static String GET_BY_USER_ID_ADDRESS_AND_STATUS;
    public static String ADD_ORDER;
    public static String GET_CREATION_DATE_BY_ID;
    public static String GET_ALL_ORDERS_BY_USER;
    public static String GET_CONFIRMED_ORDERS_SORTED_BY_STATUS_THEN_TIME;
    public static String GET_CONFIRMED_ORDERS_SORTED_BY_TIME_THEN_STATUS;
    public static String FIND_ORDER_BY_ID;
    public static String CHANGE_ORDER_STATUS_BY_ID;
    public static String DELETE_ORDER_BY_ID;
    public static String REMOVE_DISH_FROM_ORDER;
    public static String GET_NUMBER_DISHES_IN_ORDER;
    public static String GET_NUMBER_OF_CONFIRMED_ORDERS;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_QUERIES_FILE);
        GET_BY_USER_ID_ADDRESS_AND_STATUS = (String) PROPERTIES.get("order.GET_BY_USER_ID_ADDRESS_AND_STATUS");
        ADD_ORDER = (String) PROPERTIES.get("order.ADD_ORDER");
        GET_CREATION_DATE_BY_ID = (String) PROPERTIES.get("order.GET_CREATION_DATE_BY_ID");
        GET_ALL_ORDERS_BY_USER = (String) PROPERTIES.get("order.GET_ALL_ORDERS_BY_USER");
        GET_CONFIRMED_ORDERS_SORTED_BY_STATUS_THEN_TIME = (String) PROPERTIES.get("order.GET_CONFIRMED_ORDERS_SORTED_BY_STATUS_THEN_TIME");
        GET_CONFIRMED_ORDERS_SORTED_BY_TIME_THEN_STATUS = (String) PROPERTIES.get("order.GET_CONFIRMED_ORDERS_SORTED_BY_TIME_THEN_STATUS");
        FIND_ORDER_BY_ID = (String) PROPERTIES.get("order.FIND_ORDER_BY_ID");
        CHANGE_ORDER_STATUS_BY_ID = (String) PROPERTIES.get("order.CHANGE_ORDER_STATUS_BY_ID");
        REMOVE_DISH_FROM_ORDER = (String) PROPERTIES.get("order.REMOVE_DISH_FROM_ORDER");
        GET_NUMBER_DISHES_IN_ORDER = (String) PROPERTIES.get("order.GET_NUMBER_DISHES_IN_ORDER");
        DELETE_ORDER_BY_ID = (String) PROPERTIES.get("order.DELETE_ORDER_BY_ID");
        GET_NUMBER_OF_CONFIRMED_ORDERS = (String) PROPERTIES.get("order.GET_NUMBER_OF_CONFIRMED_ORDERS");
        LOG.debug("Database queries for \"Custom order\" table have been initialised successfully.");
    }
}
