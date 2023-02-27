package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.entity.mapper.BasketMapper;
import com.epam.havryliuk.restaurant.model.database.databaseFieds.BasketFields;
import com.epam.havryliuk.restaurant.model.database.databaseFieds.DishFields;
import com.epam.havryliuk.restaurant.model.database.queries.BasketQuery;
import com.epam.havryliuk.restaurant.model.database.queries.DishQuery;
import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class BasketDao extends AbstractDao<Basket> {
    private static final Logger LOG = LogManager.getLogger(BasketDao.class);

    @Override
    public Basket create(Basket basket) throws DAOException, SQLIntegrityConstraintViolationException {
        try (PreparedStatement stmt = connection.prepareStatement(BasketQuery.ADD_DISH_TO_BASKET)) {
            int k = 0;
            stmt.setLong(++k, basket.getOrder().getId());
            stmt.setLong(++k, basket.getDish().getId());
            stmt.setInt(++k, basket.getAmount());
            stmt.setBigDecimal(++k, basket.getFixedPrice());
            stmt.executeUpdate();
            LOG.debug("Basket has been created.");
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (SQLException e) {
            throw new DAOException("Unable to create basket.", e);
        }
        return basket;
    }

    /**
     * Method returns list of Basket, that is: Order, Dish, dish price and dish amount.
     * If order is not confirmed yet (user just put it to his basket), will be displayed
     * the actual price of a dish. Otherwise, user will get the fixed price of a dish for the
     * moment of the confirmation an order.
     */
    public List<Basket> getOrderDishes(Order order) throws DAOException {
        List<Basket> baskets = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_ALL_BY_ORDER)) {
            stmt.setLong(1, order.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    baskets.add(BasketMapper.mapBasket(rs, order));
                }
            }
            LOG.debug("List of baskets has been received from database. ");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return baskets;
    }

//    /**
//     * Method maps Dish, dish amount and dish price from ResultSet.
//     * If BookingStatus is "Booking" - the order is not confirmed yet by user,
//     * then for Basket sets price from Dish table, otherwise, for Basket sets
//     * fixed price from table with baskets
//     */
//    private Basket mapBasket(ResultSet rs, Order order) throws SQLException {
//        DishDao dishDao = new DishDao();
//        Dish dish = dishDao.mapDish(rs);
//        int amount = rs.getInt(BasketFields.DISH_AMOUNT);
//        BigDecimal price = rs.getBigDecimal(BasketFields.DISH_PRICE);
//        if (order.getBookingStatus() != BookingStatus.BOOKING) {
//            dish.setPrice(price);
//        }
//        return Basket.getInstance(order, dish, price, amount);
//    }

    public Map<String, Integer> getNumberOfRequestedDishesInOrder(long orderId) throws DAOException {
        Map<String, Integer> dishes = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(BasketQuery.GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER)) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String dishName = rs.getString(DishFields.DISH_NAME);
                    int dishesAmount = rs.getInt(BasketFields.DISH_AMOUNT);
                    dishes.put(dishName, dishesAmount);
                }
            }
            LOG.debug("Number of requested dishes has been receive from database.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return dishes;
    }

    @Override
    public Optional<Basket> findById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Basket> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Basket update(Basket entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Basket entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }
}
