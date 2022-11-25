package com.havryliuk.restaurant.db.connection;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.exceptions.DBException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RestaurantConnectionPool implements ConnectionPool {

//    private String url;
//    private String user;
//    private String password;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static int INITIAL_POOL_SIZE;
    private static int MAX_POOL_SIZE;
    private static int MAX_TIMEOUT;
    private static final Properties properties = new Properties();

    private static volatile RestaurantConnectionPool instance;


    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();


    public static RestaurantConnectionPool getInstance() throws DBException {
        loadProperties();
        initialiseVariable();
        return getRestaurantConnectionPool();
    }

    private static RestaurantConnectionPool getRestaurantConnectionPool() throws DBException {
        RestaurantConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (RestaurantConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
                    for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                        pool.add(createConnection(URL, USER, PASSWORD));
                    }
                    instance = localInstance = new RestaurantConnectionPool(pool);
                }
            }
        }
        return localInstance;
    }


    private static void initialiseVariable() {
        URL = (String) properties.get(Constants.DATABASE_URL);
        USER = (String) properties.get(Constants.DATABASE_USER);
        PASSWORD = (String) properties.get(Constants.DATABASE_PASSWORD);
        INITIAL_POOL_SIZE = Integer.parseInt((String) properties.get(Constants.INITIAL_POOL_SIZE));
        MAX_POOL_SIZE = Integer.parseInt((String) properties.get(Constants.MAX_POOL_SIZE));
        MAX_TIMEOUT = Integer.parseInt((String) properties.get(Constants.MAX_TIMEOUT));
    }

    private static void loadProperties() {
        try {
            properties.load(new FileReader(Constants.SETTINGS_FILE));
        } catch (IOException e) {
            e.printStackTrace(); //todo log
        }
    }

    private RestaurantConnectionPool(List<Connection> pool) {
        this.connectionPool = pool;
    }


//    private static RestaurantConnectionPool create() throws DBException {
//        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
//        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
//            pool.add(createConnection(URL, USER, PASSWORD));
//        }
//        return new RestaurantConnectionPool(url, user, password, pool);
//    }
//
//    private RestaurantConnectionPool(String url, String user, String password, List<Connection> pool) {
//        this.url = url;
//        this.user = user;
//        this.password = password;
//        this.connectionPool = pool;
//    }

    @Override
    public Connection getConnection() throws DBException {
        addConnectionIfPoolIsEmpty();
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        connection = createNewIfCurrentNotValid(connection);
        usedConnections.add(connection);
        return connection;
    }


    @Override
    public void shutdown() throws DBException {
//        usedConnections.forEach(this::releaseConnection);

        for (Connection connection : usedConnections) {
            releaseConnection(connection);
        }

        for (Connection c : connectionPool) {
            try {
                c.close();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
        connectionPool.clear();
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getUser() {
        return USER;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    private void addConnectionIfPoolIsEmpty() throws DBException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(URL, USER, PASSWORD));
            } else {
                throw new DBException(
                        "Maximum pool size reached, no available connections!");
            }
        }
    }

    private Connection createNewIfCurrentNotValid(Connection connection) throws DBException {
        try {
            if(!connection.isValid(MAX_TIMEOUT)){
                connection = createConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return connection;
    }

    private static Connection createConnection(String url, String user, String password) throws DBException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}