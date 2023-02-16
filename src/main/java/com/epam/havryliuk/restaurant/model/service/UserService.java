package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.UserDao;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;

import com.epam.havryliuk.restaurant.model.util.annotations.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService implements Service {
    private static final Logger LOG = LogManager.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private EntityTransaction transaction;

    /**
     * Method adds the User to a storage. Before adding the User to it, method checks if email
     * of that user does not exist in that storage.
     *
     * @param user that has to be added to a storage.
     * @throws DuplicatedEntityException if the user with such email is already exist in a storage.
     * @throws ServiceException          if any Exception situation occurs while adding user to a storage.
     */
    public void addNewUser(User user) throws ServiceException, DuplicatedEntityException {
        try {
            transaction.init(userDao);
            checkIfEmailDoesNotExist(user, userDao);
            userDao.create(user);
            LOG.debug("The user was successfully created.");
        } catch (DuplicatedEntityException e) {
            LOG.info(e.getMessage());
            throw new DuplicatedEntityException(e.getMessage(), e);
        } catch (DAOException e) {
            String errorMessage = "Error in creating the user.";
            LOG.info(errorMessage);
            throw new ServiceException(errorMessage, e);
        } finally {
            transaction.end();
        }
    }


    /**
     * Method gets the user from storage by email.
     *
     * @param email of the user that User entity has to be received from a storage.
     * @return User that received from storage by email.
     * @throws EntityNotFoundException if User with such email doesn't exist in a storage.
     * @throws ServiceException        if any Exception situation occurs while receiving user from storage.
     */
    public User getUserFromDatabase(String email) throws ServiceException {
        User user;
        try {
            transaction.init(userDao);
            user = userDao.findByEmail(email);
            if (user == null) {
                String errorMessage = "User hasn't been found.";
                LOG.info(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }
            LOG.debug("User got from database. Login and password are correct.");
        } catch (DAOException e) {
            String errorMessage = "An error occurs during receiving the user.";
            LOG.info(errorMessage);
            throw new ServiceException(errorMessage, e);
        } finally {
            transaction.end();
        }
        return user;
    }

    /**
     * Checking for User email uniqueness before registration that User. Trying to get from database user with the
     * same email (login), if it presents there method throws DuplicatedEntityException with correspondent message
     *
     * @param user    whose email needs to be checked for uniqueness.
     * @param userDao Data Access Object instance that doing such trying to get user by email.
     * @throws DAOException              if any Exception situation occurs while receiving user from a storage.
     * @throws DuplicatedEntityException if the user with such email is already exist in a storage.
     */
    private void checkIfEmailDoesNotExist(User user, UserDao userDao) throws ServiceException, DAOException {
        if (userDao.findByEmail(user.getEmail()) != null) {
            String errorMessage = "The user with such login is already exists.";
            LOG.info(errorMessage);
            throw new DuplicatedEntityException(errorMessage);
        }
    }

}
