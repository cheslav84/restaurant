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

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.DISH_LIST;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.LOCALE;
import static com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath.FORWARD_INDEX;
import static com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath.FORWARD_MENU_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MenuCommandTest {
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
    private MenuCommand menu;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeCoffeeDishesPresent() throws ServletException, IOException, ServiceException {
        int dishesAmount = 5;
        Category currentMenu = Category.COFFEE;
        List<Dish> dishes = getTestDishes(dishesAmount);
        when(menuResponseManager.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(dishService.getMenuByCategory(currentMenu)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        menu.execute(request, response);
        verify(dishService).getMenuByCategory(menuResponseManager.getCurrentMenu(request));
        verify(menuResponseManager).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_MENU_PAGE);
    }


    @Test
    void executeAllDishesPresent() throws ServletException, IOException, ServiceException {
        int dishesAmount = 5;
        String sortParameter = "name";
        Category currentMenu = Category.ALL;
        List<Dish> dishes = getTestDishes(dishesAmount);
        when(menuResponseManager.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(dishService.getAllMenuSortedBy(sortParameter)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        menu.execute(request, response);
        verify(dishService).getAllMenuSortedBy(sortParameter);
        verify(menuResponseManager).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_MENU_PAGE);;
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