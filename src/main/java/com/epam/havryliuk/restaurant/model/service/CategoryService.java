package com.epam.havryliuk.restaurant.model.service;

import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.constants.CategoryName;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CategoryService {
    private static final Logger log = LogManager.getLogger(CategoryService.class);

    public Category getCategoryByName(String categoryName) throws ServiceException {
//        if (categoryName == null) {// todo get initial value from client side
//            categoryName = "COFFEE";
//        }


        Category category;
        try {
            category = Category.getInstance(CategoryName.valueOf(categoryName));
            log.debug("Service: category initialised " + category.getCategoryName().name());
        } catch (IllegalArgumentException e){
            log.error("Such category does not exist.", e);
            throw new ServiceException(e);
        }
//        log.debug("getCategoryByName: " + categoryName);
////
//         categoryName = "COFFEE";
//
//
//        category = Category.getInstance(Category.CategoryName.valueOf(categoryName));
//        log.debug("Category initialised " + category.getCategoryName().name());

        return category;
    }



}