package com.epam.havryliuk.restaurant.model.entity;


//import com.epam.havryliuk.restaurant.model.entity.constants.Status;

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

//public enum BookingStatus implements Entity {
//    BOOKING (1, "", "Confirm my orders"),
//    NEW (2, "", "Send for cooking"),
//    COOKING (3, "", "Send to client"),
//    IN_DELIVERY (4, "", "Request payment"),
//    WAITING_PAYMENT (5, "", "Pay for order"),
//    PAID (6, "", "Complete order"),
//    COMPLETED (7, "", "");
//
//    BookingStatus(final long id, final String description, final String nextStatus) {
//        this.id = id;
//        this.nextStatus =nextStatus;
//        this.description =description;
//    }
//
//    private final long id;
//    private final String nextStatus;
//    private final String description;
//
//    public long getId() {
//        return id;
//    }
//    public String getDescription() {
//        return description;
//    }
//    public String getNextStatus() {
//        return nextStatus;
//    }
//
//
//
//    public static BookingStatus getStatus (long id) {
//        return BookingStatus.values()[(int) id - 1];
//    }
//}












//    public class BookingStatus implements Entity {
//    private Long id;
//    private Status name;
//
//    public static BookingStatus getInstance(String name) {
//        BookingStatus bookingStatus = new BookingStatus();
//        bookingStatus.setName(name);
//        return bookingStatus;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Status getName() {
//        return name;
//    }
//
//    public void setName(Status name) {
//        this.name = name;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        BookingStatus that = (BookingStatus) o;
//
//        return name != null ? name.equals(that.name) : that.name == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return name != null ? name.hashCode() : 0;
//    }
//}
