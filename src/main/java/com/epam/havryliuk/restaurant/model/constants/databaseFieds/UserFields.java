package com.epam.havryliuk.restaurant.model.constants.databaseFieds;

import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import com.epam.havryliuk.restaurant.model.constants.ResourcePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class UserFields {
    private static final Logger LOG = LogManager.getLogger(UserFields.class);
    private static final Properties PROPERTIES;
    public static final String USER_ID;
    public static final String USER_EMAIL;
    public static final String USER_PASSWORD;
    public static final String USER_NAME;
    public static final String USER_SURNAME;
    public static final String USER_GENDER;
    public static final String USER_IS_AGE_OVER_EIGHTEEN;
    public static final String USER_ACCOUNT_CREATION_DATE;
    public static final String USER_ROLE_ID;
    public static final String MANAGER_ID;
    public static final String MANAGER_BIRTH_DATE;
    public static final String MANAGER_PASSPORT;
    public static final String MANAGER_BANK_ACCOUNT;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourcePath.DB_FIELDS_SETTING_FILE);
        USER_ID = (String) PROPERTIES.get("user.id");
        USER_EMAIL = (String) PROPERTIES.get("user.email");
        USER_PASSWORD = (String) PROPERTIES.get("user.password");
        USER_NAME = (String) PROPERTIES.get("user.name");
        USER_SURNAME = (String) PROPERTIES.get("user.surname");
        USER_GENDER = (String) PROPERTIES.get("user.gender");
        USER_IS_AGE_OVER_EIGHTEEN = (String) PROPERTIES.get("user.isOverEighteen");
        USER_ACCOUNT_CREATION_DATE = (String) PROPERTIES.get("user.creationTime");
        USER_ROLE_ID = (String) PROPERTIES.get("user.roleId");
        MANAGER_ID = (String) PROPERTIES.get("manager.id");
        MANAGER_BIRTH_DATE = (String) PROPERTIES.get("manager.birthDate");
        MANAGER_PASSPORT = (String) PROPERTIES.get("manager.passport");
        MANAGER_BANK_ACCOUNT = (String) PROPERTIES.get("manager.bankAccount");
        LOG.debug("Database fields for \"User\" table have been initialised successfully.");
    }

}
