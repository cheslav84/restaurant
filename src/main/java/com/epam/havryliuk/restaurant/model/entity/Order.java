package com.epam.havryliuk.restaurant.model.entity;

import java.util.*;

public class Order implements Entity {
    private Long id;
    private String address;
    private String phoneNumber;
    private boolean isPayed;
    private Date creationDate;
    private Date closeDate;
    private User user;
    private BookingStatus bookingStatus;
    private List<Basket> baskets;


    public static Order getInstance(String address,
                                    String phoneNumber,
                                    boolean isPayed,
                                    BookingStatus bookingStatus) {
        Order order = new Order();
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setPayed(isPayed);
        order.setBookingStatus(bookingStatus);
        order.setBaskets(new ArrayList<>());
        return order;
    }


    public static Order getInstance(long id,
                                    String address,
                                    String phoneNumber,
                                    boolean isPayed,
                                    Date creationDate,
                                    Date closeDate,
                                    BookingStatus bookingStatus) {
        Order order = getInstance(address, phoneNumber, isPayed, bookingStatus);
        order.setId(id);
        order.setCreationDate(creationDate);
        order.setCloseDate(closeDate);
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
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

//    public Date getCloseDate() {
//        return closeDate;
//    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }


    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (isPayed != order.isPayed) return false;
        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(order.phoneNumber) : order.phoneNumber != null) return false;
        if (creationDate != null ? !creationDate.equals(order.creationDate) : order.creationDate != null) return false;
        if (closeDate != null ? !closeDate.equals(order.closeDate) : order.closeDate != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (bookingStatus != order.bookingStatus) return false;
        return baskets != null ? baskets.equals(order.baskets) : order.baskets == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (isPayed ? 1 : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (bookingStatus != null ? bookingStatus.hashCode() : 0);
        result = 31 * result + (baskets != null ? baskets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isPayed=" + isPayed +
                ", creationDate=" + creationDate +
                ", closeDate=" + closeDate +
                ", user=" + user +
                ", bookingStatus=" + bookingStatus +
                ", baskets=" + baskets +
                '}';
    }
}
