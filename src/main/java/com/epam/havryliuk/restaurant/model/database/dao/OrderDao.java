package com.epam.havryliuk.restaurant.model.database.dao;

import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;

import java.util.List;

public interface OrderDao extends DAO<Order> {

    Order geByUserIdAddressStatus(long userId, String address, BookingStatus bookingStatus) throws DBException;


    boolean addNewDishesToOrder(Order order, Dish dish, int amount) throws DBException;
    boolean changeBookingStatus(Dish dish, BookingStatus status) throws DBException;
    List<Order> getByUserSortedByTime() throws DBException;
    List<Order> getByBookingStatus(BookingStatus status) throws DBException;




}
