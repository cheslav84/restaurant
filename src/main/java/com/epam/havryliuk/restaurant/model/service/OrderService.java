package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.BasketDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.exceptions.*;
import com.epam.havryliuk.restaurant.model.util.annotations.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService implements Service {
    private static final Logger LOG = LogManager.getLogger(OrderService.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private DishDao dishDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private EntityTransaction transaction;

    /**
     * Method receives an Order id and a new value of BookingStatus that has to be changed in that Order.
     * If user wants to confirm Order (the status has to be set from BOOKING to NEW), before changing
     * the BookingStatus, method checks it that dishes actually present in menu (to prevent the situation
     * as user has been added dishes to his order some time ago, and only now wants to confirm the order but
     * the dishes had been already ordered by another user). If the dishes are available for an Order, method
     * reduces the amount of dishes in menu and that changes the BookingStatus to NEW. In case some Exception
     * occurs during these operations, the transaction performs "rollback" and nothing be change in storage.
     * If it needs to do the following BookingStatus changes (from NEW to COOKING, etc.), BookingStatus changes
     * just for demonstration without any additional control.
     * @param orderId - id of the Order that status has to be changed.
     * @param newStatus - new BookingStatus that has to be set in the Order.
     * @throws ServiceException when impossible to make changes in storage.
     * @throws EntityNotFoundException the child of ServiceException, throws if the requested amount any of
     * dishes are more that presented dishes in menu.
     */
    public void changeOrderStatus(long orderId, BookingStatus newStatus) throws ServiceException {
        try {
            transaction.initTransaction(orderDao, dishDao, basketDao);
            if(isOrderInConfirmingProcess(newStatus)) {
                checkDishesIfPresent(dishDao, basketDao, orderId);
                dishDao.updateDishesAmountByOrderedValues(orderId);
            }
            orderDao.changeOrderStatus(orderId, newStatus);
            transaction.commit();
            LOG.debug("\"order status\" has been changed to " + newStatus);
        } catch (DAOException e) {
            transaction.rollback();
            LOG.error("Unable to change order status.");
            throw new ServiceException("Unable to change order status.", e);
        } finally {
            transaction.endTransaction();
        }
    }

    /**
     * Method gets the list of user Order from storage, that sorted by
     * creation time of that Order. For every Order method set list of Baskets.
     * @param user - current user in session.
     * @return list of orders that belong to current User.
     * @throws ServiceException when impossible to get data from storage.
     */
    public List<Order> getAllUserOrders(User user) throws ServiceException {
        List<Order> orders;
        try {
            transaction.initTransaction(orderDao, basketDao);
            orders = orderDao.getByUserSortedByTime(user);
            for (Order order : orders) {
                order.setBaskets(basketDao.getOrderDishes(order));
            }
            LOG.debug("The order list was successfully created.");
        } catch (DAOException e) {
            LOG.error("Unable to get orders.");
            throw new ServiceException("Unable to get orders.", e);
        } finally {
            transaction.endTransaction();
        }
        return orders;
    }

    /**
     * Method doing calculation of Orders offset (for pagination purpose), based on
     * the total numbers of Orders (noOfRecords). Then receives appropriate list of
     * Orders that need to be displayed in user page, and set corresponding information
     * to the Page.
     * @param page - number of current Page that displays Order list for User.
     * @param recordsPerPage - orders amount that has to be displayed per page.
     * @param sorting - sorting parameter. Orders can be sorted by date or status.
     * @return Page of Orders that has to be displayed in one user page.
     * @throws ServiceException when impossible to get data.
     */
    public Page<Order> getAllOrders(int page, int recordsPerPage, OrderSorting sorting) throws ServiceException {
        Page<Order> orderPage = new Page<>();
        List<Order> orders;
        try {
            transaction.initTransaction(orderDao, basketDao);
            int noOfRecords = orderDao.getNoOfOrders();
            int offset = orderPage.getOffset(page, recordsPerPage);
            orders = orderDao.getPartOfOrders(offset, recordsPerPage, sorting);
            orderPage.setNoOfPages(noOfRecords, recordsPerPage);
            orderPage.setRecords(orders);
            for (Order order : orders) {
                order.setBaskets(basketDao.getOrderDishes(order));
            }
            LOG.debug("The page was successfully created.");
        } catch (DAOException e) {
            LOG.error("Unable to get orders.");
            throw new ServiceException("Unable to get orders.", e);
        } finally {
            transaction.end();
        }
        return orderPage;
    }


    /**
     * Returns an Order that firstly tries to get from storage. The Order is considered that
     * exists in it, if the delivery address is the same as the User wants to deliver to, and the
     * BookingStatus is "BOOKING" (an order is not confirmed by the User). If such Order doesn't exist -
     * creates a new one from data that received from the user side (delivery address, delivery phone).
     * As that order is new, then payment status is set to false, and the BookingStatus is "BOOKING".
     * Other data, like id and creation is formed by storage.
     * @param user current User in session.
     * @param deliveryAddress the address that Order needs to be delivered to.
     * @param deliveryPhone the contact phone number.
     * @return an Order, from storage if it exists in it, or the new one.
     * @throws ServiceException when impossible to get data from storage or to write data to it.
     * @throws ValidationException when delivery address or delivery phone is not valid.
     */
    public Order getOrCreateOrder(User user, String deliveryAddress, String deliveryPhone) throws ServiceException, ValidationException {
        Order order;
        try {
            transaction.initTransaction(orderDao);
            order = orderDao.geByUserAddressStatus(user, deliveryAddress, BookingStatus.BOOKING);
            if(order != null) {
                order.setUser(user);
                LOG.debug("Order has been received: " + order);
            } else {
                order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.BOOKING);
                order.setUser(user);
                order = orderDao.create(order);
                Date creationDate = orderDao.getCreationDate(order.getId());
                order.setCreationDate(creationDate);
                LOG.debug("Has been created new order: " + order);
            }
            transaction.commit();
        } catch (DAOException e) {
            LOG.error("Unable to get or create an order.");
            transaction.rollback();
            throw new ServiceException(e.getMessage(), e);
        } finally {
            transaction.endTransaction();
        }
        return order;
    }

    /**
     * Method checks whether dishes that user wants to order are available in menu (checkAvailableDishes),
     * if it is, creates a Basket and adds it to the Order list.
     * @param order - current user Order.
     * @param dish that user wants to order.
     * @param dishesAmountInOrder - amount of dishes that user wants to order.
     * @throws ServiceException when impossible to get data from storage or to write data to it.
     * @throws DuplicatedEntityException when dish is already exists in that order.
     * @throws IrrelevantDataException when the amount of requested dishes exceed available ones in menu.
     */
    public void addDishToOrder(Order order, Dish dish, int dishesAmountInOrder)
            throws ServiceException, DuplicatedEntityException {
        try {
            transaction.initTransaction(basketDao, dishDao);
            checkAvailableDishes(dish, dishesAmountInOrder, dishDao);
            Basket basket = Basket.getInstance(order, dish, dish.getPrice(), dishesAmountInOrder);
            basket = basketDao.create(basket);
            List<Basket> baskets = order.getBaskets();
            baskets.add(basket);
            transaction.commit(); // todo виконується без комміту.. з'ясувати
            LOG.debug("A dish has been added to the order.");
        } catch (SQLIntegrityConstraintViolationException e) {
            transaction.rollback();
            LOG.error("Unable to add a dish to the order.");
            throw new DuplicatedEntityException(e);
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
        finally {
            transaction.endTransaction();
        }
    }

    /**
     * Method removes dishes from order. If it remains the only one dish in an order,
     * then order will be removed completely.
     * @param orderId Order id received from the user.
     * @param dishId Dish id received from the user.
     * @throws ServiceException when impossible to get or to delete data from storage.
     */
    public void removeDishFromOrder(long orderId, long dishId) throws ServiceException {
        try {
            transaction.init(orderDao);
            int dishesInOrder = orderDao.findDishesNumberInOrder(orderId);
            if (dishesInOrder == 1) {//todo check such data in all methods
                orderDao.delete(orderId);
            } else if (dishesInOrder > 1) {
                orderDao.deleteDishFromOrderById(orderId, dishId);
            } else {
                String errorMessage = "Obtained incorrect amount of dishes in order " + dishesInOrder;
                LOG.error(errorMessage);
                throw new ServiceException(errorMessage);
            }
            LOG.debug("A dish has been removed from the order.");
        } catch (DAOException e) {
            LOG.error("Unable to remove a dish from the order.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    /**
     * Method receives requested dishes from a client and checks if that dishes are really present in storage.
     * RequestedDishes - gets the requested amounts and names of the dishes within an order.
     * PresentDishes - gets the actual amounts and names of the dishes within an order that are presents in menu.
     * If the requested amount any of dishes are more that presented dishes in menu, method th
     * @param orderId id of user Order.
     * @throws EntityNotFoundException If the requested amount any of dishes are more that presented dishes in menu,
     * method throws the EntityAbsentException with the list of absent dishes in its message.
     */
    private void checkDishesIfPresent(DishDao dishDao, BasketDao basketDao, long orderId) throws EntityNotFoundException, DAOException {
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
            LOG.debug("Some of dishes are not present.");
            throw new EntityNotFoundException(absentDishes); //todo наскільки коректно через exception передавати повідомлення на view?
        }
    }


    /**
     * Method checks if the user wants to confirm order (the status has to be set from BOOKING to NEW).
     * @param newStatus - new BookingStatus that has to be set in the Order.
     * @return true if the BookingStatus that has to be set is "NEW". Otherwise, return false.
     */
    private boolean isOrderInConfirmingProcess(BookingStatus newStatus) {
        return newStatus.equals(BookingStatus.NEW);
    }

    /**
     * Method gets receives the number of available dishes in menu, and compares it with the request
     * amount of dishes.
     * @param dish that user wants to order.
     * @param dishesAmountInOrder - amount of dishes that user wants to order.
     * @throws DAOException when impossible to get data from storage or to write data to it.
     * @throws IrrelevantDataException when the amount of requested dishes exceed available ones in menu.
     */
    private void checkAvailableDishes(Dish dish, int dishesAmountInOrder, DishDao dishDao)
            throws DAOException, IrrelevantDataException {
        int numberOfDishesInMenu = dishDao.getNumberOfAllDishesInOrder(dish);
        if (numberOfDishesInMenu < dishesAmountInOrder) {
            String errorMessage = "The request number of dishes exceed available.";
            LOG.error(errorMessage);
            throw new IrrelevantDataException(errorMessage);
        }
    }

    /**
     * Method calculates the total price of each order.
     * @param orders list of orders which prices need to be calculated.
     * @return map of orders, the keys are the orders, and values are the
     * total prices of that order.
     */
    public Map<Order, BigDecimal> getTotalPrices(List<Order> orders) {
        Map<Order, BigDecimal> ordersAndPrices = orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        o -> o.getBaskets()
                                .stream()
                                .map(basket -> basket.getFixedPrice()
                                        .multiply(BigDecimal.valueOf(basket.getAmount())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));
        return sortOrdersByPrice(ordersAndPrices);
    }

    /**
     * Sort map by creation date of the orders.
     * @param ordersAndPrices unsorted map.
     * @return map sorted by creation date.
     */
    private LinkedHashMap<Order, BigDecimal> sortOrdersByPrice(Map<Order, BigDecimal> ordersAndPrices) {
        return ordersAndPrices.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(
                        (o1, o2) -> o2.getCreationDate()
                                .compareTo(o1.getCreationDate())
                )).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }
}
