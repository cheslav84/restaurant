package com.epam.havryliuk.restaurant.model.database.databaseFieds;

import com.epam.havryliuk.restaurant.model.ResourceProperties;
import com.epam.havryliuk.restaurant.model.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class OrderFields {
    private static final Logger LOG = LogManager.getLogger(OrderFields.class);
    private static final Properties PROPERTIES;
    public static final String ORDER_ID;
    public static final String ORDER_ADDRESS;
    public static final String ORDER_PHONE_NUMBER;
    public static final String ORDER_PAYMENT;
    public static final String ORDER_CREATION_DATE;
    public static final String ORDER_CLOSE_DATE;
    public static final String ORDER_USER_ID;
    public static final String ORDER_BOOKING_STATUS;
    public static final String NUMBER_OF_DISHES_IN_ORDER;
    public static final String NUMBER_OF_ORDERS;

    static {
        PROPERTIES = PropertiesLoader.getProperties(ResourceProperties.DB_FIELDS_SETTING_FILE);
        ORDER_ID = (String) PROPERTIES.get("order.id");
        ORDER_ADDRESS = (String) PROPERTIES.get("order.address");
        ORDER_PHONE_NUMBER = (String) PROPERTIES.get("order.phoneNumber");
        ORDER_PAYMENT = (String) PROPERTIES.get("order.payment");
        ORDER_CREATION_DATE = (String) PROPERTIES.get("order.creationDate");
        ORDER_CLOSE_DATE = (String) PROPERTIES.get("order.closeDate");
        ORDER_USER_ID = (String) PROPERTIES.get("order.userId");
        ORDER_BOOKING_STATUS = (String) PROPERTIES.get("order.bookingStatusId");
        NUMBER_OF_DISHES_IN_ORDER = (String) PROPERTIES.get("basket.numberOfDishesInOrder");
        NUMBER_OF_ORDERS = (String) PROPERTIES.get("order.numberOfOrders");
        LOG.info("Database fields for \"OrderFields\" table have been initialised successfully.");
    }
}
