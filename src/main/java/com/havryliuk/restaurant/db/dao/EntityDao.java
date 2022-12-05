package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Entity;
import com.havryliuk.restaurant.exceptions.DBException;

import java.util.List;

public interface EntityDao<K, T extends Entity> {
    boolean create(T entity) throws DBException;
    T findByName(String name) throws DBException;

    T findById(K id) throws DBException;

    List<T> findAll() throws DBException;
    boolean update(T entity) throws DBException;
    boolean delete(T entity) throws DBException;
    boolean delete(K id) throws DBException;
}
