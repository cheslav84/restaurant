package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityAbsentException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class SetNextStatusCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(SetNextStatusCommand.class);

    private static final Map<BookingStatus, Role> bookingAccessRoles;
    private final OrderService orderService;

    static {
        bookingAccessRoles = getBookingAccessRoles();
    }

    public SetNextStatusCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        orderService = appContext.getInstance(OrderService.class);
        System.out.println(orderService);
    }



    /**
     * The command method gets the next booking status that has to be set in order, then checks
     * if the user has rights to do it, and if user has its, asks service to change the status by id.
     * If some unforeseen situation occurs, method catches the appropriate exception and sends corresponding
     * message to user.
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long orderId = Long.parseLong(request.getParameter(RequestParameters.ORDER_ID));
        HttpSession session = request.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
//        OrderService orderService = new OrderService();
        try {
            BookingStatus nextBookingStatus = getNextBookingStatus(request);
            checkAccessRights(session, nextBookingStatus);//todo подумати, може зробити через фільтр
            orderService.changeOrderStatus(orderId, nextBookingStatus);
            session.removeAttribute(CURRENT_ORDER);
        } catch (EntityAbsentException e) {
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ABSENT_DISHES) + e.getMessage());
            log.error("Some of dishes are already absent in menu.", e);
        } catch (ServiceException e) {
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.ORDER_CONFIRM_ERROR));
            log.error(e.getMessage(), e);
        } catch (AuthenticationException e) {
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.UNAPPROPRIATED_RIGHTS_TO_CHANGE_STATUS));
            log.error(e.getMessage(), e);
        }
        response.sendRedirect(URLUtil.getRefererPage(request));
    }


    /**
     * Checks if user has the rights to change the order status.
     * @param session
     * @param nextBookingStatus
     * @throws AuthenticationException
     */
    private void checkAccessRights(HttpSession session, BookingStatus nextBookingStatus) throws AuthenticationException {
        User user = (User) session.getAttribute(LOGGED_USER);
        if (!user.getRole().equals(bookingAccessRoles.get(nextBookingStatus))) {
            throw new AuthenticationException("Unappropriated rights to change the order status.");
        }
    }

    /**
     * Method retrieves current BookingStatus from the request,
     * and returns the status that has to be set next.
     * @param request
     * @return
     */
    @NotNull
    private BookingStatus getNextBookingStatus(HttpServletRequest request) {
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

    /**
     * Map contains the booking statuses and correspondent user roles
     * that has rights to set that statuses.
     * @return
     */
    private static Map<BookingStatus, Role> getBookingAccessRoles() {
        Map<BookingStatus, Role> bookingAccessRoles = new HashMap<>();
        bookingAccessRoles.put(BookingStatus.NEW, Role.CLIENT);
        bookingAccessRoles.put(BookingStatus.COOKING, Role.MANAGER);
        bookingAccessRoles.put(BookingStatus.IN_DELIVERY, Role.MANAGER);
        bookingAccessRoles.put(BookingStatus.WAITING_PAYMENT, Role.MANAGER);
        bookingAccessRoles.put(BookingStatus.PAID, Role.CLIENT);
        bookingAccessRoles.put(BookingStatus.COMPLETED, Role.MANAGER);
        return bookingAccessRoles;
    }

}