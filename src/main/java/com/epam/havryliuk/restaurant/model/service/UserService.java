package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.RoleDao;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.UserDao;
import com.epam.havryliuk.restaurant.model.entity.constants.UserRole;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.PassEncryptor;
import com.epam.havryliuk.restaurant.model.util.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);

//    DAO<User> userDao;

    public User addNewUser(HttpServletRequest request) throws ServiceException {
        final User user = mapUser(request);
        EntityTransaction transaction = new EntityTransaction();
        UserDao userDao = new UserDao();
        RoleDao roleDao = new RoleDao();
        try {
            transaction.initTransaction(userDao, roleDao);

            //todo див. ст. 442 Блінова нову (втановлюється тип транзакції).
            // Може виникнути phantom reads. Можливо додати до EntityTransaction
            // методи що встановлюють її у TRANSACTION_SERIALIZABLE ... подумати

            checkIfLoginDoesNotExist(user, userDao);
            RoleService roleService = new RoleService();
            roleService.setRoleForUser(roleDao, UserRole.CLIENT, user);
            userDao.create(user);
            transaction.commit();
            log.debug("The user was successfully created.");
        } catch (DAOException e) {
            log.error("Error in creating the user.");
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return user;
    }

    /**
     * Checking for User email uniqueness. Trying to get from database user with the same
     * email (login), if it presents there method throws DBException with correspondent message
     * @param user
     * @param userDao
     * @throws DAOException
     */
    private void checkIfLoginDoesNotExist(User user, UserDao userDao) throws DAOException {
        if (userDao.findByEmail(user.getEmail()) != null){
            String message = "The user with such login is already exists. Try to use another one.";
            log.error(message);
            throw new DAOException(message);
        }
    }


    public User getLoggedInUser(HttpServletRequest req) throws ServiceException, GeneralSecurityException {
        User user = getUserFromSession(req);
        return (user == null) ? getUserFromDatabase(req) : user;
    }


    @NotNull
    private User getUserFromDatabase(HttpServletRequest req) throws ServiceException, GeneralSecurityException {
        final String email = req.getParameter("email").trim();
        final String password = req.getParameter("password").trim();
        validateEmailAndPassword(email, password);//todo refactor

        User user;
        EntityTransaction transaction = new EntityTransaction();
        try {
            UserDao userDao = new UserDao();
            transaction.init(userDao);
            user = userDao.findByEmail(email);
            if (user == null) {
                throw new ServiceException("User with such login doesn't exist.");
            }
            checkIfPasswordsCoincide(PassEncryptor.encrypt(password), user.getPassword());// todo при вводі одного і того ж паролю до енкриптора різні результати. З'ясувати
            log.debug("User got from database. Login and password are correct.");
        } catch (DAOException e) {
            log.error("Bad credentials: " + e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        } finally {
            transaction.end();
        }
        return user;
    }



    public User getUserFromDatabase(String email) throws ServiceException {
        User user;
        EntityTransaction transaction = new EntityTransaction();
        try {
            UserDao userDao = new UserDao();
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




    private void validateEmailAndPassword(String email, String password) throws ServiceException {
        try {
            Validator.validateEmail(email);
            Validator.validatePassword(password);
        } catch (BadCredentialsException e) {
            log.error("Bad credentials: " + e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public User getUserFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute(RequestAttributes.LOGGED_USER);
    }


    private void checkIfPasswordsCoincide(String password, String encryptedPassword) throws ServiceException {
        if (!password.equals(encryptedPassword)) {
            String errorMessage = "Entered password is wrong.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
    }


//    private void validateEmail(String email) throws BadCredentialsException {
//        //todo
//        if (email == null) {
//            String loginError = "Email null"; //todo add concrete cause
//            throw new BadCredentialsException(loginError);
//        }
//    }
//
//    private void validatePassword(String password) throws BadCredentialsException {
//        //todo
//        if (password == null) {
//            String passwordError = "Password null"; //todo add concrete cause
//            throw new BadCredentialsException(passwordError);
//        }
//    }




//    public UserService() throws DBException {
//        userDao = new UserDAO();
//    }
//
//    public void addUser(User user) throws DBException {
//        userDao.create(user);
//    }


    @NotNull
    private User mapUser(HttpServletRequest req) {

        String password = req.getParameter("password");
//        try {
//            password = PassEncryptor.encrypt(password);// todo при вводі одного і того ж паролю різні результати. З'ясувати
//        } catch (GeneralSecurityException e) {
//            log.error("Failed to encrypt password. ", e);
//            //todo redirect to error page...
//        }
        final String email = req.getParameter("email").trim();
        final String name = req.getParameter("name").trim();
        final String surname = req.getParameter("surname").trim();
        final String gender = req.getParameter("userGender").trim();
        final boolean isOverEighteen = req.getParameter("userOverEighteenAge") != null;
//        final Role userRole = Role.getInstance(UserRole.CLIENT);

        //todo validate data (email, password etc.)

        return User.getInstance(email, password, name, surname, gender, isOverEighteen);
    }
}
