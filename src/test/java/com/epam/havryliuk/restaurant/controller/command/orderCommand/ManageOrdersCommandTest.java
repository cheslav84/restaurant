package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.OrderSorting;
import com.epam.havryliuk.restaurant.model.entity.Page;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManageOrdersCommandTest {
    Locale locale = new Locale("en", "EN");
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private ManageOrdersCommand manageOrders;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute() throws ServiceException, ServletException, IOException {
        int pageNumber = 1;
        int recordsPerPage = 2;
        OrderSorting sortingParameter = OrderSorting.STATUS;
        Page<Order> ordersPage = new Page<>();
        List<Order> orders = ordersPage.getRecords();
        int noOfPages = ordersPage.getNoOfPages();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(request.getParameter(RequestParameters.ORDER_SORTING_PARAMETER)).thenReturn(String.valueOf(sortingParameter));
        when(request.getParameter(RequestParameters.PAGE_NUMBER)).thenReturn(String.valueOf(pageNumber));
        when(request.getParameter(RequestParameters.RECORDS_PER_PAGE)).thenReturn(String.valueOf(recordsPerPage));
        when(request.getRequestDispatcher(AppPagesPath.FORWARD_MANAGE_ORDERS)).thenReturn(requestDispatcher);
        when(orderService.getAllOrders(pageNumber, recordsPerPage, sortingParameter)).thenReturn(ordersPage);
        manageOrders.execute(request, response);
        verify(request).setAttribute(NUMBER_OF_PAGES, noOfPages);
        verify(request).setAttribute(CURRENT_PAGE, pageNumber);
        verify(request).setAttribute(ORDER_LIST, orders);
    }

    @Test
    void executeExceptionFromService() throws ServiceException, ServletException, IOException {
        int pageNumber = 1;
        int recordsPerPage = 2;
        OrderSorting sortingParameter = OrderSorting.STATUS;
        Page<Order> ordersPage = new Page<>();
          when(request.getSession()).thenReturn(session);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(request.getParameter(RequestParameters.ORDER_SORTING_PARAMETER)).thenReturn(String.valueOf(sortingParameter));
        when(request.getParameter(RequestParameters.PAGE_NUMBER)).thenReturn(String.valueOf(pageNumber));
        when(request.getParameter(RequestParameters.RECORDS_PER_PAGE)).thenReturn(String.valueOf(recordsPerPage));
        when(orderService.getAllOrders(pageNumber, recordsPerPage, sortingParameter)).thenReturn(ordersPage);
        when(request.getRequestDispatcher(AppPagesPath.FORWARD_MANAGE_ORDERS)).thenReturn(requestDispatcher);
        manageOrders.execute(request, response);
        when(orderService.getAllOrders(pageNumber, recordsPerPage, sortingParameter)).thenThrow(new ServiceException("Unable to get orders."));
        Exception exception = assertThrows(ServiceException.class, () ->
                orderService.getAllOrders(pageNumber, recordsPerPage, sortingParameter));
        assertEquals("Unable to get orders.", exception.getMessage());
    }
}