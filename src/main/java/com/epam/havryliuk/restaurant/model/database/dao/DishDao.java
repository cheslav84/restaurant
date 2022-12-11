package com.epam.havryliuk.restaurant.model.database.dao;

import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;

import java.util.List;

public interface DishDao extends DAO<Dish> {
    Dish findByName(String name) throws DBException;
    List<Dish> findByCategory(Category category) throws DBException;
    List<Dish> getSortedByName() throws DBException;
    List<Dish> getSortedByPrice() throws DBException;
    List<Dish> getSortedByCategory() throws DBException;
}
