package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.database.connection.ConnectionManager;
import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.database.dao.databaseFieds.OrderFields;
import com.epam.havryliuk.restaurant.model.database.dao.queries.DishQuery;
import com.epam.havryliuk.restaurant.model.database.dao.queries.OrderQuery;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao extends AbstractDao<Order> {
    private static final Logger log = LogManager.getLogger(OrderDao.class);

    @Override
    public boolean create(Order order) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.ADD_ORDER, Statement.RETURN_GENERATED_KEYS)) {
                setOrderParameters(order, stmt);
                int insertionAmount = stmt.executeUpdate();
                if (insertionAmount > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            order.setId(rs.getLong(1));
                        }
                    }
                }
            log.debug("The order has been added to database.");
        } catch (SQLException e) {
            String message = "Something went wrong. Try to make an order later please.";
            log.error("Error in inserting order to database.", e);
            throw new DAOException(message, e);
        }
        return true;
    }

    public Order geByUserAddressStatus(User user, String address, BookingStatus bookingStatus) throws DAOException {//todo think if it could be a list
        Order order = null;
        try ( PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_BY_USER_ID_ADDRESS_AND_STATUS)) {
            int k=0;
            stmt.setLong(++k, user.getId());
            stmt.setString(++k, address);
            stmt.setString(++k, bookingStatus.name());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = mapOrder(rs, user);
                }
            }
            log.debug("Order has been received from database.");
        } catch (SQLException e) {
            String errorMassage = "Searched order is absent in database";
            log.error(errorMassage, e);
            throw new DAOException(errorMassage, e);
        }
        return order;
    }

    public boolean addNewDishesToOrder(Order order, Dish dish, int amount) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.ADD_DISH_TO_BASKET)) {
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
            throw new DAOException(errorMassage, e);
        }
        return false;
    }

    public boolean changeBookingStatus(Order order, BookingStatus status) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public List<Order> getByUserSortedByTime(User user) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_ALL_ORDERS_BY_USER)) {
            stmt.setLong(1, user.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs, user));
                }
            }
            log.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from database. ", e);
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getByBookingStatus(BookingStatus status) throws DAOException {
        throw new UnsupportedOperationException();
    }

    private Order mapOrder(ResultSet rs, User user) throws SQLException {
        long id = rs.getLong(OrderFields.ORDER_ID);
        String address = rs.getString(OrderFields.ORDER_ADDRESS);
        String phoneNumber = rs.getString(OrderFields.ORDER_PHONE_NUMBER);
        boolean isPayed = rs.getBoolean(OrderFields.ORDER_PAYMENT);
        Date creationDate = rs.getTimestamp(OrderFields.ORDER_CREATION_DATE);
        Date closeDate = rs.getTimestamp(OrderFields.ORDER_CLOSE_DATE);
        long bookingStatusId = rs.getLong(OrderFields.ORDER_BOOKING_STATUS);
        BookingStatus bookingStatus = BookingStatus.getStatus(bookingStatusId);
        return Order.getInstance(id, address, phoneNumber, isPayed, creationDate, closeDate, user, bookingStatus);
    }

//    private void addOrder(Order order, Connection con) throws DAOException, SQLException {
//        PreparedStatement stmt = null;
//        try {
//            stmt = con.prepareStatement(OrderQuery.ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
//            setOrderParameters(order, stmt);
//            int insertionAmount = stmt.executeUpdate();
//            if (insertionAmount > 0) {
//                try (ResultSet rs = stmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        order.setId(rs.getLong(1));
//                    }
//                }
//            }
//            Date creationDate = getCreationDate(con, order.getId());
//            order.setCreationDate(creationDate);
//        } catch (SQLException e) {
//            log.error("Error in inserting order to database.", e);
//            throw new SQLException(e);
//        } finally {
//            connectionManager.close(stmt);
//        }
//    }


    public Date getCreationDate(long orderId) throws DAOException {
        Date date = null;
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_CREATION_DATE_BY_ID)) {
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
            throw new DAOException(errorMassage, e);
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
    public Order findById(long id) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order update(Order entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Order entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws DAOException {
        throw new UnsupportedOperationException();
    }


}
