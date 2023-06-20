package com.befoodly.be.exception.throwable;

import lombok.NonNull;

public class InvalidException extends RuntimeException {
    public InvalidException(@NonNull String message) {
        super(message);
    }
}
