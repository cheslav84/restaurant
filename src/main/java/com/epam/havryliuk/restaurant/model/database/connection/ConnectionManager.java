package com.epam.havryliuk.restaurant.model.database.connection;

import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static final Logger log = LogManager.getLogger(ConnectionManager.class);// todo add logs for class

    private static volatile ConnectionManager instance;

    private static DataSource ds = null;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() throws DBException {
        initDataSource();
        ConnectionManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionManager();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws DBException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            String errorMessage = "Database connection wasn't established";
            log.error(errorMessage, e);
            throw new DBException(errorMessage, e);
        }
    }



    private static void initDataSource() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/Restaurant");
        } catch (NamingException e) {
            log.error("Can't get Initial context for DataSource.", e);
            throw new DBException(e);
        }
    }


    public void close(AutoCloseable closeable) throws DBException {
        synchronized (ConnectionManager.class) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    log.error("Error closing " + closeable, e);
                    throw new DBException(e);
                }
            }
        }
    }

    public void setAutoCommit(Connection con, boolean value) throws DBException {
        synchronized (ConnectionManager.class) {
            try {
                con.setAutoCommit(value);
            } catch (Exception e) {
                log.error("Error setting " + value + " in connection " + con, e);
                throw new DBException(e);
            }
        }
    }


    public void rollback(Connection con) throws DBException {
        synchronized (ConnectionManager.class) {
            try {
                con.rollback();
            } catch (Exception e) {
                log.error("Enable to roll back connection" + con, e);
                throw new DBException(e);
            }
        }
    }


}