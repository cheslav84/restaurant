package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath.FORWARD_BASKET;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasketCommandTest {
    Locale locale = new Locale("en", "EN");
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Map<Order, BigDecimal> ordersAndTotalPriced;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private BasketCommand basket;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute() throws ServiceException, ServletException, IOException {
        int ordersInList = 3;
        User user = initTestUser();
        List<Order> orders = initTestOrderList(ordersInList);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        when(orderService.getAllUserOrders(user)).thenReturn(orders);
        when(orderService.getTotalPrices(orders)).thenReturn(ordersAndTotalPriced);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        basket.execute(request, response);
        verify(session).setAttribute(ORDER_PRICE_MAP, ordersAndTotalPriced);
        verify(request).getRequestDispatcher(FORWARD_BASKET);
    }

    @Test
    void executeNoOrders() throws ServiceException, ServletException, IOException {
        User user = initTestUser();
        List<Order> orders = new ArrayList<>();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        when(orderService.getAllUserOrders(user)).thenReturn(orders);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        basket.execute(request, response);
        verify(session).setAttribute(ERROR_MESSAGE, "You have no orders yet.");
        verify(request).getRequestDispatcher(FORWARD_BASKET);
    }

    private User initTestUser() {
        return User.getInstance("email", "password", "name", "surname", "male", true);
    }

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
}