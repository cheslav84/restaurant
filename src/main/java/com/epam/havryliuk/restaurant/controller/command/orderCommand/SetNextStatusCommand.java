package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.controller.dispatchers.URLDispatcher;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.*;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command changes Order Statuses to the next position.
 */
public class SetNextStatusCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(SetNextStatusCommand.class);
    private static final Map<BookingStatus, Role> bookingAccessRoles;
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;

    static {
        bookingAccessRoles = getBookingAccessRoles();
    }

    public SetNextStatusCommand() {
        orderService = ApplicationProcessor.getInstance(OrderService.class);
    }

    /**
     * The command method gets the next booking status that has to be set in order, then checks
     * if the user has rights to do it, and if user has its, asks service to change the status by id.
     * If some unforeseen situation occurs, method catches the appropriate exception and sends corresponding
     * message to user.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.trace("SetNextStatusCommand.");
        long orderId = Long.parseLong(request.getParameter(RequestParameters.ORDER_ID));
        HttpSession session = request.getSession();
        try {
            BookingStatus nextBookingStatus = getNextBookingStatus(request);
            checkAccessRights(session, nextBookingStatus);
            orderService.changeOrderStatus(orderId, nextBookingStatus);
            session.removeAttribute(CURRENT_ORDER);
        } catch (EntityNotFoundException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE,
                    ResponseMessages.DISH_ALREADY_IN_ORDER + e.getMessage());
            LOG.info("Some of dishes are already absent in menu.", e);
        } catch (ServiceException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.ORDER_CONFIRM_ERROR);
            LOG.info(e.getMessage(), e);
        } catch (AuthenticationException e) {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.UNAPPROPRIATED_RIGHTS_TO_CHANGE_STATUS);
            LOG.info(e.getMessage(), e);
        }
        response.sendRedirect(URLDispatcher.getRefererPage(request));
    }

    /**
     * Checks if user has the rights to change the order status.
     *
     * @param nextBookingStatus Booking status that needs to be set as next Status
     * @throws AuthenticationException when user doesn't have right to change it, for example
     *                                 if User Role is Manager than he can't change Booking status from "Booking" to "New", or
     *                                 if User Role is User, Status also can't be changed from "New" to "Cooking", and so on.
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
     */
    private BookingStatus getNextBookingStatus(HttpServletRequest request) {
        String current = request.getParameter(RequestParameters.CURRENT_STATUS);
        BookingStatus currentStatus = BookingStatus.valueOf(current);
        long currentStatusId = currentStatus.getId();
        BookingStatus nextStatus = currentStatus;
        List<BookingStatus> statusList = Arrays.asList(BookingStatus.values());
        if (currentStatusId < statusList.size()) {
            nextStatus = BookingStatus.getStatus(currentStatusId + 1);
        }
        LOG.debug("Have request to change order status to {}", nextStatus);
        return nextStatus;
    }

    /**
     * Map contains the booking statuses and correspondent user roles
     * that has rights to set that statuses.
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