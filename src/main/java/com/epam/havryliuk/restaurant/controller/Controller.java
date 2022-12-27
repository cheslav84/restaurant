package com.epam.havryliuk.restaurant.controller;


import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.controller.command.CommandFactory;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;


@WebServlet(name = "Controller", urlPatterns = {"/page/*", "/auth/*",  "/login", "/register", "/logout", "/showOrderInfo", "/menu/*", "/locale/*",
                            "/client/*", "/admin/*", "/index", "/makeOrder", "/basket", "/removeFromOrder", "/confirmOrder"})
//@WebServlet("/")
public class Controller extends HttpServlet {
    private static final Logger log = LogManager.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException {
        CommandFactory client = new CommandFactory();
        ActionCommand command = client.defineCommand(request);
        try {
            command.execute(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("error");
//            response.sendRedirect(request.getContextPath() + AppPagesPath.ERROR);//todo
        }

//        String page = null;
//
//        if (page != null) {
//            response.sendRedirect(page);
//        } else {
//            page = ConfigurationManager.getProperty("path.page.index");
//            request.getSession().setAttribute("nullPage",
//                    MessageManager.EN.getProperty("message.nullPage"));//todo
//            response.sendRedirect(request.getContextPath() + page);//todo
//        }

    }
}