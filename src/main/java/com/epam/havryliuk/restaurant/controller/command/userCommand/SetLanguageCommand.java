//package com.epam.havryliuk.restaurant.controller.command.userCommand;
//
//import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
//import com.epam.havryliuk.restaurant.model.constants.RequestAttributes;
//import com.epam.havryliuk.restaurant.model.util.URLUtil;
//import com.mysql.cj.Session;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.IOException;
//import java.util.Optional;
//
//public class SetLanguageCommand implements ActionCommand {
//    private static final Logger log = LogManager.getLogger(SetLanguageCommand.class);
//
//    @Override
//    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String language = request.getParameter("language");
//
//        if (request.getParameter(language) != null) {
//            Cookie cookie = new Cookie(RequestAttributes.LANGUAGE, request.getParameter(language));
//            response.addCookie(cookie);
//        } else {
//
//        }
////        HttpSession session = request.getSession();
////        session.setAttribute(RequestAttributes.LANGUAGE, language);
//        String referer = URLUtil.getRefererPage(request);
//        response.sendRedirect(referer);
////        Optional<String> requestedPath = Optional.of(request.getParameter("path"));
////        String path = "/WEB-INF/jsp/" + requestedPath.orElse("index") + ".jsp";//todo move to constants
////        log.debug("Going move to: " + path);
////        request.getRequestDispatcher(path).forward(request, response);
//    }
//
//}