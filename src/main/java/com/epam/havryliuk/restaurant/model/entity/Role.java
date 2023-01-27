package com.epam.havryliuk.restaurant.model.entity;

public enum Role implements Entity {
    MANAGER(1),
    CLIENT(2);

    Role(final long id) {
        this.id = id;
    }

    private final long id;

    public long getId() {
        return id;
    }

    public static Role getRole(long id) {
        return Role.values()[(int) id - 1];
    }
}
