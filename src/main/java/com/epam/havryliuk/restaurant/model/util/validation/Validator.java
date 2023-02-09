package com.epam.havryliuk.restaurant.model.util.validation;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;

public class Validator {

    /**
     * Method validates the data entered by user during registration. If some data is incorrect
     * ValidationException will be thrown, and correspondent message set to HttpSession
     * (in case the checkbox or password data is invalid), or message can be set directly to user
     * String fields for displaying that massage on proper place, for example in HTML input fields.
     * @param user User that data of which has to be validated.
     * @throws ValidationException in case some data is invalid.
     */
    public static boolean validateUserData(User user, HttpServletRequest req) throws ValidationException {
        HttpSession session = req.getSession();
        BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
        boolean correctFields = true;
        if(!user.getGender().equalsIgnoreCase(RequestParameters.MALE) &&
                !user.getGender().equalsIgnoreCase(RequestParameters.FEMALE)){
            String message = bundleManager.getProperty(ResponseMessages.WRONG_GENDER_FIELD);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE, message);
            correctFields = false;
        }
        if(!regexChecker(user.getName(), Regex.NAME)){
            String message = bundleManager.getProperty(ResponseMessages.WRONG_NAME_FIELD);
            user.setName(message);
            correctFields = false;
        }
        if(!regexChecker(user.getSurname(), Regex.NAME)){
            String message = bundleManager.getProperty(ResponseMessages.WRONG_SURNAME_FIELD);
            user.setSurname(message);
            correctFields = false;
        }
        if(!regexChecker(user.getEmail(), Regex.EMAIL)){
            String message = bundleManager.getProperty(ResponseMessages.WRONG_EMAIL_FIELD);
            user.setEmail(message);
            correctFields = false;
        }
        if(!regexChecker(user.getPassword(), Regex.PASSWORD)){
            String message = bundleManager.getProperty(ResponseMessages.WRONG_PASSWORD_FIELD);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE, message);
            correctFields = false;
        }
        String passwordConfirmation = req.getParameter(RequestParameters.PASSWORD_CONFIRMATION);
        if (!user.getPassword().equals(passwordConfirmation)) {
            String message = bundleManager.getProperty(ResponseMessages.PASSWORDS_NOT_COINCIDE);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE, message);
            correctFields = false;
        }

        if (!correctFields) {
            throw new ValidationException();
        }
        return true;
    }



    /**
     * Validates delivery address and phone when User makes an order.
     * @param deliveryAddress address that User wants the order has to be delivered to.
     * @param deliveryPhone phone number that Manager can call to Client.
     * @return true if data is correct.
     * @throws ValidationException in case some data is invalid.
     */
    public static boolean validateDeliveryData(String deliveryAddress, String deliveryPhone, HttpServletRequest req)
            throws ValidationException {
        HttpSession session = req.getSession();
        BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
        boolean correctFields = true;
        if(!regexChecker(deliveryAddress, Regex.ADDRESS)){
            session.setAttribute(ORDER_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_ADDRESS));
            correctFields = false;
        }
        session.setAttribute(DELIVERY_ADDRESS, deliveryAddress);
        deliveryPhone = deliveryPhone.replaceAll("[\\s()-]", "");
        if(!regexChecker(deliveryPhone, Regex.PHONE)) {
            session.setAttribute(ORDER_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_PHONE));
            correctFields = false;
        }
        session.setAttribute(DELIVERY_PHONE, deliveryPhone);
        if (!correctFields){
            throw new ValidationException("The delivery data is incorrect.");
        }
        return true;
    }


    /**
     *Validates dishes amount that User wants to order.
     * @param dishesAmount that needs to be validated
     * @param req HttpServletRequest from User. From Request method get Session and set Error message
     *            when dishes amount is 0 or less.
     * @throws ValidationException when dishes amount is 0 or less.
     */

    public static void validateDishesAmount(int dishesAmount, HttpServletRequest req) throws ValidationException {
        if ((dishesAmount <= 0)){
            HttpSession session = req.getSession();
            BundleManager bundleManager = BundleManager.valueOf(((Locale) session.getAttribute(LOCALE)).getCountry());
            session.setAttribute(ERROR_MESSAGE,
                    bundleManager.getProperty(ResponseMessages.INCORRECT_NUMBER_OF_DISHES_ERROR));
            throw new ValidationException("The number of dishes is incorrect.");        }
    }


    public static boolean isDishCreatingDataValid(Dish dish, String path,
                                                  HttpSession session, BundleManager bundleManager) {
        StringBuilder builder = new StringBuilder();
        boolean correctFields = validateDish(dish, session, bundleManager, builder);
        if(dish.getImage().isEmpty()){
            builder.append(bundleManager.getProperty(ResponseMessages.IMAGE_DOES_NOT_SET));
            correctFields = false;
        }
        if(isSuchImageExist(path)){
            builder.append(bundleManager.getProperty(ResponseMessages.SUCH_IMAGE_EXISTS));
            correctFields = false;
        }
        setMessageIfDataNotValid(session, builder, correctFields);
        return correctFields;
    }

    public static boolean isDishEditingDataValid(Dish dish, HttpSession session, BundleManager bundleManager) {
        StringBuilder builder = new StringBuilder();
        boolean correctFields = validateDish(dish, session, bundleManager, builder);
        if(dish.getAmount() < 0 || dish.getAmount() > Regex.MAX_AMOUNT){
            builder.append(bundleManager.getProperty(ResponseMessages.WRONG_DISH_AMOUNT_FIELD));
            correctFields = false;
        }
        setMessageIfDataNotValid(session, builder, correctFields);
        return correctFields;
    }

    private static boolean validateDish(Dish dish, HttpSession session,
                                        BundleManager bundleManager, StringBuilder builder){
        session.removeAttribute(WRONG_DISH_FIELD_MESSAGE);
        boolean correctFields = true;
        if(!regexChecker(dish.getName(), Regex.DISH_NAME)){
            String message = bundleManager.getProperty(ResponseMessages.WRONG_DISH_NAME_FIELD);
            dish.setName(message);
            correctFields = false;
        }
        if(!regexChecker(dish.getDescription(), Regex.DISH_DESCRIPTION)){
            String message = bundleManager.getProperty(ResponseMessages.WRONG_DISH_DESCRIPTION_FIELD);
            dish.setDescription(message);
            correctFields = false;
        }
        if(dish.getWeight() < Regex.MIN_WEIGHT || dish.getWeight() > Regex.MAX_WEIGHT){
            builder.append(bundleManager.getProperty(ResponseMessages.WRONG_DISH_WEIGHT_FIELD));
            correctFields = false;
        }
        if(dish.getPrice().compareTo(Regex.MIN_PRICE) < 0 || dish.getPrice().compareTo(Regex.MAX_PRICE) > 0){
            builder.append(bundleManager.getProperty(ResponseMessages.WRONG_DISH_PRICE_FIELD));
            correctFields = false;
        }
        if(dish.getCategory() == null){
            builder.append(bundleManager.getProperty(ResponseMessages.WRONG_DISH_CATEGORY_FIELD));
            correctFields = false;
        }
        return correctFields;
    }

    private static void setMessageIfDataNotValid(HttpSession session, StringBuilder builder, boolean correctFields) {
        if (!correctFields) {
            session.setAttribute(WRONG_DISH_FIELD_MESSAGE, builder.toString());
        }
    }


    private static boolean isSuchImageExist(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean regexChecker (String toCheck, String regex) {
        Pattern regexPattern = Pattern.compile(regex);
        Matcher regexMatcher = regexPattern.matcher(toCheck);
        return regexMatcher.matches();
    }


}
