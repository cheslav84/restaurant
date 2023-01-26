package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.responseManager.MenuResponseManager;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

/**
 * Command to show the menu page and list of dishes in it.
 */
public class MenuCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(MenuCommand.class);
    private static final String DEFAULT_SORTING = "name";
    @SuppressWarnings("FieldMayBeFinal")
    private MenuResponseManager menuResponseManager;
    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public MenuCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        dishService = appContext.getInstance(DishService.class);
        menuResponseManager = new MenuResponseManager();
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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Category currentMenu = menuResponseManager.getCurrentMenu(request);
        BundleManager bundleManager = BundleManager.valueOf(((Locale) request.getSession().getAttribute(LOCALE)).getCountry());
        List<Dish> dishes = null;
        List<Dish> specials = null;
        try {
            if (currentMenu.equals(Category.ALL)) {
                String sortParameter = getSortParameter(request);
                dishes = dishService.getAllMenuSortedBy(sortParameter);
            } else {
                dishes = dishService.getMenuByCategory(menuResponseManager.getCurrentMenu(request));
            }
            if (dishes.isEmpty()) {
                request.setAttribute(MENU_MESSAGE,
                        bundleManager.getProperty(ResponseMessages.MENU_EMPTY));
            }
            specials = dishService.getMenuByCategory(Category.SPECIALS);
            LOG.debug("List of dishes received by servlet and going to be sending to client side.");
        } catch (ServiceException e) {
            request.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.MENU_UNAVAILABLE));
            LOG.error(e);
        }
        menuResponseManager.setOrderInfoAttribute(request);
        request.setAttribute(DISH_LIST, dishes);
        request.setAttribute(SPECIALS_DISH_LIST, specials);
        request.getRequestDispatcher(AppPagesPath.FORWARD_MENU_PAGE).forward(request, response);
    }

    /**
     * @param request HttpServletRequest from user.
     * @return String dishes sorting option that obtains from HttpServletRequest.
     */
    private String getSortParameter(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter(RequestParameters.MENU_SORTING_OPTION)).orElse(DEFAULT_SORTING);
    }

}