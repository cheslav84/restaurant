package com.epam.havryliuk.restaurant.model.database.dao;

import com.epam.havryliuk.restaurant.model.entity.Entity;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;

import java.util.List;

public interface DAO<T extends Entity> {
    boolean create(T entity) throws DBException;
    T findByName(String name) throws DBException;// todo як шукати в таких випадках? Ім'я не є унікальним ні в юзера, ні в блюда. Можна за іншим ідентефікатором. Чи видалити взагалі з інтерфейсу?
    T findById(long id) throws DBException;
    List<T> findAll() throws DBException;
    boolean update(T entity) throws DBException;
    boolean delete(T entity) throws DBException;
    boolean delete(long id) throws DBException;
}
