//package com.epam.havryliuk.restaurant.model.service;
//
//import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.RoleDao;
//import com.epam.havryliuk.restaurant.model.entity.User;
//import com.epam.havryliuk.restaurant.model.entity.constants.UserRole;
//import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class RoleService {
//    private static final Logger log = LogManager.getLogger(RoleService.class);
//
//    public void setRoleForUser(RoleDao roleDAO,  UserRole userRole, User user) throws DAOException {
//            user.setRole(roleDAO.findByName(userRole.name()));
//        }
//
//
//
//    }
