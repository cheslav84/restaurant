package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.responseManager.MenuResponseManager;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath.FORWARD_INDEX;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndexCommandTest {
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
    private DishService dishService;
    @Mock
    private MenuResponseManager menuResponseManager;
    @InjectMocks
    private IndexCommand index;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeDishesPresent() throws ServletException, IOException, ServiceException {
        int dishesAmount = 5;
        Category currentMenu = Category.COFFEE;
        List<Dish> dishes = getTestDishes(dishesAmount);
        when(menuResponseManager.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(dishService.getMenuByCategory(currentMenu)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        index.execute(request, response);
        verify(menuResponseManager).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_INDEX);
    }

    @Test
    void executeDishesAbsent() throws ServletException, IOException, ServiceException {
        String messageMenuEmpty = "There are no dishes it such category available now. Go to another category please.";
        Category currentMenu = Category.COFFEE;
        List<Dish> dishes = new ArrayList<>();
        when(menuResponseManager.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(dishService.getMenuByCategory(currentMenu)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        index.execute(request, response);
        verify(request).setAttribute(MENU_MESSAGE, messageMenuEmpty);
        verify(menuResponseManager).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_INDEX);
    }


    @Test
    void executeExceptionFromService() throws ServletException, IOException, ServiceException {
        String messageMenuEmpty = "Menu is temporary unavailable. Try again later please.";
        Category currentMenu = Category.COFFEE;
        List<Dish> dishes = null;
        when(menuResponseManager.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(dishService.getMenuByCategory(currentMenu)).thenThrow(new ServiceException("Such list of Dishes hasn't been found."));
        index.execute(request, response);
        verify(request).setAttribute(ERROR_MESSAGE, messageMenuEmpty);
        verify(menuResponseManager).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_INDEX);
    }

    private List<Dish> getTestDishes(int size){
        List<Dish> dishes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Dish dish = Dish.getInstance("DishName" + i, new BigDecimal(size - i), i);
            dishes.add(dish);
        }
        return dishes;
    }

}