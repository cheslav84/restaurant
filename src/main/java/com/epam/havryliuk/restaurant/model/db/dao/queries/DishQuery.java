package com.epam.havryliuk.restaurant.model.db.dao.queries;

import com.epam.havryliuk.restaurant.model.utils.Constants;
import com.epam.havryliuk.restaurant.model.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

 public class DishQuery {
     private static final Logger log = LogManager.getLogger(DishQuery.class);

     private static final Properties properties;
     public static String ADD_DISH;
     public static String FIND_DISH_BY_NAME;
     public static String FIND_ALL_ORDERED_BY_NAME;
     public static String FIND_ALL_ORDERED_BY_PRICE;
     public static String FIND_ALL_ORDERED_BY_CATEGORY;
     public static String FIND_ALL_BY_CATEGORY;
     public static String UPDATE_DISH;
     public static String  DELETE_DISH;

     static {
         properties = PropertiesLoader.getProperties(Constants.DB_QUERIES_FILE);
         initialiseVariable();
         log.debug("Database queries for \"Dish\" table have been initialised successfully.");
     }

        private static void initialiseVariable() {
            ADD_DISH = (String) properties.get("dish.ADD_DISH");
            FIND_DISH_BY_NAME = (String) properties.get("dish.FIND_DISH_BY_NAME");
            FIND_ALL_ORDERED_BY_NAME = (String) properties.get("dish.FIND_ALL_ORDERED_BY_NAME");
            FIND_ALL_ORDERED_BY_PRICE = (String) properties.get("dish.FIND_ALL_ORDERED_BY_PRICE");
            FIND_ALL_ORDERED_BY_CATEGORY = (String) properties.get("dish.FIND_ALL_ORDERED_BY_CATEGORY");
            FIND_ALL_BY_CATEGORY = (String) properties.get("dish.FIND_ALL_BY_CATEGORY");
            UPDATE_DISH = (String) properties.get("dish.UPDATE_DISH");
            DELETE_DISH = (String) properties.get("dish.DELETE_DISH");

        }
 }
