package com.havryliuk.restaurant.db.dao.implemetnation;

import com.havryliuk.restaurant.db.connection.ConnectionPool;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.db.dao.DAO;
import com.havryliuk.restaurant.db.dao.databaseFieds.CategoryFields;
import com.havryliuk.restaurant.db.dao.queries.CategoryQuery;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements DAO<Category> {
    private static final Logger log = LogManager.getLogger(CategoryDAO.class);
    private final ConnectionPool connectionPool;

    public CategoryDAO() throws DBException {
        connectionPool = RestaurantConnectionPool.getInstance(); //todo get class by reflection
    }

    @Override
    public Category findByName(String name) throws DBException {
        Category category = null;
        Connection con = connectionPool.getConnection();//todo запитати чи потрібно connection в блок try-catch якщо в нас летить одне і теж виключення?
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.FIND_CATEGORY_BY_NAME)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = mapCategory(rs);
                }
            }
            log.debug("The \"" + category + "\" category received from database.");
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
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.FIND_CATEGORY_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = mapCategory(rs);
                }
            }
            log.debug("The category with id \"" + id + "\" received from database.");
        } catch (SQLException e) {
            log.error("Error in getting category with id \"" + id + "\" from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return category;
    }

    @Override
    public boolean create(Category category) throws DBException {
        Connection con = connectionPool.getConnection();
        try  {
            addCategory(category, con);
            log.debug("The \"" + category.getName() + "\" category has been added to database.");
        } catch (SQLException e) {
            log.error("Error in inserting category \"" + category.getName() + "\" to database.", e);
            throw new DBException(e);
        }
        finally {
            connectionPool.releaseConnection(con);
        }
        return true;
    }

    private void addCategory(Category category, Connection con) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.ADD_CATEGORY,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            int insertionAmount = stmt.executeUpdate();
            if (insertionAmount > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        category.setId(rs.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public List<Category> findAll() throws DBException {
        List<Category> categories = new ArrayList<>();
        Connection con = connectionPool.getConnection();
         try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.FIND_ALL_CATEGORIES);
              ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
            log.debug("List of categories have been received from database.");
        } catch (SQLException e) {
            log.debug("Error in getting list of categories from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return categories;
    }


    @Override
    public boolean update(Category category) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.UPDATE_CATEGORY)) {
            stmt.setString(1, category.getName());
            stmt.setLong(2, category.getId());
            stmt.executeUpdate();
            log.debug("The category with id \"" + category.getId() +
                    "\", has been successfully updated, category name set to \"" + category.getName() + "\".");
        } catch (SQLException e) {
            log.error("The category with id \"" + category.getId() +
                    "\", has not been updated, category name \"" + category.getName()+ "\" hasn't been set.", e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Category category) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.DELETE_CATEGORY_BY_NAME)) {
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
            log.debug("The category \"" + category.getName() + "\", has been successfully deleted.");
        } catch (SQLException e) {
            log.error("The category \"" + category.getName() + "\", has not been deleted.", e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.DELETE_CATEGORY_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.debug("The category with id \"" + id + "\", has been successfully deleted");
        } catch (SQLException e) {
            log.error("The category with id \"" + id + "\", has not been deleted.", e);
            throw new DBException(e);
        }
        return true;
    }


    private Category mapCategory(ResultSet rs) throws SQLException {
        long id = rs.getLong(CategoryFields.CATEGORY_ID);
        String name = rs.getString(CategoryFields.CATEGORY_NAME);
        return Category.getInstance(id, name);
    }



//    enum CategorySql {// todo add queries to properties file?
//        ADD_CATEGORY("INSERT INTO category (name) VALUE (?)"),
//        FIND_CATEGORY_BY_ID("SELECT * FROM category c WHERE c.id=?"),
//        FIND_CATEGORY_BY_NAME("SELECT * FROM category c WHERE c.name=?"),
//        FIND_ALL_CATEGORIES("SELECT * FROM category ORDER BY name"),
//        UPDATE_CATEGORY("UPDATE category SET name=? WHERE id=?"),
//        DELETE_CATEGORY_BY_NAME("DELETE FROM category WHERE name=?"),
//        DELETE_CATEGORY_BY_ID("DELETE FROM category WHERE id=?");
//
//        private final String query;
//
//        CategorySql(String QUERY) {
//            this.query = QUERY;
//        }
//    }
}