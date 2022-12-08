package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.controllers.MenuController;
import com.epam.havryliuk.restaurant.model.db.dao.DAO;
import com.epam.havryliuk.restaurant.model.db.dao.DaoImpl.DishDaoImpl;
import com.epam.havryliuk.restaurant.model.db.dao.DishDao;
import com.epam.havryliuk.restaurant.model.db.entity.Category;
import com.epam.havryliuk.restaurant.model.db.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.NoSuchElementException;

public class DishService {
    private static final Logger log = LogManager.getLogger(DishService.class);

    public List<Dish> getMenuByCategory(String categoryName) throws NoSuchEntityException {
        CategoryService categoryService = new CategoryService();

        Category category = categoryService.getCategoryByName(categoryName);
        DishDao dishDao;
        List<Dish> dishes = null;
        try {
            dishDao = new DishDaoImpl();
            dishes = dishDao.findByCategory(category);
        } catch (DBException e) {
            e.printStackTrace();
            throw new NoSuchElementException("Such list of Dishes hasn't been found.");
        }
        return dishes;

    }
}
