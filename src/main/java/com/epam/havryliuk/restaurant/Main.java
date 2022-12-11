package com.epam.havryliuk.restaurant;

import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    static User user;
    public static void main(String[] args) {


        String categoryName = "COFFEE";
        Category category;


//
//            category = Category.getInstance(Category.CategoryName.valueOf(categoryName));
//            log.debug("Service: category initialised " + category.getCategoryName().name());




//        user = new User();
//        user.setId(1);
//        int id = 1;
//        Optional.of(findById(id)).ifPresent(System.out::println);
//        Optional.of(findById(id)).ifPresent(user1 -> createLog(user));
//        Optional.of(findById(id)).ifPresent(user1 -> createLog());
//        User us = findById(id).orElse(createNewUser());
//        User us2 = findById(id).orElseGet(() -> User.getInstance("mail", "pass", "name", "sur", "male", true, new Role()));
//
//
//    }
//    public static Optional<User> findById(int id){
//        if (id == 1) {
//            return Optional.of(user);
//        } else {
//            return Optional.empty();
//        }
//    }
//
//    public static void createLog(User user){
//        System.out.println("user " + user);
//    }
//
//    public static void createLog(){
//        System.out.println("void " + user);
//    }
//
//    public static User createNewUser(){
//        return new User();
    }


}
