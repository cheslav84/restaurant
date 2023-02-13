package com.epam.havryliuk.restaurant.model.entityMappers;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.database.databaseFieds.UserFields;
import com.epam.havryliuk.restaurant.model.entity.Role;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.entity.UserDetails;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserMapper {

    public static synchronized User mapUser(ResultSet rs) throws SQLException {
        long id = rs.getLong(UserFields.USER_ID);
        String email = rs.getString(UserFields.USER_EMAIL);
        String password = rs.getString(UserFields.USER_PASSWORD);
        String name = rs.getString(UserFields.USER_NAME);
        String surname = rs.getString(UserFields.USER_SURNAME);
        String gender = rs.getString(UserFields.USER_GENDER);
        boolean isOverEighteen = rs.getBoolean(UserFields.USER_IS_AGE_OVER_EIGHTEEN);
        Date accountCreationDate = rs.getTimestamp(UserFields.USER_ACCOUNT_CREATION_DATE);
        long roleId = rs.getLong(UserFields.USER_ROLE_ID);
        Role role = Role.getRole(roleId);
        UserDetails userDetails = null;
        if (role == Role.MANAGER) {
            userDetails = mapUserDetails(rs);
        }
        return User.getInstance(id, email, password, name, surname,
                gender, isOverEighteen, accountCreationDate, role, userDetails);
    }

    private static synchronized UserDetails mapUserDetails(ResultSet rs) throws SQLException {
        Date birthDate = new Date(rs.getDate(UserFields.MANAGER_BIRTH_DATE).getTime());
        String passport = rs.getString(UserFields.MANAGER_PASSPORT);
        String bankAccount = rs.getString(UserFields.MANAGER_BANK_ACCOUNT);
        return UserDetails.getInstance(birthDate, passport, bankAccount);
    }

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
    public static synchronized User mapUser(HttpServletRequest req) {
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
