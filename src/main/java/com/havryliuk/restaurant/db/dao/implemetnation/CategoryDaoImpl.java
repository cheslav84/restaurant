package com.havryliuk.restaurant.db.dao.implemetnation;

import com.havryliuk.restaurant.db.connection.ConnectionPool;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.db.dao.CategoryDao;
import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.databaseFieds.CategoryFields;
import com.havryliuk.restaurant.db.dao.databaseFieds.DishFields;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.exceptions.DBException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    static Logger log = Logger.getLogger(CategoryDaoImpl.class.getName());
    private static ConnectionPool connectionPool;

    public CategoryDaoImpl() throws DBException {
        connectionPool = RestaurantConnectionPool.getInstance();
    }

    @Override
    public Category findByName(String name) throws DBException {
        Category category = null;
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(CategorySql.FIND_CATEGORY_BY_NAME.QUERY)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = mapCategory(rs);
                }
            }
            log.info("The \"" + category + "\" category received from database.");
        } catch (SQLException e) {
            log.error("Error in getting category \"" + name + "\" from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return category;
    }

    @Override
    public Category findById(Long id) throws DBException {
        Category category = null;
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(CategorySql.FIND_CATEGORY_BY_ID.QUERY)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = mapCategory(rs);
                }
            }
            log.info("The category with id \"" + id + "\" received from database.");
        } catch (SQLException e) {
            log.error("Error in getting category with id \"" + id + "\" from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return category;
    }

    @Override
    public boolean create(Category entity) {
        return false;//todo
    }

    @Override
    public List<Category> findAll() throws DBException {
        return null;
    }

    @Override
    public Category update(Category entity) throws DBException {
        return null;
    }

    @Override
    public boolean delete(Category entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DBException {
        return false;
    }

    private Category mapCategory(ResultSet rs) throws SQLException {
        String name = rs.getString(CategoryFields.CATEGORY_NAME);
        return Category.valueOf(name.toUpperCase());
    }

    enum CategorySql {
        ADD_DISH("INSERT INTO dish (name) values (?)"),//todo
        FIND_CATEGORY_BY_ID("SELECT * FROM category c WHERE c.id=?"),
        FIND_CATEGORY_BY_NAME("SELECT * FROM category c WHERE c.name=?"),
        UPDATE_DISH("UPDATE teams SET name=? WHERE id=?"),//todo
        //        DELETE_TEAM("DELETE t, ut FROM teams t JOIN users_teams ut WHERE t.id=? AND ut.team_id=?;"),
        DELETE_DISH("DELETE FROM teams WHERE id=?"),//todo
        DELETE_USERS_IN_TEAM("DELETE FROM users_teams WHERE team_id=?"),//todo
        GET_ALL_DISHES("SELECT * FROM teams t ORDER BY t.name"),//todo
        GET_TEAMS_BY_USER("SELECT id, name FROM teams t JOIN users_teams ut ON t.id=ut.team_id WHERE ut.user_id=?");//todo

        String QUERY;

        CategorySql(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
