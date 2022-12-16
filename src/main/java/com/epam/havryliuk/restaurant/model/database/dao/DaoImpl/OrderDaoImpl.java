package com.epam.havryliuk.restaurant.model.database.dao.DaoImpl;

import com.epam.havryliuk.restaurant.model.database.connection.ConnectionManager;
import com.epam.havryliuk.restaurant.model.database.dao.OrderDao;
import com.epam.havryliuk.restaurant.model.database.dao.databaseFieds.OrderFields;
import com.epam.havryliuk.restaurant.model.database.dao.queries.OrderQuery;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.services.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final Logger log = LogManager.getLogger(OrderService.class);
    private final ConnectionManager connectionManager;

    public OrderDaoImpl() throws DBException {
        connectionManager = ConnectionManager.getInstance();
    }

    @Override
    public boolean create(Order order) throws DBException {
        Connection con = null;
        try {
            con = connectionManager.getConnection();
            con.setAutoCommit(false);
            addOrder(order, con);
            log.debug("The order has been added to database.");
        } catch (SQLException e) {
            String message = "Something went wrong. Try to make an order later please.";
            log.error("Error in inserting order to database.", e);
            connectionManager.rollback(con);
            throw new DBException(message, e);
        } finally {
            connectionManager.setAutoCommit(con, true);
            connectionManager.close(con);
        }
        return true;
    }

    @Override
    public Order geByUserIdAddressStatus(long userId, String address, BookingStatus bookingStatus) throws DBException {//todo think if it could be a list
        Order order = null;

        try (Connection con = connectionManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(OrderQuery.GET_BY_USER_ID_ADDRESS_AND_STATUS)) {
            int k=0;
            stmt.setLong(++k, userId);
            stmt.setString(++k, address);
            stmt.setString(++k, bookingStatus.name());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = mapOrder(rs);
                }
            }
            log.debug("Order has been received from database.");
        } catch (SQLException e) {
            String errorMassage = "Searched order is absent in database";
            log.error(errorMassage, e);
            throw new DBException(errorMassage, e);
        }
        return order;
    }

    @Override
    public boolean addNewDishesToOrder(Order order, Dish dish, int amount) throws DBException {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(OrderQuery.ADD_DISH_TO_BASKET)) {
            int k=0;
            stmt.setLong(++k, order.getId());
            stmt.setLong(++k, dish.getId());
            stmt.setInt(++k, amount);
            stmt.setBigDecimal(++k, dish.getPrice());
            stmt.executeUpdate();
            log.debug("Dish has been added to database");
        } catch (SQLException e) {
            String errorMassage = "Something went wrong. Dish haven't been added to basket. Try please again later.";
            log.error(errorMassage, e);
            throw new DBException(errorMassage, e);
        }
        return false;
    }

    @Override
    public boolean changeBookingStatus(Dish dish, BookingStatus status) throws DBException {
        return false;
    }

    @Override
    public List<Order> getByUserSortedByTime() throws DBException {
        return null;
    }

    @Override
    public List<Order> getByBookingStatus(BookingStatus status) throws DBException {
        return null;
    }


    private Order mapOrder(ResultSet rs) throws SQLException {
        long id = rs.getLong(OrderFields.ORDER_ID);
        String address = rs.getString(OrderFields.ORDER_ADDRESS);
        String phoneNumber = rs.getString(OrderFields.ORDER_PHONE_NUMBER);
        boolean isPayed = rs.getBoolean(OrderFields.ORDER_PAYMENT);
        Date creationDate = rs.getTimestamp(OrderFields.ORDER_CREATION_DATE);
        Date closeDate = rs.getTimestamp(OrderFields.ORDER_CLOSE_DATE);

        return Order.getInstance(id, address, phoneNumber, isPayed, creationDate, closeDate);
    }

    private void addOrder(Order order, Connection con) throws DBException, SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(OrderQuery.ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            setOrderParameters(order, stmt);
            int insertionAmount = stmt.executeUpdate();
            if (insertionAmount > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getLong(1));
                    }
                }
            }
            Date creationDate = getCreationDate(con, order.getId());
            order.setCreationDate(creationDate);
        } catch (SQLException e) {
            log.error("Error in inserting order to database.", e);
            throw new SQLException(e);
        } finally {
            connectionManager.close(stmt);
        }
    }


    private Date getCreationDate(Connection con, long orderId) throws SQLException {
        Date date = null;
        try (PreparedStatement stmt = con.prepareStatement(OrderQuery.GET_CREATION_DATE_BY_ID)) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    date = rs.getTimestamp(OrderFields.ORDER_CREATION_DATE);
                }
            }
            log.debug("Searched order creation date has not been found in database");
        } catch (SQLException e) {
            String errorMassage = "Searched order is absent in database";
            log.error(errorMassage, e);
            throw new SQLException(errorMassage, e);
        }
        return date;
    }

    private void setOrderParameters(Order order, PreparedStatement stmt) throws SQLException {
        int k = 1;
        stmt.setString(k++, order.getAddress());
        stmt.setString(k++, order.getPhoneNumber());
        stmt.setBoolean(k++, order.isPayed());
        stmt.setLong(k++, order.getUser().getId());
        stmt.setLong(k++, order.getBookingStatus().getId());
    }

    @Override
    public Order findByName(String name) throws DBException {
        return null;
    }

    @Override
    public Order findById(long id) throws DBException {
        return null;
    }

    @Override
    public List<Order> findAll() throws DBException {
        return null;
    }

    @Override
    public boolean update(Order entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(Order entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DBException {
        return false;
    }
}
