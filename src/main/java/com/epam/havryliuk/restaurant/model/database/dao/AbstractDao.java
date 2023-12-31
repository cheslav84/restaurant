package com.epam.havryliuk.restaurant.model.database.dao;

import com.epam.havryliuk.restaurant.model.entity.Entity;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> {
    private static final Logger LOG = LogManager.getLogger(AbstractDao.class);

    protected Connection connection;

    public AbstractDao() {
    }

    public abstract T create(T entity) throws DAOException, SQLIntegrityConstraintViolationException;

    public abstract Optional<T> findById(long id) throws DAOException;

    public abstract List<T> findAll() throws DAOException;

    public abstract T update(T entity) throws DAOException;

    public abstract boolean delete(T entity) throws DAOException;

    public abstract boolean delete(long id) throws DAOException;

    public void close(Statement statement) {//todo check usages
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOG.error("SQL exception while closing Statement.");
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
