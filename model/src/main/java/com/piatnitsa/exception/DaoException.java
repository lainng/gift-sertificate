package com.piatnitsa.exception;

/**
 * This class is an exception that is thrown when the requested resource is not found in the database
 * or there was an error saving it.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 * @see Exception
 */
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
