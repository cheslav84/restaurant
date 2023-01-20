package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityAbsentException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ERROR_MESSAGE;

public class BasketCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(BasketCommand.class);
    private final OrderService orderService;

    public BasketCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        orderService = appContext.getInstance(OrderService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
        User user = (User) session.getAttribute(LOGGED_USER);
        try {
            List<Order> orders = orderService.getAllUserOrders(user);
            checkIfOrdersPresent(orders);
            Map<Order, BigDecimal> ordersAndTotalPriced = orderService.getTotalPrices(orders);
            session.setAttribute(ORDER_PRICE_MAP, ordersAndTotalPriced);
//            session.removeAttribute(ERROR_MESSAGE);
        } catch (EntityAbsentException e) {
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.EMPTY_BASKET));
            log.error(e);
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.USER_ORDERS_UNAVAILABLE));
            log.error(e);
        }
        request.getRequestDispatcher(AppPagesPath.FORWARD_BASKET).forward(request, response);
    }

    private void checkIfOrdersPresent(List<Order> orders) throws EntityAbsentException {
        if (orders.size() == 0) {
            throw new EntityAbsentException();
        }
    }
}