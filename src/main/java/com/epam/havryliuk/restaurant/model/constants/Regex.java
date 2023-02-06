package com.epam.havryliuk.restaurant.model.constants;

import java.math.BigDecimal;

public interface Regex {
    String PHONE = "^(\\+[1-9]{1}[0-9]{11})|([0]{1}[0-9]{9})$";
    String EMAIL = "^(?=.{0,24}$)([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})$";
    //Minimum eight characters, at least one letter, one number and one special character:
    String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";
    String NAME = "^[A-Za-zА-Яа-я]{2,24}$";
    String DISH_NAME = "^[A-Za-zА-Яа-я\\d]{2,24}$";
    String DISH_DESCRIPTION = "^[A-Za-zА-Яа-я\\d\\s.,!?\"%&/-]{10,255}$";
    String ADDRESS = "^[A-Za-zА-Яа-я\\d\\s.,/-]{13,100}$";
    int MIN_WEIGHT = 25;
    int MAX_WEIGHT = 999;
    BigDecimal MIN_PRICE = new BigDecimal(5);
    BigDecimal MAX_PRICE = new BigDecimal(5000);
}
