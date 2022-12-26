package com.epam.havryliuk.restaurant.model.exceptions;

public class DuplicatedEntityException extends DAOException {

    public DuplicatedEntityException() {
        super();
    }

    public DuplicatedEntityException(String message) {
        super(message);
    }

    public DuplicatedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEntityException(Throwable cause) {
        super(cause);
    }
}
