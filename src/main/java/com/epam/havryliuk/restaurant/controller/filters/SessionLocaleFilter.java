package com.epam.havryliuk.restaurant.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.LANGUAGE;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.LOCALE;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = { "/*" },
        initParams = {
                @WebInitParam(name = "locale", value = "UA", description = "Default locale") })
public class SessionLocaleFilter implements Filter {
    private static final Logger log = LogManager.getLogger(SessionLocaleFilter.class);
    private String defaultLocale;

    @Override
    public void init(FilterConfig config) throws ServletException {
        defaultLocale = config.getInitParameter("locale");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("\"/SessionLocaleFilter\" doFilter starts.");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

//        Locale currentLocale = httpRequest.getLocale();
//        System.out.println(currentLocale.getDisplayLanguage()); //English
//        System.out.println(currentLocale.getDisplayCountry());	//United States
//        System.out.println(currentLocale.getLanguage());		//en
//        System.out.println(currentLocale.getCountry());

        if (httpRequest.getParameter(LOCALE) != null) {
            session.setAttribute(LANGUAGE, httpRequest.getParameter(LOCALE));
        } else if (session.getAttribute(LANGUAGE) == null) {
            session.setAttribute(LANGUAGE, getDefaultLocale(httpRequest));
        }
        chain.doFilter(request, response);
    }

    private String getDefaultLocale(HttpServletRequest httpRequest) {
        String initialLocale = httpRequest.getLocale().getCountry();
        if (!initialLocale.equals("UA") || !initialLocale.equals("EN")) {
            initialLocale = defaultLocale;
        }
        return initialLocale;
    }
}