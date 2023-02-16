package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.dispatchers.DishDispatcher;
import com.epam.havryliuk.restaurant.controller.dispatchers.MenuDispatcher;
import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

/**
 * Command to show the menu page and list of dishes in it.
 */
public class MenuCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(MenuCommand.class);
    private static final String DEFAULT_SORTING = "name";
    @SuppressWarnings("FieldMayBeFinal")
    private MenuDispatcher menuDispatcher;
    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public MenuCommand () {
        dishService = ApplicationServiceContext.getInstance(DishService.class);
        menuDispatcher = new MenuDispatcher();
    }

    /**
     * Method executes the "menu" command, it gets list of dishes that is dependent on
     * Category (menu) which user asks, and sets that list as attribute to HttpServletRequest.
     * If dishes list is empty then correspondent message will be showed to user. Method also
     * asks the Response Manager to show or hide part of html code in menu page that allows
     * to display dish order information on that page. In contradistinction to index page,
     * menu page can show to user all dishes, and if user asks that he can choose the way of
     * sorting dishes.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.trace("MenuCommand.");
        User user = (User) request.getSession().getAttribute(LOGGED_USER);
        List<Dish> dishes = null;
        List<Dish> specials = null;
        String sortParameter = getSortParameter(request);
        try {
            dishes = getDishes(request, user, sortParameter);
            DishDispatcher.setMessageIfDishesAbsent(request, dishes);
            specials = dishService.getMenuByCategory(Category.SPECIALS, user);
            LOG.debug("List of dishes received by servlet and going to be sending to client side.");
        } catch (ServiceException e) {
            MessageDispatcher.setToRequest(request, ERROR_MESSAGE, ResponseMessages.MENU_UNAVAILABLE);
            LOG.error(e);
        }
        menuDispatcher.setOrderInfoAttribute(request);
        request.setAttribute(DISH_LIST, dishes);
        request.setAttribute(SPECIALS_DISH_LIST, specials);
        request.getSession().setAttribute(MENU_SORTING_OPTION, sortParameter);

        request.getRequestDispatcher(getRedirectionPage(user)).forward(request, response);
    }

    /**
     * Method decides which of page return to User depends on User Role.
     * @param user current User that page is need to be shown.
     * @return String representation of redirection URL.
     */

    private String getRedirectionPage(User user) {
        String redirectionPage;
        if (user != null && user.getRole().equals(Role.MANAGER) ) {
            redirectionPage = AppPagesPath.FORWARD_MANAGER_MENU_PAGE;
        } else {
            redirectionPage = AppPagesPath.FORWARD_MENU_PAGE;
        }
        return redirectionPage;
    }

    /**
     * Method decides which list of Dishes shows to User, depends on chose Category.
     * If User wants to get all dishes, then he chose sorting method of that dishes.
     * @param sortParameter parameter, or method according to which menu should be sorted to
     *                     (by name, price or Category).
     * @return List of Dishes that is need to be displayed for User.
     * @throws ServiceException throws is any unpredictable situation occurs, and will be
     * impossible to get dishes.
     */
    private List<Dish> getDishes(HttpServletRequest request, User user, String sortParameter) throws ServiceException {
        Category currentMenu = menuDispatcher.getCurrentMenu(request);
        List<Dish> dishes;
        if (currentMenu.equals(Category.ALL)) {
            dishes = dishService.getAllAvailableMenuSortedBy(sortParameter, user);
        } else {
            dishes = dishService.getMenuByCategory(currentMenu, user);
        }
        return dishes;
    }

    /**
     * @param request HttpServletRequest from user.
     * @return String dishes sorting option that obtains from HttpServletRequest.
     */
    private String getSortParameter(HttpServletRequest request) {
        String parameterFromRequest = request.getParameter(RequestParameters.MENU_SORTING_OPTION);
        String parameterFromSession = (String) request.getSession().getAttribute(MENU_SORTING_OPTION);
        if(parameterFromRequest != null) {
            return parameterFromRequest;
        } else if (parameterFromSession != null) {
            return parameterFromSession;
        }
        return DEFAULT_SORTING;
    }

}