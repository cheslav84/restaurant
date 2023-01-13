package com.epam.havryliuk.restaurant.controller.command.orderCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class OrderInfoCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(OrderInfoCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long dishId = Long.parseLong(request.getParameter(RequestParameters.DISH_ID));
        log.debug("\"/dishId\" " + dishId + " has been received from user.");
        HttpSession session = request.getSession();
        DishService dishService = new DishService();
        Dish dish;
        try {
            dish = dishService.getDish(dishId);
            session.setAttribute(CURRENT_DISH, dish);
            session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);// value to show ordering menu of concrete dish
            session.removeAttribute(ORDER_MESSAGE);
        } catch (ServiceException e) {
            MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.DISH_IN_MENU_NOT_FOUND));
            log.error(e);
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