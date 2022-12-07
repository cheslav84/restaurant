package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.db.dao.DAO;
import com.epam.havryliuk.restaurant.model.db.entity.Role;
import com.epam.havryliuk.restaurant.model.db.entity.User;
import com.epam.havryliuk.restaurant.model.db.dao.DaoImpl.UserDAO;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);// todo add logs for class

    DAO<User> userDao;

    public User getUser(HttpServletRequest request) throws DBException {
        //todo validate data
        final User user = mapUser(request);

        DAO<User> userDao = new UserDAO();
        userDao.create(user);//todo add to DB and get id
        return user;
    }

    @NotNull
    private User mapUser(HttpServletRequest request) {
        String password  = request.getParameter("password");
//        try {
//            password = PassEncryptor.encrypt(password);// todo при вводі одного і того ж паролю різні результати. З'ясувати
//        } catch (GeneralSecurityException e) {
//            log.error("Failed to encrypt password. ", e);
//            //todo redirect to error page...
//        }
        final String email = request.getParameter("email");
        final String name = request.getParameter("name");
        final String surname = request.getParameter("surname");
        final String gender = request.getParameter("userGender");
        final boolean isOverEighteen = request.getParameter("userOverEighteenAge") != null;
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

}
