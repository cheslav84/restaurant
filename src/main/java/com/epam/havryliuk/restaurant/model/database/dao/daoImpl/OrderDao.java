package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.entityMappers.OrderMapper;
import com.epam.havryliuk.restaurant.model.database.databaseFieds.OrderFields;
import com.epam.havryliuk.restaurant.model.database.queries.OrderQuery;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order> {
    private static final Logger LOG = LogManager.getLogger(OrderDao.class);

    @Override
    public Order create(Order order) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.ADD_ORDER,
                Statement.RETURN_GENERATED_KEYS)) {
                setOrderParameters(order, stmt);
                int insertionAmount = stmt.executeUpdate();
                if (insertionAmount > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            order.setId(rs.getLong(1));
                        }
                    }
                }
            LOG.debug("The order has been added to database.");
        } catch (SQLException e) {
            String message = "Error in inserting order to database.";
            LOG.error(message, e);
            throw new DAOException(message, e);
        }
        return order;
    }

    public Order geByUserAddressStatus(User user, String address, BookingStatus bookingStatus) throws DAOException {
        Order order = null;
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_BY_USER_ID_ADDRESS_AND_STATUS)) {
            int k = 0;
            stmt.setLong(++k, user.getId());
            stmt.setString(++k, address);
            stmt.setString(++k, bookingStatus.name());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = OrderMapper.mapOrder(rs);
                    order.setUser(user);//todo виконується в сервісі
                }
            }
            LOG.debug("Order has been received from database.");
        } catch (SQLException e) {
            String errorMassage = "Searched order is absent in database.";
            LOG.error(errorMassage, e);
            throw new DAOException(errorMassage, e);
        }
        return order;
    }

    public List<Order> getByUserSortedByTime(User user) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_ALL_ORDERS_BY_USER)) {
            stmt.setLong(1, user.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = OrderMapper.mapOrder(rs);
                    order.setUser(user);
                    orders.add(order);
                }
            }
            LOG.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            LOG.error("Error in getting list of dishes from database. ", e);
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getPartOfOrders(int offset, int noOfRecords, OrderSorting sorting) throws DAOException {
        List<Order> orders = new ArrayList<>();
        String query;
        if (sorting.equals(OrderSorting.STATUS)) {
            query = OrderQuery.GET_CONFIRMED_ORDERS_SORTED_BY_STATUS_THEN_TIME;
        } else {
            query = OrderQuery.GET_CONFIRMED_ORDERS_SORTED_BY_TIME_THEN_STATUS;
        }
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, noOfRecords);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(OrderMapper.mapOrder(rs));
                }
            }
            LOG.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            LOG.error("Error in getting list of dishes from database. ", e);
            throw new DAOException(e);
        }
        return orders;
    }

    public int getNoOfOrders() throws DAOException {
        int numberOfOrders = 0;
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_NUMBER_OF_CONFIRMED_ORDERS)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    numberOfOrders = rs.getInt(OrderFields.NUMBER_OF_ORDERS);
                }
            }
            LOG.debug("Number of orders has been received from database: " + numberOfOrders);
        } catch (SQLException e) {
            LOG.error("Number of orders has not been received from database.");
            throw new DAOException(e);
        }
        return numberOfOrders;

    }

//    private Order mapOrder(ResultSet rs) throws SQLException {
//        long id = rs.getLong(OrderFields.ORDER_ID);
//        String address = rs.getString(OrderFields.ORDER_ADDRESS);
//        String phoneNumber = rs.getString(OrderFields.ORDER_PHONE_NUMBER);
//        boolean isPayed = rs.getBoolean(OrderFields.ORDER_PAYMENT);
//        Date creationDate = rs.getTimestamp(OrderFields.ORDER_CREATION_DATE);
//        Date closeDate = rs.getTimestamp(OrderFields.ORDER_CLOSE_DATE);
//        long bookingStatusId = rs.getLong(OrderFields.ORDER_BOOKING_STATUS);
//        BookingStatus bookingStatus = BookingStatus.getStatus(bookingStatusId);
//        return Order.getInstance(id, address, phoneNumber, isPayed, creationDate, closeDate, bookingStatus);
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
            LOG.debug("Searched order creation date has not been found in database");
        } catch (SQLException e) {
            String errorMassage = "Error getting date from database.";
            LOG.error(errorMassage, e);
            throw new DAOException(errorMassage, e);
        }
        return date;
    }
    @SuppressWarnings("UnusedAssignment")
    private void setOrderParameters(Order order, PreparedStatement stmt) throws SQLException {
        int k = 1;
        stmt.setString(k++, order.getAddress());
        stmt.setString(k++, order.getPhoneNumber());
        stmt.setBoolean(k++, order.isPayed());
        stmt.setLong(k++, order.getUser().getId());
        stmt.setLong(k++, order.getBookingStatus().getId());
    }

    @Override
    public Optional<Order> findById(long id) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("UnusedAssignment")
    public boolean changeOrderStatus(long orderId, BookingStatus bookingStatus) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.CHANGE_ORDER_STATUS_BY_ID)) {
            int k = 1;
            stmt.setLong(k++, orderId);
            stmt.setString(k++, bookingStatus.name());
            stmt.executeUpdate();
            LOG.debug("The status in order with id \"{}\", has been successfully changed", orderId );
        } catch (SQLException e) {
            String errorMessage = "The status in order has not been changed";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return true;
    }

    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order update(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.DELETE_ORDER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            LOG.debug("Order has been removed from order in database");
        } catch (SQLException e) {
            LOG.error("Something went wrong. Order haven't been removed.");
            throw new DAOException(e);
        }
        return true;
    }

    public boolean deleteDishFromOrderById(long orderId, long dishId) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.REMOVE_DISH_FROM_ORDER)) {
            int k = 0;
            stmt.setLong(++k, orderId);
            stmt.setLong(++k, dishId);
            stmt.executeUpdate();
            LOG.debug("Dish has been removed from order in database");
        } catch (SQLException e) {
            LOG.error("Something went wrong. Dish haven't been removed. Try please again later.");
            throw new DAOException(e);
        }
        return true;
    }

    public int findDishesNumberInOrder(long orderId) throws DAOException {
        int numberOfDishes = 0;
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_NUMBER_DISHES_IN_ORDER)) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    numberOfDishes = rs.getInt(OrderFields.NUMBER_OF_DISHES_IN_ORDER);
                }
            }
            LOG.debug("Number of dishes in order has been received from database.");
        } catch (SQLException e) {
            LOG.error("Number of dishes in order has not been received from database.");
            throw new DAOException(e);
        }
        return numberOfDishes;
    }

}
