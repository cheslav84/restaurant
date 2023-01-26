package com.epam.havryliuk.restaurant.model.constants.queries;

import com.epam.havryliuk.restaurant.model.constants.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class UserQuery {
    private static final Logger LOG = LogManager.getLogger(UserQuery.class);
    private static final Properties PROPERTIES;
    public static String ADD_USER;
    public static String FIND_USER_BY_ID;
    public static String FIND_USER_BY_LOGIN;
    public static String FIND_ALL_USERS;
    public static String UPDATE_USER;
    public static String DELETE_USER;
    public static String DELETE_USER_BY_ID;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_QUERIES_FILE);
        ADD_USER = (String) PROPERTIES.get("user.ADD_USER");
        FIND_USER_BY_ID = (String) PROPERTIES.get("user.FIND_USER_BY_ID");
        FIND_USER_BY_LOGIN = (String) PROPERTIES.get("user.FIND_USER_BY_LOGIN");
        FIND_ALL_USERS = (String) PROPERTIES.get("user.FIND_ALL_USERS");
        UPDATE_USER = (String) PROPERTIES.get("user.UPDATE_USER");
        DELETE_USER = (String) PROPERTIES.get("user.DELETE_USER");
        DELETE_USER_BY_ID = (String) PROPERTIES.get("user.DELETE_USER_BY_ID");
        LOG.debug("Database queries for \"User\" table have been initialised successfully.");
    }
}
