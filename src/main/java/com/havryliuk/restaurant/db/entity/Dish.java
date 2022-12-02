package com.havryliuk.restaurant.db.entity;

import java.math.BigDecimal;

public class Dish implements Entity {//todo implements Serializable (to save Objects in session)
    private long id;
    private String name;
    private String description;
    private int weight;
    private BigDecimal price;
    private int amount;
    private boolean special;
    private String image;
    private Category category;

    public Dish() {
    }

    public static Dish getInstance(String name, BigDecimal price, int amount) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setPrice(price);
        dish.setAmount(amount);
        return dish;
    }

    public static Dish getInstance(long id, String name, String description, int weight, BigDecimal price,
                                   int amount, boolean special, String image, Category category) {
        Dish dish = getInstance(name, price, amount);
        dish.setId(id);
        dish.setDescription(description);
        dish.setWeight(weight);
        dish.setSpecial(special);
        dish.setImage(image);
        dish.setCategory(category);
        return dish;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (weight != dish.weight) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        return price != null ? price.equals(dish.price) : dish.price == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {//todo represent like jsp?
        return "Dish{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", amount=" + amount +
                ", special=" + special +
                ", image='" + image + '\'' +
                ", category=" + category +
                '}' + "\n" ;
    }
}
