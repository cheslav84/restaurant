package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();

        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));

        int pageNo = 1;
        int recordsPerPage = 2;

        if (request.getParameter("page") != null) {
            pageNo = Integer.parseInt(request.getParameter("page"));
        }

        try {
            Page<Order> ordersPage = orderService.getAllOrders(pageNo, recordsPerPage);
            List<Order> orders = ordersPage.getRecords();
            int noOfPages = ordersPage.getNoOfPages();
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", pageNo);
            request.setAttribute(ORDER_LIST, orders);
            request.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            request.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ORDERS_ERROR));
        }

//        int noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);





//        RequestDispatcher view = request.getRequestDispatcher("display.jsp");
//
//        view.forward(request, response);



        request.getRequestDispatcher(AppPagesPath.FORWARD_MANAGE_ORDERS).forward(request, response);
    }


}