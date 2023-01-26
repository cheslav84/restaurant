//package com.epam.havryliuk.restaurant.model.constants.databaseFieds;
//
//import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
//
//import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.Properties;
//
//public class CategoryFields {
//    private static final Logger LOG = LogManager.getLogger(CategoryFields.class);
//    private static final Properties PROPERTIES;
//    public static final String CATEGORY_ID;
//    public static final String CATEGORY_NAME;
//
//    static {
//        PROPERTIES = PropertiesLoader.getProperties(ResourcePath.DB_FIELDS_SETTING_FILE);
//        CATEGORY_ID = (String) PROPERTIES.get("category.id");
//        CATEGORY_NAME = (String) PROPERTIES.get("category.name");
//        LOG.debug("Database fields for \"Category\" table have been initialised successfully.");
//    }
//}
