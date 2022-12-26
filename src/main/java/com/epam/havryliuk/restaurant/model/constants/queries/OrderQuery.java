package com.epam.havryliuk.restaurant.model.constants.queries;

import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class OrderQuery {
    private static final Logger log = LogManager.getLogger(OrderQuery.class);
    private static final Properties properties;
    public static String GET_BY_USER_ID_ADDRESS_AND_STATUS;
    public static String ADD_ORDER;
    public static String GET_CREATION_DATE_BY_ID;
    public static String GET_ALL_ORDERS_BY_USER;
    public static String FIND_ORDER_BY_ID;
    public static String CHANGE_ORDER_STATUS_BY_ID;
    public static String DELETE_ORDER_BY_ID;
    public static String REMOVE_DISH_FROM_ORDER;
    public static String GET_NUMBER_DISHES_IN_ORDER;


    static {
        properties = PropertiesLoader.getProperties(ResourcePath.DB_QUERIES_FILE);
        initialiseVariable();
        log.debug("Database queries for \"Custom order\" table have been initialised successfully.");
    }

    private static void initialiseVariable() {
        GET_BY_USER_ID_ADDRESS_AND_STATUS = (String) properties.get("order.GET_BY_USER_ID_ADDRESS_AND_STATUS");
        ADD_ORDER = (String) properties.get("order.ADD_ORDER");
        GET_CREATION_DATE_BY_ID = (String) properties.get("order.GET_CREATION_DATE_BY_ID");
        GET_ALL_ORDERS_BY_USER = (String) properties.get("order.GET_ALL_ORDERS_BY_USER");
        FIND_ORDER_BY_ID = (String) properties.get("order.FIND_ORDER_BY_ID");
        CHANGE_ORDER_STATUS_BY_ID = (String) properties.get("order.CHANGE_ORDER_STATUS_BY_ID");
        REMOVE_DISH_FROM_ORDER = (String) properties.get("order.REMOVE_DISH_FROM_ORDER");
        GET_NUMBER_DISHES_IN_ORDER = (String) properties.get("order.GET_NUMBER_DISHES_IN_ORDER");
        DELETE_ORDER_BY_ID = (String) properties.get("order.DELETE_ORDER_BY_ID");
    }
}
