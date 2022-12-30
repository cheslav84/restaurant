package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Basket;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.ERROR_MESSAGE;

public class BasketCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(BasketCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LOGGED_USER);
        try {
            List<Order> orders = orderService.getAllUserOrders(user);
            Map<Order, BigDecimal> ordersAndTotalPriced = getTotalPrices(orders);
            session.setAttribute(ORDER_PRICE_MAP, ordersAndTotalPriced);
            session.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.USER_ORDERS_UNAVAILABLE));
            log.error(e);
        }
        request.getRequestDispatcher(AppPagesPath.FORWARD_BASKET).forward(request, response);
    }

    private Map<Order, BigDecimal> getTotalPrices(List<Order> orders) {
        Map<Order, BigDecimal> ordersAndPrices = orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        o -> o.getBaskets()
                                .stream()
                                .map(basket -> basket.getFixedPrice().multiply(BigDecimal.valueOf(basket.getAmount())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));

        return ordersAndPrices.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(
                        (o1, o2) -> o2.getCreationDate()
                                .compareTo(o1.getCreationDate())
                )).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));

    }

}