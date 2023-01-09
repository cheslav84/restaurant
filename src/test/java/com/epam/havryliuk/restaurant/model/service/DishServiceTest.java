package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DishServiceTest {


    @Mock
    DishDao dishDao;
//    List<Dish> dishes;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



//    @Test
//    void getMenuByCategory() {
//        when(dishes.get(0)).thenReturn("JournalDev");
//        assertEquals("JournalDev", mockList.get(0));
//    }

    @Test
    void getDish() {
    }

    @Test
    void getAllMenuSortedBy() {
    }
}