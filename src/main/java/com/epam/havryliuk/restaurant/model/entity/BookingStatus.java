package com.epam.havryliuk.restaurant.model.entity;

public enum BookingStatus implements Entity {
    BOOKING (1),
    NEW (2),
    COOKING (3),
    IN_DELIVERY (4),
    WAITING_PAYMENT (5),
    PAID (6),
    COMPLETED (7);

    BookingStatus(final long id) {
        this.id = id;
    }

    private final long id;

    public long getId() {
        return id;
    }

    public static BookingStatus getStatus (long id) {
        return BookingStatus.values()[(int) id - 1];
    }
}

