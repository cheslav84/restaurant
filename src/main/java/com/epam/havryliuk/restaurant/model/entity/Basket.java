package com.epam.havryliuk.restaurant.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Basket it's an entity that represents only one row in database
 * table "order_has_dishes".
 */
public class Basket implements Entity {// todo think of renaming, for example DishInOrder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Basket basket = (Basket) o;

        if (order != null ? !order.equals(basket.order) : basket.order != null) return false;
        if (dish != null ? !dish.equals(basket.dish) : basket.dish != null) return false;
        if (fixedPrice != null ? !fixedPrice.equals(basket.fixedPrice) : basket.fixedPrice != null) return false;
        return amount != null ? amount.equals(basket.amount) : basket.amount == null;
    }

    @Override
    public int hashCode() {
        int
                result = 1;
//                result = order != null ? order.hashCode() : 0;
        result = 31 * result + (dish != null ? dish.hashCode() : 0);
        result = 31 * result + (fixedPrice != null ? fixedPrice.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
