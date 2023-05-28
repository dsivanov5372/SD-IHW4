package com.example.ordermicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ValidationException;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(value = { ValidationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestsErrors(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = { DishNotFoundException.class,
                                OrderNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundErrors(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = { InvalidJwtException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handlerUnauthorizedErrors(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
