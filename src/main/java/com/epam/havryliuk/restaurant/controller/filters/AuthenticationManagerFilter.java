package com.epam.havryliuk.restaurant.controller.filters;

import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.LOGGED_USER;

@WebFilter(filterName = "AuthenticationManagerFilter",
        urlPatterns = { "/manage_orders", "/add_dish_page", "/edit_dish"})
public class AuthenticationManagerFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(AuthenticationManagerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        LOG.debug("\"/AuthenticationManagerFilter\" doFilter starts.");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        User user = (User) httpRequest.getSession().getAttribute(LOGGED_USER);
        if (user != null && user.getRole() == Role.MANAGER) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(AppPagesPath.REDIRECT_INDEX);
        }
    }

}
