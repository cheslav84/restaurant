package com.epam.havryliuk.restaurant.model.utils;

import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
