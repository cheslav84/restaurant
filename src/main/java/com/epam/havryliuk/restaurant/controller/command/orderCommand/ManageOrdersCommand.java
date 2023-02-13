package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.OrderSorting;
import com.epam.havryliuk.restaurant.model.entity.Page;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command that displays list of orders to manager, navigates pagination,
 * and manages the sorting of order lists.
 */
public class ManageOrdersCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ManageOrdersCommand.class);
    private int pageNumber = 1;
    private int recordsPerPage = 4;
    private OrderSorting sortingParameter = OrderSorting.STATUS;
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;

    public ManageOrdersCommand() {
        orderService = ApplicationServiceContext.getInstance(OrderService.class);
    }

    /**
     * Method executes ManageOrder command that sets to class fields sorting parameters
     * (by creation time or by order status), sets current page number of displaying orders list
     * (in pagination process), and sets the amount of orders that is going to be displayed
     * in current page. Method receives Page of Orders from service that has to be displayed
     * in one user page, then sets to session following attributes: list of orders, total number
     * of pages for displaying navigation in user page and current page number. In case of receiving
     * ServiceException user will be informed by correspondent message.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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
            MessageDispatcher.setToRequest(request, ERROR_MESSAGE, ResponseMessages.ORDERS_ERROR);
            LOG.error(e.getMessage(), e);
        }
        request.getRequestDispatcher(AppPagesPath.FORWARD_MANAGE_ORDERS).forward(request, response);
    }

    /**
     * Method extracts the sorting parameters of order from request (by creation time or by order status),
     * and sets that value as the class field. If any exception occurs sorting orders will be by default.
     */
    private void setSortingParameter(HttpServletRequest request) {
        try {
            sortingParameter = OrderSorting.valueOf(request.getParameter(
                    RequestParameters.ORDER_SORTING_PARAMETER).toUpperCase());
            LOG.debug("Sorting parameter: " + sortingParameter);
        } catch (Exception e) {
            LOG.error("Sorting parameter is not correct.");
        }
    }

    /**
     * Method extracts the number of page that should be displayed for user, and sets that
     * value as the class field. If any exception occurs, the page number will be by default.
     */
    private void setPageNumber(HttpServletRequest request) {
        try {
            pageNumber = Integer.parseInt(request.getParameter(RequestParameters.PAGE_NUMBER));
        } catch (Exception e) {
            LOG.error("Page number isn't a Number.");
        }
    }

    /**
     * Method extracts the number of orders per page that should be displayed for user, and sets that
     * value as the class field. If any exception occurs, the number records will be by default.
     */
    private void setRecordsPerPage(HttpServletRequest request) {
        try {
            recordsPerPage = Integer.parseInt(request.getParameter(RequestParameters.RECORDS_PER_PAGE));
        } catch (Exception e) {
            LOG.error("Records per page isn't a Number.");
        }
    }

}