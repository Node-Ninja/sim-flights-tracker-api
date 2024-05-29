package dev.nodeninja.simflightstracker.exceptions;

import lombok.Data;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
