package com.havryliuk.restaurant.services;

import com.havryliuk.restaurant.db.dao.DAO;
import com.havryliuk.restaurant.db.dao.implemetnation.UserDAO;
import com.havryliuk.restaurant.db.entity.User;
import com.havryliuk.restaurant.exceptions.DBException;

public class UserService {
    DAO<Long, ? super User> userDao;

    public UserService() throws DBException {
        userDao = new UserDAO<>();
    }

    public void addUser(User user) throws DBException {
        userDao.create(user);
    }

    public User createUser(){
        return new User();
    };

}
