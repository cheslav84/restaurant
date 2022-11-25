package com.havryliuk.restaurant.db.connection;

import com.havryliuk.restaurant.Constants;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConnectionManager {
    static Logger log = Logger.getLogger(ConnectionManager.class.getName());

    private static final Properties properties = new Properties();
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try {
            properties.load(new FileReader(Constants.APP_PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace(); //todo log
        }
        URL = (String) properties.get(Constants.DATABASE_URL);
        USER = (String) properties.get(Constants.DATABASE_USER);
        PASSWORD = (String) properties.get(Constants.DATABASE_PASSWORD);
    }

    private ConnectionManager() {
    }

//    public static Connection createConnection() throws DBException {
//        ConnectionPool connectionPool = RestaurantConnectionPool.create(URL, USER, PASSWORD);
//        return connectionPool.getConnection();
//    }

}