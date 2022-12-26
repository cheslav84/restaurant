package com.epam.havryliuk.restaurant.model.entity;

import java.util.*;

public class Order implements Entity{
    private Long id;
    private String address;
    private String phoneNumber;
    private boolean isPayed;
    private Date creationDate;
    private Date closeDate;
    private User user;
    private BookingStatus bookingStatus;
    private List<Basket> baskets;

//    public static Order getInstance(String address, String phoneNumber,
//                                    boolean isPayed, BookingStatus bookingStatus) {
//        Order order = new Order();
//        order.setAddress(address);
//        order.setPhoneNumber(phoneNumber);
//        order.setPayed(isPayed);
//        order.setBookingStatus(bookingStatus);
//        return order;
//    }

//    public static Order getInstance(long id,
//                                    String address,
//                                    String phoneNumber,
//                                    boolean isPayed,
//                                    Date creationDate,
//                                    Date closeDate,
//                                    User user,
//                                    BookingStatus bookingStatus) {
//        Order order = new Order();
//        order.setId(id);
//        order.setAddress(address);
//        order.setPhoneNumber(phoneNumber);
//        order.setPayed(isPayed);
//        order.setCreationDate(creationDate);
//        order.setCloseDate(closeDate);
//        order.setUser(user);
//        order.setBookingStatus(bookingStatus);
//        order.setDishes(new HashMap<>());
//        return order;
//    }



    public static Order getInstance(String address,
                                    String phoneNumber,
                                    boolean isPayed,
                                    User user,
                                    BookingStatus bookingStatus) {
        Order order = new Order();
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setPayed(isPayed);
        order.setUser(user);
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
                                    User user,
                                    BookingStatus bookingStatus) {
        Order order =getInstance( address, phoneNumber, isPayed, user, bookingStatus);
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

    public Date getCloseDate() {
        return closeDate;
    }

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
}
