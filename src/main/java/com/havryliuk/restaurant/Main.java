package com.havryliuk.restaurant;

import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.implemetnation.DishDaoImpl;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.exceptions.DBException;

public class Main {
    public static void main(String[] args) throws DBException {

        DishDao dishDao = new DishDaoImpl();

        Dish dish = dishDao.findByName("Single Cup Brew");
        System.out.println(dish);
//        System.out.println(dishDao.getSortedByName());
//        System.out.println(dishDao.getSortedByPrice());


    }
}
