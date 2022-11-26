package com.havryliuk.restaurant.db.connection;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.exceptions.DBException;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RestaurantConnectionPool implements ConnectionPool {

    static Logger log = Logger.getLogger(RestaurantConnectionPool.class.getName());

    private static String url;
    private static String user;
    private static String password;
    private static int initialPoolSize;
    private static int maxPoolSize;
    private static int maxTimeout;
    private static final Properties properties = new Properties();

    private static volatile RestaurantConnectionPool instance;//todo it's not primitive type. Ask???




    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();


    public static RestaurantConnectionPool getInstance() throws DBException {
        loadProperties();
        initVariables();
        return getRestaurantConnectionPool();
    }

    private static void loadProperties() throws DBException {
        try(FileReader fileReader = new FileReader(Constants.APP_PROPERTIES_FILE)) {
            properties.load(fileReader);
        } catch (IOException e) {
            log.error("Error in loading properties from " + Constants.APP_PROPERTIES_FILE, e);
            throw new DBException(e);
        }
    }

    private static void initVariables() {
        url = (String) properties.get(Constants.DATABASE_URL);
        user = (String) properties.get(Constants.DATABASE_USER);
        password = (String) properties.get(Constants.DATABASE_PASSWORD);
        initialPoolSize = Integer.parseInt((String) properties.get(Constants.INITIAL_POOL_SIZE));
        maxPoolSize = Integer.parseInt((String) properties.get(Constants.MAX_POOL_SIZE));
        maxTimeout = Integer.parseInt((String) properties.get(Constants.MAX_TIMEOUT));
    }

    private static RestaurantConnectionPool getRestaurantConnectionPool() throws DBException {
        RestaurantConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (RestaurantConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    List<Connection> pool = new ArrayList<>(initialPoolSize);
                    for (int i = 0; i < initialPoolSize; i++) {
                        pool.add(createConnection(url, user, password));
                    }
                    instance = localInstance = new RestaurantConnectionPool(pool);
                }
            }
        }
        return localInstance;
    }

    private RestaurantConnectionPool(List<Connection> pool) {
        this.connectionPool = pool;
    }


    @Override
    public Connection getConnection() throws DBException {//todo maybe throw another type of Exception?
        addConnectionIfPoolIsEmpty();
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        connection = createNewIfCurrentNotValid(connection);
        usedConnections.add(connection);
        return connection;
    }


    @Override
    public void shutdown() throws DBException {
//        usedConnections.forEach(this::releaseConnection);//todo UnsupportedOperationException

        for (Connection connection : usedConnections) {//todo UnsupportedOperationException
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
    public boolean releaseConnection(Connection connection) {//todo while Exception occurs in giving Connection, it's possible to get null in finally blocks.
        if (connection != null) {                           // todo is it correct to check for null here?
            connectionPool.add(connection);
            return usedConnections.remove(connection);
        }
        return false;
    }

    @Override
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    private void addConnectionIfPoolIsEmpty() throws DBException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxPoolSize) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                throw new DBException(
                        "Maximum pool size reached, no available connections!");
            }
        }
    }

    private Connection createNewIfCurrentNotValid(Connection connection) throws DBException {
        try {
            if(!connection.isValid(maxTimeout)){
                connection = createConnection(url, user, password);
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