package com.piatnitsa.service.validator;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.validator.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagValidatorTest {
    private static final String CORRECT_NAME = "Correct name";
    private static final String INCORRECT_NAME = "in";
    private static final Tag CORRECT_TAG = new Tag(1, CORRECT_NAME);
    private static final Tag INCORRECT_TAG = new Tag(0, INCORRECT_NAME);

    @Test
    void validateTest_CorrectTag() {
        Assertions.assertDoesNotThrow(() -> TagValidator.validate(CORRECT_TAG));
    }

    @Test
    void validateTest_IncorrectTag() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> TagValidator.validate(INCORRECT_TAG));
    }

    @Test
    void validateTest_EmptyTag() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> TagValidator.validate(new Tag()));
    }

    @Test
    void validateNameTest_CorrectName() {
        Assertions.assertDoesNotThrow(() -> TagValidator.validateName(CORRECT_NAME));
    }

    @Test
    void validateTest_IncorrectName() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> TagValidator.validateName(INCORRECT_NAME));
    }

    @Test
    void validateTest_EmptyName() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> TagValidator.validateName(new Tag().getName()));
    }
}
