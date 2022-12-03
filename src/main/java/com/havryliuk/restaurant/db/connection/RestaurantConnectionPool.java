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
import java.util.ArrayList;
import java.util.List;

public class RestaurantConnectionPool implements ConnectionPool {
    private static final Logger log = LogManager.getLogger(RestaurantConnectionPool.class);// todo add logs for class


    private static String url;
    private static String user;
    private static String password;
    private static int initialPoolSize = 10;
    private static int maxPoolSize = 20;
    private static int maxTimeout = 30;

//    private static final Properties properties = new Properties();

    private static volatile RestaurantConnectionPool instance;

    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();

    private static DataSource ds = null;
    private static Context envContext;


    public static RestaurantConnectionPool getInstance() throws DBException {
//        PropertiesLoader.getProperties(Constants.APP_PROPERTIES_FILE);
//        loadProperties();
//        initVariables();
        try {
            Context initContext = new InitialContext();
            envContext = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/Restaurant");
        } catch (NamingException e) {
            log.error("Can't get Initial context for ");
            throw new DBException(e);
        }
        return getRestaurantConnectionPool();
    }



    private static void loadProperties() throws DBException {
//        properties.setProperty("database.url", "jdbc:mysql://localhost:3306/restaurant");
//        properties.setProperty("database.user", "root");
//        properties.setProperty("database.password", "1111");
//        properties.setProperty("connection_pool.initial_size", "10");
//        properties.setProperty("connection_pool.max_size", "20");
//        properties.setProperty("connection_pool.max_timeout", "30");



//        try(FileReader fileReader = new FileReader(Constants.APP_PROPERTIES_FILE)) {
//            properties.load(fileReader);
//        } catch (IOException e) {
//            log.error("Error in loading properties from " + Constants.APP_PROPERTIES_FILE, e);
//            throw new DBException(e);
//        }
    }




//    private static void loadProperties() throws DBException {
//        try(FileReader fileReader = new FileReader(Constants.APP_PROPERTIES_FILE)) {
//            properties.load(fileReader);
//        } catch (IOException e) {
//            log.error("Error in loading properties from " + Constants.APP_PROPERTIES_FILE, e);
//            throw new DBException(e);
//        }
//    }




//    private static void initVariables() {
//        url = (String) properties.get(Constants.DATABASE_URL);
//        user = (String) properties.get(Constants.DATABASE_USER);
//        password = (String) properties.get(Constants.DATABASE_PASSWORD);
//        initialPoolSize = Integer.parseInt((String) properties.get(Constants.INITIAL_POOL_SIZE));
//        maxPoolSize = Integer.parseInt((String) properties.get(Constants.MAX_POOL_SIZE));
//        maxTimeout = Integer.parseInt((String) properties.get(Constants.MAX_TIMEOUT));
//    }

    private static RestaurantConnectionPool getRestaurantConnectionPool() throws DBException {
        RestaurantConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (RestaurantConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    List<Connection> pool = new ArrayList<>(initialPoolSize);
                    for (int i = 0; i < initialPoolSize; i++) {
                        pool.add(createConnection());
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

        Connection connection;
        synchronized (RestaurantConnectionPool.class) {

            connection = connectionPool.remove(connectionPool.size() - 1);
            connection = createNewIfCurrentNotValid(connection);
            usedConnections.add(connection);
        }
        return connection;
    }


    @Override
    public void shutdown() throws DBException {
//        usedConnections.forEach(this::releaseConnection);

//        for (Connection connection : usedConnections) {//todo ConcurrentModificationException
//            releaseConnection(connection);
//        }

        for (int i = 0; i < usedConnections.size(); i++) {
            Connection connection = usedConnections.get(i);
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
        synchronized (RestaurantConnectionPool.class) {
            if (connection != null) {                           // todo is it correct to check for null here?
                connectionPool.add(connection);
                return usedConnections.remove(connection);
            }
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
                connectionPool.add(createConnection());
            } else {
                log.error("Maximum pool size reached, no available connections!");
                throw new DBException(
                        "Maximum pool size reached, no available connections!");
            }
        }
    }

    private Connection createNewIfCurrentNotValid(Connection connection) throws DBException {
        try {
            if(!connection.isValid(maxTimeout)){
                connection = createConnection();
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return connection;
    }


    private static Connection createConnection() throws DBException {
        try {
//
//            DataSource ds = null;
//            Context initContext = new InitialContext();
//            Context envContext  = (Context)initContext.lookup("java:/comp/env");
//            ds = (DataSource)envContext.lookup("jdbc/Restaurant");
            Connection conn = ds.getConnection();

            return conn;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}