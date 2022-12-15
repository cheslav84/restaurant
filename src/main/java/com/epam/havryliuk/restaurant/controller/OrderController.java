package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.NoSuchEntityException;
import com.epam.havryliuk.restaurant.model.services.OrderService;
import com.epam.havryliuk.restaurant.model.utils.URLUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Enumeration;

import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;


@WebServlet("/addToOrder")
public class OrderController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(OrderController.class);// todo add logs for class

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/BasketController\" request doGet.");
//
//
//        req.getRequestDispatcher("view/jsp/user/index.jsp").forward(req, resp);
//    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        addDishToBasket(req);
        //todo check if basket in session
        //todo check if it exist in database (this user, status booking)
        //todo (create basket if new order, add it to session)
        //todo add dish to basket
        HttpSession session = req.getSession();

        OrderService orderService = new OrderService();
        Order order = (Order) session.getAttribute(ORDER);
        if (order == null) {
            try {
                order = orderService.getOrder(req);
                log.debug(order);
                session.removeAttribute(ERROR_MESSAGE);
            } catch (NoSuchEntityException | BadCredentialsException e) {
                String errorMessage = e.getMessage();
                session.setAttribute(SHOW_DISH_INFO, SHOW_DISH_INFO);
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                log.error(errorMessage, e);
            }
        }


        String redirectionPage = URLUtil.getRefererPage(req);


        int dishesAmount = Integer.parseInt(req.getParameter("amount").trim());//todo try-catch
        log.debug("Request for \"" + dishesAmount + "\" has been received.");


        Dish currentDish = (Dish) session.getAttribute(CURRENT_DISH);//todo може теоретично бути null якщо ми його точно ложили в сесію?... подумати
        session.removeAttribute(CURRENT_DISH);

        User currentUser = (User) session.getAttribute(LOGGED_USER);
        orderService.addDishesToUserBasket(currentUser, currentDish, dishesAmount);


        String continueOrder = req.getParameter("continue");
        if (continueOrder == null) {
            redirectionPage = "basket";
        }


        resp.sendRedirect(redirectionPage);
    }


//    private void addDishToBasket(HttpServletRequest req) {
//
////        Map<Dish, Integer> basket = (Map<Dish, Integer>) req.getSession().getAttribute("basket");//todo можна зберігати в сесії, чи кожного разу записуємо в базу?
////        if(basket == null) {
////            basket = new HashMap<>();
////            session.setAttribute("basket", basket);
////        }
//        basket.put(currentDish, dishesAmount);
//
//    }
//}


}


//    System.out.println("ContextPath(): " + req.getContextPath());
//        System.out.println("HttpServletMapping(): " +req.getHttpServletMapping());
//        System.out.println("getAuthType(): " +req.getAuthType());
//        System.out.println("req.getPathInfo(): " +req.getPathInfo());
//        System.out.println("req.getPathTranslated(): " +req.getPathTranslated());
//        System.out.println("req.getQueryString(): " +req.getQueryString());
//        System.out.println("req.getRemoteUser(): " +req.getRemoteUser());
//        System.out.println("req.getRequestURI(): " +req.getRequestURI());
//        System.out.println("req.getServletPath(): " +req.getServletPath());
//        System.out.println("req.getLocalAddr(): " +req.getLocalAddr());
//        System.out.println("req.getRemoteAddr(): " +req.getRemoteAddr());
//        System.out.println("req.getRemoteHost(): " +req.getRemoteHost());
//        System.out.println("req.getLocale(): " +req.getLocale());
//        System.out.println("req.getHeaderNames(): " +req.getHeaderNames());
//        System.out.println("req.getHeaderNames(): " +req.getHeader("Referer"));



