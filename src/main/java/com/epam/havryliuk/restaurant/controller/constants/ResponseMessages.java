package com.epam.havryliuk.restaurant.controller.constants;

/**
 * A list of messages that should be displayed to user
 * in some cases during the running of application.
 */
public interface ResponseMessages {

    String LOGIN_ERROR = "message.loginError";
    String PASSWORD_ERROR = "message.passwordError";
    String REGISTRATION_ERROR = "message.registrationError";
    String REGISTRATION_USER_EXISTS = "message.registrationUserExists";
    String ORDERS_ERROR = "message.ordersError";
    String DISH_ALREADY_IN_ORDER = "message.dishInOrder";
    String USER_UNDER_EIGHTEEN_ORDER_ALCOHOL = "message.userUnderEighteenOrderAlcohol";
    String INCORRECT_NUMBER_OF_DISHES_ERROR = "message.incorrectNumberDishesInOrder";
    String NUMBER_OF_DISHES_IS_EMPTY_ERROR = "message.numberDishesInOrderIsEmpty";
    String INCORRECT_DELIVERY_ADDRESS = "message.incorrectDeliveryAddress";
    String INCORRECT_DELIVERY_PHONE = "message.incorrectDeliveryPhone";
    String UNAVAILABLE_DISHES_AMOUNT = "message.unavailableDishesAmount";
    String ORDER_DISH_NOT_FOUND = "message.orderDishNotFound";
    String USER_ORDERS_UNAVAILABLE = "message.userOrdersUnavailable";
    String ORDER_CONFIRM_ERROR = "message.orderConfirmError";
    String DISH_IN_MENU_NOT_FOUND = "message.dishInMenuNotFound";
    String REMOVE_DISH_FROM_ORDER_ERROR = "message.removeDishFromOrderError";
    String MENU_UNAVAILABLE = "message.menuUnavailable";
    String MENU_EMPTY = "message.menuEmpty";
//    String ABSENT_DISHES = "message.dishesAbsent";
    String WRONG_EMAIL_FIELD = "message.wrongEmailField";
    String WRONG_NAME_FIELD = "message.wrongNameField";
    String WRONG_SURNAME_FIELD = "message.wrongSurnameField";
    String WRONG_PASSWORD_FIELD = "message.wrongPasswordField";
    String PASSWORDS_NOT_COINCIDE = "message.passwordsNotCoincide";
    String WRONG_GENDER_FIELD = "message.wrongGenderField";
    String EMPTY_BASKET = "message.emptyBasket";
    String UNAPPROPRIATED_RIGHTS_TO_CHANGE_STATUS = "message.unappropriatedRightsToChangeStatus";
    String WRONG_DISH_NAME_FIELD = "message.wrongDishName";
    String WRONG_DISH_DESCRIPTION_FIELD = "message.wrongDishDescription";
    String WRONG_DISH_WEIGHT_FIELD = "message.wrongDishWeight";
    String WRONG_DISH_PRICE_FIELD = "message.wrongDishPrice";
    String WRONG_DISH_CATEGORY_FIELD = "message.wrongDishCategory";
    String WRONG_DISH_AMOUNT_FIELD = "message.wrongDishAmount";
    String SUCH_DISH_NAME_EXISTS = "message.suchDishNameExists";
    String SUCH_IMAGE_EXISTS = "message.suchImageExists";
    String IMAGE_DOES_NOT_SET = "message.imageDoesNotSet";
    String GLOBAL_ERROR = "message.somethingWentWrong";

}
