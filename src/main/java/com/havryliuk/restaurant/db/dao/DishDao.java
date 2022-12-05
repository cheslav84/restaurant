package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.exceptions.DBException;

import java.util.List;

public interface DishDao extends DAO<Dish> {
    Dish findByName(String name) throws DBException;
    List<Dish> findByCategory(Category category) throws DBException;
    List<Dish> getSortedByName() throws DBException;
    List<Dish> getSortedByPrice() throws DBException;
    List<Dish> getSortedByCategory() throws DBException;
}
