package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.resource.MessageManager;
import com.epam.havryliuk.restaurant.model.service.DishService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class MenuCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(MenuCommand.class);
    private static final String DEFAULT_MENU = "COFFEE";
    private static final String DEFAULT_SORTING = "name";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Category currentMenu = getCurrentMenu(request);
        MessageManager messageManager = MessageManager.valueOf((String) request.getSession().getAttribute(LOCALE));

        DishService dishService = new DishService();
        List<Dish> dishes = null;

        try {
            if (currentMenu.equals(Category.ALL)) {//todo не відповідає таблицям. Запитати.
                String sortParameter = getSortParameter(request);
                dishes = dishService.getAllMenuSortedBy(sortParameter);
                System.err.println("ALL command");
            } else {
                dishes = dishService.getMenuByCategory(getCurrentMenu(request));
            }

            if (dishes.isEmpty()) {
                request.setAttribute(MENU_MESSAGE,
                        messageManager.getProperty(ResponseMessages.MENU_EMPTY));
            }
            log.debug("List of dishes received by servlet and going to be sending to client side.");

        } catch (ServiceException e) {
            request.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.MENU_UNAVAILABLE));
            log.error(e);
        }
        hideOrderInfoOnReloadPage(request);
        request.setAttribute(DISH_LIST, dishes);
        request.getRequestDispatcher(AppPagesPath.FORWARD_MENU_PAGE).forward(request, response);
    }

    private String getSortParameter(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter(RequestParameters.MENU_SORTING_OPTION)).orElse(DEFAULT_SORTING);
    }


    @NotNull
    private Category getCurrentMenu(HttpServletRequest req) {//todo повторюється
        HttpSession session = req.getSession();
        String lastVisitedMenu = (String) session.getAttribute(MENU_CATEGORY);
        String currentMenu = req.getParameter(RequestParameters.MENU_CATEGORY);
        if (currentMenu != null) {
            session.setAttribute(MENU_CATEGORY, currentMenu);
        } else {
            currentMenu = Optional.ofNullable(lastVisitedMenu).orElse(DEFAULT_MENU);
//            if (lastVisitedMenu != null) {
//                currentMenu = lastVisitedMenu;
//            } else {
//                currentMenu = DEFAULT_MENU;
//            }
        }
        return Category.valueOf(currentMenu);
    }

    private void hideOrderInfoOnReloadPage(HttpServletRequest req)  {//todo повторюється
        HttpSession session = req.getSession();
        if (session.getAttribute(SHOW_DISH_INFO) != null){
            if (req.getAttribute(SHOW_DISH_INFO) == null) {
                req.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
//                log.debug("NEW request for or");
            } else  {
                req.removeAttribute(SHOW_DISH_INFO);
//                log.debug("This is a REFRESH");
            }
            session.removeAttribute(SHOW_DISH_INFO);
        }
    }

}