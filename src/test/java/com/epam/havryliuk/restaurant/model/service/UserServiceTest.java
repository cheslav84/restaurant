package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.UserDao;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addNewUserWhenDoeNotExist() throws DAOException, ServiceException {
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        when(userDao.findByEmail(user.getEmail())).thenReturn(null);
        userService.addNewUser(user);
        verify(userDao, times(1)).create(user);
    }

    @Test
    void addNewUserWhenExist() throws DAOException, ServiceException {
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        when(userDao.findByEmail(user.getEmail())).thenReturn(user);
        Exception exception = assertThrows(ServiceException.class, () -> userService.addNewUser(user));
        assertEquals("The user with such login is already exists. Try to use another one.", exception.getMessage());

    }

    @Test
    void getUserFromDatabase() {
    }
}