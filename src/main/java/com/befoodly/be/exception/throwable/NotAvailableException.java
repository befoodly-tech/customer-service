package com.befoodly.be.exception.throwable;

import lombok.NonNull;

public class NotAvailableException extends UnknownError {
    public NotAvailableException (@NonNull String message) {super(message);}
}
