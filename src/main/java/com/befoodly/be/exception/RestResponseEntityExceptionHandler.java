package com.befoodly.be.exception;

import com.befoodly.be.exception.throwable.DuplicationException;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<?> notFoundHandler(Exception exception) {
        return GenericResponse.builder()
                .errorMessage(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ExceptionHandler(DuplicationException.class)
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<?> duplicateDataFoundHandler(DuplicationException duplicationException) {
        return GenericResponse.builder()
                .errorMessage(duplicationException.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
    }

    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<?> invalidDataFoundHandler(InvalidException invalidException) {
        return GenericResponse.builder()
                .errorMessage(invalidException.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
