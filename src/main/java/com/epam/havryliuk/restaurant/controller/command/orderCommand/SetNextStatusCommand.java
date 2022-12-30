package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class SetNextStatusCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(SetNextStatusCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long orderId = Long.parseLong(request.getParameter(RequestParameters.ORDER_ID));
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        BookingStatus bookingStatus = getBookingStatus(request);
        try {
            orderService.changeOrderStatus(orderId, bookingStatus);
            session.removeAttribute(CURRENT_ORDER);
        } catch (ServiceException e) {
            MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LANGUAGE));
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ORDER_CONFIRM_ERROR));
            log.error(e);
        }

        String page = URLUtil.getRefererPage(request);
        response.sendRedirect(page);

//        response.sendRedirect("basket");


    }

    @NotNull
    private BookingStatus getBookingStatus(HttpServletRequest request) {
        String current = request.getParameter(RequestParameters.CURRENT_STATUS);
        BookingStatus currentStatus = BookingStatus.valueOf(current);
        long currentStatusId = currentStatus.getId();
        BookingStatus nextStatus = currentStatus;
        List<BookingStatus> statusList = Arrays.asList(BookingStatus.values());
        if (currentStatusId < statusList.size()) {
            nextStatus = BookingStatus.getStatus(currentStatusId + 1);
        }
        log.debug("Have request to change order status to " + nextStatus);
        return nextStatus;
    }


}