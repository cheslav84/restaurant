package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Basket;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.service.OrderService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

/**
 * Command that removes one type of Dish from user Order list.
 */
public class RemoveFromOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RemoveFromOrderCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private OrderService orderService;

    public RemoveFromOrderCommand() {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        orderService = appContext.getInstance(OrderService.class);
    }

    /**
     * Method receives Order id and Dish id from HttpServletRequest, removes Dish from
     * Order firstly from storage place and then from HttpSession. If method receive
     * ServiceException from OrderService, User will get message that some problem
     * occurs in deleting the Dish.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long orderId = Long.parseLong(request.getParameter(RequestParameters.ORDER_ID));
        long dishId = Long.parseLong(request.getParameter(RequestParameters.DISH_ID));
        HttpSession session = request.getSession();
        try {
            orderService.removeDishFromOrder(orderId, dishId);
            removeDishFromSession(orderId, dishId, session);
            LOG.debug("Dish has been removed from order.");
            session.removeAttribute(ERROR_MESSAGE);
        } catch (ServiceException e) {
            BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.REMOVE_DISH_FROM_ORDER_ERROR));
            LOG.error(e);
        }
        response.sendRedirect(AppPagesPath.REDIRECT_BASKET);
    }

    /**
     * Method removes Dish from Order that stored in HttpSession. If remove dish only from
     * storage place and Order is present in Session, list of dishes from Order will be
     * shown to User from that Session, User will receive incorrect information. So, Dish
     * should be removed from Session Order as well.
     * Method check if Order is present in HttpSession. If it is, and Order id and Dish id
     * that is received from user coincides with Order id and Dish id that is present in
     * session, then Dish will be removed from that Order. Moreover, if it is the last Dish
     * in that Order (Basket list of Order is empty) than Order will be removed from Session.
     *
     * @param orderId that is received from client side.
     * @param dishId  that is received from client side.
     * @param session HttpSession from which Dish should be removed.
     */
    private void removeDishFromSession(long orderId, long dishId, HttpSession session) {
        Order order = (Order) session.getAttribute(CURRENT_ORDER);
        if (order != null) {
            List<Basket> basketList = order.getBaskets();
            basketList.removeIf(basket -> basket.getDish().getId() == dishId && basket.getOrder().getId() == orderId);
            if (basketList.size() == 0) {
                session.removeAttribute(CURRENT_ORDER);
            }
        }
    }
}