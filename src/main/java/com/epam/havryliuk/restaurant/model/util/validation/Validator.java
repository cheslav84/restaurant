package com.epam.havryliuk.restaurant.model.util.validation;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.controller.dispatchers.ImageDispatcher;
import com.epam.havryliuk.restaurant.controller.dispatchers.MessageDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

public class Validator {

    /**
     * Method validates the data entered by user during registration. If some data is incorrect
     * ValidationException will be thrown, and correspondent message set to HttpSession
     * (in case the checkbox or password data is invalid), or message can be set directly to user
     * String fields for displaying that massage on proper place, for example in HTML input fields.
     * @param user User that data of which has to be validated.
//     * @throws ValidationException in case some data is invalid.
     */
    public static boolean validateUserData(User user, HttpServletRequest request) {//todo забрати
        boolean correctFields = true;
        if(!user.getGender().equalsIgnoreCase(RequestParameters.MALE) &&
                !user.getGender().equalsIgnoreCase(RequestParameters.FEMALE)){
            MessageDispatcher.setToSession(request, REGISTRATION_ERROR_MESSAGE, ResponseMessages.WRONG_GENDER_FIELD);
            correctFields = false;
        }
        if(!regexChecker(user.getName(), Regex.NAME)){
            user.setName(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_NAME_FIELD));
            correctFields = false;
        }
        if(!regexChecker(user.getSurname(), Regex.NAME)){
            user.setSurname(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_SURNAME_FIELD));
            correctFields = false;
        }
        if(!regexChecker(user.getEmail(), Regex.EMAIL)){
            user.setEmail(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_EMAIL_FIELD));
            correctFields = false;
        }
        if(!regexChecker(user.getPassword(), Regex.PASSWORD)){
            MessageDispatcher.setToSession(request, REGISTRATION_ERROR_MESSAGE, ResponseMessages.WRONG_PASSWORD_FIELD);
            correctFields = false;
        }
        String passwordConfirmation = request.getParameter(RequestParameters.PASSWORD_CONFIRMATION);
        if (!user.getPassword().equals(passwordConfirmation)) {
            MessageDispatcher.setToSession(request, REGISTRATION_ERROR_MESSAGE, ResponseMessages.PASSWORDS_NOT_COINCIDE);
            correctFields = false;
        }
        return correctFields;
    }



    /**
     * Validates delivery address and phone when User makes an order.
     * @return true if data is correct.
     */
    public static boolean validateDeliveryData(int dishesAmount, HttpServletRequest request) {
        String deliveryAddress = request.getParameter(RequestParameters.DELIVERY_ADDRESS);
        String deliveryPhone = request.getParameter(RequestParameters.DELIVERY_PHONE);
        HttpSession session = request.getSession();
        boolean correctFields = Validator.validateDishesAmount(dishesAmount, request);
        if(!regexChecker(deliveryAddress, Regex.ADDRESS)){
            MessageDispatcher.setToSession(request, ORDER_MESSAGE, ResponseMessages.INCORRECT_DELIVERY_ADDRESS);
            correctFields = false;
        }
        session.setAttribute(DELIVERY_ADDRESS, deliveryAddress);
        deliveryPhone = deliveryPhone.replaceAll("[\\s()-]", "");
        if(!regexChecker(deliveryPhone, Regex.PHONE)) {
            MessageDispatcher.setToSession(request, ORDER_MESSAGE, ResponseMessages.INCORRECT_DELIVERY_PHONE);
            correctFields = false;
        }
        session.setAttribute(DELIVERY_PHONE, deliveryPhone);//todo move from that place
        return correctFields;
    }


    /**
     *Validates dishes amount that User wants to order.
     * @param dishesAmount that needs to be validated
     * @param request HttpServletRequest from User. From Request method get Session and set Error message
     *            when dishes amount is 0 or less.
     */
    public static boolean validateDishesAmount(int dishesAmount, HttpServletRequest request) {
        if ((dishesAmount > 0 && dishesAmount <= Regex.MAX_AMOUNT)){
            return true;
        } else {
            MessageDispatcher.setToSession(request, ERROR_MESSAGE, ResponseMessages.INCORRECT_NUMBER_OF_DISHES_ERROR);
            return false;
        }
    }

    public static boolean isCreatingDishDataValid(Dish dish, ImageDispatcher imageDispatcher, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        boolean correctFields = validateDish(dish, request, builder);
        if(dish.getImage().isEmpty()){
            builder.append(MessageDispatcher.getMessage(request, ResponseMessages.IMAGE_DOES_NOT_SET));
            correctFields = false;
        } else if (isSuchImageExist(imageDispatcher.getRealPath())){
            builder.append(MessageDispatcher.getMessage(request, ResponseMessages.SUCH_IMAGE_EXISTS));
            correctFields = false;
        }
        setMessageIfDataNotValid(request, builder, correctFields);
        return correctFields;
    }

    public static boolean isEditingDishDataValid(Dish dish, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        boolean correctFields = validateDish(dish, request, builder);
        if(dish.getAmount() < 0 || dish.getAmount() > Regex.MAX_AMOUNT){
            builder.append(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_DISH_AMOUNT_FIELD));
            correctFields = false;
        }
        setMessageIfDataNotValid(request, builder, correctFields);
        return correctFields;
    }

    private static boolean validateDish(Dish dish, HttpServletRequest request, StringBuilder builder){
        request.getSession().removeAttribute(WRONG_DISH_FIELD_MESSAGE);
        boolean correctFields = true;
        if(!regexChecker(dish.getName(), Regex.DISH_NAME)){
            dish.setName(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_DISH_NAME_FIELD));
            correctFields = false;
        }
        if(!regexChecker(dish.getDescription(), Regex.DISH_DESCRIPTION)){
            dish.setDescription(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_DISH_DESCRIPTION_FIELD));
            correctFields = false;
        }
        if(dish.getWeight() < Regex.MIN_WEIGHT || dish.getWeight() > Regex.MAX_WEIGHT){
            builder.append(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_DISH_WEIGHT_FIELD));
            correctFields = false;
        }
        if(dish.getPrice().compareTo(Regex.MIN_PRICE) < 0 || dish.getPrice().compareTo(Regex.MAX_PRICE) > 0){
            builder.append(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_DISH_PRICE_FIELD));
            correctFields = false;
        }
        if(dish.getCategory() == null){
            builder.append(MessageDispatcher.getMessage(request, ResponseMessages.WRONG_DISH_CATEGORY_FIELD));
            correctFields = false;
        }
        return correctFields;
    }

    private static void setMessageIfDataNotValid(HttpServletRequest request,
                                                              StringBuilder builder, boolean correctFields) {
        if (!correctFields) {
            request.getSession().setAttribute(WRONG_DISH_FIELD_MESSAGE, builder.toString());
        }
    }

    private static boolean isSuchImageExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean regexChecker (String toCheck, String regex) {
        Pattern regexPattern = Pattern.compile(regex);
        Matcher regexMatcher = regexPattern.matcher(toCheck);
        return regexMatcher.matches();
    }


}
