package com.epam.havryliuk.restaurant.db.connection;

import com.epam.havryliuk.restaurant.model.db.connection.ConnectionPool;
import com.epam.havryliuk.restaurant.model.db.connection.RestaurantConnectionPool;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantConnectionPoolTest {

    private ConnectionPool connectionPool;


//    @BeforeAll
//    void setConnection() throws DBException {
//        connectionPool = RestaurantConnectionPool.getInstance();
//    }

    @Test
    void getConnection() throws DBException, SQLException {
        connectionPool = RestaurantConnectionPool.getInstance();
        Connection firstConnection = connectionPool.getConnection();
        Connection secondConnection = connectionPool.getConnection();

        assertTrue(firstConnection.isValid(1));
        assertTrue(secondConnection.isValid(1));
        connectionPool.releaseConnection(firstConnection);
//        assertFalse(firstConnection.isValid(1));

        connectionPool.shutdown();
        assertFalse(firstConnection.isValid(1));



    }


//    @Test
//    void releaseConnection() {
////        assertTrue(connectionPool.releaseConnection().isValid(1));
//    }

//    @AfterAll
//    void shutdown() throws DBException {
//        connectionPool.shutdown();
//    }
}