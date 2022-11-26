package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.exceptions.DBException;
import com.havryliuk.restaurant.db.entity.Entity;

import java.util.List;

public interface EntityDao<K, T extends Entity> {
    boolean create(T entity) throws DBException;
    T findById (K id) throws DBException;
    List<T> findAll() throws DBException;
    boolean update(T entity) throws DBException;
    boolean delete(T entity) throws DBException;
    boolean delete(K id) throws DBException;
}
