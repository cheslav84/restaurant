package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.database.dao.DaoImpl.DishDaoImpl;
import com.epam.havryliuk.restaurant.model.database.dao.DishDao;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DBException;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.NoSuchElementException;

public class DishService {
    private static final Logger log = LogManager.getLogger(DishService.class);

    public List<Dish> getMenuByCategory(String categoryName) throws NoSuchEntityException {
        CategoryService categoryService = new CategoryService();

        if (categoryName == "") {

        }//todo забув що щотів зробити..


        Category category = categoryService.getCategoryByName(categoryName);
        DishDao dishDao;
        List<Dish> dishes;
        try {
            dishDao = new DishDaoImpl();
            dishes = dishDao.findByCategory(category);
        } catch (DBException e) {
            e.printStackTrace();
            throw new NoSuchElementException("Such list of Dishes hasn't been found.");
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
            dishDao = new DishDaoImpl();
            dish = dishDao.findById(dishId);
            log.debug("\"/dish\" " + dish + " has been received from database.");

        } catch (DBException e) {
            e.printStackTrace();
            throw new NoSuchElementException("Such dish hasn't been found.");
        }

        HttpSession session = req.getSession();
        session.setAttribute("currentDish", dish);
        session.setAttribute("showDishInfo", "showDishInfo");// value to show ordering menu of concrete dish
    }

    public void hideOrderInfoOnReloadPage(HttpServletRequest req)  {
        HttpSession session = req.getSession();
        if (session.getAttribute("showDishInfo") != null){
            if (req.getAttribute("showDishInfo") == null) {
                req.setAttribute("showDishInfo", "showDishInfo");
                log.debug("NEW request for or");
            } else  {
                req.removeAttribute("showDishInfo");
                log.debug("This is a REFRESH");
            }
        session.removeAttribute("showDishInfo");
        }
    }

}
