package com.havryliuk.restaurant;


import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.DAO;
import com.havryliuk.restaurant.db.dao.implemetnation.DishDaoImpl;
import com.havryliuk.restaurant.db.dao.implemetnation.ManagerDAO;
import com.havryliuk.restaurant.db.dao.implemetnation.UserDAO;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.db.entity.Manager;
import com.havryliuk.restaurant.db.entity.User;
import com.havryliuk.restaurant.exceptions.DBException;

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




        DAO<Long, User> userDAO = new UserDAO<>();
        DAO<Long, User> managerDAO = new ManagerDAO();

        User user = userDAO.findByName("email");
        User manager = managerDAO.findByName("email");

        userDAO.create(new User());
        userDAO.create(new Manager());// todo ???
        managerDAO.create(new Manager());







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
