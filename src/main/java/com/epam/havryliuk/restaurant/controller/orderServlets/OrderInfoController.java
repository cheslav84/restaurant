package com.epam.havryliuk.restaurant.controller.orderServlets;

import com.epam.havryliuk.restaurant.model.services.DishService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


//todo read about PRG pattern

@WebServlet("/showOrderInfo")
public class OrderInfoController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(OrderInfoController.class);// todo add logs for class

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/order\" request doGet in OrderController");
//
//        HttpSession session = req.getSession();
//
//        //todo ask from which page user came
//        req.getRequestDispatcher("view/jsp/registration.jsp").forward(req, resp);
//    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("\"/login\" request doGet in OrderController");


        DishService dishService = new DishService();
        dishService.getDishInfo(req);//todo може винести реквести з рівня сервісів???

        resp.sendRedirect("index");
    }





}

