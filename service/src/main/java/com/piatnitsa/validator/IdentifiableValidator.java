package com.piatnitsa.validator;

import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;

/**
 * This class provides a validator for entity identifier.
 */
public class IdentifiableValidator {

    /**
     * Validates an entity ID.
     * @param id an entity ID.
     * @throws IncorrectParameterException if the ID contains incorrect value.
     */
    public static void validateId(long id) throws IncorrectParameterException {
        if (id < 1) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_ID);
        }
    }
}
