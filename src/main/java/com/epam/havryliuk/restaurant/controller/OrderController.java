package com.epam.havryliuk.restaurant.controller;

import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.services.BasketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import static com.epam.havryliuk.restaurant.controller.RequestAttributes.*;


@WebServlet("/addToOrder")
public class OrderController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(OrderController.class);// todo add logs for class

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("\"/BasketController\" request doGet.");
//
//
//        req.getRequestDispatcher("view/jsp/user/basket.jsp").forward(req, resp);
//    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


//        addDishToBasket(req);



        //todo check if basket in session
        //todo check if it exist in database (this user, status booking)
        //todo (create basket if new order, add it to session)
        //todo add dish to basket
        HttpSession session = req.getSession();

        Order order = (Order) session.getAttribute(BASKET);
            if (order == null) {
            order = createBasket(req);
        }





        int dishesAmount = Integer.parseInt(req.getParameter("amount").trim());//todo try-catch
        log.debug("Request for \"" + dishesAmount + "\" has been received.");


        Dish currentDish = (Dish) session.getAttribute(CURRENT_DISH);//todo може теоретично бути ексепшин якщо ми його точно ложили в сесію
        session.removeAttribute(CURRENT_DISH);

        User currentUser = (User) session.getAttribute(LOGGED_USER);
        BasketService basketService = new BasketService();
        basketService.addDishesToUserBasket(currentUser, currentDish, dishesAmount);


        String continueOrder = req.getParameter("continue");
        String redirectionPage;
        if (continueOrder == null) {
            redirectionPage = "basket";
        } else {
            redirectionPage = "menu";
        }

        resp.sendRedirect(redirectionPage);
    }

    private Order createBasket(HttpServletRequest req) {
        String deliveryAddress = req.getParameter("deliveryAddress");
        String deliveryPhone = req.getParameter("deliveryPhone");


        Order order = new Order();
        return order;
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











