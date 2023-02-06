package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
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
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MakeOrderCommandTest {
    private final Locale locale = new Locale("en", "EN");
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
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeOrderInSessionRedirectionToBasket() throws IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        String dishesAmount = "2";
        String redirectionPage = "basket";
        Order order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.NEW);
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        Dish dish = Dish.getInstance("Dish name", new BigDecimal(50), 20);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(order);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(dish);
        when(request.getParameter(RequestParameters.ORDER_DISHES_AMOUNT)).thenReturn(dishesAmount);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(request.getParameter(RequestParameters.CONTINUE_ORDERING)).thenReturn(null);
        makeOrder.execute(request, response);
        verify(session).removeAttribute(CURRENT_DISH);
        verify(response).sendRedirect(redirectionPage);
    }

    @Test
    void executeOrderIsNotInSessionRedirectionToBasket() throws ValidationException, ServiceException, IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        String dishesAmount = "2";
        String redirectionPage = "basket";
        Order order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.NEW);
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        Dish dish = Dish.getInstance("Dish name", new BigDecimal(50), 20);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(null);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(dish);
        when(request.getParameter(RequestParameters.ORDER_DISHES_AMOUNT)).thenReturn(dishesAmount);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(request.getParameter(RequestParameters.CONTINUE_ORDERING)).thenReturn(null);
        when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
        try (MockedStatic<Validator> validator = Mockito.mockStatic(Validator.class)) {
            validator.when(() -> Validator.validateDeliveryData(deliveryAddress, deliveryPhone, request))
                    .thenReturn(true);
            try (MockedStatic<URLUtil> util = Mockito.mockStatic(URLUtil.class)) {
                util.when(() -> URLUtil.getRefererPage(any(HttpServletRequest.class)))
                        .thenReturn(redirectionPage);
                makeOrder.execute(request, response);
                verify(session).setAttribute(CURRENT_ORDER, order);
                verify(session).removeAttribute(CURRENT_DISH);
                verify(response).sendRedirect(redirectionPage);
            }
        }
    }



    @Test
    void executeContinueWhenButtonContinuePressed() throws ValidationException, ServiceException, IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        String continueOrderParameter = "continue";
        String dishesAmount = "2";
        String redirectionPage = "samePage";
        Order order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.NEW);
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        Dish dish = Dish.getInstance("Dish name", new BigDecimal(50), 20);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(null);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(dish);
        when(request.getParameter(RequestParameters.ORDER_DISHES_AMOUNT)).thenReturn(dishesAmount);
        when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(request.getParameter(RequestParameters.CONTINUE_ORDERING)).thenReturn(continueOrderParameter);
        try (MockedStatic<Validator> validator = Mockito.mockStatic(Validator.class)) {
            validator.when(() -> Validator.validateDeliveryData(deliveryAddress, deliveryPhone, request))
                    .thenReturn(true);
            try (MockedStatic<URLUtil> util = Mockito.mockStatic(URLUtil.class)) {
                util.when(() -> URLUtil.getRefererPage(any(HttpServletRequest.class)))
                        .thenReturn(redirectionPage);
                makeOrder.execute(request, response);
                verify(session).setAttribute(CURRENT_ORDER, order);
                verify(session).removeAttribute(CURRENT_DISH);
                verify(response).sendRedirect(redirectionPage);
            }
        }
    }

    @Test
    void executeContinueWhenDishNotFound() throws ValidationException, ServiceException, IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        String redirectionPage = "samePage";
        Order order = Order.getInstance(deliveryAddress, deliveryPhone, false, BookingStatus.NEW);
        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(null);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(null);
        when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(session.getAttribute(ERROR_MESSAGE)).thenReturn(null);
        when(session.getAttribute(ORDER_MESSAGE)).thenReturn(ORDER_MESSAGE);
        try (MockedStatic<Validator> validator = Mockito.mockStatic(Validator.class)) {
            validator.when(() -> Validator.validateDeliveryData(deliveryAddress, deliveryPhone, request))
                    .thenReturn(true);
            try (MockedStatic<URLUtil> util = Mockito.mockStatic(URLUtil.class)) {
                util.when(() -> URLUtil.getRefererPage(any(HttpServletRequest.class)))
                        .thenReturn(redirectionPage);
                makeOrder.execute(request, response);
                verify(session).setAttribute(CURRENT_ORDER, order);
                verify(session).setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                verify(response).sendRedirect(redirectionPage);
            }
        }
    }
}