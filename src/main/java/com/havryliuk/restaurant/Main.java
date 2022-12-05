package com.havryliuk.restaurant;


import com.havryliuk.restaurant.db.connection.ConnectionPool;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.EntityDao;
import com.havryliuk.restaurant.db.dao.implemetnation.CategoryDaoImpl;
import com.havryliuk.restaurant.db.dao.implemetnation.DishDaoImpl;
import com.havryliuk.restaurant.db.dao.implemetnation.UserDaoImpl;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.db.entity.Manager;
import com.havryliuk.restaurant.db.entity.User;
import com.havryliuk.restaurant.exceptions.DBException;

import java.sql.Connection;
import java.util.List;

public class Main {

//    public List<Dish> main() throws DBException {
    public static void main(String[] args) throws DBException {

        long startTime = System.currentTimeMillis();
        DishDao dishDao = new DishDaoImpl();
        List<Dish> dishes = dishDao.findByCategory(Category.getInstance("Coffee"));
        System.out.println(dishes);
        long finishTime = System.currentTimeMillis();
        System.out.println(finishTime - startTime);


        EntityDao<Long, ? super Manager> dao = new UserDaoImpl<>();


        Object o = dao.findByName("name");
        User user = dao.findByName("userName");
        Manager manager = dao.findByName("managerName");


        dao.create()






//return dishes;
//        List<Dish> dishes1 = dishDao.findAll();
//        System.out.println(dishes1);


//        BlockingQueue<Integer> list = new LinkedBlockingQueue<>();
//        list.add(10);
//        for (Integer value : list) {
//            list.remove(value);
//        }



//        ConnectionPool connectionPool = RestaurantConnectionPool.getInstance();
//        Connection con1 = connectionPool.getConnection();
////        Connection con2 = connectionPool.getConnection();
////        Connection con3 = connectionPool.getConnection();
////        System.out.println(con1.isValid(3));
//        connectionPool.shutdown();
////        System.out.println(con3.isValid(3));
//
////        DishDao dishDao = new DishDaoImpl();
//
//        Dish dish = dishDao.findByName("Single Cup Brew");
//        System.out.println(dish);
//        System.out.println(dishDao.getSortedByName());
//        System.out.println(dishDao.getSortedByPrice());

//        CategoryDao categoryDao = new CategoryDaoImpl();
//        Category category = Category.getInstance("Ncategory");
//        categoryDao.create(category);
//        System.out.println(category.getId());
//        category.setName("Updated category");
//        categoryDao.update(category);
//        categoryDao.delete(category);
//        category = Category.getInstance("New category");
//        categoryDao.create(category);
//        Long id = category.getId();
//        categoryDao.delete(id);




    }
}
