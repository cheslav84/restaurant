package com.epam.havryliuk.restaurant.model.db.dao.queries;

import com.epam.havryliuk.restaurant.model.utils.Constants;
import com.epam.havryliuk.restaurant.model.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class ManagerQuery {
    private static final Logger log = LogManager.getLogger(ManagerQuery.class);
    private static final Properties properties;
    public static String ADD_MANAGER;
    public static String FIND_MANAGER_BY_ID;
    public static String FIND_MANAGER_BY_NAME;
    public static String FIND_ALL_MANAGERS;
    public static String UPDATE_MANAGER;
    public static String DELETE_MANAGER_BY_NAME;
    public static String DELETE_MANAGER_BY_ID;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_QUERIES_FILE);
        initialiseVariable();
        log.debug("Database queries for \"Manager\" table have been initialised successfully.");
    }

       private static void initialiseVariable() {
           ADD_MANAGER = (String) properties.get("manager.ADD_MANAGER");
           FIND_MANAGER_BY_ID = (String) properties.get("manager.FIND_MANAGER_BY_ID");
           FIND_MANAGER_BY_NAME = (String) properties.get("manager.FIND_MANAGER_BY_NAME");
           FIND_ALL_MANAGERS = (String) properties.get("manager.FIND_ALL_MANAGERS");
           UPDATE_MANAGER = (String) properties.get("manager.UPDATE_MANAGER");
           DELETE_MANAGER_BY_NAME = (String) properties.get("manager.DELETE_MANAGER_BY_NAME");
           DELETE_MANAGER_BY_ID = (String) properties.get("manager.DELETE_MANAGER_BY_ID");
       }
}
