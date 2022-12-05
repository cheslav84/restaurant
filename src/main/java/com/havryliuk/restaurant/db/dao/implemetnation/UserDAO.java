package com.havryliuk.restaurant.db.dao.implemetnation;

import com.havryliuk.restaurant.db.connection.ConnectionPool;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.db.dao.DAO;
import com.havryliuk.restaurant.db.dao.databaseFieds.UserFields;
import com.havryliuk.restaurant.db.dao.queries.UserQuery;
import com.havryliuk.restaurant.db.entity.*;
import com.havryliuk.restaurant.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//public class UserDAO<T extends User> implements DAO<Long, User> {
public class UserDAO<T extends User> implements DAO<Long, User> {
    private static final Logger log = LogManager.getLogger(UserDAO.class);
    private final ConnectionPool connectionPool;

    public UserDAO() throws DBException {
        connectionPool = RestaurantConnectionPool.getInstance();
    }

    @Override
    public User findByName(String name) throws DBException {
        User user = null;
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
    public User findById(Long id) throws DBException {// todo зробити абстрактний клас зі всіма аналогічними методами і передаваити як аргумент query?
        User user = null;
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
            log.debug("The \"" + user.getName() + "\" user has been added to database.");
        } catch (SQLException e) {
            log.error("Error in inserting user \"" + user.getName() + "\" to database.", e);
            throw new DBException(e);
        }
        finally {
            connectionPool.releaseConnection(con);
        }
        return true;
    }

    @Override
    public List<? extends Manager> findAll() throws DBException {
        List<User> users = new ArrayList<>();
        Connection con = connectionPool.getConnection();
         try (PreparedStatement stmt = con.prepareStatement(UserQuery.FIND_ALL_USERS);
              ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
            log.debug("List of user have been received from database.");
        } catch (SQLException e) {
            log.debug("Error in getting list of user from database.", e);
            throw new DBException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.UPDATE_USER)) {

            setUserParameters(user, stmt);
            stmt.executeUpdate();
            log.debug("The user with id \"" + user.getId() +
                    "\", has been successfully updated");
        } catch (SQLException e) {
            log.error("The user with id \"" + user.getId() +
                    "\", has not been updated", e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(User user) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.DELETE_USER)) {
            stmt.setString(1, user.getEmail());
            stmt.executeUpdate();
            log.debug("The user \"" + user.getName() + "\", has been successfully deleted.");
        } catch (SQLException e) {
            log.error("The user \"" + user.getName() + "\", has not been deleted.", e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) throws DBException {
        Connection con = connectionPool.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.DELETE_USER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.debug("The user with id \"" + id + "\", has been successfully deleted");
        } catch (SQLException e) {
            log.error("The user with id \"" + id + "\", has not been deleted.", e);
            throw new DBException(e);
        }
        return true;
    }

    private void addUser(User user, Connection con) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(UserQuery.ADD_USER,
                Statement.RETURN_GENERATED_KEYS)) {
            setUserParameters(user, stmt);
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

    private void setUserParameters(User user, PreparedStatement stmt) throws SQLException {
        Role userRole = user.getRole();
        int k = 1;
        stmt.setString(k++, user.getEmail());
        stmt.setString(k++, user.getPassword());
        stmt.setString(k++, user.getName());
        stmt.setString(k++, user.getSurname());
        stmt.setString(k++, user.getGender());
        stmt.setBoolean(k++, user.isOverEighteen());
        stmt.setLong(k++, userRole.getId());
    }

    protected User mapUser(ResultSet rs) throws SQLException {
        long id = rs.getLong(UserFields.USER_ID);
        String email = rs.getString(UserFields.USER_EMAIL);
        String password = rs.getString(UserFields.USER_PASSWORD);
        String name = rs.getString(UserFields.USER_NAME);
        String surname = rs.getString(UserFields.USER_SURNAME);
        String gender = rs.getString(UserFields.USER_GENDER);
        boolean isOverEighteen = rs.getBoolean(UserFields.USER_IS_AGE_OVER_EIGHTEEN);
        Date accountCreationDate = rs.getTimestamp(UserFields.USER_ACCOUNT_CREATION_DATE);
//        Role role = mapRoleForUser(rs);
        User user = User.getInstance(id, email, password, name, surname,
                                     gender, isOverEighteen, accountCreationDate);
//        if (role.getUserRole() == UserRole.MANAGER) { // todo краще витягувати всі дані відразу, чи зайвий раз сходити в базу,
//            return  mapManager(rs, user);
//        }
        return user;
    }

//    private Manager mapManager(ResultSet rs, User user) throws SQLException {
//        Date birthDate =new Date(rs.getDate(UserFields.MANAGER_BIRTH_DATE).getTime());;
//        String passport = rs.getString(UserFields.MANAGER_PASSPORT);
//        String bankAccount = rs.getString(UserFields.MANAGER_BANK_ACCOUNT);
//        return Manager.getInstance(user, birthDate, passport, bankAccount);
//    }

//    private Role mapRoleForUser(ResultSet rs) throws SQLException {
//        String roleName = rs.getString(CategoryFields.CATEGORY_NAME);
//        return Role.getInstance(UserRole.valueOf(roleName));
//    }

}
