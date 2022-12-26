package com.epam.havryliuk.restaurant.model.constants.paths;

import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class AppPagesPath {
    private static final Logger log = LogManager.getLogger(AppPagesPath.class);
    private static final Properties properties;
    public static String FORWARD_INDEX;
    public static String REDIRECT_INDEX;
    public static String MENU;
    public static String FORWARD_REGISTRATION;
    public static String REDIRECT_REGISTRATION;
    public static String ERROR;



    static {
        properties = PropertiesLoader.getProperties(ResourcePath.PAGES_PATH_FILE);
        initialiseVariable();
    }

       private static void initialiseVariable() {
           FORWARD_INDEX = (String) properties.get("path.page.forward.index");
           REDIRECT_INDEX = (String) properties.get("path.page.redirect.index");
           FORWARD_REGISTRATION = (String) properties.get("path.page.forward.registration");
           REDIRECT_REGISTRATION = (String) properties.get("path.page.redirect.registration");
           ERROR = (String) properties.get("path.page.error");
           MENU = (String) properties.get("path.page.menu");

       }
}
