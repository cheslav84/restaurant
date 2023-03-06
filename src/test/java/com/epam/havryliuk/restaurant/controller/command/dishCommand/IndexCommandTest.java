package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.dispatchers.MenuDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath.FORWARD_INDEX;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndexCommandTest {
    private final Locale locale = new Locale("en", "EN");

    private User user;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DishService dishService;

    @Mock
    private MenuDispatcher menuDispatcher;
    @InjectMocks
    private IndexCommand indexCommand;

    @BeforeAll
    public void initUser() {
        user = new User.UserBuilder()
                .withEmail("email")
                .withPassword("password")
                .withName("name")
                .withSurname("surname")
                .withGender("Male")
                .withOverEighteen(true)
                .withRole(Role.CLIENT)
                .build();
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeDishesPresent() throws ServletException, IOException, ServiceException {
        int dishesAmount = 5;
        Category currentMenu = Category.COFFEE;
        List<Dish> dishes = getTestDishes(dishesAmount);
        when(menuDispatcher.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOGGED_USER)).thenReturn(user);
        when(dishService.getMenuByCategory(currentMenu, user)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        indexCommand.execute(request, response);
        verify(menuDispatcher).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_INDEX);
    }

    @Test
    void executeDishesAbsent() throws ServletException, IOException, ServiceException {
        String messageMenuEmpty = "There are no dishes it such category available now. Go to another category please.";
        Category currentMenu = Category.COFFEE;
        List<Dish> dishes = new ArrayList<>();
        when(menuDispatcher.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(session.getAttribute(LOGGED_USER)).thenReturn(user);
        when(dishService.getMenuByCategory(currentMenu, user)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        indexCommand.execute(request, response);
        verify(request).setAttribute(MENU_MESSAGE, messageMenuEmpty);
        verify(menuDispatcher).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_INDEX);
    }


    @Test
    void executeExceptionFromService() throws ServletException, IOException, ServiceException {
        String messageMenuEmpty = "Menu is temporary unavailable. Try again later please.";
        Category currentMenu = Category.COFFEE;
        when(menuDispatcher.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(session.getAttribute(LOGGED_USER)).thenReturn(user);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(dishService.getMenuByCategory(currentMenu, user)).thenThrow(new ServiceException("Such list of Dishes hasn't been found."));
        indexCommand.execute(request, response);
        verify(request).setAttribute(ERROR_MESSAGE, messageMenuEmpty);
        verify(menuDispatcher).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, null);
        verify(request).getRequestDispatcher(FORWARD_INDEX);
    }

    private List<Dish> getTestDishes(int size){
        List<Dish> dishes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Dish dish = new Dish.DishBuilder()
                    .withName("DishName" + i)
                    .withPrice(new BigDecimal(size - i))
                    .withAmount(i)
                    .build();
            dishes.add(dish);
        }
        return dishes;
    }

}