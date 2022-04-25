package com.piatnitsa.exception;

/**
 * This class represents the exception that is thrown when the validation of entity parameters fails.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 * @see com.piatnitsa.validator.IdentifiableValidator
 * @see com.piatnitsa.validator.GiftCertificateValidator
 * @see com.piatnitsa.validator.TagValidator
 */
public class IncorrectParameterException extends Exception {
    public IncorrectParameterException() {
    }

    public IncorrectParameterException(String messageCode) {
        super(messageCode);
    }

    public IncorrectParameterException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public IncorrectParameterException(Throwable cause) {
        super(cause);
    }
}
