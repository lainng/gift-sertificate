package com.piatnitsa.validator;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;

/**
 * This class provides a validator for {@link Tag} entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class TagValidator {
    private static final int MAX_LENGTH_NAME = 40;
    private static final int MIN_LENGTH_NAME = 3;

    /**
     * Validates a {@link Tag} entity.
     * @param item a {@link Tag} entity for validating.
     * @throws IncorrectParameterException if the entity contains incorrect fields.
     */
    public static void validate(Tag item) throws IncorrectParameterException {
        validateName(item.getName());
    }

    /**
     * Validates a {@link Tag} entity name.
     * @param name a {@link Tag} name.
     * @throws IncorrectParameterException if name contains incorrect value.
     */
    public static void validateName(String name) throws IncorrectParameterException {
        if (name == null
                || name.length() < MIN_LENGTH_NAME
                || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_TAG_NAME);
        }
    }
}
