package com.epam.havryliuk.restaurant.model.database.dao;

import com.epam.havryliuk.restaurant.model.database.connection.ConnectionManager;
import com.epam.havryliuk.restaurant.model.entity.Entity;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    private static final Logger log = LogManager.getLogger(AbstractDao.class);

    protected ConnectionManager connectionManager;
    public abstract boolean create(T entity) throws DAOException;
//    public abstract T findByName(String name) throws DBException;// todo як шукати в таких випадках? Ім'я не є унікальним ні в юзера, ні в блюда. Можна за іншим ідентефікатором. Чи видалити взагалі з інтерфейсу?
    public abstract T findById(long id) throws DAOException;
    public abstract List<T> findAll() throws DAOException;
    public abstract T update(T entity) throws DAOException;
    public abstract boolean delete(T entity) throws DAOException;
    public abstract boolean delete(long id) throws DAOException;

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            log.error("SQL exception while closing Statement.");
        }
    }
}
