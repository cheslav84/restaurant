package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.responseManager.MenuResponseManager;
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
import static com.epam.havryliuk.restaurant.controller.paths.AppPagesPath.FORWARD_MANAGER_MENU_PAGE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MenuCommandTest {
    private final Locale locale = new Locale("en", "EN");
    private User user;
    private List<Dish> dishes;
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
//    @Mock

    @Mock
    private MenuResponseManager menuResponseManager;
    @InjectMocks
    private MenuCommand menu;
    @BeforeEach
    public void setup() {
        user = User.getInstance("email", "password", "name", "surname", "Male", true);
        user.setRole(Role.MANAGER);
        int dishesAmount = 5;
        dishes = getTestDishes(dishesAmount);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeCoffeeDishesPresent() throws ServletException, IOException, ServiceException {
//        int dishesAmount = 5;
        Category currentMenu = Category.COFFEE;
//        List<Dish> dishes = getTestDishes(dishesAmount);
        when(menuResponseManager.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(session.getAttribute(LOGGED_USER)).thenReturn(user);
        when(dishService.getMenuByCategory(currentMenu, user)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        menu.execute(request, response);
        verify(dishService).getMenuByCategory(menuResponseManager.getCurrentMenu(request), user);
        verify(menuResponseManager).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_MANAGER_MENU_PAGE);
    }


    @Test
    void executeAllDishesPresent() throws ServletException, IOException, ServiceException {

        String sortParameter = "name";
        Category currentMenu = Category.ALL;
//        User user = User.getInstance("email", "password", "name", "surname", "Male", true);
//        user.setRole(Role.MANAGER);
//        List<Dish> dishes = getTestDishes(dishesAmount);
        when(menuResponseManager.getCurrentMenu(request)).thenReturn(currentMenu);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(session.getAttribute(LOGGED_USER)).thenReturn(user);
        when(dishService.getAllAvailableMenuSortedBy(sortParameter, user)).thenReturn(dishes);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        menu.execute(request, response);
        verify(dishService).getAllAvailableMenuSortedBy(sortParameter, user);
        verify(menuResponseManager).setOrderInfoAttribute(request);
        verify(request).setAttribute(DISH_LIST, dishes);
        verify(request).getRequestDispatcher(FORWARD_MANAGER_MENU_PAGE);
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