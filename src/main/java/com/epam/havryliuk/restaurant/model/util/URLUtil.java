package com.epam.havryliuk.restaurant.model.util;

import jakarta.servlet.http.HttpServletRequest;

public class URLUtil {

    /**
     * Gets the referer URL from the header in request, and returns
     * the "command" String for redirecting a response.
     * @return String of 
     */
    public static String getRefererPage (HttpServletRequest req) {
        String referer = req.getHeader("Referer");
        int lastSlashIndex = referer.lastIndexOf('/');
        return referer.substring(lastSlashIndex + 1);
    }
}
