package com.epam.havryliuk.restaurant.controller.dispatchers;

import jakarta.servlet.http.HttpServletRequest;

public class URLDispatcher {

    /**
     * Gets the referer URL from the header in request, and returns
     * the "command" String for redirecting a response. Actually,
     * user will be redirected to the same page the request have been
     * done from.
     *
     * @return command that leads to the same user page.
     */
    public static String getRefererPage(HttpServletRequest req) {

        String referer = req.getHeader("Referer");
        int lastSlashIndex = referer.lastIndexOf('/');
        return referer.substring(lastSlashIndex + 1);
    }


}
