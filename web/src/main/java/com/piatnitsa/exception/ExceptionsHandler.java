package com.piatnitsa.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piatnitsa.config.language.ExceptionMessageTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * This class presents entity which will be returned from controller in case generating exceptions.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
            DaoException.class,
            IncorrectParameterException.class
    })
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        String errorCode = ex.getMessage();
        String details = ExceptionMessageTranslator.toLocale(errorCode);

        HttpStatus httpStatus = parseHttpStatus(errorCode);
        errorResponse.setErrorCode(errorCode + " " + httpStatus.getReasonPhrase());
        errorResponse.setErrorMessage(details);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ErrorResponse> methodNotAllowedExceptionException() {
        ErrorResponse errorResponse = new ErrorResponse();
        String details = ExceptionMessageTranslator.toLocale("exception.notSupported");
        errorResponse.setErrorCode(HttpStatus.METHOD_NOT_ALLOWED.toString());
        errorResponse.setErrorMessage(details);
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            JsonProcessingException.class,
            HttpMessageNotReadableException.class
    })
    public final ResponseEntity<ErrorResponse> handleBadRequestExceptions() {
        ErrorResponse errorResponse = new ErrorResponse();
        String details = ExceptionMessageTranslator.toLocale("exception.badRequest");
        errorResponse.setErrorMessage(details);
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<ErrorResponse> handleBadRequestException() {
        ErrorResponse errorResponse = new ErrorResponse();
        String details = ExceptionMessageTranslator.toLocale("exception.noHandler");
        errorResponse.setErrorMessage(details);
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private HttpStatus parseHttpStatus(String exCode) {
        int httpStatusCode = Integer.parseInt(exCode.substring(0,3));
        return HttpStatus.valueOf(httpStatusCode);
    }
}
