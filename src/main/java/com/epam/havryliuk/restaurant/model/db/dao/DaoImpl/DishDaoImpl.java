package com.epam.havryliuk.restaurant.model.db.dao.DaoImpl;

import com.epam.havryliuk.restaurant.model.db.connection.DBManager;
import com.epam.havryliuk.restaurant.model.db.dao.DishDao;
import com.epam.havryliuk.restaurant.model.db.dao.databaseFieds.DishFields;
import com.epam.havryliuk.restaurant.model.db.dao.queries.DishQuery;
import com.epam.havryliuk.restaurant.model.db.entity.Dish;
import com.epam.havryliuk.restaurant.model.db.entity.Category;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishDaoImpl implements DishDao {
    private static final Logger log = LogManager.getLogger(DishDaoImpl.class);
    private final DBManager dbManager;

    public DishDaoImpl () throws DBException {
        dbManager = DBManager.getInstance();
    }

    @Override
    public Dish findByName(String name) throws DBException {
        Dish dish = null;

        try (Connection con = dbManager.getConnection();
                PreparedStatement stmt = con.prepareStatement(DishQuery.FIND_DISH_BY_NAME)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dish = mapDish(rs);
                }
            }
            log.debug("The \"" + name + "\" dish has been received from database.");
        } catch (SQLException e) {
            log.error("Error in getting dish \"" + name +  "\" from database. ", e);
            throw new DBException(e);
        }
        return dish;
    }

    @Override
    public List<Dish> findByCategory(Category category) throws DBException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection con = dbManager.getConnection();
                PreparedStatement stmt = con.prepareStatement(DishQuery.FIND_ALL_BY_CATEGORY)) {
            stmt.setString(1, category.getCategoryName().name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            for (Dish dish : dishes) {
                System.out.println(dish);
            }
            log.debug("List of dishes (by category) has been received from database. ");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from database. ", e);
            throw new DBException(e);
        }
        return dishes;
    }


    @Override
    public List<Dish> getSortedByName() throws DBException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_NAME);
    }

    @Override
    public List<Dish> getSortedByPrice() throws DBException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_PRICE);
    }

    @Override
    public List<Dish> getSortedByCategory() throws DBException {
        return getDishes(DishQuery.FIND_ALL_ORDERED_BY_CATEGORY);
    }

    @Override
    public boolean create(Dish entity) {
        return false;
    }

    @Override
    public Dish findById(long id) throws DBException {
        return null;
    }

    @Override
    public List<Dish> findAll() throws DBException {
        return null;
    }

    @Override
    public boolean update(Dish entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(Dish entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DBException {
        return false;
    }

    private List<Dish> getDishes(String query) throws DBException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dishes.add(mapDish(rs));
            }
            log.debug("List of dishes have been received from database.");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from DB.", e);
            throw new DBException(e);
        }
        return dishes;
    }

    private Dish mapDish(ResultSet rs) throws SQLException, DBException {
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

//    private Category mapCategoryForDish(ResultSet rs) throws SQLException { //todo low coupling
//        long id = rs.getLong(DishFields.DISH_CATEGORY_ID);
//        String name = rs.getString(CategoryFields.CATEGORY_NAME);
//        return Category.getInstance(id, CategoryName.valueOf(name));
//    }
}
