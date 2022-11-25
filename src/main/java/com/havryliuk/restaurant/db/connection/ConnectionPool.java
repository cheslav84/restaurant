package com.havryliuk.restaurant.db.connection;

import com.havryliuk.restaurant.exceptions.DBException;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection() throws DBException;
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();
    String getPassword();
    void shutdown() throws DBException;
    int getSize();
}
