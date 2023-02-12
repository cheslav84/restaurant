package com.epam.havryliuk.restaurant.model.database.connection;

import com.epam.havryliuk.restaurant.model.database.DatabaseContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger LOG = LogManager.getLogger(ConnectionManager.class);
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private static DataSource ds = null;

    static {
        initDataSource();
        LOG.info("DataSource initialised.");
    }

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
//        ConnectionManager localInstance = instance;
//        if (localInstance == null) {
//            synchronized (ConnectionManager.class) {
//                localInstance = instance;
//                if (localInstance == null) {
//                    instance = localInstance = new ConnectionManager();
//                    log.debug("Connection manager has been initialised.");
//                }
//            }
//        }
//        return localInstance;
        LOG.debug("It is asked for instance of DataSource.");
        return INSTANCE;
    }

    private static void initDataSource() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(DatabaseContext.CONTEXT);
            ds = (DataSource) envContext.lookup(DatabaseContext.SOURCE);
        } catch (NamingException e) {
            LOG.error("Can't get Initial context for DataSource.", e);
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            String errorMessage = "Database connection hasn't been established.";
            LOG.error(errorMessage, e);
            throw new SQLException(errorMessage, e);
        }
    }


    public void close(AutoCloseable closeable) {
//        synchronized (ConnectionManager.class) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOG.error("Error closing " + closeable, e);
            }
//            }
        }
    }

}