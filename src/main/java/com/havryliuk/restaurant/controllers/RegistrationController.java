package com.havryliuk.restaurant.controllers;

import com.havryliuk.restaurant.db.dao.DishDao;
import com.havryliuk.restaurant.db.dao.implemetnation.DishDaoImpl;
import com.havryliuk.restaurant.db.entity.*;
import com.havryliuk.restaurant.exceptions.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;


//todo read about PRG pattern

@WebServlet("/registration")
//@WebServlet("/reservation")
public class RegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //todo ask from which page user came
//        request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
        request.getRequestDispatcher("/view/registration.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        //todo create Session

        //todo validate

        final String email = request.getParameter("email");

        final String password  = request.getParameter("password");
        //todo encrypt password

        final String name = request.getParameter("name");
        final String surname = request.getParameter("surname");
        final String gender = request.getParameter("userGender");
        final boolean isOverEighteen = request.getParameter("userOverEighteenAge") != null;
        final Role userRole = Role.getInstance(Role.UserRole.CLIENT);

        final User user = User.getInstance(email, password, name, surname, gender, isOverEighteen, userRole);

        System.out.println(user);

        final long id;//todo add to DB and get id


//        request.getRequestDispatcher("/WEB-INF/view/user.jsp").forward(request, response);
        request.getRequestDispatcher("/view/index.jsp").forward(request, response);
        //todo redirect
    }

}

