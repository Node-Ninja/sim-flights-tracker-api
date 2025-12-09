package dev.nodeninja.simflightstracker.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message, String errorCode, Integer statusCode) {
        super(message);
    }
}
