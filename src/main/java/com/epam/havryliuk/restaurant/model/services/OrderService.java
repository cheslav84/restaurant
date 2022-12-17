package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.database.dao.DaoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import com.epam.havryliuk.restaurant.model.utils.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static com.epam.havryliuk.restaurant.controller.RequestAttributes.CURRENT_DISH;
import static com.epam.havryliuk.restaurant.controller.RequestAttributes.LOGGED_USER;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);


    /**
     * Returns an order that firstly tries to get from database. If such order doesn't exist -
     * creates a new one from data that received from user side (delivery address, delivery phone).
     * As that order is new, then payment status is set to false, and the booking status is "Booking".
     * Other data, like id and creation is formed by database.
     * @param req
     * @return
     * @throws NoSuchEntityException
     * @throws BadCredentialsException
     */
    public Order getOrder(HttpServletRequest req) throws NoSuchEntityException, BadCredentialsException {

        String deliveryAddress = req.getParameter("deliveryAddress");
        String deliveryPhone = req.getParameter("deliveryPhone");
//        URLUtil.isAddressCorrect(deliveryAddress);
//        URLUtil.isPhoneCorrect(deliveryPhone);

        Order order;
        try {
            User user = getCurrentUser(req);
            OrderDao orderDao = new OrderDao();
            order = orderDao.geByUserIdAddressStatus(user.getId(), deliveryAddress, BookingStatus.BOOKING);
            log.debug("geByUserIdAddressStatus - userId: " + user.getId() + ", deliveryAddress: " + deliveryAddress + ", BookingStatus: " + BookingStatus.BOOKING);
            if(order == null) {
                order = Order.getInstance(deliveryAddress, deliveryPhone, false, user, BookingStatus.BOOKING);
                orderDao.create(order);
            }
        } catch (DAOException e) {
            log.error(e);
            throw new NoSuchEntityException(e.getMessage(), e);
        }
        return order;
    }
            //todo / перевірити чи замовленню не більше, примірок, дні три. Якщо більше - видалити це замовлення
            //todo / та створити нове. Ідея полягає в тому, що створення замовлення і додавання продуктів в кошик
            //todo / розглядаються як окремі процеси. Юзер може додати один продукт до кошику ввечері, інший вранці.
            //todo / при цьому фіксується ціна на цей продукт в момент додвавання до кошику. Але, щоб не виникло
            //todo / ситуації, коли Юзер додав одне блюдо пів року тому, а сьогодні інше та підтвердив замовлення.
            //todo / В такому випадку, ціна на попереднє блюдо може бути абсолютно не актуальною.
            //todo /
            //todo / Або, як альтернативне рішення, до моменту підтвердження замовлення ціна береться із меню,
            //todo / а в після підтвердження фіксується в кошику.


    public void addDishToOrder(HttpServletRequest req, Order order) throws NoSuchEntityException, BadCredentialsException {
        Dish dish = getCurrentDish(req);
        int dishesAmount = getDishesAmount(req);

        try {
            OrderDao orderDao = new OrderDao();
            orderDao.addNewDishesToOrder(order, dish, dishesAmount);
        } catch (DAOException e) {
            log.error(e.getMessage(), e);
            throw new NoSuchEntityException(e);
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


    private User getCurrentUser(HttpServletRequest req) throws NoSuchEntityException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(LOGGED_USER);
        if (user == null) {
            String errorMessage = "The user has been logged out.";
            log.error(errorMessage);
            throw new NoSuchEntityException(errorMessage);
        }
        return user;
    }

    private Dish getCurrentDish(HttpServletRequest req) throws NoSuchEntityException {
        HttpSession session = req.getSession();
        Dish dish = (Dish) session.getAttribute(CURRENT_DISH);
        if (dish == null) {
            String errorMessage = "Choose the dish to add it to basket.";
            log.error(errorMessage);
            throw new NoSuchEntityException(errorMessage);
        }
        return dish;
    }



}
