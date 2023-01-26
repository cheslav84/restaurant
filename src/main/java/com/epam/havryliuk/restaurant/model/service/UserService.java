package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.BasketDao;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.UserDao;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;

import com.epam.havryliuk.restaurant.model.util.annotations.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private EntityTransaction transaction;

    public User addNewUser(User user) throws ServiceException, DuplicatedEntityException {
        try {
            transaction.init(userDao);
            checkIfLoginDoesNotExist(user, userDao);
//            user.setRole(Role.CLIENT);
            userDao.create(user);
            log.debug("The user was successfully created.");
        } catch (DuplicatedEntityException e) {
            String errorMessage = "The user with such login is already exists. Try to use another one.";
            log.error(errorMessage);
            throw new DuplicatedEntityException(errorMessage, e);
        } catch (DAOException e) {
            String errorMessage = "Error in creating the user.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        } finally {
            transaction.end();
        }
        return user;
    }

    public User getUserFromDatabase(String email) throws ServiceException {
        User user;
        try {
            transaction.init(userDao);
            user = userDao.findByEmail(email);
            if (user == null) {
                String errorMessage = "User hasn't been found.";
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }
            log.debug("User got from database. Login and password are correct.");
        } catch (DAOException e) {
            String errorMessage = "An error occurs during receiving the user.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        } finally {
            transaction.end();
        }
        return user;
    }

    /**
     * Checking for User email uniqueness. Trying to get from database user with the same
     * email (login), if it presents there method throws DuplicatedEntityException with correspondent message
     * @param user
     * @param userDao
     * @throws DAOException
     * @throws DuplicatedEntityException
     */
    private void checkIfLoginDoesNotExist(User user, UserDao userDao) throws ServiceException, DAOException {
        if (userDao.findByEmail(user.getEmail()) != null){
            log.error("The user with such login is already exists. Try to use another one.");
            throw new DuplicatedEntityException();
        }
    }

}
