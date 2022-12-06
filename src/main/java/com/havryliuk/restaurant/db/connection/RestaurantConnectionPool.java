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
import java.util.concurrent.CopyOnWriteArrayList;

public class RestaurantConnectionPool implements ConnectionPool {// TODO CHANGE TO DB MANAGER. НАРАЗІ ВЗЯВ CONNECTION З TOMCAT DATASOURSE
    private static final Logger log = LogManager.getLogger(RestaurantConnectionPool.class);// todo add logs for class


    private static String url;//todo
    private static String user;//todo
    private static String password;//todo
    private static int initialPoolSize = 10;
    private static int maxPoolSize = 20;
    private static int maxTimeout = 30;

//    private static final Properties properties = new Properties();

    private static volatile RestaurantConnectionPool instance;

    private List<Connection> connectionPool = null;
//    private final List<Connection> usedConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new CopyOnWriteArrayList<>();

    private static DataSource ds = null;
    private static Context envContext;

    private RestaurantConnectionPool() {
    }

    public static RestaurantConnectionPool getInstance() throws DBException {
        initDataSource();
        return getRestaurantConnectionPool();
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

    private static RestaurantConnectionPool getRestaurantConnectionPool() throws DBException {
        RestaurantConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (RestaurantConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
//                    List<Connection> pool = new ArrayList<>(initialPoolSize);
                    List<Connection> pool = new CopyOnWriteArrayList<>();
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
            Connection conn = ds.getConnection();
            return conn;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}