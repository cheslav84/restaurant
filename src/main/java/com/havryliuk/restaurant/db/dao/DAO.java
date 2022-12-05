package com.havryliuk.restaurant.db.dao;

import com.havryliuk.restaurant.db.entity.Entity;
import com.havryliuk.restaurant.exceptions.DBException;

import java.util.List;

public interface DAO<K, T extends Entity> {
    boolean create(T entity) throws DBException;
    T findByName(String name) throws DBException;// todo як шукати в таких випадках? Ім'я не є унікальним ні в юзера, ні в блюда. Можна за іншим ідентефікатором. Чи видалити взагалі з інтерфейсу?
    T findById(K id) throws DBException;
    List<T> findAll() throws DBException;
    boolean update(T entity) throws DBException;
    boolean delete(T entity) throws DBException;
    boolean delete(K id) throws DBException;
}
