package com.epam.havryliuk.restaurant.controller.filters;

import com.epam.havryliuk.restaurant.model.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.LOGGED_USER;


@WebFilter( urlPatterns = { "/basket", "/order", "/addToOrder", "/removeFromOrder", "/showOrderInfo"},
        initParams = { @WebInitParam(name = "FORWARD_PATH", value = "view/jsp/registration.jsp") })
public class AuthFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AuthFilter.class);

    private String indexPath;

    public void init(FilterConfig fConfig) throws ServletException {
        log.debug("\"/FilterConfig\" init starts.");
        indexPath = fConfig.getInitParameter("FORWARD_PATH");
        log.debug("\"/FilterConfig\" set redirection path to: " + indexPath);

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.debug("\"/FilterConfig\" doFilter starts.");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.debug("\"HttpServletMapping()\" " + httpRequest.getHttpServletMapping());

        if (!isUserLoggedIn(httpRequest)) {
            httpRequest.getRequestDispatcher(indexPath).forward(httpRequest, httpResponse);
        }
        log.debug("User logged in. ");
        chain.doFilter(request, response);
    }

    private boolean isUserLoggedIn(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
            User loggedUser = (User) session.getAttribute(LOGGED_USER);
            return loggedUser != null;
    }

}
