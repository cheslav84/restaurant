//package com.epam.havryliuk.restaurant.controller.dishServlets;
//
//import com.epam.havryliuk.restaurant.model.entity.Dish;
//import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
//import com.epam.havryliuk.restaurant.model.service.DishService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//import java.util.List;
//
//import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
//
//@WebServlet("/")
//public class MainPageController extends HttpServlet {
//    private static final Logger log = LogManager.getLogger(MainPageController.class);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/index\" request doGet MenuController");
//
//        String currentMenu = getCurrentMenu(req);//todo привести у відповідність - перенести в сервіс. якщо передаємо реквест у сервіс робити це всюди однаково
//
//        DishService dishService = new DishService();
//        List<Dish> dishes = null;
//        try {
//            dishes = dishService.getMenuByCategory(currentMenu);
//            log.debug("List of dishes received by servlet and going to be sending to client side.");
//        } catch (ServiceException e) {
//            log.error("List of dishes hasn't been received.");
////            req.setAttribute(ERROR_MESSAGE, "Message");//todo inform user!!!
//        }
//
//        dishService.hideOrderInfoOnReloadPage(req);
//
//        req.setAttribute(DISH_LIST, dishes);
//        req.getRequestDispatcher("view/jsp/index.jsp").forward(req, resp);
//    }
//
//
//    @NotNull
//    private String getCurrentMenu(HttpServletRequest req) {
//        HttpSession session = req.getSession();
//        String lastVisitedMenu = (String) session.getAttribute(MENU_CATEGORY);
//        String currentMenu = req.getParameter(MENU_CATEGORY);
//        if (currentMenu != null) {
//            session.setAttribute(MENU_CATEGORY, currentMenu);
//        } else {
//            if (lastVisitedMenu != null) {
//                currentMenu = lastVisitedMenu;
//            } else {
//                currentMenu = "COFFEE";
//            }
//        }
//        return currentMenu;
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
