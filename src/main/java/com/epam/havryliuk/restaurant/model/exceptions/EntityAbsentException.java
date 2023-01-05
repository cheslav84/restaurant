package com.epam.havryliuk.restaurant.model.exceptions;

public class EntityAbsentException extends ServiceException {

    public EntityAbsentException() {
        super();
    }

    public EntityAbsentException(String message) {
        super(message);
    }

    public EntityAbsentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAbsentException(Throwable cause) {
        super(cause);
    }
}
