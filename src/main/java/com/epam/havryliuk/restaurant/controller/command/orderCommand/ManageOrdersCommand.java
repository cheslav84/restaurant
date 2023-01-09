package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.Page;
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
import java.util.List;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class ManageOrdersCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ManageOrdersCommand.class);
    private int defaultPageNumber = 1;
    private int recordsPerPage = 4;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();

        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));

        if (request.getParameter(RequestParameters.PAGE_NUMBER) != null) {
            defaultPageNumber = Integer.parseInt(request.getParameter(RequestParameters.PAGE_NUMBER));
        }

        if (request.getParameter(RequestParameters.RECORDS_PER_PAGE) != null) {
            recordsPerPage = Integer.parseInt(request.getParameter(RequestParameters.RECORDS_PER_PAGE));
        }

        try {
            Page<Order> ordersPage = orderService.getAllOrders(defaultPageNumber, recordsPerPage);
            List<Order> orders = ordersPage.getRecords();
            int noOfPages = ordersPage.getNoOfPages();
            request.setAttribute(NUMBER_OF_PAGES, noOfPages);
            request.setAttribute(CURRENT_PAGE, defaultPageNumber);
            request.setAttribute(ORDER_LIST, orders);
            request.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            request.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ORDERS_ERROR));
        }

        request.getRequestDispatcher(AppPagesPath.FORWARD_MANAGE_ORDERS).forward(request, response);
    }


}