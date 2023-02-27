package com.epam.havryliuk.restaurant.model.entity;

import java.math.BigDecimal;
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
    private BigDecimal price;
    private List<Basket> baskets;

    private Order (OrderBuilder builder) {
        this.id = builder.id;
        this.address =  builder.address;
        this.phoneNumber =  builder.phoneNumber;
        this.isPayed = builder.isPayed;
        this.creationDate = builder.creationDate;
        this.closeDate = builder.closeDate;
        this.user = builder.user;
        this.bookingStatus = builder.bookingStatus;
        this.price = builder.price;
        this.baskets = builder.baskets;
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

    public boolean isPayed() {
        return isPayed;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public static class OrderBuilder {
        private Long id;
        private String address;
        private String phoneNumber;
        private boolean isPayed;
        private Date creationDate;
        private Date closeDate;
        private User user;
        private BookingStatus bookingStatus;
        private BigDecimal price;
        private List<Basket> baskets = new ArrayList<>();

        public OrderBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public OrderBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public OrderBuilder withPayed(boolean payed) {
            isPayed = payed;
            return this;
        }

        public OrderBuilder withCreationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public OrderBuilder withCloseDate(Date closeDate) {
            this.closeDate = closeDate;
            return this;
        }

        public OrderBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public OrderBuilder withBookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public OrderBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderBuilder withBaskets(List<Basket> baskets) {
            this.baskets = baskets;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}
