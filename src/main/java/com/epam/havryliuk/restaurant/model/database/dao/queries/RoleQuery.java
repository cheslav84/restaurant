package com.epam.havryliuk.restaurant.model.database.dao.queries;

import com.epam.havryliuk.restaurant.model.utils.Constants;
import com.epam.havryliuk.restaurant.model.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class RoleQuery {
    private static final Logger log = LogManager.getLogger(RoleQuery.class);
    private static final Properties properties;
    public static String ADD_ROLE;
    public static String FIND_ROLE_BY_ID;
    public static String FIND_ROLE_BY_NAME;
    public static String FIND_ALL_ROLES;
    public static String UPDATE_ROLE;
    public static String DELETE_ROLE_BY_NAME;
    public static String DELETE_ROLE_BY_ID;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_QUERIES_FILE);
        initialiseVariable();
        log.debug("Database queries for \"Role\" table have been initialised successfully.");
    }

       private static void initialiseVariable() {
           ADD_ROLE = (String) properties.get("role.ADD_ROLE");
           FIND_ROLE_BY_ID = (String) properties.get("role.FIND_ROLE_BY_ID");
           FIND_ROLE_BY_NAME = (String) properties.get("role.FIND_ROLE_BY_NAME");
           FIND_ALL_ROLES = (String) properties.get("role.FIND_ALL_ROLES");
           UPDATE_ROLE = (String) properties.get("role.UPDATE_ROLE");
           DELETE_ROLE_BY_NAME = (String) properties.get("role.DELETE_ROLE_BY_NAME");
           DELETE_ROLE_BY_ID = (String) properties.get("role.DELETE_ROLE_BY_ID");

       }
}
