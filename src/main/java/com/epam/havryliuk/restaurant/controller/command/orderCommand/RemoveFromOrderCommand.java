package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Basket;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.MessageManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class RemoveFromOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RemoveFromOrderCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;
    public RemoveFromOrderCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        orderService = appContext.getInstance(OrderService.class);
        System.out.println(orderService);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long orderId = Long.parseLong(request.getParameter(RequestParameters.ORDER_ID));
        long dishId = Long.parseLong(request.getParameter(RequestParameters.DISH_ID));
        HttpSession session = request.getSession();
        try {
            orderService.removeDishFromOrder(orderId, dishId);
            removeDishFromSession(orderId, dishId, session);
            LOG.debug("Dish has been removed from order.");
            session.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            MessageManager messageManager = MessageManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.REMOVE_DISH_FROM_ORDER_ERROR));
            LOG.error(e);
        }
        response.sendRedirect(AppPagesPath.REDIRECT_BASKET);
    }

    private void removeDishFromSession(long orderId, long dishId, HttpSession session) {
        Order order = (Order) session.getAttribute(CURRENT_ORDER);
        if (order != null) {
            List<Basket> basketList = order.getBaskets();
            basketList.removeIf(basket -> basket.getDish().getId() == dishId && basket.getOrder().getId() == orderId);
            if (basketList.size() == 0){
                session.removeAttribute(CURRENT_ORDER);
            }
        }
    }

}