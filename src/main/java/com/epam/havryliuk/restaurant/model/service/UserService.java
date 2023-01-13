package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.UserDao;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);


    public User addNewUser(User user) throws ServiceException, DuplicatedEntityException {
        EntityTransaction transaction = new EntityTransaction();
        UserDao userDao = new UserDao();

        try {
            transaction.init(userDao);
            //todo див. ст. 442 Блінова нову (втановлюється тип транзакції).
            // Може виникнути phantom reads. Можливо додати до EntityTransaction
            // методи що встановлюють її у TRANSACTION_SERIALIZABLE ... подумати

            checkIfLoginDoesNotExist(user, userDao);
            user.setRole(Role.CLIENT);
            userDao.create(user);
//            transaction.commit();
            log.debug("The user was successfully created.");
        } catch (DuplicatedEntityException e) {
            log.error("The user with such login is already exists. Try to use another one.");
            throw new DuplicatedEntityException(e);
        } catch (DAOException e) {
            log.error("Error in creating the user.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return user;
    }

    public User getUserFromDatabase(String email) throws ServiceException {
        User user;
        EntityTransaction transaction = new EntityTransaction();
        try {
            UserDao userDao = new UserDao();//todo make singleton
            transaction.init(userDao);
            user = userDao.findByEmail(email);
            if (user == null) {
                throw new ServiceException();
            }
            log.debug("User got from database. Login and password are correct.");
        } catch (DAOException e) {
            log.error("Bad credentials: " + e.getMessage(), e);
            throw new ServiceException(e);
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
