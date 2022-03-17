package com.piatnitsa.exception;

public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String errorMessageCode, Throwable cause) {
        super(errorMessageCode, cause);
    }

    public DaoException(String errorMessageCode) {
        super(errorMessageCode);
    }
}
