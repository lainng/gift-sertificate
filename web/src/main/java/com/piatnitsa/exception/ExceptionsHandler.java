package com.piatnitsa.exception;

import com.piatnitsa.config.language.ExceptionMessageTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DaoException.class)
    public final ResponseEntity<ErrorResponse> handleDaoExceptions(DaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        String errorCode = ex.getMessage();
        String details = ExceptionMessageTranslator.toLocale(errorCode);

        HttpStatus httpStatus = parseHttpStatus(errorCode);
        errorResponse.setErrorCode(errorCode + " " + httpStatus.getReasonPhrase());
        errorResponse.setErrorMessage(details);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private HttpStatus parseHttpStatus(String exCode) {
        int httpStatusCode = Integer.parseInt(exCode.substring(0,3));
        return HttpStatus.valueOf(httpStatusCode);
    }
}
