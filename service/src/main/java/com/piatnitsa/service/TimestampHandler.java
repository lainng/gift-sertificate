package com.piatnitsa.service;

import java.time.LocalDateTime;

/**
 * This class represents the time handler needed to update the timestamp of the {@link com.piatnitsa.entity.GiftCertificate}.
 */
public class TimestampHandler {

    /**
     * Returns the current timestamp in ISO 8601 format.
     * @return current timestamp of string type.
     */
    public static String getCurrentTimestamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.toString();
    }
}
