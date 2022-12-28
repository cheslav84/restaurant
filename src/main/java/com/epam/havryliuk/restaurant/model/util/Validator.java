package com.epam.havryliuk.restaurant.model.util;

import com.epam.havryliuk.restaurant.model.exceptions.BadCredentialsException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isAddressCorrect(String deliveryAddress) {
        if (deliveryAddress != null){
            int addressLength = deliveryAddress.length();
            if (addressLength > 13 && addressLength < 100) {
                return true;
            }
        }
    return false;
    }

    public static boolean isPhoneCorrect(String deliveryPhone) {
        if (deliveryPhone != null){
            String skippedSymbols = deliveryPhone.replaceAll("[\\s()-]", "");
            String patterns = "^(\\+[1-9]{1}[0-9]{11})|([0]{1}[0-9]{9})$";
            Pattern pattern = Pattern.compile(patterns);
            Matcher matcher = pattern.matcher(skippedSymbols);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }


    public static boolean isDishesAmountCorrect(int dishesAmount) {
        return dishesAmount > 0;
    }

    public static void validateEmail(String email) throws BadCredentialsException {
        //todo
        if (email == null) {
            String loginError = "Email null"; //todo add concrete cause
            throw new BadCredentialsException(loginError);
        }
    }

    public static void validatePassword(String password) throws BadCredentialsException {
        //todo
        if (password == null) {
            String passwordError = "Password null"; //todo add concrete cause
            throw new BadCredentialsException(passwordError);
        }
    }


    public static void checkIfPasswordsCoincide(String password, String encryptedPassword) throws GeneralSecurityException {
        if (!password.equals(encryptedPassword)) {
            String errorMessage = "Entered password is wrong.";
            throw new GeneralSecurityException(errorMessage);
        }
    }
}
