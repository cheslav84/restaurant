//package com.epam.havryliuk.restaurant.model.database.dao.daoImpl;
//
//import com.epam.havryliuk.restaurant.model.entity.Category;
//import com.epam.havryliuk.restaurant.model.entity.Dish;
//import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DishDaoTest {
//
//
//    @Mock
//    DishDao dishDAO;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
////    @Test
////    void findByNameTrue() throws DAOException {
////        Mockito.when(dishDAO.findByName("name1")).thenReturn(getDishInstance(1));
////        Dish dish = dishDAO.findByName("name1");
////        Assertions.assertEquals(dish, getDishInstance(1));
////    }
////
////    @Test
////    void findByNameFalse() throws DAOException {
////        Mockito.when(dishDAO.findByName("name1")).thenReturn(getDishInstance(1));
////        Dish dish = dishDAO.findByName("name1");
////        Assertions.assertNotEquals(dish, getDishInstance(2));
////    }
////
////    @Test
////    void findByNameException() throws DAOException {
////        Mockito.when(dishDAO.findByName("someName")).thenThrow(new DAOException());
////        Assertions.assertThrows(DAOException.class, () -> dishDAO.findByName("someName"));
////    }
//
//
//
//
//    @Test
//    void findPresentsByCategory() throws DAOException {
//        int expectedNumberOfDishes = 3;
//        Mockito.when(dishDAO.findPresentsByCategory(Category.COFFEE)).thenReturn(getDishesList(expectedNumberOfDishes));
//        List<Dish> dishes = dishDAO.findPresentsByCategory(Category.COFFEE);
//        assertEquals(dishes, getDishesList(expectedNumberOfDishes));
//        assertEquals(dishes.size(), expectedNumberOfDishes);
//        assertNotEquals(dishes.size(), expectedNumberOfDishes + 1);
//    }
//
//    @Test
//    void findPresentsByCategoryNotFound() throws DAOException {
//        int expectedNumberOfDishes = 0;
//        Mockito.when(dishDAO.findPresentsByCategory(Category.COFFEE)).thenReturn(getDishesList(expectedNumberOfDishes));
//        List<Dish> dishes = dishDAO.findPresentsByCategory(Category.COFFEE);
//        assertEquals(dishes.size(), 0);
//    }
//
//    @Test
//    void findPresentsByCategoryException() throws DAOException {
//        Mockito.when(dishDAO.findPresentsByCategory(Category.COFFEE)).thenThrow(new DAOException());
//        assertThrows(DAOException.class, () -> dishDAO.findPresentsByCategory(Category.COFFEE));
//    }
//
//    @Test
//    void getSortedByName() throws DAOException {
//        int expectedNumberOfDishes = 3;
//        Mockito.when(dishDAO.getSortedByName()).thenReturn(getDishesList(expectedNumberOfDishes));
//        List<Dish> dishes = dishDAO.getSortedByName();
//        assertTrue(dishes.get(0).getName().compareTo(dishes.get(1).getName()) < 1);
//        assertTrue(dishes.get(1).getName().compareTo(dishes.get(2).getName()) < 1);
//    }
//
//    @Test
//    void getSortedByPrice() throws DAOException {
//        int expectedNumberOfDishes = 3;
//        Mockito.when(dishDAO.getSortedByPrice()).thenReturn(getDishesList(expectedNumberOfDishes));
//        List<Dish> dishes = dishDAO.getSortedByPrice();
//        assertTrue(dishes.get(0).getPrice().compareTo(dishes.get(1).getPrice()) < 1);
//        assertTrue(dishes.get(1).getPrice().compareTo(dishes.get(2).getPrice()) < 1);
//    }
//
//    @Test
//    void findById() throws DAOException {
//        Mockito.when(dishDAO.findById(6)).thenReturn(Optional.of(getDishInstance(6)));
//        Optional<Dish> dish = dishDAO.findById(6);
//        assertEquals(dish.orElseThrow(DAOException::new), getDishInstance(6));
//    }
//
//    @Test
//    void getNumberOfAllDishesInOrder() throws DAOException {
//        int expectedNumberOfDishes = 1;
//        Dish dish = getDishInstance(2);
//        Mockito.when(dishDAO.getNumberOfAllDishesInOrder(dish)).thenReturn(expectedNumberOfDishes);
//        assertEquals(1, dishDAO.getNumberOfAllDishesInOrder(dish));
//    }
//
//    @Test
//    void updateDishesAmountByOrderedValuesPositive() throws DAOException {
//        Mockito.when(dishDAO.updateDishesAmountByOrderedValues(1)).thenReturn(true);
//        assertTrue(dishDAO.updateDishesAmountByOrderedValues(1));
//    }
//
//    @Test
//    void updateDishesAmountByOrderedValuesException() throws DAOException {
//        Mockito.when(dishDAO.updateDishesAmountByOrderedValues(2)).thenThrow(new DAOException());
//        assertThrows(DAOException.class, () -> dishDAO.updateDishesAmountByOrderedValues(2));
//    }
//
//    @Test
//    void getNumberOfEachDishInOrder() throws DAOException {
//        Map<String, Integer> dishesAmount = Map.of("fish", 2, "meet", 3);
//        Mockito.when(dishDAO.getNumberOfEachDishInOrder(3)).thenReturn(dishesAmount);
//        assertEquals(dishDAO.getNumberOfEachDishInOrder(3), dishesAmount);
//    }
//
//    @Test
//    void getNumberOfEachDishInOrderFalse() throws DAOException {
//        Map<String, Integer> dishesAmountMock = Map.of("fish", 2, "meet", 3);
//        Map<String, Integer> dishesAmount = Map.of("fish", 2, "meet", 3, "beer", 1);
//        Mockito.when(dishDAO.getNumberOfEachDishInOrder(3)).thenReturn(dishesAmountMock);
//        assertNotEquals(dishDAO.getNumberOfEachDishInOrder(3), dishesAmount);
//    }
//
//
//
//    private Dish getDishInstance(long id){
//        String name = "name" + id;
//        String description = "description" + id;
//        int weight = (int) id;
//        BigDecimal price = BigDecimal.valueOf(id);
//        int amount = (int) id;
//        String image = "image" + id;
//        return Dish.getInstance(id, name, description, weight, price, amount, image);
//    }
//
//    private List<Dish> getDishesList(int numberOfDishes) {
//        List<Dish> dishes = new ArrayList<>();
//        for (int i = 0; i < numberOfDishes; i++) {
//            dishes.add(getDishInstance(i));
//        }
//        return dishes;
//    }
//
//}