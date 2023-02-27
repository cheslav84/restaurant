package com.epam.havryliuk.restaurant.model.entity.mapper;

import com.epam.havryliuk.restaurant.model.database.databaseFieds.OrderFields;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OrderMapper {
    public static synchronized Order mapOrder(ResultSet rs) throws SQLException {
        long id = rs.getLong(OrderFields.ORDER_ID);
        String address = rs.getString(OrderFields.ORDER_ADDRESS);
        String phoneNumber = rs.getString(OrderFields.ORDER_PHONE_NUMBER);
        boolean isPayed = rs.getBoolean(OrderFields.ORDER_PAYMENT);
        Date creationDate = rs.getTimestamp(OrderFields.ORDER_CREATION_DATE);
        Date closeDate = rs.getTimestamp(OrderFields.ORDER_CLOSE_DATE);
        long bookingStatusId = rs.getLong(OrderFields.ORDER_BOOKING_STATUS);
        BookingStatus bookingStatus = BookingStatus.getStatus(bookingStatusId);
        return new Order.OrderBuilder()
                .withId(id)
                .withAddress(address)
                .withPhoneNumber(phoneNumber)
                .withPayed(isPayed)
                .withCreationDate(creationDate)
                .withCloseDate(closeDate)
                .withBookingStatus(bookingStatus)
                .build();
    }
}
