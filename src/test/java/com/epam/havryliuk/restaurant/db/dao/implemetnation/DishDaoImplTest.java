//package com.epam.havryliuk.restaurant.db.dao.implemetnation;
//
//import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
//import com.epam.havryliuk.restaurant.model.entity.Dish;
//import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DishDaoImplTest {
//
//    private static DishDao dishDao;
//    private static Dish beer;
//
//    @BeforeEach
//    void setUp(){
//        dishDao = Mockito.mock(DishDao.class);
//        BigDecimal price = BigDecimal.valueOf(50.0);
//        beer = Dish.getInstance("Beer", price, 20);
//    }
//
//
//
//    @Test
//    void findDishByNamePositive() throws DAOException {
//        String name = "Beer";
//        Mockito.when(dishDao.findByName(name)).thenReturn(beer);
//        Dish dish = dishDao.findByName("Beer");
//        assertEquals(dish, beer);
//    }
//
//    @Test
//    void findDishByNameNegative() throws DAOException {
//        String name = "Beer";
//        Mockito.when(dishDao.findByName(name)).thenReturn(beer);
//        Dish fish = dishDao.findByName("Fish");
//        assertNull(fish);
//    }
//
//
//
//    @Test
//    void findByCategory() {
//    }
//
//    @Test
//    void getSortedByName() {
//    }
//
//    @Test
//    void getSortedByPrice() {
//    }
//
//    @Test
//    void getSortedByCategory() {
//    }
//
//    @Test
//    void create() {
//    }
//
//    @Test
//    void findById() {
//    }
//
//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void testDelete() {
//    }
//}