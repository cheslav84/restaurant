package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.exceptions.EntityAbsentException;
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
        HttpSession session = request.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
        BookingStatus bookingStatus = getBookingStatus(request);
        OrderService orderService = new OrderService();
        try {
            orderService.changeOrderStatus(orderId, bookingStatus);//todo if booking status new check every dish if it is present
            session.removeAttribute(CURRENT_ORDER);
        } catch (EntityAbsentException e){
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ABSENT_DISHES) + e.getMessage());
            log.error("Some of dishes are already absent in menu.");
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ORDER_CONFIRM_ERROR));
            log.error(e.getMessage(), e);
        }
        response.sendRedirect(URLUtil.getRefererPage(request));
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