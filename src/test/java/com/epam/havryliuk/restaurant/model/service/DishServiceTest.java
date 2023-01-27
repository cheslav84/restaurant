package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DishServiceTest {

    @Mock
    private DishDao dishDao;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private DishService dishService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMenuByCategory() throws DAOException, ServiceException {
        int dishesInList = 5;
        when(dishDao.findPresentsByCategory(Category.SPECIALS)).thenReturn(getDishes(dishesInList));
        List<Dish> actual = dishService.getMenuByCategory(Category.SPECIALS);
        List<Dish> expected = getDishes(5);
        assertEquals(expected, actual);
        assertEquals(dishesInList, actual.size());
    }

    @Test
    void getMenuByCategoryNegative() throws DAOException, ServiceException {
        int dishesInList = 5;
        when(dishDao.findPresentsByCategory(Category.SPECIALS)).thenReturn(getDishes(dishesInList));
        List<Dish> actual = dishService.getMenuByCategory(Category.SPECIALS);
        actual.get(0).setName("Differ dish");
        List<Dish> expected = getDishes(5);
        assertNotEquals(expected, actual);
    }

    @Test
    void getMenuByCategoryException() throws DAOException {
        when(dishDao.findPresentsByCategory(Category.SPECIALS)).thenThrow(new DAOException());
        Exception exception = assertThrows(ServiceException.class, () -> dishService.getMenuByCategory(Category.SPECIALS));
        assertEquals("Such list of Dishes hasn't been found.", exception.getMessage());
    }

    @Test
    void getDish() throws DAOException, ServiceException {
        long dishId = 5;
        when(dishDao.findById(dishId)).thenReturn(getDishForTest(dishId));
        Dish actual = dishService.getDish(dishId);
        Dish expected = getDishForTest(dishId).orElseThrow(DAOException::new);
        assertEquals(expected, actual);
    }

    @Test
    void getDishException() throws DAOException {
        long dishId = 5;
        when(dishDao.findById(dishId)).thenThrow(new DAOException());
        Exception exception = assertThrows(ServiceException.class, () -> dishService.getDish(dishId));
        assertEquals("Such dish hasn't been found.", exception.getMessage());
    }

    @Test
    void getAllMenuSortedBy() throws DAOException, ServiceException {
        int dishesInList = 5;
        when(dishDao.getSortedByName()).thenReturn(getDishesByName(dishesInList));
        when(dishDao.getSortedByPrice()).thenReturn(getDishesByPrice(dishesInList));
        when(dishDao.getSortedByCategory()).thenReturn(getDishes(dishesInList));

        List<Dish> sortedByName = dishService.getAllMenuSortedBy("name");
        List<Dish> sortedByPrice = dishService.getAllMenuSortedBy("price");
        List<Dish> sortedByCategory = dishService.getAllMenuSortedBy("category");

        List<Dish> expectedByName = getDishesByName(5);
        List<Dish> expectedByPrice = getDishesByPrice(5);
        List<Dish> expectedByCategory = getDishes(5);

        assertEquals(expectedByName, sortedByName);
        assertEquals(expectedByPrice, sortedByPrice);
        assertEquals(expectedByCategory, sortedByCategory);
    }

    private List<Dish> getDishesByName(int size){
        return getDishes(size).stream()
                .sorted(Comparator.comparing(Dish::getName))
                .collect(Collectors.toList());
    }

    private List<Dish> getDishesByPrice(int size){
        return getDishes(size).stream()
                .sorted(Comparator.comparing(Dish::getPrice))
                .collect(Collectors.toList());
    }
    private List<Dish> getDishes(int size){
        List<Dish> dishes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Dish dish = Dish.getInstance("DishName" + i, new BigDecimal(size - i), i);
            dishes.add(dish);
        }
        return dishes;
    }

    private Optional<Dish> getDishForTest (Long dishId) {
       return Optional.of(Dish.getInstance(dishId, "Name", "lorem ispum",
                50, new BigDecimal(450), 30, "image.png"));
    }
}