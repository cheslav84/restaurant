package com.epam.havryliuk.restaurant.model.database.dao.databaseFieds;

import com.epam.havryliuk.restaurant.model.utils.PropertiesLoader;
import com.epam.havryliuk.restaurant.model.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class UserFields {
    private static final Logger log = LogManager.getLogger(UserFields.class);

    private static final Properties properties;
    public static String USER_ID;
    public static String USER_EMAIL;
    public static String USER_PASSWORD;
    public static String USER_NAME;
    public static String USER_SURNAME;
    public static String USER_GENDER;
    public static String USER_IS_AGE_OVER_EIGHTEEN;
    public static String USER_ACCOUNT_CREATION_DATE;
    public static String USER_ROLE_ID;
    public static String MANAGER_ID;
    public static String MANAGER_BIRTH_DATE;
    public static String MANAGER_PASSPORT;
    public static String MANAGER_BANK_ACCOUNT;



    static {
        properties = PropertiesLoader.getProperties(Constants.DB_FIELDS_SETTING_FILE);
        initialiseVariable();
        log.debug("Database fields for \"User\" table have been initialised successfully.");
    }

    private static void initialiseVariable() {
        USER_ID = (String) properties.get("user.id");
        USER_EMAIL = (String) properties.get("user.email");
        USER_PASSWORD = (String) properties.get("user.password");
        USER_NAME = (String) properties.get("user.name");
        USER_SURNAME = (String) properties.get("user.surname");
        USER_GENDER = (String) properties.get("user.gender");
        USER_IS_AGE_OVER_EIGHTEEN = (String) properties.get("user.isOverEighteen");
        USER_ACCOUNT_CREATION_DATE = (String) properties.get("user.creationTime");
        USER_ROLE_ID = (String) properties.get("user.roleId");
        MANAGER_ID = (String) properties.get("manager.id");
        MANAGER_BIRTH_DATE = (String) properties.get("manager.birthDate");
        MANAGER_PASSPORT = (String) properties.get("manager.passport");
        MANAGER_BANK_ACCOUNT = (String) properties.get("manager.bankAccount");

    }
}
