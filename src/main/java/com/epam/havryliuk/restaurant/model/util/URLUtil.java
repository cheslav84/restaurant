package com.epam.havryliuk.restaurant.model.util;

import jakarta.servlet.http.HttpServletRequest;

public class URLUtil {

    /**
     * Gets the referer URL from the header in request, and returns
     * the String "page" for redirecting a response to the same page.
     * @param req
     * @return
     */
    public static String getRefererPage (HttpServletRequest req) {
        String referer = req.getHeader("Referer");
        int lastSlashIndex = referer.lastIndexOf('/');
        return referer.substring(lastSlashIndex + 1);
    }

}
