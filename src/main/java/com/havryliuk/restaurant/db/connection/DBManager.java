package com.havryliuk.restaurant.db.connection;

import com.havryliuk.restaurant.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DBManager {

    private static final Logger log = LogManager.getLogger(DBManager.class);// todo add logs for class

    private static volatile DBManager instance;

    private static DataSource ds = null;
    private static Context envContext;

    private DBManager() {
    }

    public static DBManager getInstance() throws DBException {
        initDataSource();
        DBManager localInstance = instance;
        if (localInstance == null) {
            synchronized (DBManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DBManager();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws DBException {
        try {
            Connection conn = ds.getConnection();
            return conn;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }



    private static void initDataSource() throws DBException {
        try {
            Context initContext = new InitialContext();
            envContext = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/Restaurant");
        } catch (NamingException e) {
            log.error("Can't get Initial context for DataSource.", e);
            throw new DBException(e);
        }
    }







}