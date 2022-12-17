//package com.epam.havryliuk.restaurant.model.database.dao;
//
//import com.epam.havryliuk.restaurant.model.entity.Order;
//import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
//import com.epam.havryliuk.restaurant.model.entity.Dish;
//import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
//
//import java.util.List;
//
//public interface OrderAbstractDao extends AbstractDAO<Order> {
//
//    Order geByUserIdAddressStatus(long userId, String address, BookingStatus bookingStatus) throws DAOException;
//
//
//    boolean addNewDishesToOrder(Order order, Dish dish, int amount) throws DAOException;
//    boolean changeBookingStatus(Dish dish, BookingStatus status) throws DAOException;
//    List<Order> getByUserSortedByTime() throws DAOException;
//    List<Order> getByBookingStatus(BookingStatus status) throws DAOException;
//
//
//
//
//}
