package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
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
        long orderId = Long.parseLong(request.getParameter(RequestParameters.ORDER_ID));
        long dishId = Long.parseLong(request.getParameter(RequestParameters.DISH_ID));
        HttpSession session = request.getSession();
        try {
            OrderService orderService = new OrderService();
            orderService.removeDishFromOrder(orderId, dishId);
            log.debug("Dish has been removed from order.");
            session.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.REMOVE_DISH_FROM_ORDER_ERROR));
            log.error(e);
        }
        response.sendRedirect(AppPagesPath.REDIRECT_BASKET);
    }


}