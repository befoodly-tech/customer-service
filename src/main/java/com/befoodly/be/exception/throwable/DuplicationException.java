package com.befoodly.be.exception.throwable;

import lombok.NonNull;

public class DuplicationException extends RuntimeException {
    public DuplicationException(@NonNull String message) {
        super(message);
    }
}
