package com.piatnitsa.validator;

import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;

public class FilterParameterValidator {
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    public static void validateSortType(String value) throws IncorrectParameterException {
        if (value == null
                || (!value.equalsIgnoreCase(ASCENDING) && !value.equalsIgnoreCase(DESCENDING))) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_SORTING_TYPE);
        }
    }
}
