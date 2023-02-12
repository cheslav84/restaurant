package com.epam.havryliuk.restaurant.model.requestMapper;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public class UserRequestMapper {

    /**
     * Method maps User from data entered by User During registration process, and validates that data.
     * If some data is invalid, method gets ValidationException, removes User password and sets that User
     * to session under the flag "loggingUser", and throws ValidationException again to the method above.
     * The User that is set to Session while ValidationException occurs, can contain in his Entity fields
     * messages of incorrect data. The fields that is valid will be displayed in proper input page fields
     * preventing user to enter correct data again.
     * @return correct User mapped from data entered by user.
    //     * @throws ValidationException when some data entered by user is incorrect.
     */
    public static User mapUser(HttpServletRequest req) {
        final String password = req.getParameter(RequestParameters.PASSWORD);
        final String email = req.getParameter(RequestParameters.EMAIL).trim();
        final String name = req.getParameter(RequestParameters.NAME).trim();
        final String surname = req.getParameter(RequestParameters.SURNAME).trim();
        final String gender = req.getParameter(RequestParameters.GENDER).trim();
        final boolean isOverEighteen = req.getParameter(RequestParameters.OVER_EIGHTEEN_AGE) != null;
        User user = User.getInstance(email, password, name, surname, gender, isOverEighteen);
        user.setRole(Role.CLIENT);
        return user;
    }

}
