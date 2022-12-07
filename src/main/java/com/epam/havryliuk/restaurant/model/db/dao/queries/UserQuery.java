package com.epam.havryliuk.restaurant.model.db.dao.queries;

import com.epam.havryliuk.restaurant.model.utils.Constants;
import com.epam.havryliuk.restaurant.model.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class UserQuery {
    private static final Logger log = LogManager.getLogger(UserQuery.class);
    private static final Properties properties;
    public static String ADD_USER;
    public static String FIND_USER_BY_ID;
    public static String FIND_USER_BY_NAME;
    public static String FIND_ALL_USERS;
    public static String UPDATE_USER;
    public static String DELETE_USER;
    public static String DELETE_USER_BY_ID;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_QUERIES_FILE);
        initialiseVariable();
        log.debug("Database queries for \"User\" table have been initialised successfully.");
    }

       private static void initialiseVariable() {
           ADD_USER = (String) properties.get("user.ADD_USER");
           FIND_USER_BY_ID = (String) properties.get("user.FIND_USER_BY_ID");
           FIND_USER_BY_NAME = (String) properties.get("user.FIND_USER_BY_NAME");
           FIND_ALL_USERS = (String) properties.get("user.FIND_ALL_USERS");
           UPDATE_USER = (String) properties.get("user.UPDATE_USER");
           DELETE_USER = (String) properties.get("user.DELETE_USER");
           DELETE_USER_BY_ID = (String) properties.get("user.DELETE_USER_BY_ID");
       }
}
