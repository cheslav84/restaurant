package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.Exceptions.DaoException;
import com.havryliuk.restaurant.db.entity.Dish;

import java.util.List;

public interface DishDao extends EntityDao<Long, Dish>  {
    Dish findByName (String name) throws DaoException;
    List<Dish> findByCategory() throws DaoException;
    List<Dish> getSortedByName() throws DaoException;
    List<Dish> getSortedByPrice() throws DaoException;
    List<Dish> getSortedByCategory() throws DaoException;
}
