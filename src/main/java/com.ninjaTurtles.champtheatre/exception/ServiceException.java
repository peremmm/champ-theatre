package com.ninjaTurtles.champtheatre.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 4482527458772936691L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable t) {
        super(message, t);
    }

    public static ServiceException instance(String message) {
        return new ServiceException(message);
    }

    public static ServiceException instance(String message, Throwable t) {
        return new ServiceException(message, t);
    }
}
