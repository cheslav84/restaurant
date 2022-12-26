package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.service.DishService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class OrderInfoCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(OrderInfoCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long dishId = Long.parseLong(request.getParameter("dishId"));
        log.debug("\"/dishId\" " + dishId + " has been received from user.");
        HttpSession session = request.getSession();
        DishService dishService = new DishService();//todo redirect if not logged in
        Dish dish = dishService.getDish(dishId);
        session.setAttribute(CURRENT_DISH, dish);
        session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);// value to show ordering menu of concrete dish
        session.removeAttribute(ORDER_MESSAGE);
        response.sendRedirect("index");
    }
}