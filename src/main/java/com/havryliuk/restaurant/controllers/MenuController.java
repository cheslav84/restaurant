package com.havryliuk.restaurant.controllers;

import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.implemetnation.DishDaoImpl;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.exceptions.DBException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.GregorianCalendar;
import java.util.List;


//todo read about PRG pattern

@WebServlet("/")
public class MenuController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DishDao dishDao = null;
        List<Dish> dishes = null;
        try {
            dishDao = new DishDaoImpl();
            dishes = dishDao.findByCategory(Category.getInstance("Coffee"));

            for (Dish dish : dishes) {
                dish.getName();
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        request.setAttribute("dishes", dishes);
        request.getRequestDispatcher("/view/index.jsp").forward(request, response);
    }

}





//
//@WebServlet("/")
// class IndexController extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String path = request.getContextPath() + "/index";
//        response.sendRedirect(path);
//    }
//}
//







//@WebServlet("/timeaction")
// class TimeServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        processRequest(request, response);
//    }
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        GregorianCalendar gc = new GregorianCalendar();
//        String timeJsp = request.getParameter("time");
//        float delta = ((float)(gc.getTimeInMillis() - Long.parseLong(timeJsp)))/1_000;
//        request.setAttribute("res", delta);
//        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
//    }
//}


//@WebServlet("/index")
//public class MenuController extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//
//        processRequest(request, response);
//
////        req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
//    }
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
////        GregorianCalendar gc = new GregorianCalendar();
////        String timeJsp = request.getParameter("time");
////        float delta = ((float)(gc.getTimeInMillis() - Long.parseLong(timeJsp)))/1_000;
////        request.setAttribute("res", delta);
//
////        String menuList = request.getParameter("menu");
////
////        System.out.println(menuList);
//
//        DishDao dishDao = null;
//        List<Dish> dishes = null;
//        try {
//            dishDao = new DishDaoImpl();
//            dishes = dishDao.findByCategory(Category.getInstance("Coffee"));
//
//            for (Dish dish : dishes) {
//                dish.getName();
//            }
//        } catch (DBException e) {
//            e.printStackTrace();
//        }
//
//        request.setAttribute("dishes", dishes);
//
//        request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
//    }
//}

