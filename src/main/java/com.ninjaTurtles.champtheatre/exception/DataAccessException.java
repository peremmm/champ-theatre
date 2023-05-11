package com.ninjaTurtles.champtheatre.exception;

public class DataAccessException extends RuntimeException {
    private static final long serialVersionUID = 4482527458772936691L;

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable t) {
        super(message, t);
    }

    public static DataAccessException instance(String message) {
        return new DataAccessException(message);
    }

    public static DataAccessException instance(String message, Throwable t) {
        return new DataAccessException(message, t);
    }
}
