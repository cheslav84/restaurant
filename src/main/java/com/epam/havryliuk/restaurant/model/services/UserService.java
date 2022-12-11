package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.database.dao.DAO;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.database.dao.DaoImpl.UserDAO;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);// todo add logs for class

    DAO<User> userDao;

    public User addNewUser(HttpServletRequest request) throws DBException {
        //todo validate data
        final User user = mapUser(request);

        DAO<User> userDao = new UserDAO();
        userDao.create(user);//todo add to DB and get id
        return user;
    }




    @NotNull
    private User mapUser(HttpServletRequest req) {
        String password  = req.getParameter("password");
//        try {
//            password = PassEncryptor.encrypt(password);// todo при вводі одного і того ж паролю різні результати. З'ясувати
//        } catch (GeneralSecurityException e) {
//            log.error("Failed to encrypt password. ", e);
//            //todo redirect to error page...
//        }
        final String email = req.getParameter("email");
        final String name = req.getParameter("name");
        final String surname = req.getParameter("surname");
        final String gender = req.getParameter("userGender");
        final boolean isOverEighteen = req.getParameter("userOverEighteenAge") != null;
        final Role userRole = Role.getInstance(Role.UserRole.CLIENT);
        final User user = User.getInstance(email, password, name, surname, gender, isOverEighteen, userRole);
        return user;
    }


    public UserService() throws DBException {
        userDao = new UserDAO();
    }

    public void addUser(User user) throws DBException {
        userDao.create(user);
    }

    public User getUserByLogin(HttpServletRequest req) {
        String password  = req.getParameter("password");
        final String email = req.getParameter("email");

        //        try {
//            password = PassEncryptor.encrypt(password);// todo при вводі одного і того ж паролю різні результати. З'ясувати
//        } catch (GeneralSecurityException e) {
//            log.error("Failed to encrypt password. ", e);
//            //todo redirect to error page...
//        }

        DAO<User> userDao;
        User user;
        try {
            userDao = new UserDAO();
            user = userDao.findByName(email);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        return user;

    }
}
