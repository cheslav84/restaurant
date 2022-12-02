package com.havryliuk.restaurant.db.dao.queries;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.db.dao.implemetnation.DishDaoImpl;
import com.havryliuk.restaurant.exceptions.PropertyInitializationException;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


//public enum DishQuery1 {
//    ADD_DISH("INSERT INTO dish (name) values (?)"),//todo
//    FIND_DISH_BY_NAME("SELECT * FROM dish d WHERE d.name=?"),
//    FIND_ALL_ORDERED_BY_NAME("SELECT * FROM dish ORDER BY name"),
//    FIND_ALL_ORDERED_BY_PRICE("SELECT * FROM dish ORDER BY price"),
//    FIND_ALL_ORDERED_BY_CATEGORY("SELECT * FROM dish ORDER BY category"),
//
//
//
////    FIND_ALL_BY_CATEGORY("SELECT d.*, c.name as 'category_name' FROM dish d JOIN category c ON c.name=?"),
//    FIND_ALL_BY_CATEGORY("SELECT * FROM dish d JOIN category c WHERE c.name=?"),
//
//    FIND_DISH_BY_CATEGORY("SELECT * FROM dish d WHERE d.name=?"),
//
//
//    UPDATE_DISH("UPDATE teams SET name=? WHERE id=?"),//todo
//    //        DELETE_TEAM("DELETE t, ut FROM teams t JOIN users_teams ut WHERE t.id=? AND ut.team_id=?;"),
//    DELETE_DISH("DELETE FROM teams WHERE id=?"),//todo
//    DELETE_USERS_IN_TEAM("DELETE FROM users_teams WHERE team_id=?"),//todo
//    GET_ALL_DISHES("SELECT * FROM teams t ORDER BY t.name"),//todo
//    GET_TEAMS_BY_USER("SELECT id, name FROM teams t JOIN users_teams ut ON t.id=ut.team_id WHERE ut.user_id=?");//todo
//
//    public String QUERY;
//    private static final Properties properties = new Properties();
//
//     static {
//        try (FileReader fileReader = new FileReader(Constants.DB_FIELDS_SETTING_FILE)) {
//            properties.load(fileReader);
//        } catch (IOException e) {
//            e.printStackTrace(); //todo log
//        }
//    }
//
//    DishQuery1(String QUERY) {
//        this.QUERY = QUERY;
//    }
//}

 public class DishQuery {
     static Logger log = Logger.getLogger(DishQuery.class.getName());

     private static final Properties properties = new Properties();

     public static String ADD_DISH;
     public static String FIND_DISH_BY_NAME;
     public static String FIND_ALL_ORDERED_BY_NAME;
     public static String FIND_ALL_ORDERED_BY_PRICE;
     public static String FIND_ALL_ORDERED_BY_CATEGORY;
     public static String FIND_ALL_BY_CATEGORY;
     public static String UPDATE_DISH;
     public static String  DELETE_DISH;

     static {
         loadProperties();
         initialiseVariable();
     }

        private static void loadProperties() throws PropertyInitializationException {
            try (FileReader fileReader = new FileReader(Constants.DB_QUERIES_FILE)) {
                properties.load(fileReader);
            } catch (IOException e) {
                log.error("Error loading query properties from file " + Constants.DB_QUERIES_FILE, e);
                throw new PropertyInitializationException();
            }
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

    // public static String UPDATE_DISH("UPDATE teams SET name=? WHERE id=?"),//todo
     // public static String  DELETE_DISH("DELETE FROM teams WHERE id=?"),//todo



 }
