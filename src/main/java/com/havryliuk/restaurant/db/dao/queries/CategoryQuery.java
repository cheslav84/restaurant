package com.havryliuk.restaurant.db.dao.queries;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.utils.PropertiesLoader;
import org.apache.log4j.Logger;
import java.util.Properties;

public class CategoryQuery {
    static Logger log = Logger.getLogger(CategoryQuery.class.getName());
    private static final Properties properties;
    public static String ADD_CATEGORY;
    public static String FIND_CATEGORY_BY_ID;
    public static String FIND_CATEGORY_BY_NAME;
    public static String FIND_ALL_CATEGORIES;
    public static String UPDATE_CATEGORY;
    public static String DELETE_CATEGORY_BY_NAME;
    public static String DELETE_CATEGORY_BY_ID;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_QUERIES_FILE);
        initialiseVariable();
        log.debug("Database queries for \"Category\" table have been initialised successfully.");
    }

       private static void initialiseVariable() {
           ADD_CATEGORY = (String) properties.get("category.ADD_CATEGORY");
           FIND_CATEGORY_BY_ID = (String) properties.get("category.FIND_CATEGORY_BY_ID");
           FIND_CATEGORY_BY_NAME = (String) properties.get("category.FIND_CATEGORY_BY_NAME");
           FIND_ALL_CATEGORIES = (String) properties.get("category.FIND_ALL_CATEGORIES");
           UPDATE_CATEGORY = (String) properties.get("category.UPDATE_CATEGORY");
           DELETE_CATEGORY_BY_NAME = (String) properties.get("category.DELETE_CATEGORY_BY_NAME");
           DELETE_CATEGORY_BY_ID = (String) properties.get("category.DELETE_CATEGORY_BY_ID");

       }
}
