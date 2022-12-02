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
import java.io.PrintWriter;
import java.util.List;

public class MenuController {

    //todo read about PRG patter

    @WebServlet("/index")
    public class FrontController extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
            PrintWriter out = resp.getWriter();
            out.println("Print coffee menu.");

            DishDao dishDao = null;
            List<Dish> dishes = null;
            try {
                dishDao = new DishDaoImpl();
                dishes = dishDao.findByCategory(Category.getInstance("Coffee"));
            } catch (DBException e) {
                throw new RuntimeException(e);
            }


//            req.setAttribute("coffeeMenu", dishes);
            req.setAttribute("coffeeMenu", dishes.get(0));
            req.setAttribute("coffeeMenu", "dishes");




        }
    }
}

