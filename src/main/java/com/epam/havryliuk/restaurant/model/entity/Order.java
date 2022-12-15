package com.epam.havryliuk.restaurant.model.entity;

import java.util.Date;

public class Order implements Entity{
    private Long id;
    private String address;
    private String phoneNumber;
    private boolean payed;
    private Date creationDate;
    private Date closeDate;
    private BookingStatus bookingStatus;

    public static Order getInstance(String address, String phoneNumber) {
        Order order = new Order();
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        return order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payment) {
        this.payed = payment;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(order.phoneNumber) : order.phoneNumber != null) return false;
        return creationDate != null ? creationDate.equals(order.creationDate) : order.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
