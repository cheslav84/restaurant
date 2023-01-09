package com.epam.havryliuk.restaurant.model.constants;

public interface Regex {
    String PHONE = "^(\\+[1-9]{1}[0-9]{11})|([0]{1}[0-9]{9})$";
    String EMAIL = "^(?=.{0,24}$)([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})$";
    //Minimum eight characters, at least one letter, one number and one special character:
    String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";
    String NAME = "^[A-Za-zА-Яа-я]{2,24}$";
    String DISH_WEIGHT = "^\\d{2,3}$";
    String DISH_PRICE = "^\\d{1,4}\\.\\d{2}$";
    String DISH_DESCRIPTION = "^[A-Za-zА-Яа-я\\d\\s.,!?\"'%&/-]{10,255}$";

    String ADDRESS = "^[A-Za-zА-Яа-я\\d\\s.,/-]{13,100}$";



}
