package com.epam.havryliuk.restaurant.model.utils;

import jakarta.servlet.http.HttpServletRequest;

public class URLUtil {

    public static String getRefererPage (HttpServletRequest req) {
        String referer = req.getHeader("Referer");
        int lastSlashIndex = referer.lastIndexOf('/');
        return referer.substring(lastSlashIndex + 1);
    }
}
