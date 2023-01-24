package com.epam.havryliuk.restaurant.model.service.validation;

import com.epam.havryliuk.restaurant.model.constants.Regex;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.ValidationException;
import com.epam.havryliuk.restaurant.model.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.DELIVERY_PHONE;

public class Validator {

    public void validateUserData(User user, HttpServletRequest req) throws ValidationException {
        HttpSession session = req.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
        boolean correctFields = true;
        if(!user.getGender().equalsIgnoreCase(RequestParameters.MALE) &&
                !user.getGender().equalsIgnoreCase(RequestParameters.FEMALE)){
            String message = messageManager.getProperty(ResponseMessages.WRONG_GENDER_FIELD);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE, message);
            correctFields = false;
        }
        if(!regexChecker(user.getName(), Regex.NAME)){
            String message = messageManager.getProperty(ResponseMessages.WRONG_NAME_FIELD);
            user.setName(message);
            correctFields = false;
        }
        if(!regexChecker(user.getSurname(), Regex.NAME)){
            String message = messageManager.getProperty(ResponseMessages.WRONG_SURNAME_FIELD);
            user.setSurname(message);
            correctFields = false;
        }
        if(!regexChecker(user.getEmail(), Regex.EMAIL)){
            String message = messageManager.getProperty(ResponseMessages.WRONG_EMAIL_FIELD);
            user.setEmail(message);
            correctFields = false;
        }
        if(!regexChecker(user.getPassword(), Regex.PASSWORD)){
            String message = messageManager.getProperty(ResponseMessages.WRONG_PASSWORD_FIELD);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE, message);
            correctFields = false;
        }
        String passwordConfirmation = req.getParameter(RequestParameters.PASSWORD_CONFIRMATION);
        if (!user.getPassword().equals(passwordConfirmation)) {
            String message = messageManager.getProperty(ResponseMessages.PASSWORDS_NOT_COINCIDE);
            session.setAttribute(REGISTRATION_ERROR_MESSAGE, message);
            correctFields = false;
        }
        if (!correctFields) {
            throw new ValidationException();
        }
    }

    private boolean regexChecker (String toCheck, String regex) {
        Pattern regexPattern = Pattern.compile(regex);
        Matcher regexMatcher = regexPattern.matcher(toCheck);
        return regexMatcher.matches();
    }

    public void validateDeliveryData(String deliveryAddress, String deliveryPhone, HttpServletRequest req)
            throws ValidationException {
        HttpSession session = req.getSession();
        MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
        boolean correctFields = true;
        if(!regexChecker(deliveryAddress, Regex.ADDRESS)){
            session.setAttribute(ORDER_MESSAGE,
                    messageManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_ADDRESS));
            correctFields = false;
        }
        session.setAttribute(DELIVERY_ADDRESS, deliveryAddress);
        deliveryPhone = deliveryPhone.replaceAll("[\\s()-]", "");
        if(!regexChecker(deliveryPhone, Regex.PHONE)) {
            session.setAttribute(ORDER_MESSAGE,
                    messageManager.getProperty(ResponseMessages.INCORRECT_DELIVERY_PHONE));
            correctFields = false;
        }
        session.setAttribute(DELIVERY_PHONE, deliveryPhone);
        if (!correctFields){
            throw new ValidationException("The delivery data is incorrect.");
        }
    }

    public void validateDishesAmount(int dishesAmount, HttpServletRequest req) throws ValidationException {
        if ((dishesAmount <= 0)){
            HttpSession session = req.getSession();
            MessageManager messageManager = MessageManager.valueOf((String) session.getAttribute(LOCALE));
            session.setAttribute(ERROR_MESSAGE,
                    messageManager.getProperty(ResponseMessages.INCORRECT_NUMBER_OF_DISHES_ERROR));
            throw new ValidationException("The number of dishes is incorrect.");        }
    }

}
