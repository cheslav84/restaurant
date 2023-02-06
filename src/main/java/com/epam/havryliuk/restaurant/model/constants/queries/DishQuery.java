package com.epam.havryliuk.restaurant.model.constants.queries;

import com.epam.havryliuk.restaurant.model.constants.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

 public class DishQuery {
     private static final Logger LOG = LogManager.getLogger(DishQuery.class);
     private static final Properties PROPERTIES;
     public static final String ADD_DISH;
     public static final String FIND_DISH_BY_ID;
     public static final String FIND_DISH_BY_NAME;
     public static final String FIND_ALL_ORDERED_BY_NAME;
     public static final String FIND_ALL_AVAILABLE_ORDERED_BY_NAME;
     public static final String FIND_ALL_ORDERED_BY_PRICE;
     public static final String FIND_ALL_AVAILABLE_ORDERED_BY_PRICE;
     public static final String FIND_ALL_ORDERED_BY_CATEGORY;
     public static final String FIND_ALL_AVAILABLE_ORDERED_BY_CATEGORY;
     public static final String FIND_ALL_BY_CATEGORY;
     public static final String FIND_ALL_AVAILABLE_BY_CATEGORY;
     public static final String FIND_ALL_BY_ORDER;
     public static final String UPDATE_DISH;
     public static final String DELETE_DISH;
     public static final String GET_NUMBER_OF_ALL_DISHES_IN_ORDER;
     public static final String GET_NUMBER_OF_EACH_DISH_IN_ORDER;
     public static final String CHANGE_DISHES_AMOUNT_BY_ORDERED_VALUES;
     public static final String ADD_DISH_TO_CATEGORY;
     public static final String UPDATE_DISH_CATEGORY;
     public static final String REMOVE_DISH_FROM_CATEGORY;
     public static final String COUNT_DISHES_BY_NAME;

     static {
         PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_QUERIES_FILE);
         ADD_DISH = (String) PROPERTIES.get("dish.ADD_DISH");
         FIND_DISH_BY_NAME = (String) PROPERTIES.get("dish.FIND_DISH_BY_NAME");
         FIND_DISH_BY_ID = (String) PROPERTIES.get("dish.FIND_DISH_BY_ID");
         FIND_ALL_ORDERED_BY_NAME = (String) PROPERTIES.get("dish.FIND_ALL_ORDERED_BY_NAME");
         FIND_ALL_AVAILABLE_ORDERED_BY_NAME = (String) PROPERTIES.get("dish.FIND_ALL_AVAILABLE_ORDERED_BY_NAME");
         FIND_ALL_ORDERED_BY_PRICE = (String) PROPERTIES.get("dish.FIND_ALL_ORDERED_BY_PRICE");
         FIND_ALL_AVAILABLE_ORDERED_BY_PRICE = (String) PROPERTIES.get("dish.FIND_ALL_AVAILABLE_ORDERED_BY_PRICE");
         FIND_ALL_ORDERED_BY_CATEGORY = (String) PROPERTIES.get("dish.FIND_ALL_ORDERED_BY_CATEGORY");
         FIND_ALL_BY_CATEGORY = (String) PROPERTIES.get("dish.FIND_ALL_BY_CATEGORY");
         FIND_ALL_AVAILABLE_ORDERED_BY_CATEGORY = (String) PROPERTIES.get("dish.FIND_ALL_AVAILABLE_ORDERED_BY_CATEGORY");
         FIND_ALL_AVAILABLE_BY_CATEGORY = (String) PROPERTIES.get("dish.FIND_ALL_PRESENTS_BY_CATEGORY");
         FIND_ALL_BY_ORDER = (String) PROPERTIES.get("dish.FIND_ALL_BY_ORDER");
         UPDATE_DISH = (String) PROPERTIES.get("dish.UPDATE_DISH");
         DELETE_DISH = (String) PROPERTIES.get("dish.DELETE_DISH");
         GET_NUMBER_OF_ALL_DISHES_IN_ORDER = (String) PROPERTIES.get("dish.GET_NUMBER_OF_ALL_DISHES_IN_ORDER");
         GET_NUMBER_OF_EACH_DISH_IN_ORDER = (String) PROPERTIES.get("dish.GET_NUMBER_OF_EACH_DISH_IN_ORDER");
         CHANGE_DISHES_AMOUNT_BY_ORDERED_VALUES = (String) PROPERTIES.get("dish.CHANGE_DISHES_AMOUNT_BY_ORDERED_VALUES");
         ADD_DISH_TO_CATEGORY = (String) PROPERTIES.get("dish.ADD_DISH_TO_CATEGORY");
         UPDATE_DISH_CATEGORY = (String) PROPERTIES.get("dish.UPDATE_DISH_CATEGORY");
         REMOVE_DISH_FROM_CATEGORY = (String) PROPERTIES.get("dish.REMOVE_DISH_FROM_CATEGORY");
         COUNT_DISHES_BY_NAME = (String) PROPERTIES.get("dish.COUNT_DISHES_BY_NAME");
         LOG.debug("Database queries for \"Dish\" table have been initialised successfully.");
     }
 }