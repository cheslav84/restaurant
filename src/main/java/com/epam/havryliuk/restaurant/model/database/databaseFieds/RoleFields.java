package com.epam.havryliuk.restaurant.model.database.databaseFieds;

import com.epam.havryliuk.restaurant.model.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class RoleFields {
    private static final Logger LOG = LogManager.getLogger(RoleFields.class);
    private static final Properties PROPERTIES;
    public static final String ROLE_ID;
    public static final String ROLE_NAME;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_FIELDS_SETTING_FILE);
        ROLE_ID = (String) PROPERTIES.get("role.id");
        ROLE_NAME = (String) PROPERTIES.get("role.name");
        LOG.info("Database fields for \"Role\" table have been initialised successfully.");
    }

}
