package com.epam.havryliuk.restaurant.controller;


import com.epam.havryliuk.restaurant.controller.command.ActionCommand;
import com.epam.havryliuk.restaurant.controller.command.CommandFactory;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;


@WebServlet(name = "Controller", urlPatterns = {"/page/*", "/auth/*",  "/login", "/register", "/logout",
        "/show_order_info", "/menu/*", "/locale/*", "/client/*", "/admin/*", "/index", "/make_order",
        "/basket", "/remove_from_order", "/set_next_status/*", "/manage_orders", "/login_page", "/add_dish_page"})
//@WebServlet("/")
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        CommandFactory client = new CommandFactory();
        ActionCommand command = client.defineCommand(request);
        try {
            command.execute(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Unable to execute request.");
            response.sendRedirect(request.getContextPath() + AppPagesPath.REDIRECT_ERROR);
        }
    }
}