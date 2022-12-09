package com.epam.havryliuk.restaurant.controllers;

import com.epam.havryliuk.restaurant.model.db.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import com.epam.havryliuk.restaurant.model.services.DishService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import java.util.List;


//todo read about PRG pattern

@WebServlet("/index")
public class MenuController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(MenuController.class);// todo add logs for class

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("\"/index\" request doGet MenuController");

        String currentMenu = getCurrentMenu(req);

        DishService dishService = new DishService();
        List<Dish> dishes = null;
        try {
            dishes = dishService.getMenuByCategory(currentMenu);
            log.debug("List of dishes received by servlet and going to be sending to client side.");
        } catch (NoSuchEntityException e) {
            log.error("vList of dishes has been received.");
            //req.setAttribute("message", "Message");//todo inform user!!!
        }

        req.setAttribute("dishes", dishes);
        req.getRequestDispatcher("view/jsp/index.jsp").forward(req, resp);
    }


    @NotNull
    private String getCurrentMenu(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lastVisitedMenu = (String) session.getAttribute("menuCategory");
        String currentMenu = req.getParameter("menuCategory");
        if (currentMenu != null) {
            session.setAttribute("menuCategory", currentMenu);
        } else {
            if (lastVisitedMenu != null) {
                currentMenu = lastVisitedMenu;
            } else {
                currentMenu = "COFFEE";
            }
        }
        return currentMenu;
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        response.sendRedirect("index");
//    }

}















