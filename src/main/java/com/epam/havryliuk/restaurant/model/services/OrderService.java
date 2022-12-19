package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.utils.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.CURRENT_DISH;
import static com.epam.havryliuk.restaurant.controller.RequestAttributes.LOGGED_USER;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);


    public List<Order> getAllUserOrders(HttpServletRequest req) throws ServiceException {
        User user = getCurrentUser(req);//todo think of renaming - non informative
        OrderDao orderDao = new OrderDao();
        DishDao dishDao = new DishDao();
        EntityTransaction transaction = new EntityTransaction();
        List<Order> orders;
        try {
            transaction.initTransaction(orderDao, dishDao);
            orders = orderDao.getByUserSortedByTime(user);
            for (Order order : orders) {
                order.setDishes(dishDao.getOrderDishes(order));
            }
            transaction.commit();
            //todo чи потрібно такі запипити виконувати в транзакції, якщо ми лише читаємо
            // з бази даних, наприклад встановити відповідні ключі для транзкації для виключення
            // dirty reads, наприклад? А якщо dirty reads не можливі (за логікою програми)?
            log.debug("The order list was successfully created.");
        } catch (DAOException e) {
            String message = "The orders is temporary unavailable, try again later pleese.";
            log.error(message, e);
            throw new ServiceException(message, e);
        } finally {
            transaction.endTransaction();
        }
        return orders;
    }



    /**
     * Returns an order that firstly tries to get from database. If such order doesn't exist -
     * creates a new one from data that received from user side (delivery address, delivery phone).
     * As that order is new, then payment status is set to false, and the booking status is "Booking".
     * Other data, like id and creation is formed by database.
     * @param req
     * @return
     * @throws ServiceException
     * @throws BadCredentialsException
     */
    public Order getOrder(HttpServletRequest req) throws ServiceException, BadCredentialsException {
        String deliveryAddress = req.getParameter("deliveryAddress");
        String deliveryPhone = req.getParameter("deliveryPhone");
        User user = getCurrentUser(req);//todo think of renaming - non informative

        EntityTransaction transaction = new EntityTransaction();
        Order order;
        try {
            OrderDao orderDao = new OrderDao();
            transaction.init(orderDao);
            order = orderDao.geByUserAddressStatus(user, deliveryAddress, BookingStatus.BOOKING);
            log.debug("geByUserIdAddressStatus - userId: " + user.getId() + ", deliveryAddress: " + deliveryAddress + ", BookingStatus: " + BookingStatus.BOOKING);
            if(order != null) {
//                order.setBookingStatus(BookingStatus.BOOKING);
                order.setUser(user);
            } else {
                order = Order.getInstance(deliveryAddress, deliveryPhone, false, user, BookingStatus.BOOKING);
                orderDao.create(order);
                Date creationDate = orderDao.getCreationDate(order.getId());
                order.setCreationDate(creationDate);
            }
        } catch (DAOException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        } finally {
            transaction.end();
        }
        return order;
    }

    public void addDishToOrder(HttpServletRequest req, Order order) throws ServiceException, BadCredentialsException {
        Dish dish = getCurrentDish(req);
        int dishesAmount = getDishesAmount(req);
        order.addDishes(dish, dishesAmount);
        EntityTransaction transaction = new EntityTransaction();
        try {
            OrderDao orderDao = new OrderDao();
            transaction.init(orderDao);
            orderDao.addNewDishesToOrder(order, dish, dishesAmount);
            //todo два рази вказуємо одні і ті ж значення (dish, dishesAmount), одного разу додаємо до замовлення яке
            // знаходиться в пам'яті (в сесії), а другий раз коли хочемо записати в базу даних. Можна використати
            // LinkedHashMap, і діставати останній доданий, але тоді доведеться по лінках шукати останній елемент,
            // що теж не є ефективним.
        } catch (DAOException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        } finally {
            transaction.end();
        }
    }

    private int getDishesAmount(HttpServletRequest req) throws BadCredentialsException {
        int dishesAmount;

        try {
            dishesAmount = Integer.parseInt(req.getParameter("amount").trim());
            if(!Validator.isDishesAmountCorrect(dishesAmount)) {
                throw new BadCredentialsException("The the number of dishes is incorrect.");
            }
            log.debug("Request for \"" + dishesAmount + "\" has been received.");
        } catch (NumberFormatException e) {
            throw new BadCredentialsException("Enter amount of dishes you want to order please.");
        }
        return dishesAmount;
    }


    private User getCurrentUser(HttpServletRequest req) throws ServiceException {// todo move to User service
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(LOGGED_USER);
        if (user == null) {
            String errorMessage = "The user has been logged out.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
        return user;
    }

    private Dish getCurrentDish(HttpServletRequest req) throws ServiceException {
        HttpSession session = req.getSession();
        Dish dish = (Dish) session.getAttribute(CURRENT_DISH);
        if (dish == null) {
            String errorMessage = "Choose the dish for adding it to basket.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage);
        }
        return dish;
    }



}
