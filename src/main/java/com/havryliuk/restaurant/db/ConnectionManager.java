package com.havryliuk.restaurant.db;

import com.havryliuk.restaurant.Exceptions.DBException;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    static Logger log = Logger.getLogger(ConnectionManager.class.getName());

    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;

    static {
        try {
            properties.load(new FileReader(Constants.SETTINGS_FILE));
        } catch (IOException e) {
            e.printStackTrace(); //
        }
        DATABASE_URL = (String) properties.get(Constants.CONNECTION_URL);
    }

    private ConnectionManager() {
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }

    public static void rollback(Connection con) throws DBException {
        try {
            con.rollback();
        } catch (SQLException e) {
            log.error("Error in connection rollback" + con, e);//todo
            throw new DBException(e);
        }
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.error("Error closing " + closeable, e);
            }
        }
    }

    public static void setAutocommit(Connection con, boolean value) {
        try {
            con.setAutoCommit(value);
        } catch (Exception e) {
            log.error("Error setting " + value + " in connection " + con, e);
        }
    }

}