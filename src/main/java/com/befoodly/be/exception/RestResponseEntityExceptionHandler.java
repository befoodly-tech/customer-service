package com.befoodly.be.exception;

import com.befoodly.be.model.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GenericResponse<?> userNotFoundHandler(Exception exception) {
        return GenericResponse.builder()
                .errorMessage(exception.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
