package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.DishDao;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.annotations.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DishService implements Service {
    private static final Logger LOG = LogManager.getLogger(DishService.class);
    @Autowired
    private DishDao dishDao;
    @Autowired
    private EntityTransaction transaction;

    /**
     * Method receives list of Dishes that belongs to a certain Category. Depends on who is asking that
     * list, will be displayed all dishes (to Manager) or only available for ordering (to Client).
     * @param category of which list of Dishes should be received.
     * @return Dishes list of certain Category.
     * @throws ServiceException when some Exception occurs during receiving list of Dishes from a storage.
     */
    public List<Dish> getMenuByCategory(Category category, User user) throws ServiceException {
        List<Dish> dishes;
        try {
            transaction.init(dishDao);
            if (user != null && user.getRole().equals(Role.MANAGER) ) {
                dishes = dishDao.findAllByCategory(category);
            } else {
                dishes = dishDao.findAvailableByCategory(category);
            }
        } catch (DAOException e) {
            String errorMessage = "Such list of Dishes hasn't been found.";
            LOG.error(errorMessage);
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
            LOG.error(errorMessage);
            throw new ServiceException(errorMessage, e);
        } finally {
            transaction.end();
        }
    }

    /**
     * Method receives available list of Dishes (amount of dishes is greater than 0, so User can order it)
     * or the list of all Dishes (displays for manager) from a storage ordered by name, price or category.
     * @param sortParameter that list of Dishes has to be sorted by.
     * @return list of Dishes that is sorted by name, price or category.
     * @throws ServiceException when some Exception occurs during receiving list of Dishes from a storage.
     */
    public List<Dish> getAllAvailableMenuSortedBy(String sortParameter, User user) throws ServiceException {
        List<Dish> dishes;
        try {
            transaction.init(dishDao);
            sortParameter = sortParameter.toLowerCase();
            if (user != null && user.getRole().equals(Role.MANAGER) ) {
                dishes = getAllDishes(sortParameter);
            } else {
                dishes = getAvailableDishes(sortParameter);
            }
//            switch (sortParameter) {
//                case "name" -> dishes = dishDao.getAllAvailableSortedByName();
//                case "price" -> dishes = dishDao.getAllAvailableSortedByPrice();
//                case "category" -> dishes = dishDao.getAllAvailableSortedByCategory();
//                default -> throw new ServiceException();
//            }
        } catch (DAOException e) {
            LOG.error("Such list of Dishes hasn't been found.");
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return dishes;
    }

    /**
     * Asks storage all dishes sorted by parameter, that should be displayed for Manager
     * @param sortParameter that list of Dishes has to be sorted by.
     * @return list of Dishes that is sorted by name, price or category.
     */
    private List<Dish> getAllDishes (String sortParameter) throws DAOException, ServiceException {
        List<Dish> dishes;
        switch (sortParameter) {
            case "name" -> dishes = dishDao.getAllSortedByName();
            case "price" -> dishes = dishDao.getAllSortedByPrice();
            case "category" -> dishes = dishDao.getAllSortedByCategory();
            default -> throw new ServiceException();
        }
        return dishes;
    }

    /**
     * Asks storage all dishes sorted by parameter, that should be displayed for Client
     * @param sortParameter that list of Dishes has to be sorted by.
     * @return list of Dishes that is sorted by name, price or category.
     */
    private List<Dish> getAvailableDishes (String sortParameter) throws DAOException, ServiceException {
        List<Dish> dishes;
        switch (sortParameter) {
            case "name" -> dishes = dishDao.getAllAvailableSortedByName();
            case "price" -> dishes = dishDao.getAllAvailableSortedByPrice();
            case "category" -> dishes = dishDao.getAllAvailableSortedByCategory();
            default -> throw new ServiceException();
        }
        return dishes;
    }


    public void addNewDish(Dish dish) throws ServiceException, DuplicatedEntityException {
        try {
            transaction.initTransaction(dishDao);
            checkIfDishNameExists(dish, dishDao);
            dish = dishDao.create(dish);
            dishDao.addDishToCategory(dish, dish.getCategory());
            if (dish.isSpecial()) {
                dishDao.addDishToCategory(dish, Category.SPECIALS);
            }
            LOG.debug("The dish was successfully created.");
        } catch (DAOException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        } finally {
            transaction.endTransaction();
        }
    }

    private void checkIfDishNameExists(Dish dish, DishDao dishDao)
            throws DuplicatedEntityException, DAOException {
        if (dishDao.isDishNameExists(dish)) {
            String message = "Such dish is already exists.";
            LOG.info(message);
            throw new DuplicatedEntityException(message);
        }
    }

    public void updateDish(Dish dish) throws ServiceException {
        try {
            transaction.initTransaction(dishDao);
            dishDao.update(dish);
            dishDao.removeDishFromCategory(dish, Category.SPECIALS);
            dishDao.updateDishCategory(dish, dish.getCategory());
            if (dish.isSpecial()) {
                dishDao.addDishToCategory(dish, Category.SPECIALS);
            }
            LOG.debug("The dish was successfully updated.");
        } catch (DAOException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        } finally {
            transaction.endTransaction();
        }
    }

}
