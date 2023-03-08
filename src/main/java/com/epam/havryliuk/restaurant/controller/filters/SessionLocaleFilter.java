package com.epam.havryliuk.restaurant.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.LOCALE;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(SessionLocaleFilter.class);
    private static final Locale EN = new Locale("en", "EN");
    private static final Locale UA = new Locale("uk", "UA");


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.debug("\"/SessionLocaleFilter\" doFilter starts.");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String requestedLocale = httpRequest.getParameter(LOCALE);

        if (requestedLocale != null) {
            Locale locale;

            switch (requestedLocale) {
                case "EN": locale = EN;
                break;
                case "UA": locale = UA;
                break;
                default : throw new IllegalArgumentException();
            }
//            switch (requestedLocale) {
//                case "EN" -> locale = EN;
//                case "UA" -> locale = UA;
//                default -> throw new IllegalArgumentException();
//            }
            session.setAttribute(LOCALE, locale);
        } else if (session.getAttribute(LOCALE) == null) {
            session.setAttribute(LOCALE, Locale.getDefault());
        }
        chain.doFilter(request, response);
    }

}