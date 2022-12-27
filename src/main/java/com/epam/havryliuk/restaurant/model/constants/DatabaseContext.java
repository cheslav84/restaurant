package com.epam.havryliuk.restaurant.model.constants;

import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class DatabaseContext {
    private static final Logger log = LogManager.getLogger(DatabaseContext.class);

    private static final Properties properties;
    public static String CONTEXT;
    public static String SOURCE;


    static {
        properties = PropertiesLoader.getProperties(ResourcePath.DB_CONTEXT);
        initialiseVariable();
    }

    private static void initialiseVariable() {
        CONTEXT = (String) properties.get("database.context");
        SOURCE = (String) properties.get("database.source");
    }

}
