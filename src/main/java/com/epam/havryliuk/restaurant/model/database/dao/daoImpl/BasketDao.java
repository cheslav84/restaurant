package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.constants.databaseFieds.BasketFields;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.DishFields;
import com.epam.havryliuk.restaurant.model.constants.queries.BasketQuery;
import com.epam.havryliuk.restaurant.model.constants.queries.DishQuery;
import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class BasketDao extends AbstractDao<Basket> {
    private static final Logger log = LogManager.getLogger(BasketDao.class);


    @Override
    public Basket create(Basket basket) throws DAOException, SQLIntegrityConstraintViolationException {// todo void
        try (PreparedStatement stmt = connection.prepareStatement(BasketQuery.ADD_DISH_TO_BASKET)) {
            int k = 0;
            stmt.setLong(++k, basket.getOrder().getId());
            stmt.setLong(++k, basket.getDish().getId());
            stmt.setInt(++k, basket.getAmount());
            stmt.setBigDecimal(++k, basket.getFixedPrice());
            stmt.executeUpdate();
            log.debug("Basket has been created.");
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (SQLException e) {
            throw new DAOException("Unable to create basket.", e);
        }
        return basket;
    }

    @Override
    public Optional<Basket> findById(long id) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Basket> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Basket update(Basket entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Basket entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws DAOException {
        throw new UnsupportedOperationException();
    }


    /**
     * Method returns dishes, and dishes amount for corespondent dish in order consequently.
     * If order is not confirmed yet (user just put it to his basket), will be displayed
     * the actual price of a dish. Otherwise, user will get the fixed price of a dish for the
     * moment of the confirmation an order.
     * @param order
     * @return
     * @throws DAOException
     */
    public List<Basket> getOrderDishes(Order order) throws DAOException {
        List<Basket>  baskets = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_ALL_BY_ORDER)) {//todo think
            stmt.setLong(1, order.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    baskets.add(mapBasket(rs, order));
                }
            }
            log.debug("List of baskets has been received from database. ");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return baskets;
    }

    private Basket mapBasket(ResultSet rs, Order order) throws SQLException {
        DishDao dishDao = new DishDao();//todo подумати... з BasketDao викликається DishDao
        Dish dish =  dishDao.mapDish(rs);
        int amount = rs.getInt(BasketFields.DISH_AMOUNT);
        BigDecimal price = rs.getBigDecimal(BasketFields.DISH_PRICE);
        if (order.getBookingStatus() != BookingStatus.BOOKING) {
            dish.setPrice(price);
        }
        return Basket.getInstance(order, dish, price, amount);
    }

    public Map<String, Integer> getNumberOfRequestedDishesInOrder(long orderId) throws DAOException {
        Map<String, Integer> dishes = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(BasketQuery.GET_NUMBER_OF_REQUESTED_DISHES_IN_ORDER)) {
            stmt.setLong(1, orderId);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    String dishName = rs.getString(DishFields.DISH_NAME);
                    int dishesAmount = rs.getInt(BasketFields.DISH_AMOUNT);
                    dishes.put(dishName, dishesAmount);
                }
            }
            log.debug("Number of requested dishes has been receive from database.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return dishes;
    }

}
