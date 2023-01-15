package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.service.validation.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.CURRENT_ORDER;
import static org.mockito.Mockito.*;

//@PrepareForTest({ MessageManager.class })
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MakeOrderCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private OrderService orderService;

    @Mock
    private Validator validator;

//    @Mock
//    MessageManager messageManager;



    @InjectMocks
    private MakeOrderCommand makeOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void settingOrderToSession() throws ServletException, IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
//        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        Order order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.NEW);
        when(request.getSession()).thenReturn(session);
//        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);;
//        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);;
//        lenient().when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
//        mockStatic(MessageManager.class);
//        when(MessageManager.valueOf("EN")).thenReturn(MessageManager.EN);
//        doNothing().when(validator).validateDeliveryData(anyString(), anyString(), any(HttpServletRequest.class));
        session.setAttribute(CURRENT_ORDER, order);
        makeOrder.execute(request, response);
        verify(session).setAttribute(CURRENT_ORDER, order);


    }


    @Test
    void execute() throws ValidationException, ServiceException, ServletException, IOException {

        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
//        Order order = null;
//        OrderService orderService = mock(OrderService.class);

        Order order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.NEW);

        Mockito.when(request.getSession()).thenReturn(session);
//        Mockito.when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        lenient().when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
//        doReturn()

        System.out.println(order);

        session.setAttribute(CURRENT_ORDER, order);
        makeOrder.execute(request, response);

        verify(session).setAttribute(CURRENT_ORDER, order);


    }

}