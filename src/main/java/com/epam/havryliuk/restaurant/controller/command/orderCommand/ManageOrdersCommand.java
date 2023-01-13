package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.OrderSorting;
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
    private static final Logger LOG = LogManager.getLogger(ManageOrdersCommand.class);
    private int pageNumber = 1;
    private int recordsPerPage = 4;
    private OrderSorting sortingParameter = OrderSorting.STATUS;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));

        setSortingParameter(request);
        setPageNumber(request);
        setRecordsPerPage(request);

        try {
            Page<Order> ordersPage = orderService.getAllOrders(pageNumber, recordsPerPage, sortingParameter);
            List<Order> orders = ordersPage.getRecords();
            int noOfPages = ordersPage.getNoOfPages();
            request.setAttribute(NUMBER_OF_PAGES, noOfPages);
            request.setAttribute(CURRENT_PAGE, pageNumber);
            request.setAttribute(ORDER_LIST, orders);
            request.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            request.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ORDERS_ERROR));
        }
        request.getRequestDispatcher(AppPagesPath.FORWARD_MANAGE_ORDERS).forward(request, response);
    }

    private void setSortingParameter(HttpServletRequest request) {
        try {
            System.err.println(request.getParameter(RequestParameters.ORDER_SORTING_PARAMETER));
            sortingParameter = OrderSorting.valueOf(request.getParameter(RequestParameters.ORDER_SORTING_PARAMETER).toUpperCase());
            LOG.debug("Sorting parameter: " + sortingParameter);
        } catch (Exception e) {
            LOG.error("Sorting parameter is not correct.");
        }
    }

    private void setPageNumber(HttpServletRequest request) {
        try {
             pageNumber = Integer.parseInt(request.getParameter(RequestParameters.PAGE_NUMBER));
        } catch (Exception e) {
            LOG.error("Page number isn't a Number.");
        }
    }

    private void setRecordsPerPage(HttpServletRequest request) {
        try {
            recordsPerPage = Integer.parseInt(request.getParameter(RequestParameters.RECORDS_PER_PAGE));
        } catch (Exception e) {
            LOG.error("Records per page isn't a Number.");
        }
    }

}