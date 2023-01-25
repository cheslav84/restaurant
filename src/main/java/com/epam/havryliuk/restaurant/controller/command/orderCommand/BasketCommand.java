package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
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

public class BasketCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(BasketCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;

    public BasketCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        orderService = appContext.getInstance(OrderService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
        User user = (User) session.getAttribute(LOGGED_USER);
        try {
            List<Order> orders = orderService.getAllUserOrders(user);
            checkIfOrdersPresent(orders);
            Map<Order, BigDecimal> ordersAndTotalPriced = orderService.getTotalPrices(orders);
            session.setAttribute(ORDER_PRICE_MAP, ordersAndTotalPriced);
        } catch (EntityNotFoundException e) {
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.EMPTY_BASKET));
            LOG.error(e);
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.USER_ORDERS_UNAVAILABLE));
            LOG.error(e);
        }
        request.getRequestDispatcher(AppPagesPath.FORWARD_BASKET).forward(request, response);
    }

    private void checkIfOrdersPresent(List<Order> orders) throws EntityNotFoundException {
        if (orders.size() == 0) {
            throw new EntityNotFoundException();
        }
    }
}