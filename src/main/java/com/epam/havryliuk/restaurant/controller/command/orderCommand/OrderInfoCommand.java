package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

/**
 * Command displays to user order info panel on his click to "order" button
 * with following information: dish details, fields for entering amount of dishes,
 * delivery address and phone with buttons that sends dish to basket on press of them.
 */
public class OrderInfoCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(OrderInfoCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public OrderInfoCommand() {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        dishService = appContext.getInstance(DishService.class);
    }

    /**
     * Method executes command that receives dish by its id, sets it to HttpSession, and set to session
     * attribute flag that informs user page to show ordering menu of concrete dish. If, on some reason,
     * ServiceException occurs while getting a Dish, user will be informed with correspondent message.
     * All above can be done from several places, and which of pages is going to be redirected to, decides
     * "getRefererPage" method on "URLUtil" class. When user got logged out, after some period of time doing
     * nothing, execution this command will lead him to the login page.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long dishId = Long.parseLong(request.getParameter(RequestParameters.DISH_ID));
        LOG.debug("\"/dishId\" " + dishId + " has been received from user.");
        HttpSession session = request.getSession();
        Dish dish;
        try {
            dish = dishService.getDish(dishId);
            session.setAttribute(CURRENT_DISH, dish);
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            session.removeAttribute(ORDER_MESSAGE);
        } catch (ServiceException e) {
            BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.DISH_IN_MENU_NOT_FOUND));
            LOG.error(e);
        }
        String redirectingPage;
        if (session.getAttribute(LOGGED_USER) != null) {
            redirectingPage = URLUtil.getRefererPage(request);
        } else {
            redirectingPage = AppPagesPath.FORWARD_REGISTRATION;
        }
        response.sendRedirect(redirectingPage);
    }
}