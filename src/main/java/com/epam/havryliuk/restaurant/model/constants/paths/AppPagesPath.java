package com.epam.havryliuk.restaurant.model.constants.paths;

import com.epam.havryliuk.restaurant.model.constants.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class AppPagesPath {
    private static final Logger LOG = LogManager.getLogger(AppPagesPath.class);
    private static final Properties PROPERTIES;
    public static final String FORWARD_INDEX;
    public static final String FORWARD_MENU_PAGE;
    public static final String REDIRECT_INDEX;
    //    public static final String REDIRECT_MENU;
    public static final String FORWARD_REGISTRATION;
    public static final String REDIRECT_REGISTRATION;
    public static final String REDIRECT_BASKET;
    public static final String FORWARD_BASKET;
    public static final String FORWARD_ADD_DISH_PAGE;
    public static final String FORWARD_MANAGE_ORDERS;
    public static final String REDIRECT_ERROR;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.PAGES_PATH_FILE);

        FORWARD_INDEX = (String) PROPERTIES.get("path.page.forward.index");
        REDIRECT_INDEX = (String) PROPERTIES.get("path.page.redirect.index");

        FORWARD_REGISTRATION = (String) PROPERTIES.get("path.page.forward.registration");
        REDIRECT_REGISTRATION = (String) PROPERTIES.get("path.page.redirect.registration");

        FORWARD_BASKET = (String) PROPERTIES.get("path.page.forward.basket");
        REDIRECT_BASKET = (String) PROPERTIES.get("path.page.redirect.basket");

        FORWARD_MANAGE_ORDERS = (String) PROPERTIES.get("path.page.forward.manageOrders");

        FORWARD_MENU_PAGE = (String) PROPERTIES.get("path.page.forward.menu");
//        REDIRECT_MENU = (String) PROPERTIES.get("path.page.redirect.menu");

        FORWARD_ADD_DISH_PAGE = (String) PROPERTIES.get("path.page.forward.addDishPage");

        REDIRECT_ERROR = (String) PROPERTIES.get("path.page.redirect.error");

        LOG.debug("Application paths are initialised.");
    }

}
