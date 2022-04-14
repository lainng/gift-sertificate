package com.piatnitsa.service;

import java.time.LocalDateTime;

public class TimestampHandler {
    public static String getCurrentTimestamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.toString();
    }
}
