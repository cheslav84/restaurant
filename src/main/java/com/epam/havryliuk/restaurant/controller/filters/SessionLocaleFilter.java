package com.epam.havryliuk.restaurant.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {
    private static final Logger log = LogManager.getLogger(SessionLocaleFilter.class);


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("\"/SessionLocaleFilter\" doFilter starts.");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        if (httpRequest.getParameter("locale") != null) {
            session.setAttribute("language", httpRequest.getParameter("locale"));
        } else if (session.getAttribute("language") == null) {
            session.setAttribute("language", httpRequest.getLocale().getLanguage());
        }
        chain.doFilter(request, response);
    }
}