package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.validation.Validator;
import com.epam.havryliuk.restaurant.controller.responseDispatcher.URLDispatcher;
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
import java.util.Locale;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MakeOrderCommandTest {
    private Locale locale = new Locale("en", "EN");

    private final String dishesAmount = "2";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private OrderService orderService;
    @Mock
    private Order order;
    @Mock
    private User user;
    @Mock
    private Dish dish;
    @InjectMocks
    private MakeOrderCommand makeOrder;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(RequestAttributes.LOGGED_USER)).thenReturn(user);
        when(request.getParameter(RequestParameters.ORDER_DISHES_AMOUNT)).thenReturn(dishesAmount);
    }

    @Test
    void executeOrderInSessionRedirectionToBasket() throws IOException {
        String redirectionPage = "basket";
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(order);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(dish);
        when(request.getParameter(RequestParameters.CONTINUE_ORDERING)).thenReturn(null);
        makeOrder.execute(request, response);
        verify(session).removeAttribute(CURRENT_DISH);
        verify(response).sendRedirect(redirectionPage);
    }

    @Test
    void executeOrderIsNotInSessionRedirectionToBasket() throws ServiceException, IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        String redirectionPage = "basket";
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(null);
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(dish);
        when(request.getParameter(RequestParameters.CONTINUE_ORDERING)).thenReturn(null);
        try (MockedStatic<Validator> validator = Mockito.mockStatic(Validator.class)) {
            validator.when(() -> Validator.validateDeliveryData(Integer.parseInt(dishesAmount), request)).thenReturn(true);
            try (MockedStatic<URLDispatcher> util = Mockito.mockStatic(URLDispatcher.class)) {
                util.when(() -> URLDispatcher.getRefererPage(any(HttpServletRequest.class)))
                        .thenReturn(redirectionPage);
            }
                makeOrder.execute(request, response);
                verify(session).setAttribute(CURRENT_ORDER, order);
                verify(session).removeAttribute(CURRENT_DISH);
                verify(response).sendRedirect(redirectionPage);
        }
    }



    @Test
    void executeContinueWhenButtonContinuePressed() throws ServiceException, IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        String continueOrderParameter = "continue";
        String redirectionPage = "samePage";
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(null);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(dish);
        when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
        when(request.getParameter(RequestParameters.CONTINUE_ORDERING)).thenReturn(continueOrderParameter);
        try (MockedStatic<Validator> validator = Mockito.mockStatic(Validator.class)) {
            validator.when(() -> Validator.validateDeliveryData(Integer.parseInt(dishesAmount), request))
                    .thenReturn(true);
            try (MockedStatic<URLDispatcher> util = Mockito.mockStatic(URLDispatcher.class)) {
                util.when(() -> URLDispatcher.getRefererPage(any(HttpServletRequest.class)))
                        .thenReturn(redirectionPage);
                makeOrder.execute(request, response);
                verify(session).setAttribute(CURRENT_ORDER, order);
                verify(session).removeAttribute(CURRENT_DISH);
                verify(response).sendRedirect(redirectionPage);
            }
        }
    }

    @Test
    void executeContinueWhenDishNotFound() throws ServiceException, IOException {
        String deliveryAddress = "Kyiv";
        String deliveryPhone = "0961150084";
        String redirectionPage = "samePage";
        String messageAbsentDishes = "The dish haven't been selected. Choose the dish for add it to basket.";
        when(session.getAttribute(RequestAttributes.CURRENT_ORDER)).thenReturn(null);
        when(session.getAttribute(RequestAttributes.LOCALE)).thenReturn(locale);
        when(request.getParameter(RequestParameters.DELIVERY_ADDRESS)).thenReturn(deliveryAddress);
        when(request.getParameter(RequestParameters.DELIVERY_PHONE)).thenReturn(deliveryPhone);
        when(session.getAttribute(RequestAttributes.CURRENT_DISH)).thenReturn(null);
        when(orderService.getOrCreateOrder(user, deliveryAddress, deliveryPhone)).thenReturn(order);
        when(session.getAttribute(ERROR_MESSAGE)).thenReturn(null);
        when(session.getAttribute(ORDER_MESSAGE)).thenReturn(ORDER_MESSAGE);
        try (MockedStatic<Validator> validator = Mockito.mockStatic(Validator.class)) {
            validator.when(() -> Validator.validateDeliveryData(Integer.parseInt(dishesAmount), request))
                    .thenReturn(true);
            try (MockedStatic<URLDispatcher> util = Mockito.mockStatic(URLDispatcher.class)) {
                util.when(() -> URLDispatcher.getRefererPage(any(HttpServletRequest.class)))
                        .thenReturn(redirectionPage);
                makeOrder.execute(request, response);
                verify(session).setAttribute(CURRENT_ORDER, order);
                verify(session).setAttribute(ORDER_MESSAGE, messageAbsentDishes);
                verify(session).setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                verify(response).sendRedirect(redirectionPage);
            }
        }
    }
}