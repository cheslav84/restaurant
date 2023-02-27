package com.epam.havryliuk.restaurant.model.entity;

import java.math.BigDecimal;

public class Dish implements Entity {
    private long id;
    private String name;
    private String description;
    private final int weight;
    private BigDecimal price;
    private final int amount;
    private final boolean alcohol;
    private final boolean special;
    private final String image;
    private final Category category;

    private Dish (DishBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.weight = builder.weight;
        this.price = builder.price;
        this.amount = builder.amount;
        this.alcohol = builder.alcohol;
        this.special = builder.special;
        this.image = builder.image;
        this.category = builder.category;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isAlcohol() {
        return alcohol;
    }

    public boolean isSpecial() {
        return special;
    }

    public Category getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        if (id != dish.id) return false;
        if (alcohol != dish.alcohol) return false;
        return name != null ? name.equals(dish.name) : dish.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (alcohol ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", amount=" + amount +
                ", alcohol='" + alcohol +
                ", special='" + special +
                ", image='" + image + '\'' +
                '}' + "\n" ;
    }


    public static class DishBuilder {
        private long id;
        private String name;
        private String description;
        private int weight;
        private BigDecimal price;
        private int amount;
        private boolean alcohol;
        private boolean special;
        private String image;
        private Category category;

        public DishBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public DishBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public DishBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DishBuilder withWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public DishBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public DishBuilder withAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public DishBuilder withAlcohol(boolean alcohol) {
            this.alcohol = alcohol;
            return this;
        }

        public DishBuilder withSpecial(boolean special) {
            this.special = special;
            return this;
        }

        public DishBuilder withImage(String image) {
            this.image = image;
            return this;
        }

        public DishBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Dish build(){
            return new Dish(this);
        }
    }



}
