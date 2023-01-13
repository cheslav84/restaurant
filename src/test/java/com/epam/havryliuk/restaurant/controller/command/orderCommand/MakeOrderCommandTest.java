package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.CURRENT_ORDER;
import static net.bytebuddy.matcher.ElementMatchers.anyOf;
import static org.mockito.Mockito.*;

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

    @InjectMocks
    private MakeOrderCommand makeOrder;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
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
        when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);

        System.out.println(order);

//        session.setAttribute(CURRENT_ORDER, order);
        makeOrder.execute(request, response);
        verify(session).setAttribute(CURRENT_ORDER, order);


    }
}