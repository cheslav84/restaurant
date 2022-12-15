package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.database.dao.DAO;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.database.dao.DaoImpl.UserDAO;
import com.epam.havryliuk.restaurant.model.entity.constants.UserRole;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import com.epam.havryliuk.restaurant.model.utils.PassEncryptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);// todo add logs for class

//    DAO<User> userDao;

    public User addNewUser(HttpServletRequest request) throws DBException {
        final User user = mapUser(request);

        DAO<User> userDao = new UserDAO();
        userDao.create(user);//todo get id
        return user;
    }



    /**
     * getUserDatabaseSession method
     * @param req
     * @return
     * @throws NoSuchEntityException
     */

    public User getLoggedInUser(HttpServletRequest req) throws NoSuchEntityException, GeneralSecurityException {
        User user = getUserFromSession(req);
        if (user != null) {
            return user;//todo think of refactoring
        }

        String password  = req.getParameter("password").trim();
        final String email = req.getParameter("email").trim();
            try {
                validateEmail(email);
                validatePassword(password);

                DAO<User> userDao = new UserDAO();
                user = userDao.findByName(email);//todo think of renaming
                if (user == null) {
                    throw new NoSuchEntityException("Login is incorrect!");
                }

                checkIfPasswordsCoincide(PassEncryptor.encrypt(password), user.getPassword());// todo при вводі одного і того ж паролю до енкриптора різні результати. З'ясувати

                log.debug("User got from database. Login and password are correct.");
            } catch (BadCredentialsException | DBException e) {
                log.error("Bad credentials: " + e.getMessage(), e);
                throw new NoSuchEntityException(e.getMessage(), e);
            }
        return user;
    }

    private User getUserFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute("loggedUser");
    }


    private void checkIfPasswordsCoincide(String password, String encryptedPassword) throws NoSuchEntityException {
        if (!password.equals(encryptedPassword)){
            String errorMessage = "Entered password is wrong.";
            log.error(errorMessage);
            throw new NoSuchEntityException(errorMessage);
        }
    }


    private void validateEmail(String email) throws BadCredentialsException {
        //todo
        if (email == null){
            String loginError = "Email null"; //todo add concrete cause
            throw new BadCredentialsException(loginError);
        }
    }

    private void validatePassword(String password) throws BadCredentialsException {
        //todo
        if (password == null){
            String passwordError = "Password null"; //todo add concrete cause
            throw new BadCredentialsException(passwordError);
        }
    }


//    public UserService() throws DBException {
//        userDao = new UserDAO();
//    }
//
//    public void addUser(User user) throws DBException {
//        userDao.create(user);
//    }


    @NotNull
    private User mapUser(HttpServletRequest req) {

        String password  = req.getParameter("password");
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
        final Role userRole = Role.getInstance(UserRole.CLIENT);

        //todo validate data (email, password etc.)

        final User user = User.getInstance(email, password, name, surname, gender, isOverEighteen, userRole);
        return user;
    }
}
