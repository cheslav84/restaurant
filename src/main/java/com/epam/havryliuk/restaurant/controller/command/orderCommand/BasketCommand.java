package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ERROR_MESSAGE;

public class BasketCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(BasketCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LOGGED_USER);


//        if (user == null) {//todo код дублюється
//            log.error("The user has been logged out.");
//            session.setAttribute(ERROR_MESSAGE, "You should log in to make an order.");
//            request.getRequestDispatcher(AppPagesPath.FORWARD_REGISTRATION).forward(request, response);
//        }


        try {
            List<Order> orders = orderService.getAllUserOrders(user);
//            List<Order> orders = null;
//
            session.setAttribute(ORDER_LIST, orders);
            session.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            session.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
        request.getRequestDispatcher(AppPagesPath.FORWARD_BASKET).forward(request, response);
//        request.getRequestDispatcher(AppPagesPath.FORWARD_REGISTRATION).forward(request, response);
    }


}