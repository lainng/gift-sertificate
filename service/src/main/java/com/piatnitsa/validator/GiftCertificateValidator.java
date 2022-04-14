package com.piatnitsa.validator;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;

import java.math.BigDecimal;
import java.util.List;

public class GiftCertificateValidator {
    private static final int MAX_LENGTH_NAME = 30;
    private static final int MIN_LENGTH_NAME = 3;
    private static final int MIN_LENGTH_DESCRIPTION = 5;
    private static final int MAX_LENGTH_DESCRIPTION = 100;
    private static final int MAX_SCALE = 2;
    private static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private static final int MAX_DURATION = 366;
    private static final int MIN_DURATION = 1;

    public static void validate(GiftCertificate item) throws IncorrectParameterException {
        validateName(item.getName());
        validateDescription(item.getDescription());
        validatePrice(item.getPrice());
        validateDuration(item.getDuration());
        validateListOfTags(item.getTags());
    }

    public static void validateForUpdate(GiftCertificate item) throws IncorrectParameterException {
        if (item.getName() != null) {
            validateName(item.getName());
        }
        if (item.getDescription() != null) {
            validateDescription(item.getDescription());
        }
        if (item.getPrice() != null) {
            validatePrice(item.getPrice());
        }
        if (item.getDuration() != 0) {
            validateDuration(item.getDuration());
        }
        validateListOfTags(item.getTags());
    }

    public static void validateListOfTags(List<Tag> tags) throws IncorrectParameterException {
        if (tags == null) return;
        for (Tag tag : tags) {
            TagValidator.validate(tag);
        }
    }

    private static void validateName(String name) throws IncorrectParameterException {
        if (name == null
                || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_NAME);
        }
    }

    private static void validateDescription(String description) throws IncorrectParameterException {
        if (description == null
                || description.length() < MIN_LENGTH_DESCRIPTION || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_DESCRIPTION);
        }
    }

    private static void validatePrice(BigDecimal price) throws IncorrectParameterException {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_PRICE);
        }
    }

    private static void validateDuration(int duration) throws IncorrectParameterException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_DURATION);
        }
    }
}
