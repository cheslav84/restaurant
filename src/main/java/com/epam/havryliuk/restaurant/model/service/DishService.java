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

    /**
     * Method receives list of Dishes that belongs to a certain Category.
     * @param category of which list of Dishes should be received.
     * @return Dishes list of certain Category.
     * @throws ServiceException when some Exception occurs during receiving list of Dishes from a storage.
     */
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

    /**
     * Receives the Dish by its id from the storage.
     * @param dishId id of the Dish that is need to be received.
     * @return Dish from storage that is got by dish id.
     * @throws ServiceException while an Exception occurs in getting a dish from a storage.
     */
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

    /**
     * Method receives the list of Dishes from a storage ordered by name, price or category.
     * @param sortParameter that list of Dishes has to be sorted by.
     * @return list of Dishes that is sorted by name, price or category.
     * @throws ServiceException when some Exception occurs during receiving list of Dishes from a storage.
     */
    public List<Dish> getAllMenuSortedBy(String sortParameter) throws ServiceException {
        List<Dish> dishes;
        try {
            transaction.init(dishDao);
            sortParameter = sortParameter.toLowerCase();
            switch (sortParameter) {
                case "name" -> dishes = dishDao.getSortedByName();
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

}
