package com.epam.havryliuk.restaurant.model.database.dao.queries;

import com.epam.havryliuk.restaurant.model.utils.Constants;
import com.epam.havryliuk.restaurant.model.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class OrderQuery {
    private static final Logger log = LogManager.getLogger(OrderQuery.class);
    private static final Properties properties;
    public static String GET_BY_USER_ID_ADDRESS_AND_STATUS;
    public static String ADD_ORDER;
    public static String GET_CREATION_DATE_BY_ID;
//    public static String FIND_USER_BY_LOGIN;
//    public static String FIND_ALL_USERS;
//    public static String UPDATE_USER;
//    public static String DELETE_USER;
//    public static String DELETE_USER_BY_ID;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_QUERIES_FILE);
        initialiseVariable();
        log.debug("Database queries for \"User\" table have been initialised successfully.");
    }

    private static void initialiseVariable() {
        GET_BY_USER_ID_ADDRESS_AND_STATUS = (String) properties.get("order.GET_BY_USER_ID_ADDRESS_AND_STATUS");
        ADD_ORDER = (String) properties.get("order.ADD_ORDER");
        GET_CREATION_DATE_BY_ID = (String) properties.get("order.GET_CREATION_DATE_BY_ID");
//           FIND_USER_BY_LOGIN = (String) properties.get("user.FIND_USER_BY_LOGIN");
//           FIND_ALL_USERS = (String) properties.get("user.FIND_ALL_USERS");
//           UPDATE_USER = (String) properties.get("user.UPDATE_USER");
//           DELETE_USER = (String) properties.get("user.DELETE_USER");
//           DELETE_USER_BY_ID = (String) properties.get("user.DELETE_USER_BY_ID");
    }
}
