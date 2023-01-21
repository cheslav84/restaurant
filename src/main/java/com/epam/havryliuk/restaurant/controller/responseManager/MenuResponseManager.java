package com.epam.havryliuk.restaurant.controller.responseManager;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.entity.Category;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.MENU_CATEGORY;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.SHOW_DISH_INFO;

public class MenuResponseManager {
    private static final String DEFAULT_MENU = "COFFEE";

    public Category getCurrentMenu(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lastVisitedMenu = (String) session.getAttribute(MENU_CATEGORY);
        String currentMenu = req.getParameter(RequestParameters.MENU_CATEGORY);
        if (currentMenu != null) {
            session.setAttribute(MENU_CATEGORY, currentMenu);
        } else {
            currentMenu = Optional.ofNullable(lastVisitedMenu).orElse(DEFAULT_MENU);
        }
        return Category.valueOf(currentMenu);
    }

    public void hideOrderInfoOnReloadPage(HttpServletRequest req)  {
        HttpSession session = req.getSession();
        if (session.getAttribute(SHOW_DISH_INFO) != null){
            if (req.getAttribute(SHOW_DISH_INFO) == null) {
                req.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
            } else  {
                req.removeAttribute(SHOW_DISH_INFO);
            }
            session.removeAttribute(SHOW_DISH_INFO);
        }
    }

}
