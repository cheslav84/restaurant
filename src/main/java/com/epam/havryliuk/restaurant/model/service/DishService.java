package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.NoSuchElementException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.CURRENT_DISH;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.SHOW_DISH_INFO;

public class DishService {
    private static final Logger log = LogManager.getLogger(DishService.class);

    public List<Dish> getMenuByCategory(String categoryName) throws ServiceException {
        CategoryService categoryService = new CategoryService();

        if (categoryName == "") {

        }//todo забув що щотів зробити..

        Category category = categoryService.getCategoryByName(categoryName);
        EntityTransaction transaction = new EntityTransaction();

        DishDao dishDao;
        List<Dish> dishes;
        try {
            dishDao = new DishDao();
            transaction.init(dishDao);
            dishes = dishDao.findByCategory(category);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new NoSuchElementException("Such list of Dishes hasn't been found.");
        } finally {
            transaction.end();
        }
        return dishes;

    }

    public Dish getDish(long dishId){
        EntityTransaction transaction = new EntityTransaction();
        try {
            DishDao dishDao = new DishDao();
            transaction.init(dishDao);
            return dishDao.findById(dishId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new NoSuchElementException("Such dish hasn't been found.");
        } finally {
            transaction.end();
        }

    }





}
