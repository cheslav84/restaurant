package com.epam.havryliuk.restaurant.controller.filters;

import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
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

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.LOGGED_USER;


@WebFilter( urlPatterns = { "/basket", "/order",  "/removeFromOrder", "/makeOrder", "/showOrderInfo"},
        initParams = { @WebInitParam(name = "FORWARD_PATH", value = "WEB-INF/jsp/registration.jsp") })
//@WebFilter( urlPatterns = { "/basket", "/order", "/addToOrder", "/removeFromOrder", "/showOrderInfo"})
public class AuthFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AuthFilter.class);

    private String forwardPath;

    public void init(FilterConfig fConfig) throws ServletException {
        String forwardPath = "WEB-INF/jsp/registration.jsp";
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.debug("\"/FilterConfig\" doFilter starts.");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.debug("\"HttpServletMapping()\" " + httpRequest.getHttpServletMapping());

        if (!isUserLoggedIn(httpRequest)) {
//            httpRequest.getRequestDispatcher("WEB-INF/jsp/errorPage.jsp").forward(httpRequest, httpResponse);
            httpRequest.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(httpRequest, httpResponse);
//            httpResponse.sendRedirect("registration");
//            httpRequest.getRequestDispatcher(forwardPath).forward(httpRequest, httpResponse);
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
