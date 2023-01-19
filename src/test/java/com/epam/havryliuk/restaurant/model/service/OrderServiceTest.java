package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.BasketDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.IrrelevantDataException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;

import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {

    @Mock
    DishDao dishDao;

    @Mock
    BasketDao basketDao;

    @Mock
    OrderDao orderDao;

    @Mock
    EntityTransaction transaction;

    @InjectMocks
    OrderService orderService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void changeOrderStatusNormal() throws Exception {
        long orderId = 1;
        BookingStatus status = BookingStatus.NEW;
        when(dishDao.updateDishesAmountByOrderedValues(orderId)).thenReturn(true);
        OrderService orderServiceSpy = spy(orderService);
        orderServiceSpy.changeOrderStatus(orderId, status);
        verify(dishDao, times(1)).updateDishesAmountByOrderedValues(orderId);
        verify(orderServiceSpy, times(1)).changeOrderStatus(orderId, status);
    }

    @Test
    void changeOrderStatusException() throws DAOException {
        long orderId = 1;
        BookingStatus status = BookingStatus.NEW;
        when(dishDao.updateDishesAmountByOrderedValues(orderId)).thenThrow(new DAOException());
        Exception exception = assertThrows(ServiceException.class, () -> orderService.changeOrderStatus(orderId, status));
        assertEquals("Unable to change order status.", exception.getMessage());
    }

    @Test
    void getAllUserOrders() throws DAOException, ServiceException {
        int ordersInList = 3;
        User user = initTestUser();
        List<Order> ordersMock = initTestOrderList(user, ordersInList);
        when(orderDao.getByUserSortedByTime(user)).thenReturn(ordersMock);
        List<Order> orders = orderService.getAllUserOrders(user);
        List<Order> expected = initTestOrderList(user, ordersInList);
        assertEquals(expected, orders);
        assertEquals(ordersInList, orders.size());
        orders.get(0).setAddress("Kiev");
        assertNotEquals(expected, orders);
    }

    @Test
    void getAllUserOrdersExceptionFromOrderDao() throws DAOException {
        User user = initTestUser();
        when(orderDao.getByUserSortedByTime(user)).thenThrow(new DAOException());
        Exception exception = assertThrows(ServiceException.class, () -> orderService.getAllUserOrders(user));
        assertEquals("Unable to get orders.", exception.getMessage());
    }


    @Test
    void getAllUserOrdersExceptionFromBasketDao() throws DAOException {
        int recordsPerPage = 3;
        User user = initTestUser();
        List<Order> ordersMock = initTestOrderList(user, recordsPerPage);
        when(orderDao.getByUserSortedByTime(user)).thenReturn(ordersMock);
        when(basketDao.getOrderDishes(ordersMock.get(0))).thenThrow(new DAOException());
        Exception exception = assertThrows(ServiceException.class, () -> orderService.getAllUserOrders(user));
        assertEquals("Unable to get orders.", exception.getMessage());
    }

    @Test
    void getAllOrders() throws DAOException, ServiceException {
        int page = 1;
        int recordsPerPage = 3;
        int offset = getPageOffset(page, recordsPerPage);
        int noOfRecords = 10;
        OrderSorting sorting = OrderSorting.STATUS;
        List<Order> ordersMock = initTestOrderList(recordsPerPage);
        when(orderDao.getNoOfOrders()).thenReturn(noOfRecords);
        when(orderDao.getPartOfOrders(offset, recordsPerPage, sorting)).thenReturn(ordersMock);
        Page<Order> expected = initPagesList(recordsPerPage, noOfRecords);
        Page<Order> orderPage = orderService.getAllOrders(page, recordsPerPage, sorting);
        assertEquals(expected, orderPage);
        assertEquals(recordsPerPage, orderPage.getRecords().size());
        orderPage.setNoOfPages(noOfRecords, recordsPerPage + 1);
        assertNotEquals(expected, orderPage);
        assertNotEquals(recordsPerPage, recordsPerPage + 1);
    }


    @Test
    void getAllOrdersExceptionFromOrderDao() throws DAOException {
        int page = 1;
        int recordsPerPage = 3;
        int offset = getPageOffset(page, recordsPerPage);
        OrderSorting sorting = OrderSorting.STATUS;
        when(orderDao.getPartOfOrders(offset, recordsPerPage, sorting)).thenThrow(new DAOException());
        Exception exception = assertThrows(ServiceException.class, () -> orderService.getAllOrders(page, recordsPerPage, sorting));
        assertEquals("Unable to get orders.", exception.getMessage());
    }


    private int getPageOffset(int page, int recordsPerPage) {
        return (page - 1) * recordsPerPage;
    }

    @Test
    void getAllOrdersExceptionFromBasketDao() throws DAOException {
        int page = 1;
        int recordsPerPage = 3;
        int offset = getPageOffset(page, recordsPerPage);
        OrderSorting sorting = OrderSorting.STATUS;
        List<Order> ordersMock = initTestOrderList(recordsPerPage);
        when(orderDao.getPartOfOrders(offset, recordsPerPage, sorting)).thenReturn(ordersMock);
        when(basketDao.getOrderDishes(ordersMock.get(0))).thenThrow(new DAOException());
        Exception exception = assertThrows(ServiceException.class, () -> orderService.getAllOrders(page, recordsPerPage, sorting));
        assertEquals("Unable to get orders.", exception.getMessage());
    }

    @Test
    void getOrCreateOrderExistedOrder() throws DAOException, ValidationException, ServiceException {
        User user = initTestUser();
        String address = "address";
        String phoneNo = "0961150080";
        BookingStatus bookingStatus = BookingStatus.BOOKING;
        Order ordersMock = initTestOrder(bookingStatus);
        when(orderDao.geByUserAddressStatus(user, address, bookingStatus)).thenReturn(ordersMock);
        Order expected = initTestOrder(bookingStatus);
        expected.setUser(user);
        Order order = orderService.getOrCreateOrder(user, address, phoneNo);
        assertEquals(expected, order);
    }

    @Test
    void getOrCreateOrderNewOrder() throws DAOException, ValidationException, ServiceException {
        User user = initTestUser();
        String address = "address";
        String phoneNo = "0961150080";
        long orderId = 1;
        BookingStatus bookingStatus = BookingStatus.BOOKING;
        Order ordersMock = initTestOrder(bookingStatus);
        ordersMock.setUser(user);
        Order ordersMockWithId = initTestOrder(bookingStatus);
        ordersMockWithId.setId(orderId);
        ordersMockWithId.setUser(user);
        when(orderDao.geByUserAddressStatus(user, address, bookingStatus)).thenReturn(null);
        when(orderDao.create(ordersMock)).thenReturn(ordersMockWithId);
        Order expected = initTestOrder(bookingStatus);
        expected.setId(orderId);
        expected.setUser(user);
        Order order = orderService.getOrCreateOrder(user, address, phoneNo);
        assertEquals(expected, order);
    }


    @Test
    void getOrCreateOrderExceptionGettingExistedUser() throws DAOException {
        User user = initTestUser();
        String address = "address";
        String phoneNo = "0961150080";
        BookingStatus bookingStatus = BookingStatus.BOOKING;
        when(orderDao.geByUserAddressStatus(user, address, bookingStatus)).thenThrow(new DAOException("Searched order is absent in database."));
        Exception exception = assertThrows(ServiceException.class, () -> orderService.getOrCreateOrder(user, address, phoneNo));
        assertEquals("Searched order is absent in database.", exception.getMessage());
    }

    @Test
    void getOrCreateOrderExceptionCreatingUser() throws DAOException {
        User user = initTestUser();
        String address = "address";
        String phoneNo = "0961150080";
        BookingStatus bookingStatus = BookingStatus.BOOKING;
        Order order = initTestOrder(bookingStatus);
        order.setUser(user);
        when(orderDao.create(order)).thenThrow(new DAOException("Error in inserting order to database."));
        Exception exception = assertThrows(ServiceException.class, () -> orderService.getOrCreateOrder(user, address, phoneNo));
        assertEquals("Error in inserting order to database.", exception.getMessage());
    }


    @Test
    void addDishToOrder() throws DAOException, SQLIntegrityConstraintViolationException, ServiceException {
        BookingStatus bookingStatus = BookingStatus.NEW;
        Order order = initTestOrder(bookingStatus);
        Dish dish = initTestDish();
        int dishesAmountInOrder = 5;
        Basket mockedBasked = initTestBasket(bookingStatus, dishesAmountInOrder);
        Basket basket = initTestBasket(bookingStatus, dishesAmountInOrder);
        when(basketDao.create(mockedBasked)).thenReturn(basket);
        when(dishDao.getNumberOfAllDishesInOrder(dish)).thenReturn(dishesAmountInOrder + 1);
        OrderService orderServiceSpy = spy(orderService);
        orderServiceSpy.addDishToOrder(order, dish, dishesAmountInOrder);
        verify(orderServiceSpy, times(1)).addDishToOrder(order, dish, dishesAmountInOrder);
    }

    @Test
    void addDishToOrderExceptionNotEnoughDishes() {
        BookingStatus bookingStatus = BookingStatus.NEW;
        Order order = initTestOrder(bookingStatus);
        Dish dish = initTestDish();
        int dishesAmountInOrder = 5;
        Exception exception = assertThrows(IrrelevantDataException.class, () -> orderService.addDishToOrder(order, dish, dishesAmountInOrder));
        assertEquals("The request number of dishes exceed available.", exception.getMessage());
    }

    @Test
    void getOrCreateOrderExceptionCreatingBasket() throws DAOException, SQLIntegrityConstraintViolationException {
        BookingStatus bookingStatus = BookingStatus.NEW;
        Order order = initTestOrder(bookingStatus);
        Dish dish = initTestDish();
        int dishesAmountInOrder = 5;
        Basket mockedBasked = initTestBasket(bookingStatus, dishesAmountInOrder);
        when(basketDao.create(mockedBasked)).thenThrow(new DAOException("Unable to create basket."));
        when(dishDao.getNumberOfAllDishesInOrder(dish)).thenReturn(dishesAmountInOrder + 1);
//        OrderService orderServiceSpy = spy(orderService);
        Exception exception = assertThrows(ServiceException.class, () -> orderService.addDishToOrder(order, dish, dishesAmountInOrder));
        assertEquals("Unable to create basket.", exception.getMessage());
    }


    @Test
    void removeDishFromOrderThatHasOneDish() throws DAOException, ServiceException {
        long orderId = 1;
        long dishId = 1;
        int dishesInOrder = 1;
        when(orderDao.findDishesNumberInOrder(orderId)).thenReturn(dishesInOrder);
        orderService.removeDishFromOrder(orderId, dishId);
        verify(orderDao, times(1)).delete(orderId);
    }

    @Test
    void removeDishFromOrderThatHasMoreThanOneDish() throws DAOException, ServiceException {
        long orderId = 1;
        long dishId = 1;
        int dishesInOrder = 2;
        when(orderDao.findDishesNumberInOrder(orderId)).thenReturn(dishesInOrder);
        orderService.removeDishFromOrder(orderId, dishId);
        verify(orderDao, times(1)).deleteDishFromOrderById(orderId, dishId);
    }

    @Test
    void removeDishFromOrderIncorrectDishesNumber() {
        long orderId = 1;
        long dishId = 1;
        int dishesInOrder = 0;
        Exception exception = assertThrows(ServiceException.class, () -> orderService.removeDishFromOrder(orderId, dishId));
        String errorMessage = "Obtained incorrect amount of dishes in order " + dishesInOrder;
        assertEquals(errorMessage, exception.getMessage());
    }


    @NotNull
    private List<Order> initTestOrderList(User user, int numOfOrders) {
        List<Order> orders = initTestOrderList(numOfOrders);
        orders.forEach(order -> order.setUser(user));
        return orders;
    }

    @NotNull
    private List<Order> initTestOrderList(int numOfOrders) {
        if (numOfOrders < 0 || numOfOrders > 9) {
            throw new IllegalArgumentException("Number of orders should be from 0 to 9");
        }
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < numOfOrders; i++) {
            String address = "address" + i;
            String phoneNo = "096115008" + i;
            orders.add(Order.getInstance(address, phoneNo, true, BookingStatus.NEW));
        }
        return orders;
    }

    private Page<Order> initPagesList(int recordsPerPage, int noOfRecords) {
        Page<Order> orders = new Page<>();
        orders.setNoOfPages(noOfRecords, recordsPerPage);
        orders.setRecords(initTestOrderList(recordsPerPage));
        return orders;
    }

    private User initTestUser() {
        return User.getInstance("email", "password", "name", "surname", "male", true);
    }

    private Order initTestOrder(BookingStatus bookingStatus) {
        String address = "address";
        String phoneNo = "0961150080";
        return Order.getInstance(address, phoneNo, false, bookingStatus);
    }

    private Basket initTestBasket(BookingStatus bookingStatus, int amount) {
        Order order = initTestOrder(bookingStatus);
        Dish dish = initTestDish();
        BigDecimal fixedPrice = new BigDecimal(25);
        return Basket.getInstance(order, dish, fixedPrice, amount);
    }

    private Dish initTestDish() {
        String name = "dishName";
        BigDecimal price = new BigDecimal(25);
        int amount = 5;
        return Dish.getInstance(name, price, amount);

    }
}