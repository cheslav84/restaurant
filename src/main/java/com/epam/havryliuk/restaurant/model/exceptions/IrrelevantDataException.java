package com.epam.havryliuk.restaurant.model.exceptions;

public class IrrelevantDataException extends ServiceException {

    public IrrelevantDataException() {
        super();
    }

    public IrrelevantDataException(String message) {
        super(message);
    }

    public IrrelevantDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IrrelevantDataException(Throwable cause) {
        super(cause);
    }
}
