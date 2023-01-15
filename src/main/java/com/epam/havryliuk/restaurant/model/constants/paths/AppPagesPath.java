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
    public static String FORWARD_MENU_PAGE;
    public static String REDIRECT_INDEX;
    public static String REDIRECT_MENU;
    public static String FORWARD_REGISTRATION;
    public static String REDIRECT_REGISTRATION;
    public static String REDIRECT_BASKET;
    public static String FORWARD_BASKET;
    public static String FORWARD_ADD_DISH_PAGE;
//    public static String REDIRECT_ADD_DISH_PAGE;
    public static String FORWARD_MANAGE_ORDERS;
    public static String REDIRECT_ERROR;



    static {
        properties = PropertiesLoader.getProperties(ResourcePath.PAGES_PATH_FILE);
        initialiseVariable();
    }

       private static void initialiseVariable() {
           FORWARD_INDEX = (String) properties.get("path.page.forward.index");
           REDIRECT_INDEX = (String) properties.get("path.page.redirect.index");

           FORWARD_REGISTRATION = (String) properties.get("path.page.forward.registration");
           REDIRECT_REGISTRATION = (String) properties.get("path.page.redirect.registration");

           FORWARD_BASKET = (String) properties.get("path.page.forward.basket");
           REDIRECT_BASKET = (String) properties.get("path.page.redirect.basket");

           FORWARD_MANAGE_ORDERS = (String) properties.get("path.page.forward.manageOrders");

           REDIRECT_ERROR = (String) properties.get("path.page.redirect.error");

           FORWARD_MENU_PAGE = (String) properties.get("path.page.forward.menu");
           REDIRECT_MENU = (String) properties.get("path.page.redirect.menu");

           FORWARD_ADD_DISH_PAGE = (String) properties.get("path.page.forward.addDishPage");
//           REDIRECT_ADD_DISH_PAGE = (String) properties.get("path.page.redirect.addDishPage");
       }
}
