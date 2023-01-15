package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.ClassUnderTest;
import com.epam.havryliuk.restaurant.model.database.connection.ConnectionManager;
import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.BasketDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.*;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


//import static org.junit.jupiter.api.Assertions.*;
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

    @Spy
    EntityTransaction transaction;

    @InjectMocks
    OrderService orderService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUserOrders() throws DAOException, ServiceException {
        User user = new User();
        List<Order> ordersMock = new ArrayList<>();
        doNothing().when(transaction).initTransaction(any(OrderDao.class), any(BasketDao.class));
        Mockito.when(orderDao.getByUserSortedByTime(user)).thenReturn(ordersMock);
        Assertions.assertNotNull(orderService.getAllUserOrders(new User()));
    }


    @Test
    void changeOrderStatusFromNew() throws Exception {
        long orderId = 1;
        OrderService orderService = new OrderService();
        BookingStatus status = BookingStatus.NEW;
        DishDao dishDao = mock(DishDao.class);
        OrderDao orderDao = mock(OrderDao.class);
        BasketDao basketDao = mock(BasketDao.class);
//        OrderService orderServiceMock = mock(OrderService.class);
        Mockito.when(dishDao.updateDishesAmountByOrderedValues(orderId)).thenReturn(true);


        orderService.changeOrderStatus(orderId, status);
        verify(orderService, times(1)).changeOrderStatus(orderId, status);


//        OrderService orderServiceSpy = PowerMockito.spy(orderService);
////        OrderService orderServiceSpy = Mockito.spy(orderService);
//
//        DishDao dishDao = mock(DishDao.class);
//        BasketDao basketDao = mock(BasketDao.class);
//
//        Map<String, Integer> requestedDishes = Map.of("fish",2, "beer", 3);
//        Map<String, Integer> presentDishes = Map.of("fish",2, "beer", 3);
//
//        PowerMockito.doNothing().when(orderServiceSpy, "checkDishesIfPresent", dishDao, basketDao, orderId);
//
//        Mockito.when(basketDao.getNumberOfRequestedDishesInOrder(orderId)).thenReturn(requestedDishes);
//        Mockito.when(dishDao.getNumberOfEachDishInOrder(orderId)).thenReturn(presentDishes);
//
//
//
////        Mockito.doNothing().when(orderServiceSpy).checkDishesIfPresent(dishDao, basketDao, orderId);
//
//
//        verify(orderService, times(1)).changeOrderStatus(orderId, status);







//        Mockito.when(orderService.checkDishesIfPresent(dishDao, basketDao, orderId)).thenReturn(getDishesList(expectedNumberOfDishes));
//        Mockito.when(checkDishesIfPresent().invoke(dishDao, basketDao, orderId)).thenReturn(getDishesList(expectedNumberOfDishes));
//        Mockito.when(checkDishesIfPresent()).thenReturn();
//        checkDishesIfPresent().invoke(dishDao, basketDao, orderId);


//        orderService.changeOrderStatus(orderId, status);

//        verify(orderService, times(1)).
//        verify(checkDishesIfPresent(dishDao, basketDao, orderId)), times(1)).
//        verify(checkDishesIfPresent().invoke(dishDao, basketDao, orderId), times(1)).checkDishesIfPresent(dishDao, basketDao, orderId).invoke(dishDao, basketDao, orderId);

    }


    @Test
    void changeOrderStatus() throws Exception {
        long orderId = 1;
//        OrderService orderService = new OrderService();

        BookingStatus status = BookingStatus.BOOKING;
        DishDao dishDao = mock(DishDao.class);
        OrderDao orderDao = mock(OrderDao.class);
        BasketDao basketDao = mock(BasketDao.class);
        OrderService orderServiceMock = mock(OrderService.class);
        Mockito.when(dishDao.updateDishesAmountByOrderedValues(orderId)).thenReturn(true);
//        Mockito.when(orderDao.changeOrderStatus(orderId, status)).thenThrow(DAOException.class);
//        Mockito.when(orderDao.changeOrderStatus(orderId, status)).thenReturn(true);



        orderService.changeOrderStatus(orderId, status);
        verify(orderService, times(1)).changeOrderStatus(orderId, status);
    }





    @Test
    void getAllOrders() {
    }

    @Test
    void getOrder() {
    }

    @Test
    void addDishToOrder() {
    }

    @Test
    void removeDishFromOrder() {
    }
}