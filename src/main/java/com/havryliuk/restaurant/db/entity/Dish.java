package com.havryliuk.restaurant.db.entity;

import java.math.BigDecimal;

public class Dish implements Entity {
    private long id;
    private String name;
    private BigDecimal price;
    private int weight;
    private String description;
    private int amount;

    // private String picture;

    public static Dish getInstance(String name, BigDecimal price, int weight) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setPrice(price);
        dish.setWeight(weight);
        return dish;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateStringData(name, 45);
        this.name = name ;
    }



    public String getDescription()  {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    private void validateStringData(String data, int maxLength) { //todo move to separate util class and make static
        if (data == null) {
            throw new IllegalArgumentException("Input data can't be null.");
        }
        if (data.isBlank() || data.length() < 2 ) {
            throw new IllegalArgumentException("Data is blank," +
                    " length of data is too short or too long." +
                    " Data: " + data + ". (The length can't be " +
                    "more than " + maxLength + " symbols.");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price.equals(0)) { //todo check if this method valid and check for max input value
            throw new IllegalArgumentException("Price value is not correct: " + price);
        }
    }



}
