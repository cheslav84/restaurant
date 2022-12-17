//package com.epam.havryliuk.restaurant.model.database.dao;
//
//import com.epam.havryliuk.restaurant.model.entity.Dish;
//import com.epam.havryliuk.restaurant.model.entity.Category;
//import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
//
//import java.util.List;
//
//public interface DishAbstractDao extends AbstractDAO<Dish> {
//    Dish findByName(String name) throws DAOException;
//    List<Dish> findByCategory(Category category) throws DAOException;
//    List<Dish> getSortedByName() throws DAOException;
//    List<Dish> getSortedByPrice() throws DAOException;
//    List<Dish> getSortedByCategory() throws DAOException;
//}
