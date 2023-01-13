package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.OrderFields;
import com.epam.havryliuk.restaurant.model.constants.queries.OrderQuery;
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
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_BY_USER_ID_ADDRESS_AND_STATUS)) {
            int k=0;
            stmt.setLong(++k, user.getId());
            stmt.setString(++k, address);
            stmt.setString(++k, bookingStatus.name());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = mapOrder(rs);//todo наскільки коректно мапити неповноцінний об'єкт, якщо в ньому використувується не всі поля?
                    order.setUser(user);
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

//    public boolean addNewDishesToOrder(Order order, Dish dish, int amount) throws DAOException, DuplicatedEntityException {
//        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.ADD_DISH_TO_BASKET)) {
//            int k = 0;
//            stmt.setLong(++k, order.getId());
//            stmt.setLong(++k, dish.getId());
//            stmt.setInt(++k, amount);
//            stmt.setBigDecimal(++k, dish.getPrice());
//            stmt.executeUpdate();
//            log.debug("Dish has been added to database");
//        }  catch (SQLIntegrityConstraintViolationException e) {
//            String errorMassage = "Something went wrong. Dish haven't been added to basket. Try please again later.";
//            log.error(errorMassage, e);
//            throw new DuplicatedEntityException(errorMassage, e);
//        }catch (SQLException e) {
//            String errorMassage = "Something went wrong. Dish haven't been added to basket. Try please again later.";
//            log.error(errorMassage, e);
//            throw new DAOException(errorMassage, e);
//        }
//        return true;
//    }

    public boolean changeBookingStatus(Order order, BookingStatus status) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public List<Order> getByUserSortedByTime(User user) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_ALL_ORDERS_BY_USER)) {
            stmt.setLong(1, user.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = mapOrder(rs);
                    order.setUser(user);
                    orders.add(order);
                }
            }
            log.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from database. ", e);
            throw new DAOException(e);
        }
        return orders;
    }

    public List<Order> getOrdersSortedByStatusThenTime(int offset, int noOfRecords) throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.GET_CONFIRMED_ORDERS_SORTED_BY_STATUS_THEN_TIME)) {
           int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, noOfRecords);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
            log.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from database. ", e);
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
            log.debug("Number of orders has been received from database: " + numberOfOrders);
        } catch (SQLException e) {
            log.error("Number of orders has not been received from database.");
            throw new DAOException(e);
        }
        return numberOfOrders;

    }

    public List<Order> getByBookingStatus(BookingStatus status) throws DAOException {
        throw new UnsupportedOperationException();
    }



    private Order mapOrder(ResultSet rs) throws SQLException {
        long id = rs.getLong(OrderFields.ORDER_ID);
        String address = rs.getString(OrderFields.ORDER_ADDRESS);
        String phoneNumber = rs.getString(OrderFields.ORDER_PHONE_NUMBER);
        boolean isPayed = rs.getBoolean(OrderFields.ORDER_PAYMENT);
        Date creationDate = rs.getTimestamp(OrderFields.ORDER_CREATION_DATE);
        Date closeDate = rs.getTimestamp(OrderFields.ORDER_CLOSE_DATE);
        long bookingStatusId = rs.getLong(OrderFields.ORDER_BOOKING_STATUS);
        BookingStatus bookingStatus = BookingStatus.getStatus(bookingStatusId);
        return Order.getInstance(id, address, phoneNumber, isPayed, creationDate, closeDate, bookingStatus);
    }

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
    public Optional<Order> findById(long id) throws DAOException {
        throw new UnsupportedOperationException();

//        Order order = null;
//        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.FIND_ORDER_BY_ID)) {
//            stmt.setLong(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    order = mapOrder(rs);
//                }
//            }
//            log.debug("The order with \"id=" + id + "\" has been received from database.");
//        } catch (SQLException e) {
//            log.error("Error in getting order with \"id=" + id + "\" from database. ", e);
//            throw new DAOException(e);
//        }
//        return order;
    }


    public boolean changeOrderStatus(long orderId, BookingStatus bookingStatus) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.CHANGE_ORDER_STATUS_BY_ID)) {
            int k = 1;
            stmt.setLong(k++, orderId);
            stmt.setString(k++, bookingStatus.name());
            stmt.executeUpdate();
            log.debug("The status in order with id \"" + orderId +
                    "\", has been successfully changed");
        } catch (SQLException e) {
            log.error("The status in order has not been changed", e);
            throw new DAOException(e);
        }
        return true;
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
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.DELETE_ORDER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.debug("Order has been removed from order in database");
        } catch (SQLException e) {
            log.error("Something went wrong. Order haven't been removed.");
            throw new DAOException(e);
        }
        return true;
    }

    public boolean deleteDishFromOrderById(long orderId, long dishId) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(OrderQuery.REMOVE_DISH_FROM_ORDER)) {
            int k=0;
            stmt.setLong(++k, orderId);
            stmt.setLong(++k, dishId);
            stmt.executeUpdate();
            log.debug("Dish has been removed from order in database");
        } catch (SQLException e) {
            log.error("Something went wrong. Dish haven't been removed. Try please again later.");
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
            log.debug("Number of dishes in order has been received from database.");
        } catch (SQLException e) {
            log.error("Number of dishes in order has not been received from database.");
            throw new DAOException(e);
        }
        return numberOfDishes;
    }



}
