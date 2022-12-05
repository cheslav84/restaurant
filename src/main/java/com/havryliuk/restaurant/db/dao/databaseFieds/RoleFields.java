package com.havryliuk.restaurant.db.dao.databaseFieds;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class RoleFields {
    private static final Logger log = LogManager.getLogger(RoleFields.class);

    private static final Properties properties;

    public static String ROLE_ID;
    public static String ROLE_NAME;

    static {
        properties = PropertiesLoader.getProperties(Constants.DB_FIELDS_SETTING_FILE);
        initialiseVariable();
        log.debug("Database fields for \"Role\" table have been initialised successfully.");

    }

    private static void initialiseVariable() {
        ROLE_ID = (String) properties.get("role.id");
        ROLE_NAME = (String) properties.get("role.name");
    }


}
