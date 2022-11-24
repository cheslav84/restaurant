package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.Exceptions.DaoException;
import com.havryliuk.restaurant.db.entity.Entity;

import java.util.List;

public interface EntityDao<K, T extends Entity> {
    boolean create(T entity);
    T findById (K id) throws DaoException;
    List<T> findAll() throws DaoException;
    T update(T entity) throws DaoException;
    boolean delete(T entity) throws DaoException;
    boolean delete(K id) throws DaoException;
}
