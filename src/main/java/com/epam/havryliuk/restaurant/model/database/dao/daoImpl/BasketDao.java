package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.constants.databaseFieds.BasketFields;
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
    public boolean create(Basket basket) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(BasketQuery.ADD_DISH_TO_BASKET)) {
            int k = 0;
            stmt.setLong(++k, basket.getOrder().getId());
            stmt.setLong(++k, basket.getDish().getId());
            stmt.setInt(++k, basket.getAmount());
            stmt.setBigDecimal(++k, basket.getFixedPrice());
            stmt.executeUpdate();
            log.debug("Dish has been added to database");
        }  catch (SQLIntegrityConstraintViolationException e) {
            String errorMassage = "Something went wrong. Dish haven't been added to basket. Try please again later.";
            log.error(errorMassage, e);
            throw new DuplicatedEntityException(errorMassage, e);
        }catch (SQLException e) {
            String errorMassage = "Something went wrong. Dish haven't been added to basket. Try please again later.";
            log.error(errorMassage, e);
            throw new DAOException(errorMassage, e);
        }
        return true;
    }

    @Override
    public Basket findById(long id) throws DAOException {
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

//    public Basket getBasket(Basket basket) throws DAOException {
//        Basket dublicate = null;
//        try (PreparedStatement stmt = connection.prepareStatement(BasketQuery.GET_DISH_FR0M_BASKET)) {
//            //todo неекономно витягувати цілий ентіті лише для того щоб подивитись
//            int k=0;
//            stmt.setLong(++k, basket.getOrderId());
//            stmt.setLong(++k, basket.getDishId());
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    dublicate = mapBasket(rs);
//                }
//            }
//            log.debug("Order has been received from database.");
//        } catch (SQLException e) {
//            String errorMassage = "Searched order is absent in database";
//            log.error(errorMassage, e);
//            throw new DAOException(errorMassage, e);
//        }
//        return dublicate;
//    }



    /**
     * Method gets returns dishes, and dishes amount for corespondent dish in order consequently.
     * If order is not confirmed yet (user just put it to his basket), will be displayed
     * the actual price of a dish. Otherwise, user will get the fixed price of a dish for the
     * moment of the confirmation an order.
     * @param order
     * @return
     * @throws DAOException
     */
    public List<Basket> getOrderDishes(Order order) throws DAOException {
        List<Basket>  baskets = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_ALL_BY_ORDER)) {
            stmt.setLong(1, order.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    baskets.add(mapBasket(rs, order));
//                    Dish dish = mapDish(rs);
//                    if (order.getBookingStatus() != BookingStatus.BOOKING) {
//                        dish.setPrice(rs.getBigDecimal(OrderFields.ORDER_DISH_FIXED_PRICE));
//                    }
//                    Integer amountInOrder = rs.getInt(OrderFields.ORDER_DISH_AMOUNT_IN_ORDER);
//                    dishes.put(dish, amountInOrder);
                }
            }
            log.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from database. ", e);
            throw new DAOException(e);
        }
        return baskets;
    }


    private Basket mapBasket(ResultSet rs, Order order) throws SQLException {
        DishDao dishDao = new DishDao();
        Dish dish =  dishDao.mapDish(rs);
        int amount = rs.getInt(BasketFields.DISH_AMOUNT);
        BigDecimal price = rs.getBigDecimal(BasketFields.DISH_PRICE);
        if (order.getBookingStatus() != BookingStatus.BOOKING) {
            dish.setPrice(price);
        }
//
//        int amountInOrder = rs.getInt(OrderFields.ORDER_DISH_AMOUNT_IN_ORDER);
        return Basket.getInstance(order, dish, price, amount);
    }


}
