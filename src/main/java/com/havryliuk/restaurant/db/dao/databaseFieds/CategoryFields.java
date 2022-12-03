package com.havryliuk.restaurant.db.dao.databaseFieds;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.db.dao.queries.CategoryQuery;
import com.havryliuk.restaurant.utils.PropertiesLoader;
import org.apache.log4j.Logger;

import java.util.Properties;

public class CategoryFields {
    static Logger log = Logger.getLogger(CategoryFields.class.getName());

    private static final Properties properties;

    public static String CATEGORY_ID;
    public static String CATEGORY_NAME;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_FIELDS_SETTING_FILE);
        initialiseVariable();
        log.debug("Database fields for \"Category\" table have been initialised successfully.");

    }

    private static void initialiseVariable() {
        CATEGORY_ID = (String) properties.get("category.id");
        CATEGORY_NAME = (String) properties.get("category.name");
    }


}
