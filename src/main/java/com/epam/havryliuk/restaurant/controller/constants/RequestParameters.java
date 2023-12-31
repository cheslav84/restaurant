package com.epam.havryliuk.restaurant.controller.constants;

/**
 * A list of parameters that can be got from HttpServletRequest,
 * and we receive from the view layer.
 */
public interface RequestParameters {

    String EMAIL = "email";
    String NAME = "name";
    String SURNAME = "surname";
    String GENDER = "userGender";
    String OVER_EIGHTEEN_AGE = "userOverEighteenAge";
    String MALE = "Male";
    String FEMALE = "Female";
    String PASSWORD = "password";
    String PASSWORD_CONFIRMATION = "passwordConfirmation";
    String DELIVERY_ADDRESS = "deliveryAddress";
    String DELIVERY_PHONE = "deliveryPhone";
    String ORDER_DISHES_AMOUNT = "amount";
    String ORDER_ID = "orderId";
    String CURRENT_STATUS = "currentStatus";
    String MENU_CATEGORY = "menuCategory";
    String MENU_SORTING_OPTION = "menuSortingOption";
    String PAGE_NUMBER = "page";
    String ORDER_SORTING_PARAMETER = "sorted";
    String RECORDS_PER_PAGE = "recordsPerPage";
    String CONTINUE_ORDERING = "continue";
    String DISH_ID = "dishId";
    String DISH_IMAGE = "dishImage";
    String DISH_NAME = "dishName";
    String DISH_DESCRIPTION = "dishDescription";
    String DISH_PRICE = "dishPrice";
    String DISH_WEIGHT = "dishWeight";
    String DISH_AMOUNT = "dishAmount";
    String DISH_CATEGORY = "dishCategory";
    String DISH_SPECIAL = "specialDish";
    String DISH_ALCOHOL = "alcoholDish";
}
