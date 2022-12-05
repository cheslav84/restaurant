package com.havryliuk.restaurant.db.dao.implemetnation;

import com.havryliuk.restaurant.db.connection.ConnectionPool;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.db.dao.EntityDao;
import com.havryliuk.restaurant.db.dao.databaseFieds.UserFields;
import com.havryliuk.restaurant.db.dao.queries.CategoryQuery;
import com.havryliuk.restaurant.db.dao.queries.UserQuery;
import com.havryliuk.restaurant.db.entity.Role;
import com.havryliuk.restaurant.db.entity.User;
import com.havryliuk.restaurant.db.entity.Manager;
import com.havryliuk.restaurant.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserDaoImpl <K, T extends User> implements EntityDao <Long, User> {
    private static final Logger log = LogManager.getLogger(UserDaoImpl.class);
    private final ConnectionPool connectionPool;


    public UserDaoImpl() throws DBException {
        connectionPool = RestaurantConnectionPool.getInstance();
    }


    @Override
    public T findByName(String name) throws DBException {
        T user = null;
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.FIND_USER_BY_NAME)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapUser(rs);
                }
            }
            log.debug("The \"" + user + "\" user received from database.");
        } catch (SQLException e) {
            log.error("Error in getting user \"" + name + "\" from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return user;
    }

    @Override
    public T findById(Long id) throws DBException {// todo зробити абстрактний клас зі всіма аналогічними методами і передаваити як аргумент query?
        T user = null;
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.FIND_USER_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapUser(rs);
                }
            }
            log.debug("The user with id \"" + id + "\" received from database.");
        } catch (SQLException e) {
            log.error("Error in getting user with id \"" + id + "\" from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return user;
    }

    @Override
    public boolean create(User user) throws DBException {
        Connection con = connectionPool.getConnection();
        try  {
            addUser(user, con);
            log.debug("The \"" + user.getName() + "\" category has been added to database.");
        } catch (SQLException e) {
            log.error("Error in inserting category \"" + user.getName() + "\" to database.", e);
            throw new DBException(e);
        }
        finally {
            connectionPool.releaseConnection(con);
        }
        return true;
    }

    private void addUser(User user, Connection con) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.ADD_CATEGORY,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            int insertionAmount = stmt.executeUpdate();
            if (insertionAmount > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public List<User> findAll() throws DBException {
        List<User> users = new ArrayList<>();
        Connection con = connectionPool.getConnection();
         try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.FIND_ALL_CATEGORIES);
              ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
            log.debug("List of categories have been received from database.");
        } catch (SQLException e) {
            log.debug("Error in getting list of categories from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return users;
    }


    @Override
    public boolean update(User user) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(CategoryQuery.UPDATE_CATEGORY)) {
            stmt.setString(1, user.getName());
            stmt.setLong(2, user.getId());
            stmt.executeUpdate();
            log.debug("The category with id \"" + user.getId() +
                    "\", has been successfully updated, category name set to \"" + user.getName() + "\".");
        } catch (SQLException e) {
            log.error("The category with id \"" + user.getId() +
                    "\", has not been updated, category name \"" + user.getName()+ "\" hasn't been set.", e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(User user) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.DELETE_USER_BY_NAME)) {
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
            log.debug("The category \"" + user.getName() + "\", has been successfully deleted.");
        } catch (SQLException e) {
            log.error("The category \"" + user.getName() + "\", has not been deleted.", e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.FIND_USER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.debug("The category with id \"" + id + "\", has been successfully deleted");
        } catch (SQLException e) {
            log.error("The category with id \"" + id + "\", has not been deleted.", e);
            throw new DBException(e);
        }
        return true;
    }

    private <T>T mapUser(ResultSet rs) throws SQLException {
        long id = rs.getLong(UserFields.USER_ID);
        String email = rs.getString(UserFields.USER_EMAIL);
        String password = rs.getString(UserFields.USER_PASSWORD);
        String name = rs.getString(UserFields.USER_NAME);
        String surname = rs.getString(UserFields.USER_SURNAME);
        String gender = rs.getString(UserFields.USER_GENDER);
        boolean isOverEighteen = rs.getBoolean(UserFields.USER_IS_AGE_OVER_EIGHTEEN);
        Date creationDate = rs.getTimestamp(UserFields.USER_CREATION_TIME);



        Role role = rs.getString(UserFields.CATEGORY_NAME);
        Manager userDetails = rs.getString(UserFields.CATEGORY_NAME);




        return User.getInstance(id, name);
    }

    private User mapUserDetails(ResultSet rs) throws SQLException {
        long id = rs.getLong(UserFields.USER_ID);
        String email = rs.getString(UserFields.USER_EMAIL);
        String password = rs.getString(UserFields.USER_PASSWORD);
        String name = rs.getString(UserFields.USER_NAME);
        String surname = rs.getString(UserFields.USER_SURNAME);
        String gender = rs.getString(UserFields.USER_GENDER);
        boolean isOverEighteen = rs.getBoolean(UserFields.USER_IS_AGE_OVER_EIGHTEEN);


        Date birthDate =new Date(rs.getDate("date_time").getTime());;

        private String passport;
        private String bankAccount;

        Role role = rs.getString(UserFields.CATEGORY_NAME);
        Manager manager = rs.getString(UserFields.CATEGORY_NAME);

        return User.getInstance(id, name);
    }



}
