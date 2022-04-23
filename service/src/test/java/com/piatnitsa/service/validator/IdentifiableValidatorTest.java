package com.piatnitsa.service.validator;

import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.validator.IdentifiableValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdentifiableValidatorTest {
    private static final long CORRECT_ID = 1;
    private static final long INCORRECT_ID = -2;

    @Test
    void validateIdTest_CorrectId() {
        Assertions.assertDoesNotThrow(() -> IdentifiableValidator.validateId(CORRECT_ID));
    }

    @Test
    void validateIdTest_IncorrectId() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> IdentifiableValidator.validateId(INCORRECT_ID));
    }
}
