package com.epam.havryliuk.restaurant.model.constants;

public interface RequestAttributes {

   String CURRENT_ORDER = "currentOrder";
    String ORDER_LIST = "orders";
    String DISH_LIST = "dishes";
    String CURRENT_DISH = "currentDish";
    String MENU_CATEGORY = "menuCategory";
    String LOGGED_USER = "loggedUser";
    String DELIVERY_ADDRESS = "deliveryAddress";
    String DELIVERY_PHONE = "deliveryPhone";
    String PAGE_FROM_BEING_REDIRECTED = "pageFromBeingRedirected";//todo change from URLUtil class
    String SHOW_DISH_INFO = "showDishInfo";
    String ERROR_MESSAGE = "errorMessage";
    String ORDER_MESSAGE = "orderMessage";
    String LANGUAGE = "language";
    String LOCALE = "locale";
    String REGISTRATION_ERROR_MESSAGE = "registrationErrorMessage";//todo try to make one
    String REGISTRATION_PROCESS = "registrationProcess";


}
