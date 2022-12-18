package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.database.connection.ConnectionManager;
import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.database.dao.databaseFieds.CategoryFields;
import com.epam.havryliuk.restaurant.model.database.dao.queries.CategoryQuery;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.constants.CategoryName;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends AbstractDao<Category> {
    private static final Logger log = LogManager.getLogger(CategoryDao.class);
//    private final ConnectionManager connectionManager;

//    public CategoryDao() throws DAOException {
//        connectionManager = ConnectionManager.getInstance();
//    }

//    @Override
    public Category findByName(String name) throws DAOException {
        Category category = null;
        try ( PreparedStatement stmt = connection.prepareStatement(CategoryQuery.FIND_CATEGORY_BY_NAME)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = mapCategory(rs);
                }
            }
            log.debug("The \"" + category + "\" category received from database.");
        } catch (SQLException e) {
            log.error("Error in getting category \"" + name + "\" from database.", e);
            throw new DAOException(e);
        }
        return category;
    }

    @Override
    public Category findById(long id) throws DAOException {
        Category category = null;
        try (PreparedStatement stmt = connection.prepareStatement(CategoryQuery.FIND_CATEGORY_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = mapCategory(rs);
                }
            }
            log.debug("The category with id \"" + id + "\" received from database.");
        } catch (SQLException e) {
            log.error("Error in getting category with id \"" + id + "\" from database.", e);
            throw new DAOException(e);
        }
        return category;
    }

    @Override
    public boolean create(Category category) throws DAOException {
        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");

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
    public List<Category> findAll() throws DAOException {
        List<Category> categories = new ArrayList<>();

         try ( PreparedStatement stmt = connection.prepareStatement(CategoryQuery.FIND_ALL_CATEGORIES);
              ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
            log.debug("List of categories have been received from database.");
        } catch (SQLException e) {
            log.debug("Error in getting list of categories from database.", e);
            throw new DAOException(e);
        }
        return categories;
    }


    @Override
    public Category update(Category category) throws DAOException {
        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");
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
    public boolean delete(Category category) throws DAOException {
        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");
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
    public boolean delete(long id) throws DAOException {
        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");
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