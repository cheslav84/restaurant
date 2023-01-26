package com.epam.havryliuk.restaurant.model.constants.queries;

import com.epam.havryliuk.restaurant.model.constants.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class CategoryQuery {
    private static final Logger LOG = LogManager.getLogger(CategoryQuery.class);
    private static final Properties PROPERTIES;
    public static final String ADD_CATEGORY;
    public static final String FIND_CATEGORY_BY_ID;
    public static final String FIND_CATEGORY_BY_NAME;
    public static final String FIND_ALL_CATEGORIES;
    public static final String UPDATE_CATEGORY;
    public static final String DELETE_CATEGORY_BY_NAME;
    public static final String DELETE_CATEGORY_BY_ID;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_QUERIES_FILE);
        ADD_CATEGORY = (String) PROPERTIES.get("category.ADD_CATEGORY");
        FIND_CATEGORY_BY_ID = (String) PROPERTIES.get("category.FIND_CATEGORY_BY_ID");
        FIND_CATEGORY_BY_NAME = (String) PROPERTIES.get("category.FIND_CATEGORY_BY_NAME");
        FIND_ALL_CATEGORIES = (String) PROPERTIES.get("category.FIND_ALL_CATEGORIES");
        UPDATE_CATEGORY = (String) PROPERTIES.get("category.UPDATE_CATEGORY");
        DELETE_CATEGORY_BY_NAME = (String) PROPERTIES.get("category.DELETE_CATEGORY_BY_NAME");
        DELETE_CATEGORY_BY_ID = (String) PROPERTIES.get("category.DELETE_CATEGORY_BY_ID");
        LOG.debug("Database queries for \"Category\" table have been initialised successfully.");
    }
}
