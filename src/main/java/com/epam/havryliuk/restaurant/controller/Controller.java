package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.controller.command.CommandFactory;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;

@MultipartConfig
@WebServlet(name = "Controller", urlPatterns = {"/login_page", "/login", "/register", "/logout",
        "/index", "/menu/*", "/show_dish_info", "/make_order", "/basket", "/remove_from_order",
       "/set_next_status/*", "/manage_orders", "/add_dish_page", "/upload_picture", "/add_dish",
        "/edit_dish"})
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOG.trace("Controller, doGet.");
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOG.trace("Controller, doPost.");
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        CommandFactory client = new CommandFactory();// todo подивитись чи створюється нова фарбрика на користрувача
        Command command = client.defineCommand(request);// todo подивитись чи створюється нова команда на користрувача
        // todo повернути ENUM
        System.err.println(client);
        System.err.println(command);

        try {
            command.execute(request, response);
        } catch (Exception e) {// todo set in web.xml?
            e.printStackTrace();
            LOG.error("Unable to execute request.");
            response.sendRedirect(request.getContextPath() + AppPagesPath.REDIRECT_ERROR);
        }
    }
}