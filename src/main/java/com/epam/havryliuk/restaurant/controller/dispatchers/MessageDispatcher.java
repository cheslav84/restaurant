package com.epam.havryliuk.restaurant.controller.dispatchers;

import com.epam.havryliuk.restaurant.model.util.BundleManager;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.LOCALE;

public class MessageDispatcher {


    public static synchronized String getMessage(HttpServletRequest request, String message) {
        BundleManager bundleManager = getBundleManager(request);
        return bundleManager.getProperty(message);
    }

    public static synchronized void setToRequest(HttpServletRequest request, String attribute, String message) {
        BundleManager bundleManager = getBundleManager(request);
        request.setAttribute(attribute, bundleManager.getProperty(message));
    }

    public static synchronized void setToSession(HttpServletRequest request, String attribute, String message) {
        BundleManager bundleManager = getBundleManager(request);
        request.getSession().setAttribute(attribute, bundleManager.getProperty(message));
    }

    private static synchronized BundleManager getBundleManager(HttpServletRequest request) {
        return BundleManager.valueOf(((Locale) request.getSession().getAttribute(LOCALE)).getCountry());
    }
}
