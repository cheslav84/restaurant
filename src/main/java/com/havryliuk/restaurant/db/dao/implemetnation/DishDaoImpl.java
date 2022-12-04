package com.havryliuk.restaurant.db.dao.implemetnation;

import com.havryliuk.restaurant.db.connection.ConnectionPool;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.db.dao.CategoryDao;
import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.databaseFieds.CategoryFields;
import com.havryliuk.restaurant.db.dao.databaseFieds.DishFields;
import com.havryliuk.restaurant.db.dao.queries.DishQuery;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.exceptions.DBException;
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
    private static ConnectionPool connectionPool;

    public DishDaoImpl () throws DBException {
        connectionPool = RestaurantConnectionPool.getInstance();//todo як не вказувати конкретний клас? Наприклад якщо замінити в майбутньому наприкада на Hikari
    }

    @Override
    public Dish findByName(String name) throws DBException {
        Dish dish = null;
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(DishQuery.FIND_DISH_BY_NAME)) {
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
        } finally {
            connectionPool.releaseConnection(con);
        }
        return dish;
    }

    @Override
    public List<Dish> findByCategory(Category category) throws DBException {
        List<Dish> dishes = new ArrayList<>();
        Connection con = connectionPool.getConnection();

        try (PreparedStatement stmt = con.prepareStatement(DishQuery.FIND_ALL_BY_CATEGORY)) {
            stmt.setString(1, category.getName());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            log.debug("List of dishes has been received from database.");
        } catch (SQLException e) {
            log.error("Error in getting list of dishes from database. ", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
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
    public Dish findById(Long id) throws DBException {
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
    public boolean delete(Long id) throws DBException {
        return false;
    }

    private List<Dish> getDishes(String query) throws DBException {
        List<Dish> dishes = new ArrayList<>();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(query);
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

    private Dish mapDish(ResultSet rs) throws SQLException, DBException {//todo може тут відразу джоіном категорю витягувати? Навіщо 2 рази ходити в базу?
//        long id = rs.getLong(DishFields.DISH_ID);
//        String name = rs.getString(DishFields.DISH_NAME);
//        String description = rs.getString(DishFields.DISH_DESCRIPTION);
//        int weight = rs.getInt(DishFields.DISH_WEIGHT);
//        BigDecimal price = rs.getBigDecimal(DishFields.DISH_PRICE);
//        int amount = rs.getInt(DishFields.DISH_AMOUNT);
//        boolean special = rs.getBoolean(DishFields.DISH_SPECIAL);
//        String image = rs.getString(DishFields.DISH_IMAGE);
////        Long categoryId = rs.getLong(DishFields.DISH_CATEGORY_ID);
//
//        String categoryName = rs.getString(CategoryFields.CATEGORY_NAME);
//        Category category = Category.getInstance(categoryName);


        long id = rs.getLong(DishFields.DISH_ID);
        String name = rs.getString(DishFields.DISH_NAME);
        String description = rs.getString(DishFields.DISH_DESCRIPTION);
        int weight = rs.getInt(DishFields.DISH_WEIGHT);
        BigDecimal price = rs.getBigDecimal(DishFields.DISH_PRICE);
        int amount = rs.getInt(DishFields.DISH_AMOUNT);
        boolean special = rs.getBoolean(DishFields.DISH_SPECIAL);
        String image = rs.getString(DishFields.DISH_IMAGE);
//        Long categoryId = rs.getLong(DishFields.DISH_CATEGORY_ID);

        String categoryName = rs.getString(CategoryFields.CATEGORY_NAME);
        Category category = Category.getInstance(categoryName);




















//        CategoryDao categoryDao = new CategoryDaoImpl();//todo is it normal to use CategoryDao in that situation?
//        Category category = categoryDao.findById(categoryId);

        return Dish.getInstance(id, name, description, weight, price, amount, special, image, category);
    }

//    enum DishSql {
//        ADD_DISH("INSERT INTO dish (name) values (?)"),//todo
//        FIND_DISH_BY_NAME("SELECT * FROM dish d WHERE d.name=?"),
//        FIND_ALL_ORDERED_BY_NAME("SELECT * FROM dish ORDER BY name"),
//        FIND_ALL_ORDERED_BY_PRICE("SELECT * FROM dish ORDER BY price"),
//        FIND_ALL_ORDERED_BY_CATEGORY("SELECT * FROM dish ORDER BY category"),
//
//
//
//        FIND_ALL_BY_CATEGORY("SELECT d.*, c.name as 'category_name' FROM dish d JOIN category c ON c.name=?"),
//        //FIND_ALL_BY_CATEGORY("SELECT * FROM dish d JOIN category c WHERE c.name=?"),
//
//        FIND_DISH_BY_CATEGORY("SELECT * FROM dish d WHERE d.name=?"),
//
//
//        UPDATE_DISH("UPDATE teams SET name=? WHERE id=?"),//todo
//        //        DELETE_TEAM("DELETE t, ut FROM teams t JOIN users_teams ut WHERE t.id=? AND ut.team_id=?;"),
//        DELETE_DISH("DELETE FROM teams WHERE id=?"),//todo
//        DELETE_USERS_IN_TEAM("DELETE FROM users_teams WHERE team_id=?"),//todo
//        GET_ALL_DISHES("SELECT * FROM teams t ORDER BY t.name"),//todo
//        GET_TEAMS_BY_USER("SELECT id, name FROM teams t JOIN users_teams ut ON t.id=ut.team_id WHERE ut.user_id=?");//todo
//
//        String QUERY;
//
//        DishSql(String QUERY) {
//            this.QUERY = QUERY;
//        }
//    }
}
