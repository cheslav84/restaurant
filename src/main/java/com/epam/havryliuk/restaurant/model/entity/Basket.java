package com.epam.havryliuk.restaurant.model.entity;

import java.math.BigDecimal;

/**
 * Basket it's an entity that represents only one row in database
 * table "order_has_dishes".
 */
public class Basket implements Entity {
    private final Order order;
    private final Dish dish;
    private final BigDecimal fixedPrice;
    private final Integer amount;

    private Basket(BasketBuilder builder) {
        this.order = builder.order;
        this.dish = builder.dish;
        this.fixedPrice = builder.fixedPrice;
        this.amount = builder.amount;
    }

    public Order getOrder() {
        return order;
    }

    public Dish getDish() {
        return dish;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    @SuppressWarnings("EqualsReplaceableByObjectsCall")
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
        int result = 1;
        result = 31 * result + (dish != null ? dish.hashCode() : 0);
        result = 31 * result + (fixedPrice != null ? fixedPrice.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    public static class BasketBuilder {
        private Order order;
        private Dish dish;
        private BigDecimal fixedPrice;
        private Integer amount;

        public BasketBuilder withOrder(Order order) {
            this.order = order;
            return this;
        }

        public BasketBuilder withDish(Dish dish) {
            this.dish = dish;
            return this;
        }

        public BasketBuilder withPrice(BigDecimal price) {
            this.fixedPrice = price;
            return this;
        }

        public BasketBuilder withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public Basket build() {
            return new Basket(this);
        }
    }

}
