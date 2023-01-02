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
import com.epam.havryliuk.restaurant.model.util.URLUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class IndexCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(IndexCommand.class);
    private static final String DEFAULT_MENU = "COFFEE";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Category currentMenu = getCurrentMenu(request);
        MessageManager messageManager = MessageManager.valueOf((String) request.getSession().getAttribute(LANGUAGE));

        DishService dishService = new DishService();
        List<Dish> dishes = null;
        try {
            dishes = dishService.getMenuByCategory(currentMenu);
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


//        String url = URLUtil.getRefererPage(request);
////        String anchor = getAnchor(request);
//        String path = null;
//        System.err.println(url);
//        if (url.startsWith("menu")) {
//            path = AppPagesPath.FORWARD_MENU_PAGE;
//        } else {
//            path = AppPagesPath.FORWARD_INDEX;
//        }

//        String sorting = request.getParameter("sort-menu-by");
//        System.err.println(sorting);

        request.getRequestDispatcher(AppPagesPath.FORWARD_INDEX).forward(request, response);
    }

//    private String getAnchor(HttpServletRequest request) {
//        return  "#" + request.getParameter("anchor");
//    }


    @NotNull
    private Category getCurrentMenu(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lastVisitedMenu = (String) session.getAttribute(MENU_CATEGORY);
        String currentMenu = req.getParameter(RequestParameters.MENU_CATEGORY);
        if (currentMenu != null) {
            session.setAttribute(MENU_CATEGORY, currentMenu);
        } else {
            if (lastVisitedMenu != null) {
                currentMenu = lastVisitedMenu;
            } else {
                currentMenu = DEFAULT_MENU;
            }
        }
        return Category.valueOf(currentMenu);
    }

    private void hideOrderInfoOnReloadPage(HttpServletRequest req)  {
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