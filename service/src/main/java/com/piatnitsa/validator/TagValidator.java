package com.piatnitsa.validator;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;

public class TagValidator {
    private static final int MAX_LENGTH_NAME = 40;
    private static final int MIN_LENGTH_NAME = 3;

    public static void validate(Tag item) throws IncorrectParameterException {
        validateName(item.getName());
    }

    public static void validateName(String name) throws IncorrectParameterException {
        if (name == null
                || name.length() < MIN_LENGTH_NAME
                || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_TAG_NAME);
        }
    }
}
