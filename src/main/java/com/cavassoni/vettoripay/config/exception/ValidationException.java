package com.cavassoni.vettoripay.config.exception;

import java.io.Serial;

public class ValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Object... args) {
        super(String.format(message, args));
    }
}
