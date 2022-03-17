package com.piatnitsa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DaoException.class)
    public final ResponseEntity<ErrorResponse> handleDaoExceptions(DaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(40401);
        errorResponse.setErrorMessage("Object with this ID not found");
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }
}
