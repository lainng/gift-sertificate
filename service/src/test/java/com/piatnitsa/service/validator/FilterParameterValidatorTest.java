package com.piatnitsa.service.validator;

import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.validator.FilterParameterValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FilterParameterValidatorTest {
    private static final String CORRECT_SORTING_TYPE = "ASC";
    private static final String INCORRECT_SORTING_TYPE = "A_S_C";

    @Test
    void validateSortTypeTest_CorrectType() {
        Assertions.assertDoesNotThrow(() -> FilterParameterValidator.validateSortType(CORRECT_SORTING_TYPE));
    }

    @Test
    void validateSortTypeTest_IncorrectType() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> FilterParameterValidator.validateSortType(INCORRECT_SORTING_TYPE));
    }

    @Test
    void validateSortTypeTest_EmptyType() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> FilterParameterValidator.validateSortType(""));
    }
}
