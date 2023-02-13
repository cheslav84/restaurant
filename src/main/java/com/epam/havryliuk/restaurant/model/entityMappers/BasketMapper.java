package com.epam.havryliuk.restaurant.model.entityMappers;

import com.epam.havryliuk.restaurant.model.database.databaseFieds.BasketFields;
import com.epam.havryliuk.restaurant.model.entity.Basket;
import com.epam.havryliuk.restaurant.model.entity.BookingStatus;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.entity.Order;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasketMapper {

    /**
     * Method maps Dish, dish amount and dish price from ResultSet.
     * If BookingStatus is "Booking" - the order is not confirmed yet by user,
     * then for Basket sets price from Dish table, otherwise, for Basket sets
     * fixed price from table with baskets
     */
    public static synchronized Basket mapBasket(ResultSet rs, Order order) throws SQLException {
        Dish dish = DishMapper.mapDish(rs);
        int amount = rs.getInt(BasketFields.DISH_AMOUNT);
        BigDecimal price = rs.getBigDecimal(BasketFields.DISH_PRICE);
        if (order.getBookingStatus() != BookingStatus.BOOKING) {
            dish.setPrice(price);
        }
        return Basket.getInstance(order, dish, price, amount);
    }
}
