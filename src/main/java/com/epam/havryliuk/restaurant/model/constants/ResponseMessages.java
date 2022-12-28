package com.epam.havryliuk.restaurant.model.constants;

public interface ResponseMessages {

    String LOGIN_ERROR = "message.loginError";
    String PASSWORD_ERROR = "message.passwordError";
    String REGISTRATION_ERROR = "message.registrationError";
    String REGISTRATION_USER_EXISTS = "message.registrationUserExists";
    String ORDERS_ERROR = "message.ordersError";
    String DISH_ALREADY_IN_ORDER = "message.dishInOrder";
    String INCORRECT_NUMBER_OF_DISHES_ERROR = "message.incorrectNumberDishesInOrder";
    String NUMBER_OF_DISHES_IS_EMPTY_ERROR = "message.numberDishesInOrderIsEmpty";
    String INCORRECT_DELIVERY_ADDRESS = "message.incorrectDeliveryAddress";
    String INCORRECT_DELIVERY_PHONE = "message.incorrectDeliveryPhone";
    String UNAVAILABLE_DISHES_AMOUNT = "message.unavailableDishesAmount";
}
