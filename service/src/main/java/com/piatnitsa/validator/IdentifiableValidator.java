package com.piatnitsa.validator;

import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;

public class IdentifiableValidator {

    public static void validateId(long id) throws IncorrectParameterException {
        if (id < 1) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_ID);
        }
    }
}
