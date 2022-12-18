package com.epam.havryliuk.restaurant.model.services;

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

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.CURRENT_DISH;
import static com.epam.havryliuk.restaurant.controller.RequestAttributes.SHOW_DISH_INFO;

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

    public void getDishInfo(HttpServletRequest req){
//        List<Dish> dishes = new ArrayList<>();

        long dishId = Long.parseLong(req.getParameter("dishId"));
        log.debug("\"/dishId\" " + dishId + " has been received from user.");
        Dish dish;
        DishDao dishDao;
        try {
            dishDao = new DishDao();
            dish = dishDao.findById(dishId);
            log.debug("\"/dish\" " + dish + " has been received from database.");

        } catch (DAOException e) {
            e.printStackTrace();
            throw new NoSuchElementException("Such dish hasn't been found.");
        }

        HttpSession session = req.getSession();
        session.setAttribute(CURRENT_DISH, dish);
        session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);// value to show ordering menu of concrete dish
    }

    public void hideOrderInfoOnReloadPage(HttpServletRequest req)  {
        HttpSession session = req.getSession();
        if (session.getAttribute(SHOW_DISH_INFO) != null){
            if (req.getAttribute(SHOW_DISH_INFO) == null) {
                req.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
//                log.debug("NEW request for or");
            } else  {
                req.removeAttribute(SHOW_DISH_INFO);
//                log.debug("This is a REFRESH");
            }
        session.removeAttribute(SHOW_DISH_INFO);
        }
    }

}
