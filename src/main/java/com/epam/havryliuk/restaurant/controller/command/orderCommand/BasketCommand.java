package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command to show the user orders page and list of orders in it. With orders for user
 * will be showed order statuses, dates that orders have been made, and lists of dishes
 * in each order.
 */
public class BasketCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(BasketCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;

    public BasketCommand() {
        orderService = ApplicationProcessor.getInstance(OrderService.class);
    }

    /**
     * Method executes the command that obtains list of user orders. If any order presents in the list,
     * the total price will be calculated for each order and will be placed to map. That map is storing
     * in session to display orders and their prices to user. If user haven't made any order or that orders
     * ara temporary unavailable for some reason, user will get the corespondent message from session.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.trace("BasketCommand.");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LOGGED_USER);
        try {
            List<Order> orders = orderService.getAllUserOrders(user);
            checkIfOrdersPresent(orders, session);
            session.setAttribute(ORDER_LIST, orders);
        } catch (EntityNotFoundException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.EMPTY_BASKET);
            LOG.debug(e);
        } catch (ServiceException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.USER_ORDERS_UNAVAILABLE);
            LOG.error(e);
        }
        request.getRequestDispatcher(AppPagesPath.FORWARD_BASKET).forward(request, response);
    }

    /**
     * Method checks if any order is present in list that have been received from storage.
     *
     * @throws EntityNotFoundException if the list is empty.
     */
    private void checkIfOrdersPresent(List<Order> orders, HttpSession session) throws EntityNotFoundException {
        if (orders.size() == 0) {
            session.removeAttribute(ORDER_LIST);
            throw new EntityNotFoundException();
        }
    }
}