package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.exceptions.DBException;

import java.util.List;

public interface CategoryDao extends EntityDao<Long, Category> {
    Category findById (Long id) throws DBException;
    Category findByName (String name) throws DBException;
}
