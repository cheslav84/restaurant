package com.epam.havryliuk.restaurant.controller.constants;

/**
 * A list of attributes that belongs to HttpServletRequest or HttpSession.
 */
public interface RequestAttributes {
    String CURRENT_ORDER = "currentOrder";
    String ORDER_LIST = "orders";
    String ORDER_PRICE_MAP = "ordersAndPrices";
    String DISH_LIST = "dishes";
    String MENU_SORTING_OPTION = "menuSortingOption";
    String SPECIALS_DISH_LIST = "dishesSpecials";
    //    String EMAIL = "email";
    String CURRENT_DISH = "currentDish";
    String MENU_CATEGORY = "menuCategory";
    String LOGGED_USER = "loggedUser";
    String USER_IN_LOGGING = "loggingUser";
    String DELIVERY_ADDRESS = "deliveryAddress";
    String DELIVERY_PHONE = "deliveryPhone";
    String SHOW_DISH_INFO = "showDishInfo";
    String ERROR_MESSAGE = "errorMessage";
    String ORDER_MESSAGE = "orderMessage";
    String LOCALE = "locale";
    String REGISTRATION_ERROR_MESSAGE = "registrationErrorMessage";
    String REGISTRATION_PROCESS = "registrationProcess";
    String MENU_MESSAGE = "menuMessage";
    String NUMBER_OF_PAGES = "noOfPages";
    String CURRENT_PAGE = "currentPage";
    String RECORDS_PER_PAGE = "recordsPerPage";
    String WRONG_ACTION = "wrongAction";
    String WRONG_DISH_FIELD_MESSAGE = "wrongDishFieldMessage";
    String COOKIE_LOCALE = "locale";
}
