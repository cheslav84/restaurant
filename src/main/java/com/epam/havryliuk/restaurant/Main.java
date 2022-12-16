package com.epam.havryliuk.restaurant;

import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    static User user;
    public static void main(String[] args) {

//        String deliveryPhone = "+38 (096)-55 0-10-25";
        String deliveryPhone = " (196)-55 0-10-25";
        String onlyNumbers = deliveryPhone.replaceAll("[\\s()-]", "");
        System.out.println(onlyNumbers);

        if (deliveryPhone != null){


            String patterns = "^(\\+[1-9]{1}[0-9]{11})|([0]{1}[0-9]{9})$";

            Pattern pattern = Pattern.compile(patterns);
            Matcher matcher = pattern.matcher(onlyNumbers);
            if (matcher.matches()) {
                System.out.println(true);
            } else {
                System.out.println(false);
            }
        }



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
