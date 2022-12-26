package com.epam.havryliuk.restaurant.model.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static final Logger log = LogManager.getLogger(ConnectionManager.class);

    private static volatile ConnectionManager instance;

    private static DataSource ds = null;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        initDataSource();
        ConnectionManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionManager();
                    log.debug("Connection manager has been initialised.");
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            String errorMessage = "Database connection wasn't established.";
            log.error(errorMessage, e);
            throw new SQLException(errorMessage, e);
        }
    }



    private static void initDataSource() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/Restaurant");
        } catch (NamingException e) {
            log.error("Can't get Initial context for DataSource.", e);
        }
    }


    public void close(AutoCloseable closeable) {
        synchronized (ConnectionManager.class) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    log.error("Error closing " + closeable, e);
                }
            }
        }
    }

}