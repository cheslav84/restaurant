package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;

import com.epam.havryliuk.restaurant.model.database.dao.AbstractDao;
import com.epam.havryliuk.restaurant.model.constants.databaseFieds.RoleFields;
import com.epam.havryliuk.restaurant.model.constants.queries.RoleQuery;
import com.epam.havryliuk.restaurant.model.constants.queries.UserQuery;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.constants.UserRole;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RoleDao extends AbstractDao<Role> {
    private static final Logger log = LogManager.getLogger(RoleDao.class);


    public Role findByName(String name) throws DAOException {
        Role role = null;
        try ( PreparedStatement stmt = connection.prepareStatement(RoleQuery.FIND_ROLE_BY_NAME)) {
            stmt.setString(1, name);
            role = extractRole(stmt);
            log.debug("The \"" + role + "\" role received from database.");
        } catch (SQLException e) {
            log.error("Error in getting role \"" + name + "\" from database.", e);
            throw new DAOException(e);
        }
        return role;
    }

    @Override
    public Role findById(long id) throws DAOException {
        Role role;
        try (PreparedStatement stmt = connection.prepareStatement(RoleQuery.FIND_ROLE_BY_ID)) {
            stmt.setLong(1, id);
            role = extractRole(stmt);
            log.debug("The role with id \"" + id + "\" received from database.");
        } catch (SQLException e) {
            log.error("Error in getting role with id \"" + id + "\" from database.", e);
            throw new DAOException(e);
        }
        return role;
    }

    private Role extractRole(PreparedStatement stmt) throws SQLException {
        Role role = null;
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                role = mapRole(rs);
            }
        }
        return role;
    }

    @Override
    public boolean create(Role role) throws DAOException {
        try ( PreparedStatement stmt = connection.prepareStatement(RoleQuery.ADD_ROLE,
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, role.getUserRole().name());
            int insertionAmount = stmt.executeUpdate();
            if (insertionAmount > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        role.setId(rs.getLong(1));
                    }
                }
            }
            log.debug("The \"" + role.getUserRole().name() + "\" role has been added to database.");
        } catch (SQLException e) {
            log.error("Error in inserting role \"" + role.getUserRole().name() + "\" to database.", e);
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List<Role> findAll() throws DAOException {
        List<Role> roles = new ArrayList<>();
        try ( PreparedStatement stmt = connection.prepareStatement(RoleQuery.FIND_ALL_ROLES);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roles.add(mapRole(rs));
            }
            log.debug("List of roles have been received from database.");
        } catch (SQLException e) {
            log.debug("Error in getting list of roles from database.", e);
            throw new DAOException(e);
        }
        return roles;
    }

    @Override
    public Role update(Role role) throws DAOException {
        try ( PreparedStatement stmt = connection.prepareStatement(RoleQuery.UPDATE_ROLE)) {
            stmt.setString(1, role.getUserRole().name());
            stmt.executeUpdate();
            log.debug("The role with id \"" + role.getId() +
                    "\", has been successfully updated");
        } catch (SQLException e) {
            log.error("The role with id \"" + role.getId() +
                    "\", has not been updated", e);
            throw new DAOException(e);
        }
        return role;
    }

    @Override
    public boolean delete(Role role) throws DAOException {
        try ( PreparedStatement stmt = connection.prepareStatement(UserQuery.DELETE_USER)) {
            stmt.setString(1, role.getUserRole().name());
            stmt.executeUpdate();
            log.debug("The user \"" + role.getUserRole().name() + "\", has been successfully deleted.");
        } catch (SQLException e) {
            log.error("The user \"" + role.getUserRole().name() + "\", has not been deleted.", e);
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean delete(long id) throws DAOException {
        try (PreparedStatement stmt = connection.prepareStatement(UserQuery.DELETE_USER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.debug("The role with id \"" + id + "\", has been successfully deleted");
        } catch (SQLException e) {
            log.error("The role with id \"" + id + "\", has not been deleted.", e);
            throw new DAOException(e);
        }
        return true;
    }


    private Role mapRole(ResultSet rs) throws SQLException {
        long id = rs.getLong(RoleFields.ROLE_ID);
        String role = rs.getString(RoleFields.ROLE_NAME);
        return Role.getInstance(id, UserRole.valueOf(role));
    }

}
