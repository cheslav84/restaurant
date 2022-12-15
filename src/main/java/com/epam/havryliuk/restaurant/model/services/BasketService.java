package com.epam.havryliuk.restaurant.model.services;

import com.epam.havryliuk.restaurant.model.database.dao.BasketDao;
import com.epam.havryliuk.restaurant.model.database.dao.DaoImpl.BasketDaoImpl;
import com.epam.havryliuk.restaurant.model.entity.Order;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasketService {
    private static final Logger log = LogManager.getLogger(BasketService.class);



    public void addDishesToUserBasket(User user, Dish dish, int amount){

        BasketDao basketDao = new BasketDaoImpl();

        Order order = new Order();






//        basketDao.create()



    }



//    public void addDishToBasket(HttpServletRequest req) {
//
//
//        int dishesAmount  = Integer.parseInt(req.getParameter("amount").trim());
//        log.debug("Request for \"" + dishesAmount + "\" has been received.");
//
//        String continueOrder = req.getParameter("continue");
//
//        Map<Dish, Integer> basket = (Map<Dish, Integer>) req.getSession().getAttribute("basket");//todo можна зберігати в сесії, чи кожного разу записуємо в базу?
//        HttpSession session = req.getSession();
//        if(basket == null) {
//            basket = new HashMap<>();
//            session.setAttribute("basket", basket);
//        }
//
//        Dish currentDish = (Dish) session.getAttribute("currentDish");
//        session.removeAttribute("currentDish");
//        basket.put(currentDish, dishesAmount);
//
//        String redirectionPage;
//        if (continueOrder == null) {
//            redirectionPage = "basket";
//        } else {
//            redirectionPage = "menu";
//        }
//
//
//
//    }
}
