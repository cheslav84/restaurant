package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.exceptions.DBException;

public interface CategoryDao extends EntityDao<Long, Category> {
    Category findById(Long id) throws DBException;
    Category findByName(String name) throws DBException;
}
