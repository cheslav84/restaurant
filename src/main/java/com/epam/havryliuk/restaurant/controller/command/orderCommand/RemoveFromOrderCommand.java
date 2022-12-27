package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.DuplicatedEntityException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import com.epam.havryliuk.restaurant.model.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class RemoveFromOrderCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RemoveFromOrderCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long orderId = Long.parseLong(request.getParameter("orderId"));
        long dishId = Long.parseLong(request.getParameter("dishId"));
        HttpSession session = request.getSession();
        try {
            OrderService orderService = new OrderService();
            orderService.removeDishFromOrder(orderId, dishId);
            log.debug("Dish has been removed from order.");
            session.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            session.setAttribute(ERROR_MESSAGE, errorMessage);
            log.error(errorMessage, e);
        }
        response.sendRedirect(AppPagesPath.REDIRECT_BASKET);
    }


}