package com.epam.havryliuk.restaurant.model.entity;

/**
 * Enum represents Category of menu. "ALL" Category stands for
 * all categories in menu, and that field is not present in database.
 * Dishes from "SPECIALS" Category can be present in another categories
 * at the same time.
 */
public enum Category implements Entity {
    COFFEE(1),
    LUNCH(2),
    DINER(3),
    DRINKS(4),
    SPECIALS(5),
    ALL(6);

    Category(final long id) {
        this.id = id;
    }

    private final long id;

    public long getId() {
        return id;
    }

    public static Category getCategory(long id) {

        return Category.values()[(int) id - 1];
    }
}
