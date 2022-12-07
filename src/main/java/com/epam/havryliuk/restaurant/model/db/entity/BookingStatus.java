package com.epam.havryliuk.restaurant.model.db.entity;


public class BookingStatus implements Entity {
    private Long id;
    private String name;

    public static BookingStatus getInstance(String name) {
        BookingStatus bookingStatus = new BookingStatus();
        bookingStatus.setName(name);
        return bookingStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingStatus that = (BookingStatus) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
