package com.epam.havryliuk.restaurant.model.constants.paths;

import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class AppPagesPath {
    private static final Logger log = LogManager.getLogger(AppPagesPath.class);
    private static final Properties properties;
    public static String INDEX;
    public static String REGISTRATION;
    public static String ERROR;



    static {
        properties = PropertiesLoader.getProperties(ResourcePath.PAGES_PATH_FILE);
        initialiseVariable();
    }

       private static void initialiseVariable() {
           INDEX = (String) properties.get("path.page.index");
           REGISTRATION = (String) properties.get("path.page.registration");
           ERROR = (String) properties.get("path.page.error");



       }
}
