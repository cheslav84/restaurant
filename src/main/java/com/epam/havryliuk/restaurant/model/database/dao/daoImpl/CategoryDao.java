//package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;
//
//import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
//import com.epam.havryliuk.restaurant.model.constants.databaseFieds.CategoryFields;
//import com.epam.havryliuk.restaurant.model.constants.queries.CategoryQuery;
//import com.epam.havryliuk.restaurant.model.entity.Category;
//import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class CategoryDao extends AbstractDao<Category> {
//    private static final Logger log = LogManager.getLogger(CategoryDao.class);
//
//
//    public Category findByName(String name) throws DAOException {
//        Category category;
//        try ( PreparedStatement stmt = connection.prepareStatement(CategoryQuery.FIND_CATEGORY_BY_NAME)) {
//            stmt.setString(1, name);
//            category = extractCategory(stmt);
//            log.debug("The \"" + category + "\" category received from database.");
//        } catch (SQLException e) {
//            log.error("Error in getting category \"" + name + "\" from database.", e);
//            throw new DAOException(e);
//        }
//        return category;
//    }
//
//    @Override
//    public Optional<Category> findById(long id) throws DAOException {
//        Category category;
//        try (PreparedStatement stmt = connection.prepareStatement(CategoryQuery.FIND_CATEGORY_BY_ID)) {
//            stmt.setLong(1, id);
//            category = extractCategory(stmt);
//            log.debug("The category with id \"" + id + "\" received from database.");
//        } catch (SQLException e) {
//            log.error("Error in getting category with id \"" + id + "\" from database.", e);
//            throw new DAOException(e);
//        }
//        return Optional.ofNullable(category);
//    }
//
//    private Category extractCategory(PreparedStatement stmt) throws SQLException {
//        Category category = null;
//        try (ResultSet rs = stmt.executeQuery()) {
//            if (rs.next()) {
//                category = mapCategory(rs);
//            }
//        }
//        return category;
//    }
//
//    @Override
//    public Category create(Category category) {
//        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");
//    }
//
//    @Override
//    public List<Category> findAll() throws DAOException {
//        List<Category> categories = new ArrayList<>();
//
//         try ( PreparedStatement stmt = connection.prepareStatement(CategoryQuery.FIND_ALL_CATEGORIES);
//              ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                categories.add(mapCategory(rs));
//            }
//            log.debug("List of categories have been received from database.");
//        } catch (SQLException e) {
//            log.debug("Error in getting list of categories from database.", e);
//            throw new DAOException(e);
//        }
//        return categories;
//    }
//
//
//    @Override
//    public Category update(Category category) {
//        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");
//    }
//
//    @Override
//    public boolean delete(Category category) {
//        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");
//    }
//
//    @Override
//    public boolean delete(long id) {
//        throw new UnsupportedOperationException("The \"Category\" list is unmodifiable.");
//    }
//
//    private Category mapCategory(ResultSet rs) throws SQLException {
//        String name = rs.getString(CategoryFields.CATEGORY_NAME);
//        return Category.valueOf(name);
//    }
//}
