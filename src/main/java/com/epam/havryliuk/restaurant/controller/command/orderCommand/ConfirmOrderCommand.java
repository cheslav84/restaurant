package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;

import com.epam.havryliuk.restaurant.model.database.dao.EntityTransaction;
import com.epam.havryliuk.restaurant.model.database.dao.daoImpl.OrderDao;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.exceptions.DAOException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class ConfirmOrderCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ConfirmOrderCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long orderId = Long.parseLong(request.getParameter("orderId"));
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        try {
            orderService.confirmOrder(orderId);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            session.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
        response.sendRedirect("basket");
    }


}