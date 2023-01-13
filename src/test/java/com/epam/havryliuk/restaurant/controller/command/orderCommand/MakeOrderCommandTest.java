package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class MakeOrderCommandTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Mock
    OrderService orderService;

    @Test
    void execute() throws ValidationException, ServiceException {

        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        Order order = null;
//        Order order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.NEW);
        Mockito.when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        Mockito.when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);

        order = orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone);



    }
}