package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.DishFields;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.OrderFields;
import com.epam.havryliuk.restaurant.model.constants.queries.DishQuery;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishDao extends AbstractDao<Dish> {
    private static final Logger log = LogManager.getLogger(DishDao.class);
//    private final ConnectionManager connectionManager;
//
//    public DishDao() throws DAOException {
//        connectionManager = ConnectionManager.getInstance();
//    }

//    @Override
    public Dish findByName(String name) throws DAOException {
        Dish dish = null;
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_DISH_BY_NAME)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dish = mapDish(rs);
                }
            }
            log.debug("The \"" + name + "\" dish has been received from database.");
        } catch (SQLException e) {
            log.error("Error in getting dish from database. ", e);
            throw new DAOException(e);
        }
        return dish;
    }

//    @Override
    public List<Dish> findByCategory(Category category) throws DAOException {
        List<Dish> dishes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_ALL_BY_CATEGORY)) {
            stmt.setString(1, category.getCategoryName().name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            log.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from database. ", e);
            throw new DAOException(e);
        }
        return dishes;
    }


//    @Override
    public List<Dish> getSortedByName() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_NAME);
    }

//    @Override
    public List<Dish> getSortedByPrice() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_PRICE);
    }

//    @Override
    public List<Dish> getSortedByCategory() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_CATEGORY);
    }

    @Override
    public boolean create(Dish entity) {
        return false;
    }

    @Override
    public Dish findById(long id) throws DAOException {
        Dish dish = null;
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_DISH_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dish = mapDish(rs);
                }
            }
            log.debug("The dish with \"id=" + id + "\" has been received from database.");
        } catch (SQLException e) {
            log.error( "Error in getting dish from database. ", e);
            throw new DAOException(e);
        }
        return dish;
    }

    @Override
    public List<Dish> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dish update(Dish entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Dish entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws DAOException {
        throw new UnsupportedOperationException();
    }

    private List<Dish> getDishes(String query) throws DAOException {
        List<Dish> dishes = new ArrayList<>();
        try ( PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dishes.add(mapDish(rs));
            }
            log.debug("List of dishes have been received from database.");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from DB.", e);
            throw new DAOException(e);
        }
        return dishes;
    }


//    /**
//     * Method gets returns dishes, and dishes amount for corespondent dish in order consequently.
//     * If order is not confirmed yet (user just put it to his basket), will be displayed
//     * the actual price of a dish. Otherwise, user will get the fixed price of a dish for the
//     * moment of the confirmation an order.
//     * @param order
//     * @return
//     * @throws DAOException
//     */
//    public Map<Dish, Integer> getOrderDishes(Order order) throws DAOException {
//        Map<Dish, Integer>  dishes = new HashMap<>();
//        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_ALL_BY_ORDER)) {
//            stmt.setLong(1, order.getId());
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    Dish dish = mapDish(rs);
//                    if (order.getBookingStatus() != BookingStatus.BOOKING) {
//                         dish.setPrice(rs.getBigDecimal(OrderFields.ORDER_DISH_FIXED_PRICE));
//                    }
//                    Integer amountInOrder = rs.getInt(OrderFields.ORDER_DISH_AMOUNT_IN_ORDER);
//                    dishes.put(dish, amountInOrder);
//                }
//            }
//            log.debug("List of dishes (by category) has been received from database. ");
//        } catch (SQLException e) {
//            log.error("Error in getting list of dishes from database. ", e);
//            throw new DAOException(e);
//        }
//        return dishes;
//    }

    Dish mapDish(ResultSet rs) throws SQLException {
        long id = rs.getLong(DishFields.DISH_ID);
        String name = rs.getString(DishFields.DISH_NAME);
        String description = rs.getString(DishFields.DISH_DESCRIPTION);
        int weight = rs.getInt(DishFields.DISH_WEIGHT);
        BigDecimal price = rs.getBigDecimal(DishFields.DISH_PRICE);
        int amount = rs.getInt(DishFields.DISH_AMOUNT);
        String image = rs.getString(DishFields.DISH_IMAGE);
//        Category category = mapCategoryForDish(rs);
        return Dish.getInstance(id, name, description, weight, price, amount, image);
    }

    public int getNumberOfDishes(Dish dish) throws DAOException {
        int numberOfDishes = 0;
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.GET_NUMBER_OF_DISHES)) {
            stmt.setLong(1, dish.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    numberOfDishes = rs.getInt(DishFields.DISH_AMOUNT);
                }
            }
            log.debug("The number of dishes has been received from database.");
        } catch (SQLException e) {
            log.error( "Error in getting number of dishes from database. ", e);
            throw new DAOException(e);
        }
        return numberOfDishes;
    }

//    private Category mapCategoryForDish(ResultSet rs) throws SQLException { //todo low coupling
//        long id = rs.getLong(DishFields.DISH_CATEGORY_ID);
//        String name = rs.getString(CategoryFields.CATEGORY_NAME);
//        return Category.getInstance(id, CategoryName.valueOf(name));
//    }
}
