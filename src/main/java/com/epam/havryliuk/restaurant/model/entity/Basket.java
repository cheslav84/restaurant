package com.epam.havryliuk.restaurant.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Basket it's an entity that represents only one row in database
 * table "order_has_dishes".
 */
public class Basket implements Entity{

    private Order order;
    private Dish dish;
    private BigDecimal fixedPrice;
    private Integer amount;

    public static Basket getInstance(Order order, Dish dish, BigDecimal fixedPrice, Integer amount) {
        Basket basket = new Basket();
        basket.setOrder(order);
        basket.setDish(dish);
        basket.setFixedPrice(fixedPrice);
        basket.setAmount(amount);

        return basket;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
