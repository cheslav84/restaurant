package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.database.dao.OrderDao;
import com.epam.havryliuk.restaurant.model.database.dao.DaoImpl.OrderDaoImpl;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Date;

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
        checkDeliveryAddress(deliveryAddress);
        checkDeliveryPhone(deliveryPhone);

        HttpSession session = req.getSession();
        User user = getCurrentUser(session);

        Order order;
        try {
            OrderDao  orderDao = new OrderDaoImpl();
            order = orderDao.geByUserIdAddressStatus(user.getId(), deliveryAddress, BookingStatus.BOOKING);
            if(order == null) {
                order = Order.getInstance(deliveryAddress, deliveryPhone, false, user, BookingStatus.BOOKING);
                orderDao.create(order);
            }
        } catch (DBException e) {
            log.error(e);
            throw new NoSuchEntityException(e.getMessage(), e);
        }
        return order;
    }


    private void checkDeliveryAddress(String deliveryAddress) throws BadCredentialsException {
            //todo
            if (deliveryAddress == null){
                String error = "The address is incorrect.";
                throw new BadCredentialsException(error);
            }
    }
    private void checkDeliveryPhone(String deliveryPhone) throws BadCredentialsException {
        //todo
        if (deliveryPhone == null){
            String error = "The phone is incorrect.";
            throw new BadCredentialsException(error);
        }
    }


    private User getCurrentUser(HttpSession session) throws NoSuchEntityException {
        User user = (User) session.getAttribute(LOGGED_USER);
        if (user == null) {
            String errorMessage = "The user has been logged out.";
            log.error(errorMessage);
            throw new NoSuchEntityException(errorMessage);
        }
        return user;
    }


    public void addDishesToUserBasket(User user, Dish dish, int amount){

        try {
            OrderDao orderDao = new OrderDaoImpl();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        Order order = new Order();
//        basketDao.create()

    }



//    public void addDishToBasket(HttpServletRequest req) {
//
//
//        int dishesAmount  = Integer.parseInt(req.getParameter("amount").trim());
//        log.debug("Request for \"" + dishesAmount + "\" has been received.");
//
//        String continueOrder = req.getParameter("continue");
//
//        Map<Dish, Integer> basket = (Map<Dish, Integer>) req.getSession().getAttribute("basket");//todo можна зберігати в сесії, чи кожного разу записуємо в базу?
//        HttpSession session = req.getSession();
//        if(basket == null) {
//            basket = new HashMap<>();
//            session.setAttribute("basket", basket);
//        }
//
//        Dish currentDish = (Dish) session.getAttribute("currentDish");
//        session.removeAttribute("currentDish");
//        basket.put(currentDish, dishesAmount);
//
//        String redirectionPage;
//        if (continueOrder == null) {
//            redirectionPage = "basket";
//        } else {
//            redirectionPage = "menu";
//        }
//
//
//
//    }
}
