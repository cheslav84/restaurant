package com.epam.havryliuk.restaurant.controller.responseManager;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.entity.Category;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.MENU_CATEGORY;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.SHOW_DISH_INFO;

/**
 * Class manages which type of menu Category should be displayed
 * to client, in order to clicked button or default menu Category.
 * Class also decides whether should it be displayed order dish
 * information on uses page.
 */
public class MenuResponseManager {
    private static final Logger LOG = LogManager.getLogger(MenuResponseManager.class);

    private static final String DEFAULT_MENU = "COFFEE";

    /**
     * Method gets the information from request which menu Category
     * should be displayed for client in order to clicked button, and
     * sets the corespondent flag telling which Category to show to user
     * when he visited that page before. If no one category have been
     * chosen (no one button have been pressed, for example the user
     * visits the page for the first time) then attribute MENU_CATEGORY
     * in request will be null, and user haven't visited that page before,
     * the attribute MENU_CATEGORY in session is null than method sets
     * the default category.
     *
     * @param req HttpServletRequest from user.
     * @return Category of menu that will be displayed for client.
     */
    public Category getCurrentMenu(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lastVisitedMenu = (String) session.getAttribute(MENU_CATEGORY);
        String currentMenu = req.getParameter(RequestParameters.MENU_CATEGORY);
        if (currentMenu != null) {
            session.setAttribute(MENU_CATEGORY, currentMenu);
        } else {
            currentMenu = Optional.ofNullable(lastVisitedMenu).orElse(DEFAULT_MENU);
        }
        LOG.debug("Current menu: " + currentMenu);
        return Category.valueOf(currentMenu.toUpperCase());
    }

    /**
     * Method sets the flag attribute that tells to show order
     * dish information on jsp page when user clicked the button
     * to display it, and removes that attribute telling to hide
     * that menu when user reload page.
     *
     * @param req HttpServletRequest from user.
     */
    public void setOrderInfoAttribute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute(SHOW_DISH_INFO) != null) {
            if (req.getAttribute(SHOW_DISH_INFO) == null) {
                req.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                LOG.debug("Order information going to be displayed.");
            } else {
                req.removeAttribute(SHOW_DISH_INFO);
                LOG.debug("Order information going to be hide.");
            }
            session.removeAttribute(SHOW_DISH_INFO);
        }
    }

}
