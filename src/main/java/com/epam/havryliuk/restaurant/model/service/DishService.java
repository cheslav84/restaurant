package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DishService {
    private static final Logger log = LogManager.getLogger(DishService.class);
    public List<Dish> getMenuByCategory(Category category) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        DishDao dishDao;
        List<Dish> dishes;
        try {
            dishDao = new DishDao();
            transaction.init(dishDao);
            dishes = dishDao.findPresentsByCategory(category);
        } catch (DAOException e) {
            log.error("Such list of Dishes hasn't been found.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return dishes;
    }


    public Dish getDish(long dishId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        try {
            DishDao dishDao = new DishDao();
            transaction.init(dishDao);
            return dishDao.findById(dishId).orElseThrow(DAOException::new);
        } catch (DAOException e) {
            log.error("Such dish hasn't been found.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }


    public List<Dish> getAllMenuSortedBy(String sortParameter) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        DishDao dishDao;
        List<Dish> dishes;
        try {
            dishDao = new DishDao();
            transaction.init(dishDao);
            sortParameter = sortParameter.toLowerCase();
//TODO  EXCLUDE FROM CATEGORY SPECIAL, AS IT IS ALREADY IN ANOTHER ONE
            switch (sortParameter) {
                case  "name" -> dishes = dishDao.getSortedByName();
                case "price" -> dishes = dishDao.getSortedByPrice();
                case "category" -> dishes = dishDao.getSortedByCategory();
                default -> throw new ServiceException();
            }
        } catch (DAOException e) {
            log.error("Such list of Dishes hasn't been found.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return dishes;
    }

//    private List<Dish> createAndInsertDishes(int from, int to) {
////        DBManager dbm = DBManager.getInstance();
//
//
//        List<Dish> dishes = IntStream.range(from, to)
//                .mapToObj(x -> "dish" + x)
//                .map(Dish::getInstance)
//                .map(Dish::getInstance)
//                .collect(Collectors.toList());

//        for (Dish dish : dishes) {
//            dbm.insertUser(user);
//        }
//        return dishes;
//    }
}
