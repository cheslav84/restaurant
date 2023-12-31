package com.epam.havryliuk.restaurant.controller.filters;

import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.LOGGED_USER;

@WebFilter(filterName = "LogInFilter",  urlPatterns = {"/show_dish_info", "/make_order",  "/basket",
        "/remove_from_order", "/set_next_status/*", "/manage_orders", "/add_dish_page", "/managerMenu", "/edit_dish"})
public class LogInFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(LogInFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        LOG.debug("\"/LogInFilter\" doFilter starts.");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (!isUserLoggedIn(httpRequest)) {
            httpResponse.sendRedirect(AppPagesPath.REDIRECT_REGISTRATION);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isUserLoggedIn(HttpServletRequest httpRequest) {
        return httpRequest.getSession().getAttribute(LOGGED_USER) != null;
    }
}
