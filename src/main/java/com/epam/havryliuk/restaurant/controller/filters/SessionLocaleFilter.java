package com.epam.havryliuk.restaurant.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;


import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.LOCALE;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = { "/*" },
        initParams = {
                @WebInitParam(name = "locale", value = "UA", description = "Default locale") })
public class SessionLocaleFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(SessionLocaleFilter.class);
    private String defaultLocale;
    private final String[] allowedLocals = {"UA", "EN"};

    @Override
    public void init(FilterConfig config) throws ServletException {
        defaultLocale = config.getInitParameter("locale");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.debug("\"/SessionLocaleFilter\" doFilter starts.");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        if (httpRequest.getParameter(LOCALE) != null) {
            String locale = httpRequest.getParameter(LOCALE);
            checkLocale(locale);
            session.setAttribute(LOCALE, httpRequest.getParameter(LOCALE));
        } else if (session.getAttribute(LOCALE) == null) {
            session.setAttribute(LOCALE, getDefaultLocale(httpRequest));
        }
        chain.doFilter(request, response);
    }

    private void checkLocale(String locale) {
        Arrays.stream(allowedLocals)
                .filter(l -> l.equals(locale))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private String getDefaultLocale(HttpServletRequest httpRequest) {
        String initialLocale = httpRequest.getLocale().getCountry();
        if (!initialLocale.equals("UA") || !initialLocale.equals("EN")) {
            initialLocale = defaultLocale;
        }
        return initialLocale;
    }
}