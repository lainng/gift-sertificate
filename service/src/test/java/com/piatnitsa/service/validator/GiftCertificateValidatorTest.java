package com.piatnitsa.service.validator;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GiftCertificateValidatorTest {
    private static final GiftCertificate CORRECT_CERTIFICATE = new GiftCertificate(1, "giftCertificate1",
            "description1", new BigDecimal("99.90"), 1, "2020-10-20T07:20:15.156",
            "2020-10-20T07:20:15.156",
            Arrays.asList(new Tag(2, "tagName3"), new Tag(1, "tagName1")));
    private static final GiftCertificate INCORRECT_CERTIFICATE = new GiftCertificate(2, "gi",
            "", new BigDecimal("99.999"), 1, "2020-10-20T07:20:15.156",
            "2020-10-20T07:20:15.156",
            Arrays.asList(new Tag(2, "tagName3"), new Tag(1, "tagName1")));
    private static final GiftCertificate CORRECT_UPDATABLE_CERTIFICATE = new GiftCertificate(0, "giftCertificate1",
            "description1", new BigDecimal("99.99"), 3, "2020-10-20T07:20:15.156",
            "2020-10-20T07:20:15.156",
            Arrays.asList(new Tag(2, "tagName3"), new Tag(1, "tagName1")));
    private static final GiftCertificate INCORRECT_UPDATABLE_CERTIFICATE = new GiftCertificate(0, "giftCertificate1",
            "description1", new BigDecimal("0.00"), 0, "2020-10-20T07:20:15.156",
            "2020-10-20T07:20:15.156",
            Collections.emptyList());
    private static final List<Tag> CORRECT_TAG_LIST = Arrays.asList(new Tag(2, "tagName3"),
            new Tag(1, "tagName1"));
    private static final List<Tag> INCORRECT_TAG_LIST = Arrays.asList(new Tag(2, ""),
            new Tag(1, "ta"));

    @Test
    void validateTest_CorrectCertificate() {
        Assertions.assertDoesNotThrow(() -> GiftCertificateValidator.validate(CORRECT_CERTIFICATE));
    }

    @Test
    void validateTest_IncorrectCertificate() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> GiftCertificateValidator.validate(INCORRECT_CERTIFICATE));
    }

    @Test
    void validateTest_EmptyCertificate() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> GiftCertificateValidator.validate(new GiftCertificate()));
    }

    @Test
    void validateForUpdateTest_CorrectCertificate() {
        Assertions.assertDoesNotThrow(() -> GiftCertificateValidator.validateForUpdate(CORRECT_UPDATABLE_CERTIFICATE));
    }

    @Test
    void validateForUpdateTest_IncorrectCertificate() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> GiftCertificateValidator.validateForUpdate(INCORRECT_UPDATABLE_CERTIFICATE));
    }

    @Test
    void validateForUpdateTest_EmptyCertificate() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> GiftCertificateValidator.validateForUpdate(new GiftCertificate()));
    }

    @Test
    void validateListOfTagsTest_CorrectTags() {
        Assertions.assertDoesNotThrow(() -> GiftCertificateValidator.validateListOfTags(CORRECT_TAG_LIST));
    }

    @Test
    void validateListOfTagsTest_IncorrectTags() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> GiftCertificateValidator.validateListOfTags(INCORRECT_TAG_LIST));
    }
}
