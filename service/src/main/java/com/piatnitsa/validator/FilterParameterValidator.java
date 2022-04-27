package com.piatnitsa.validator;

import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;

/**
 * This class provides a validator for entity filter parameters.
 */
public class FilterParameterValidator {
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    /**
     * Validates sort order types.
     * @param value sort order type.
     * @throws IncorrectParameterException if sort type not equal "ASC" or "DESC".
     */
    public static void validateSortType(String value) throws IncorrectParameterException {
        if (value == null
                || (!value.equalsIgnoreCase(ASCENDING) && !value.equalsIgnoreCase(DESCENDING))) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_SORTING_TYPE);
        }
    }
}
