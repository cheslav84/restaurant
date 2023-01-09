package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.constants.databaseFieds.OrderFields;
import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.RoleFields;
import com.epam.havryliuk.restaurant.model.constants.queries.UserQuery;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.entity.UserDetails;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.UserFields;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class UserDao extends AbstractDao<User> {
    private static final Logger log = LogManager.getLogger(UserDao.class);


    public User findByEmail(String email) throws DAOException {
        User user = null;
        try (PreparedStatement stmt = connection.prepareStatement(UserQuery.FIND_USER_BY_LOGIN)) {
            stmt.setString(1, email);
            user = extractUser(stmt);
            log.debug("The \"" + user + "\" user received from database.");
        } catch (SQLException e) {
            log.error("Error in getting user from database.", e);
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public Optional<User> findById(long id) throws DAOException {
        User user;
          try (PreparedStatement stmt = connection.prepareStatement(UserQuery.FIND_USER_BY_ID)) {
            stmt.setLong(1, id);
            user = extractUser(stmt);
            log.debug("The user with id \"" + id + "\" received from database.");
        } catch (SQLException e) {
            log.error("Error in getting user with id from database.", e);
            throw new DAOException(e);
        }
        return Optional.ofNullable(user);
    }

    private User extractUser(PreparedStatement stmt) throws SQLException, DAOException {
        User user = null;
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                user = mapUser(rs);
            }
        }
        return user;
    }

    @Override
    public boolean create(User user) throws DAOException {
        try(PreparedStatement stmt = connection.prepareStatement(UserQuery.ADD_USER,
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
            String message = "Something went wrong. Try to sign in later please.";
            log.error("Error in inserting user to database.", e);
            throw new DAOException(message, e);
        }
        return true;
    }


    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try ( PreparedStatement stmt = connection.prepareStatement(UserQuery.FIND_ALL_USERS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
            log.debug("List of user have been received from database.");
        } catch (SQLException e) {
            log.debug("Error in getting list of user from database.", e);
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public User update(User user) throws DAOException {
        try ( PreparedStatement stmt = connection.prepareStatement(UserQuery.UPDATE_USER)) {
            setUserParameters(user, stmt);
            stmt.executeUpdate();
            log.debug("The user with id \"" + user.getId() +
                    "\", has been successfully updated");
        } catch (SQLException e) {
            log.error("The user has not been updated", e);
            throw new DAOException(e);
        }
        return user;//todo навіщо тут повертати того ж самого юзера?
    }

    @Override
    public boolean delete(User user) throws DAOException {
        try ( PreparedStatement stmt = connection.prepareStatement(UserQuery.DELETE_USER)) {
            stmt.setString(1, user.getEmail());
            stmt.executeUpdate();
            log.debug("The user \"" + user.getName() + "\", has been successfully deleted.");
        } catch (SQLException e) {
            log.error("The user  has not been deleted.", e);
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean delete(long id) throws DAOException {
        try ( PreparedStatement stmt = connection.prepareStatement(UserQuery.DELETE_USER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.debug("The user with id \"" + id + "\", has been successfully deleted");
        } catch (SQLException e) {
            log.error("The user has not been deleted.", e);
            throw new DAOException(e);
        }
        return true;
    }


    private void setUserParameters(User user, PreparedStatement stmt) throws SQLException, DAOException {
//        Role userRole = user.getRole();
//        long roleId = user.getRole().getId();
//        if (roleId == 0){
//            RoleDao roleDAO = new RoleDao();
//            userRole = roleDAO.findByName(userRole.getUserRole().name());
//            roleId = userRole.getId();
//        }

        int k = 1;
        stmt.setString(k++, user.getEmail());
        stmt.setString(k++, user.getPassword());
        stmt.setString(k++, user.getName());
        stmt.setString(k++, user.getSurname());
        stmt.setString(k++, user.getGender());
        stmt.setBoolean(k++, user.isOverEighteen());
        stmt.setLong(k++, user.getRole().getId());
    }


    private User mapUser(ResultSet rs) throws SQLException, DAOException {
        long id = rs.getLong(UserFields.USER_ID);
        String email = rs.getString(UserFields.USER_EMAIL);
        String password = rs.getString(UserFields.USER_PASSWORD);
        String name = rs.getString(UserFields.USER_NAME);
        String surname = rs.getString(UserFields.USER_SURNAME);
        String gender = rs.getString(UserFields.USER_GENDER);
        boolean isOverEighteen = rs.getBoolean(UserFields.USER_IS_AGE_OVER_EIGHTEEN);
        Date accountCreationDate = rs.getTimestamp(UserFields.USER_ACCOUNT_CREATION_DATE);
        long roleId = rs.getLong(UserFields.USER_ROLE_ID);
        Role role = Role.getRole(roleId);

        UserDetails userDetails = null;
        if (role == Role.MANAGER) { //todo
            userDetails = mapUserDetails(rs);
        }
        return User.getInstance(id, email, password, name, surname,
                gender, isOverEighteen, accountCreationDate, role, userDetails);
    }

//    private Role getUserRole(ResultSet rs) throws SQLException, DAOException {
//        String roleName = rs.getString(RoleFields.ROLE_NAME);
//        Role role = Role.getInstance(UserRole.valueOf(roleName));
//        if (role.getUserRole().equals(UserRole.MANAGER) ||
//                role.getUserRole().equals(UserRole.CLIENT)) {
//            return role;
//        } else {
//            String errorMessage ="UserRole can't be instantiated";
//            log.debug(errorMessage);
//            throw new DAOException(errorMessage);
//        }
//
//    }

    private UserDetails mapUserDetails(ResultSet rs) throws SQLException {// todo винести потім в UserDetailsDao
        Date birthDate = new Date(rs.getDate(UserFields.MANAGER_BIRTH_DATE).getTime());
        String passport = rs.getString(UserFields.MANAGER_PASSPORT);
        String bankAccount = rs.getString(UserFields.MANAGER_BANK_ACCOUNT);
        return UserDetails.getInstance(birthDate, passport, bankAccount);
    }


}
