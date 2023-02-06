package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.DishFields;
import com.epam.havryliuk.restaurant.model.constants.queries.DishQuery;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DishDao extends AbstractDao<Dish> {
    private static final Logger LOG = LogManager.getLogger(DishDao.class);

    public List<Dish> findAllByCategory(Category category) throws DAOException {
        return getDishesByCategory(category, DishQuery.FIND_ALL_BY_CATEGORY);
    }

    public List<Dish> findAvailableByCategory(Category category) throws DAOException {
        return getDishesByCategory(category, DishQuery.FIND_ALL_AVAILABLE_BY_CATEGORY);
    }

    public List<Dish> getDishesByCategory(Category category, String query) throws DAOException {
        List<Dish> dishes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            LOG.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            LOG.error("Error in getting list of dishes from database. ");
            throw new DAOException(e);
        }
        return dishes;

    }

    public List<Dish> getAllSortedByName() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_NAME);
    }

    public List<Dish> getAllAvailableSortedByName() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_AVAILABLE_ORDERED_BY_NAME);
    }
    public List<Dish> getAllSortedByPrice() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_PRICE);
    }
    public List<Dish> getAllAvailableSortedByPrice() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_AVAILABLE_ORDERED_BY_PRICE);
    }

    public List<Dish> getAllSortedByCategory() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_CATEGORY);
    }

    public List<Dish> getAllAvailableSortedByCategory() throws DAOException {
        return getDishes(DishQuery.FIND_ALL_AVAILABLE_ORDERED_BY_CATEGORY);
    }

    @Override
    public Dish create(Dish dish) throws DAOException {
        try(PreparedStatement stmt = connection.prepareStatement(DishQuery.ADD_DISH,
                Statement.RETURN_GENERATED_KEYS)) {
            setDishParameters(dish, stmt);
            int insertionAmount = stmt.executeUpdate();
            if (insertionAmount > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        dish.setId(rs.getLong(1));
                    }
                }
            }
            LOG.debug("The \"" + dish.getName() + "\" dish has been added to database.");
        } catch (SQLException e) {
            String message = "Something went wrong. Dish hasn't been added to database";
            LOG.error(message, e);
            throw new DAOException(message, e);
        }
        return dish;
    }

    @SuppressWarnings("UnusedAssignment")
    public void addDishToCategory(Dish dish, Category category) throws DAOException {
        try(PreparedStatement stmt = connection.prepareStatement(DishQuery.ADD_DISH_TO_CATEGORY)) {
            int k = 1;
            stmt.setLong(k++, dish.getId());
            stmt.setLong(k++, category.getId());
            stmt.executeUpdate();
            LOG.debug("The \"" + dish.getName() + "\" dish has been added to category \"" + category.name() + "\".");
        } catch (SQLException e) {
            String message = "Something went wrong. Dish hasn't been added to category";
            LOG.error(message, e);
            throw new DAOException(message, e);
        }
    }

    @SuppressWarnings("UnusedAssignment")
    public void updateDishCategory(Dish dish, Category category) throws DAOException {
        try(PreparedStatement stmt = connection.prepareStatement(DishQuery.UPDATE_DISH_CATEGORY)) {
            int k = 1;
            stmt.setLong(k++, category.getId());
            stmt.setLong(k++, dish.getId());
            stmt.executeUpdate();
            LOG.debug("The \"" + category.name() + "\" has been updated in dish \"" + dish.getName() + "\".");
        } catch (SQLException e) {
            String message = "Something went wrong. Dish category hasn't been updated.";
            LOG.error(message, e);
            throw new DAOException(message, e);
        }
    }

    @SuppressWarnings("UnusedAssignment")
    public void removeDishFromCategory(Dish dish, Category category) throws DAOException {
        try(PreparedStatement stmt = connection.prepareStatement(DishQuery.REMOVE_DISH_FROM_CATEGORY)) {
            int k = 1;
            stmt.setLong(k++, dish.getId());
            stmt.setLong(k++, category.getId());
            stmt.executeUpdate();
            LOG.debug("The \"" + category.name() + "\" has been updated in dish \"" + dish.getName() + "\".");
        } catch (SQLException e) {
            String message = "Something went wrong. Dish category hasn't been updated.";
            LOG.error(message, e);
            throw new DAOException(message, e);
        }
    }


    @Override
    public Optional<Dish> findById(long id) throws DAOException {
        Dish dish = null;
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_DISH_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dish = mapDish(rs);
                }
            }
            LOG.debug("The dish with \"id=" + id + "\" has been received from database.");
        } catch (SQLException e) {
            LOG.error( "Error in getting dish from database. ", e);
            throw new DAOException(e);
        }
        return Optional.ofNullable(dish);
    }


    public Optional<Dish> findByName(String name) throws DAOException {
        Dish dish = null;
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.FIND_DISH_BY_NAME)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dish = mapDish(rs);
                }
            }
            LOG.debug("The dish with \"name=" + name + "\" has been received from database.");
        } catch (SQLException e) {
            LOG.error( "Error in getting dish from database. ", e);
            throw new DAOException(e);
        }
        return Optional.ofNullable(dish);
    }

    @Override
    public List<Dish> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dish update(Dish dish) throws DAOException {
        try(PreparedStatement stmt = connection.prepareStatement(DishQuery.UPDATE_DISH)) {
            int k = setDishParameters(dish, stmt);
            stmt.setLong(k, dish.getId());
            stmt.executeUpdate();
            LOG.debug("The \"" + dish.getName() + "\" dish has been updated.");
        } catch (SQLException e) {
            String message = "Something went wrong. Dish hasn't been updated";
            LOG.error(message, e);
            throw new DAOException(message, e);
        }
        return dish;
    }

    @Override
    public boolean delete(Dish entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    private List<Dish> getDishes(String query) throws DAOException {
        List<Dish> dishes = new ArrayList<>();
        try ( PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dishes.add(mapDish(rs));
            }
            LOG.debug("List of dishes have been received from database.");
        } catch (SQLException e) {
            LOG.error("Error in getting list of dishes from DB.", e);
            throw new DAOException(e);
        }
        return dishes;
    }

    Dish mapDish(ResultSet rs) throws SQLException {// todo mapper
        long id = rs.getLong(DishFields.DISH_ID);
        String name = rs.getString(DishFields.DISH_NAME);
        String description = rs.getString(DishFields.DISH_DESCRIPTION);
        int weight = rs.getInt(DishFields.DISH_WEIGHT);
        BigDecimal price = rs.getBigDecimal(DishFields.DISH_PRICE);
        int amount = rs.getInt(DishFields.DISH_AMOUNT);
        String image = rs.getString(DishFields.DISH_IMAGE);
        boolean alcohol = rs.getBoolean(DishFields.DISH_ALCOHOL);
        return Dish.getInstance(id, name, description, weight, price, amount, image, alcohol);
    }

    public int getNumberOfAllDishesInOrder(Dish dish) throws DAOException {
        int numberOfDishes = 0;
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.GET_NUMBER_OF_ALL_DISHES_IN_ORDER)) {
            stmt.setLong(1, dish.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    numberOfDishes = rs.getInt(DishFields.DISH_AMOUNT);
                }
            }
            LOG.debug("The number of dishes has been received from database.");
        } catch (SQLException e) {
            LOG.error( "Error in getting number of dishes from database. ", e);
            throw new DAOException(e);
        }
        return numberOfDishes;
    }

    public boolean updateDishesAmountByOrderedValues(long orderId) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.CHANGE_DISHES_AMOUNT_BY_ORDERED_VALUES)) {
            stmt.setLong(1, orderId);
            stmt.executeUpdate();
            LOG.debug("The amount of dishes has been successfully changed");
        } catch (SQLException e) {
            LOG.error("The amount of dishes has not been changed");
            throw new DAOException(e);
        }
        return true;
    }

    public Map<String, Integer> getNumberOfEachDishInOrder(long orderId) throws DAOException {
        Map<String, Integer> dishes = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.GET_NUMBER_OF_EACH_DISH_IN_ORDER)) {
            stmt.setLong(1, orderId);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    String dishName = rs.getString(DishFields.DISH_NAME);
                    int dishesAmount = rs.getInt(DishFields.DISH_AMOUNT);
                    dishes.put(dishName, dishesAmount);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error in getting list of dishes from database. ");
            throw new DAOException(e);
        }
        return dishes;
    }

    private int setDishParameters(Dish dish, PreparedStatement stmt) throws SQLException {
        int k = 1;
        stmt.setString(k++, dish.getName());
        stmt.setString(k++, dish.getDescription());
        stmt.setInt(k++, dish.getWeight());
        stmt.setBigDecimal(k++, dish.getPrice());
        stmt.setInt(k++, dish.getAmount());
        stmt.setBoolean(k++, dish.isAlcohol());
        stmt.setString(k++, dish.getImage());
        return k;
    }


    public boolean isDishNameExists(Dish dish) throws DAOException {
        int numberOfDishes = 1;
        try (PreparedStatement stmt = connection.prepareStatement(DishQuery.COUNT_DISHES_BY_NAME)) {
            stmt.setString(1, dish.getName());
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    numberOfDishes = rs.getInt(DishFields.DISH_AMOUNT);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error in getting list of dishes from database. ");
            throw new DAOException(e);
        }
        return numberOfDishes > 0;
    }
}
