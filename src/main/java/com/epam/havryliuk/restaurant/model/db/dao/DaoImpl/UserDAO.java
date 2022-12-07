package com.epam.havryliuk.restaurant.model.db.dao.DaoImpl;

import com.epam.havryliuk.restaurant.model.db.connection.DBManager;
import com.epam.havryliuk.restaurant.model.db.dao.DAO;
import com.epam.havryliuk.restaurant.model.db.dao.databaseFieds.CategoryFields;
import com.epam.havryliuk.restaurant.model.db.dao.queries.UserQuery;
import com.epam.havryliuk.restaurant.model.db.entity.Role;
import com.epam.havryliuk.restaurant.model.db.entity.User;
import com.epam.havryliuk.restaurant.model.db.entity.UserDetails;
import com.epam.havryliuk.restaurant.model.db.dao.databaseFieds.UserFields;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//public class UserDAO<T extends User> implements DAO<Long, User> {
public class UserDAO implements DAO<User> {
    private static final Logger log = LogManager.getLogger(UserDAO.class);
    private final DBManager dbManager;

    public UserDAO() throws DBException {
        dbManager = DBManager.getInstance();
    }

    /**
     * by email.
     */
    @Override
    public User findByName(String name) throws DBException {
        User user = null;
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(UserQuery.FIND_USER_BY_NAME)) {
            stmt.setString(1, name);
            user = extractUser(stmt);
            log.debug("The \"" + user + "\" user received from database.");
        } catch (SQLException e) {
            log.error("Error in getting user \"" + name + "\" from database.", e);
            throw new DBException(e);
        }
        return user;
    }

    @Override
    public User findById(long id) throws DBException {// todo зробити абстрактний клас зі всіма аналогічними методами і передаваити як аргумент query?
        User user = null;
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(UserQuery.FIND_USER_BY_ID)) {
            stmt.setLong(1, id);
            user = extractUser(stmt);
            log.debug("The user with id \"" + id + "\" received from database.");
        } catch (SQLException e) {
            log.error("Error in getting user with id \"" + id + "\" from database.", e);
            throw new DBException(e);
        }
        return user;
    }

    private User extractUser(PreparedStatement stmt) throws SQLException {
        User user = null;
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                user = mapUser(rs);
            }
        }
        return user;
    }

    @Override
    public boolean create(User user) throws DBException {
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(UserQuery.ADD_USER,
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
            log.debug("The \"" + user.getName() + "\" user has been added to database.");
        } catch (SQLException e) {
            log.error("Error in inserting user \"" + user.getName() + "\" to database.", e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public List<User> findAll() throws DBException {
        List<User> users = new ArrayList<>();
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(UserQuery.FIND_ALL_USERS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
            log.debug("List of user have been received from database.");
        } catch (SQLException e) {
            log.debug("Error in getting list of user from database.", e);
            throw new DBException(e);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws DBException {
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(UserQuery.UPDATE_USER)) {
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
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(UserQuery.DELETE_USER)) {
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
    public boolean delete(long id) throws DBException {
        try (Connection con = dbManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(UserQuery.DELETE_USER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.debug("The user with id \"" + id + "\", has been successfully deleted");
        } catch (SQLException e) {
            log.error("The user with id \"" + id + "\", has not been deleted.", e);
            throw new DBException(e);
        }
        return true;
    }


    private void setUserParameters(User user, PreparedStatement stmt) throws SQLException, DBException {
        Role userRole = user.getRole();
        long roleId = userRole.getId();
        if (roleId == 0){
            RoleDAO roleDAO = new RoleDAO();
            userRole = roleDAO.findByName(userRole.getUserRole().name());
            roleId = userRole.getId();
        }

        int k = 1;
        stmt.setString(k++, user.getEmail());
        stmt.setString(k++, user.getPassword());
        stmt.setString(k++, user.getName());
        stmt.setString(k++, user.getSurname());
        stmt.setString(k++, user.getGender());
        stmt.setBoolean(k++, user.isOverEighteen());
        stmt.setLong(k++, roleId);
    }

    private User mapUser(ResultSet rs) throws SQLException {
        long id = rs.getLong(UserFields.USER_ID);
        String email = rs.getString(UserFields.USER_EMAIL);
        String password = rs.getString(UserFields.USER_PASSWORD);
        String name = rs.getString(UserFields.USER_NAME);
        String surname = rs.getString(UserFields.USER_SURNAME);
        String gender = rs.getString(UserFields.USER_GENDER);
        boolean isOverEighteen = rs.getBoolean(UserFields.USER_IS_AGE_OVER_EIGHTEEN);
        Date accountCreationDate = rs.getTimestamp(UserFields.USER_ACCOUNT_CREATION_DATE);
        Role role = mapRoleForUser(rs);
        UserDetails userDetails = null;
        if (role.getUserRole() == Role.UserRole.MANAGER) { // todo краще витягувати всі дані відразу, чи зайвий раз сходити в базу,
            userDetails = mapUserDetails(rs);
        }
        return User.getInstance(id, email, password, name, surname,
                gender, isOverEighteen, accountCreationDate, role, userDetails);
    }

    private UserDetails mapUserDetails(ResultSet rs) throws SQLException {// todo винести потім в UserDetailsDao
        Date birthDate = new Date(rs.getDate(UserFields.MANAGER_BIRTH_DATE).getTime());
        String passport = rs.getString(UserFields.MANAGER_PASSPORT);
        String bankAccount = rs.getString(UserFields.MANAGER_BANK_ACCOUNT);
        return UserDetails.getInstance(birthDate, passport, bankAccount);
    }

    private Role mapRoleForUser(ResultSet rs) throws SQLException {// todo винести потім в RoleDao
        String roleName = rs.getString(CategoryFields.CATEGORY_NAME);
        return Role.getInstance(Role.UserRole.valueOf(roleName));
    }

}
