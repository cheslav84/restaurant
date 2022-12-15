package com.epam.havryliuk.restaurant.model.database.dao.databaseFieds;

import com.epam.havryliuk.restaurant.model.utils.Constants;
import com.epam.havryliuk.restaurant.model.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class OrderFields {
    private static final Logger log = LogManager.getLogger(OrderFields.class);

    private static final Properties properties;
    public static String ORDER_ID;
    public static String ORDER_ADDRESS;
    public static String ORDER_PHONE_NUMBER;
    public static String ORDER_PAYMENT;
    public static String ORDER_CREATION_DATE;
    public static String ORDER_CLOSE_DATE;
    public static String ORDER_USER_ID;
    public static String ORDER_BOOKING_STATUS;


    static {
        properties = PropertiesLoader.getProperties(Constants.DB_FIELDS_SETTING_FILE);
        initialiseVariable();
        log.debug("Database fields for \"User\" table have been initialised successfully.");
    }

    private static void initialiseVariable() {
        ORDER_ID = (String) properties.get("order.id");
        ORDER_ADDRESS = (String) properties.get("order.address");
        ORDER_PHONE_NUMBER = (String) properties.get("order.phone_number");
        ORDER_PAYMENT = (String) properties.get("order.payment");
        ORDER_CREATION_DATE = (String) properties.get("order.creationDate");
        ORDER_CLOSE_DATE = (String) properties.get("order.closeDate");
        ORDER_USER_ID = (String) properties.get("order.userId");
        ORDER_BOOKING_STATUS = (String) properties.get("order.bookingStatusId");
    }
}
