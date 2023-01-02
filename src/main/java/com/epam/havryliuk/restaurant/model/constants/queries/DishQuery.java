package com.epam.havryliuk.restaurant.model.constants.queries;

import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

 public class DishQuery {
     private static final Logger log = LogManager.getLogger(DishQuery.class);

     private static final Properties properties;
     public static String ADD_DISH;
     public static String FIND_DISH_BY_ID;
     public static String FIND_DISH_BY_NAME;
     public static String FIND_ALL_AVAILABLE_ORDERED_BY_NAME;
     public static String FIND_ALL_AVAILABLE_ORDERED_BY_PRICE;
     public static String FIND_ALL_AVAILABLE_ORDERED_BY_CATEGORY;
     public static String FIND_ALL_BY_CATEGORY;
     public static String FIND_ALL_PRESENTS_BY_CATEGORY;
     public static String FIND_ALL_BY_ORDER;
     public static String UPDATE_DISH;
     public static String DELETE_DISH;
     public static String GET_NUMBER_OF_DISHES;
     public static String CHANGE_DISHES_AMOUNT_BY_ORDERED_VALUES;


     static {
         properties = PropertiesLoader.getProperties(ResourcePath.DB_QUERIES_FILE);
         initialiseVariable();
         log.debug("Database queries for \"Dish\" table have been initialised successfully.");
     }

        private static void initialiseVariable() {
            ADD_DISH = (String) properties.get("dish.ADD_DISH");
            FIND_DISH_BY_NAME = (String) properties.get("dish.FIND_DISH_BY_NAME");
            FIND_DISH_BY_ID = (String) properties.get("dish.FIND_DISH_BY_ID");
            FIND_ALL_AVAILABLE_ORDERED_BY_NAME = (String) properties.get("dish.FIND_ALL_AVAILABLE_ORDERED_BY_NAME");
            FIND_ALL_AVAILABLE_ORDERED_BY_PRICE = (String) properties.get("dish.FIND_ALL_AVAILABLE_ORDERED_BY_PRICE");
            FIND_ALL_AVAILABLE_ORDERED_BY_CATEGORY = (String) properties.get("dish.FIND_ALL_AVAILABLE_ORDERED_BY_CATEGORY");
            FIND_ALL_BY_CATEGORY = (String) properties.get("dish.FIND_ALL_BY_CATEGORY");
            FIND_ALL_PRESENTS_BY_CATEGORY = (String) properties.get("dish.FIND_ALL_PRESENTS_BY_CATEGORY");
            FIND_ALL_BY_ORDER = (String) properties.get("dish.FIND_ALL_BY_ORDER");
            UPDATE_DISH = (String) properties.get("dish.UPDATE_DISH");
            DELETE_DISH = (String) properties.get("dish.DELETE_DISH");
            GET_NUMBER_OF_DISHES = (String) properties.get("dish.GET_NUMBER_OF_DISHES");
            CHANGE_DISHES_AMOUNT_BY_ORDERED_VALUES = (String) properties.get("dish.CHANGE_DISHES_AMOUNT_BY_ORDERED_VALUES");

        }
 }
