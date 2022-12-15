package com.epam.havryliuk.restaurant.model.database.dao.DaoImpl;

import com.epam.havryliuk.restaurant.model.database.dao.BasketDao;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;

import java.util.List;

public class BasketDaoImpl implements BasketDao {

    @Override
    public boolean addNewDishes(Dish dishes, int amount) throws DBException {
        return false;
    }

    @Override
    public boolean changeBookingStatus(Dish dish, BookingStatus status) throws DBException {
        return false;
    }

    @Override
    public List<Order> getByUserSortedByTime() throws DBException {
        return null;
    }

    @Override
    public List<Order> getByBookingStatus(BookingStatus status) throws DBException {
        return null;
    }

    @Override
    public boolean create(Order entity) throws DBException {
        return false;
    }

    @Override
    public Order findByName(String name) throws DBException {
        return null;
    }

    @Override
    public Order findById(long id) throws DBException {
        return null;
    }

    @Override
    public List<Order> findAll() throws DBException {
        return null;
    }

    @Override
    public boolean update(Order entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(Order entity) throws DBException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DBException {
        return false;
    }
}
