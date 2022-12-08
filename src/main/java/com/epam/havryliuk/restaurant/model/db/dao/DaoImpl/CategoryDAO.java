package com.epam.havryliuk.restaurant.model.db.dao.DaoImpl;

import com.epam.havryliuk.restaurant.model.db.connection.ConnectionPool;
import com.epam.havryliuk.restaurant.model.db.connection.RestaurantConnectionPool;
import com.epam.havryliuk.restaurant.model.db.dao.DAO;
import com.epam.havryliuk.restaurant.model.db.dao.databaseFieds.CategoryFields;
import com.epam.havryliuk.restaurant.model.db.dao.queries.CategoryQuery;
import com.epam.havryliuk.restaurant.model.db.entity.Category;
import com.epam.havryliuk.restaurant.model.db.entity.constants.CategoryName;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
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
    public Category findById(long id) throws DBException {
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
        throw new UnsupportedOperationException("The \"Category\" list is unmodified.");

//        Connection con = connectionPool.getConnection();
//        try  {
//            addCategory(category, con);
//            log.debug("The \"" + category.getCategoryName() + "\" category has been added to database.");
//        } catch (SQLException e) {
//            log.error("Error in inserting category \"" + category.getCategoryName() + "\" to database.", e);
//            throw new DBException(e);
//        }
//        finally {
//            connectionPool.releaseConnection(con);
//        }
//        return true;
    }

//    private void addCategory(Category category, Connection con) throws SQLException {
//        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.ADD_CATEGORY,
//                Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, category.getCategoryName().name());
//            int insertionAmount = stmt.executeUpdate();
//            if (insertionAmount > 0) {
//                try (ResultSet rs = stmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        category.setId(rs.getLong(1));
//                    }
//                }
//            }
//        }
//    }

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
        throw new UnsupportedOperationException("The \"Category\" list is unmodified.");
//        Connection con = connectionPool.getConnection();
//        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.UPDATE_CATEGORY)) {
//            stmt.setString(1, category.getCategoryName().name());
//            stmt.setLong(2, category.getId());
//            stmt.executeUpdate();
//            log.debug("The category with id \"" + category.getId() +
//                    "\", has been successfully updated, category name set to \"" + category.getCategoryName() + "\".");
//        } catch (SQLException e) {
//            log.error("The category with id \"" + category.getId() +
//                    "\", has not been updated, category name \"" + category.getCategoryName()+ "\" hasn't been set.", e);
//            throw new DBException(e);
//        }
//        return true;
    }

    @Override
    public boolean delete(Category category) throws DBException {
        throw new UnsupportedOperationException("The \"Category\" list is unmodified.");
//        Connection con = connectionPool.getConnection();
//        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.DELETE_CATEGORY_BY_NAME)) {
//            stmt.setString(1, category.getCategoryName().name());
//            stmt.executeUpdate();
//            log.debug("The category \"" + category.getCategoryName() + "\", has been successfully deleted.");
//        } catch (SQLException e) {
//            log.error("The category \"" + category.getCategoryName() + "\", has not been deleted.", e);
//            throw new DBException(e);
//        }
//        return true;
    }

    @Override
    public boolean delete(long id) throws DBException {
        throw new UnsupportedOperationException("The \"Category\" list is unmodified.");
//        Connection con = connectionPool.getConnection();
//        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.DELETE_CATEGORY_BY_ID)) {
//            stmt.setLong(1, id);
//            stmt.executeUpdate();
//            log.debug("The category with id \"" + id + "\", has been successfully deleted");
//        } catch (SQLException e) {
//            log.error("The category with id \"" + id + "\", has not been deleted.", e);
//            throw new DBException(e);
//        }
//        return true;
    }


    private Category mapCategory(ResultSet rs) throws SQLException {
        long id = rs.getLong(CategoryFields.CATEGORY_ID);
        String name = rs.getString(CategoryFields.CATEGORY_NAME);
        return Category.getInstance(id, CategoryName.valueOf(name));
    }
}
