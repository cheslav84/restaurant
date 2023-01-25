package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.annotations.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DishService {
    private static final Logger log = LogManager.getLogger(DishService.class);
    @Autowired
    private DishDao dishDao;
    @Autowired
    private EntityTransaction transaction;

    public List<Dish> getMenuByCategory(Category category) throws ServiceException {
        List<Dish> dishes;
        try {
            transaction.init(dishDao);
            dishes = dishDao.findPresentsByCategory(category);
        } catch (DAOException e) {
            String errorMessage = "Such list of Dishes hasn't been found.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        } finally {
            transaction.end();
        }
        return dishes;
    }

    public Dish getDish(long dishId) throws ServiceException {
        try {
            transaction.init(dishDao);
            return dishDao.findById(dishId).orElseThrow(DAOException::new);
        } catch (DAOException e) {
            String errorMessage = "Such dish hasn't been found.";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        } finally {
            transaction.end();
        }
    }

    public List<Dish> getAllMenuSortedBy(String sortParameter) throws ServiceException {
        List<Dish> dishes;
        try {
            transaction.init(dishDao);
            sortParameter = sortParameter.toLowerCase();
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
