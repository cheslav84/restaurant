package com.havryliuk.restaurant.controllers;

import com.havryliuk.restaurant.Main;
import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.implemetnation.DishDaoImpl;
import com.havryliuk.restaurant.db.entity.Category;
import com.havryliuk.restaurant.db.entity.Dish;
import com.havryliuk.restaurant.exceptions.DBException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;


//todo read about PRG pattern

    @WebServlet("/")
    public class MenuController extends HttpServlet {


        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            System.out.println("do get");

            try {
                DataSource ds = null;
                Context initContext = new InitialContext();
                Context envContext  = (Context)initContext.lookup("java:/comp/env");
                ds = (DataSource)envContext.lookup("jdbc/Restaurant");
                Connection conn = ds.getConnection();

                System.out.println(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }



            DishDao dishDao = null;
            List<Dish> dishes = null;
            try {
                dishDao = new DishDaoImpl();
                dishes = dishDao.findByCategory(Category.getInstance("Coffee"));
//                System.out.println(dishes);
            } catch (DBException e) {
                e.printStackTrace();
            }

            req.setAttribute("dishes", "dishes");

            req.getRequestDispatcher("/WEB-INF/view/index.jsp")
                    .forward(req, resp);



        }
    }


