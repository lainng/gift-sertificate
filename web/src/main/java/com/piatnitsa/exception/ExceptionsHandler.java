package com.piatnitsa.exception;

import com.piatnitsa.config.language.Translator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DaoException.class)
    public final ResponseEntity<ErrorResponse> handleDaoExceptions(DaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        String details = Translator.toLocale(ex.getMessage());
        errorResponse.setErrorCode(ExceptionCodes.NOT_FOUND_EXCEPTION.toString());
        errorResponse.setErrorMessage(details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }
}
