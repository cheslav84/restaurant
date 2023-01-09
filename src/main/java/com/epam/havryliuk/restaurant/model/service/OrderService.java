package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.BasketDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);

    public void changeOrderStatus(long orderId, BookingStatus newStatus) throws EntityAbsentException,  ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        OrderDao orderDao = new OrderDao();
        DishDao dishDao = new DishDao();
        BasketDao basketDao = new BasketDao();
        try {
            transaction.initTransaction(orderDao, dishDao, basketDao);
            if(isOrderInConfirmingProcess(newStatus)) {
                checkDishesIfPresent(dishDao, basketDao, orderId);
                dishDao.updateDishesAmountByOrderedValues(orderId);
            }
            orderDao.changeOrderStatus(orderId, newStatus);
            log.debug("\"order status\" has been changed to " + newStatus);
        } catch (DAOException e) {
            String message = "Something went wrong. Try to confirm your order later please.";
            log.debug(message);//todo подумати над тим що давати в лог на цьому рівні, і на рівні DAO (exception чи повідомлення.)
            throw new ServiceException(message);
        } finally {
            transaction.commit();
            transaction.endTransaction();
        }
    }

    private void checkDishesIfPresent(DishDao dishDao, BasketDao basketDao, long orderId) throws DAOException, EntityAbsentException {
        Map<String, Integer> requestedDishes = basketDao.getNumberOfRequestedDishesInOrder(orderId);
        Map<String, Integer> presentDishes = dishDao.getNumberOfEachDishInOrder(orderId);
        List<String> absentDishesList = new ArrayList<>();
        for (String reqDishName : requestedDishes.keySet()) {
            if (requestedDishes.get(reqDishName) > presentDishes.get(reqDishName)) {
                absentDishesList.add(reqDishName);
            }
        }
        if (absentDishesList.size() != 0) {
            String absentDishes = "\"" + String.join("\", \"", absentDishesList) + "\"";
            throw new EntityAbsentException(absentDishes);//todo наскільки коректно через exception передавати повідомлення на view?
        }

//        requestedDishes.keySet().stream()
//                .(k -> requestedDishes.get(k) == presentDishes.get(k)
//                .map(id -> dishesIdThatAbsent.add(id))
//    );


    }


    private boolean isOrderInConfirmingProcess(BookingStatus newStatus) {
        return newStatus.equals(BookingStatus.NEW);
    }

    public List<Order> getAllUserOrders(User user) throws ServiceException {
        OrderDao orderDao = new OrderDao();
        BasketDao basketDao = new BasketDao();
        EntityTransaction transaction = new EntityTransaction();
        List<Order> orders;
        try {
            transaction.initTransaction(orderDao, basketDao);
            orders = orderDao.getByUserSortedByTime(user);
            for (Order order : orders) {
                order.setBaskets(basketDao.getOrderDishes(order));
            }
            transaction.commit();
            //todo чи потрібно такі запипити виконувати в транзакції, якщо ми лише читаємо
            // з бази даних, наприклад встановити відповідні ключі для транзкації для виключення
            // dirty reads, наприклад? А якщо dirty reads не можливі (за логікою програми)?
            log.debug("The order list was successfully created.");
        } catch (DAOException e) {
            log.error("The orders is temporary unavailable, try again later please.");
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return orders;
    }


    public Page<Order> getAllOrders(int page, int recordsPerPage) throws ServiceException {
        OrderDao orderDao = new OrderDao();
        EntityTransaction transaction = new EntityTransaction();
        BasketDao basketDao = new BasketDao();
        Page<Order> orderPage = new Page<>();
        List<Order> orders;
        try {
            transaction.initTransaction(orderDao, basketDao);
            int noOfRecords = orderDao.getNoOfOrders();
            int offset = orderPage.getOffset(page, recordsPerPage);
            orderPage.setNoOfPages(noOfRecords, recordsPerPage);
            orders = orderDao.getOrdersSortedByStatusThenTime(offset, recordsPerPage);
            orderPage.setRecords(orders);
            for (Order order : orders) {
                order.setBaskets(basketDao.getOrderDishes(order));
            }
            transaction.commit();
            log.debug("The order list was successfully created.");
        } catch (DAOException e) {
            log.error("The orders are temporary unavailable, try again later please.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return orderPage;
    }



//    public List<Order> getAllOrders(int page, int recordsPerPage) throws ServiceException {
//        OrderDao orderDao = new OrderDao();
//        EntityTransaction transaction = new EntityTransaction();
//        BasketDao basketDao = new BasketDao();
//        List<Order> orders;
//
//
//        try {
//            transaction.initTransaction(orderDao, basketDao);
//
//
//            int offset = (page - 1) * recordsPerPage;
//            int noOfRecords = orderDao.getNoOfOrders();
//            int noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//
//            orders = orderDao.getUncompletedOrdersSortedByStatusThenTime(offset, noOfRecords);
//
//
//            for (Order order : orders) {
//                order.setBaskets(basketDao.getOrderDishes(order));
//            }
//            transaction.commit();
//            log.debug("The order list was successfully created.");
//        } catch (DAOException e) {
//            log.error("The orders are temporary unavailable, try again later please.");
//            throw new ServiceException(e);
//        } finally {
//            transaction.end();
//        }
//        return orders;
//    }


    /**
     * Returns an order that firstly tries to get from database. The order is considered that
     * exists in database, if the delivery address is the same as a user wants to deliver to, and the
     * booking status is "booking" (the order is not confirmed by user). If such order doesn't exist -
     * creates a new one from data that received from user side (delivery address, delivery phone).
     * As that order is new, then payment status is set to false, and the booking status is "Booking".
     * Other data, like id and creation is formed by database.
     * @param
     * @return
     * @throws ServiceException
     * @throws BadCredentialsException
     */
    public Order getOrder(User user, String deliveryAddress, String deliveryPhone) throws ServiceException, BadCredentialsException {
        EntityTransaction transaction = new EntityTransaction();
        Order order;
        try {
            OrderDao orderDao = new OrderDao();
            transaction.init(orderDao);
            order = orderDao.geByUserAddressStatus(user, deliveryAddress, BookingStatus.BOOKING);
            if(order != null) {
                order.setUser(user);
                log.debug("Order has been received from database: " + order);
            } else {
                order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.BOOKING);
                order.setUser(user);
                orderDao.create(order);
                Date creationDate = orderDao.getCreationDate(order.getId());
                order.setCreationDate(creationDate);
                log.debug("Has been created new order: " + order);
            }
        } catch (DAOException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        } finally {
            transaction.end();
        }
        return order;
    }

    public void addDishToOrder(Order order, Dish dish, int dishesAmountInOrder)
            throws ServiceException, BadCredentialsException, DuplicatedEntityException {
        Basket basket = Basket.getInstance(order, dish, dish.getPrice(), dishesAmountInOrder);
        List<Basket> baskets = order.getBaskets();
        baskets.add(basket);

        EntityTransaction transaction = new EntityTransaction();
        DishDao dishDao = new DishDao();
        BasketDao basketDao = new BasketDao();
        try {
            transaction.initTransaction(basketDao, dishDao);
            checkAvailableDishes(dish, dishesAmountInOrder, dishDao);
            basketDao.create(basket);
        } catch (DuplicatedEntityException e) {
            throw new DuplicatedEntityException(e);
        } catch (DAOException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        finally {
//            transaction.commit();//todo ask
            transaction.endTransaction();
        }
    }

    private void checkAvailableDishes(Dish dish, int dishesAmountInOrder, DishDao dishDao)
            throws DAOException, IrrelevantDataException {
        int numberOfDishesInMenu = dishDao.getNumberOfAllDishesInOrder(dish);
        if (numberOfDishesInMenu < dishesAmountInOrder) {
            log.error("The request number of dishes exceed available.");
            throw new IrrelevantDataException();
        }
    }

    /**
     * Method removes dishes from order. If it remains the only dish in an order,
     * that order will be removed completely.
     * @param orderId received from the user side.
     * @param dishId received from the user side.
     * @throws ServiceException
     */
    public void removeDishFromOrder(long orderId, long dishId) throws ServiceException  {//todo make basket
        EntityTransaction transaction = new EntityTransaction();
        try {
            OrderDao orderDao = new OrderDao();
            transaction.init(orderDao);
            int dishesInOrder = orderDao.findDishesNumberInOrder(orderId);
            if (dishesInOrder == 1) {
                orderDao.delete(orderId);
            } else {
                orderDao.deleteDishFromOrderById(orderId,dishId);
            }
            log.debug("Dish has been removed from order.");
        } catch (DAOException e) {
            log.error("Number of dishes in order has not been received from database.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }




//    private int getDishesAmount(HttpServletRequest req) throws BadCredentialsException {
//        int dishesAmount;
//
//        try {
//            dishesAmount = Integer.parseInt(req.getParameter("amount").trim());
//            if(!Validator.isDishesAmountCorrect(dishesAmount)) {
//                throw new BadCredentialsException("The the number of dishes is incorrect.");
//            }
//            log.debug("Request for \"" + dishesAmount + "\" has been received.");
//        } catch (NumberFormatException e) {
//            throw new BadCredentialsException("Enter amount of dishes you want to order please.");
//        }
//        return dishesAmount;
//    }

//    private User getCurrentUser(HttpServletRequest req) throws ServiceException {// todo move to User service
//        HttpSession session = req.getSession();
//        User user = (User) session.getAttribute(LOGGED_USER);
//        if (user == null) {
//            String errorMessage = "The user has been logged out.";
//            log.error(errorMessage);
//            throw new ServiceException(errorMessage);
//        }
//        return user;
//    }

//    private Dish getCurrentDish(HttpServletRequest req) throws ServiceException {
//        HttpSession session = req.getSession();
//        Dish dish = (Dish) session.getAttribute(CURRENT_DISH);
//        if (dish == null) {
//            String errorMessage = "Choose the dish for adding it to basket.";
//            log.error(errorMessage);
//            throw new ServiceException(errorMessage);
//        }
//        return dish;
//    }



}
