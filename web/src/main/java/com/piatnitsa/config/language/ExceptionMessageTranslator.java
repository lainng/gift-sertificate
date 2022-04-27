package com.piatnitsa.config.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * This class provides tools for getting localized messages.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@Component
public class ExceptionMessageTranslator {
    private static MessageSource messageSource;

    @Autowired
    public ExceptionMessageTranslator(MessageSource messageSource) {
        ExceptionMessageTranslator.messageSource = messageSource;
    }

    /**
     * Returns localized message from property file.
     * @param messageKey code of message to get.
     * @return localized message.
     */
    public static String toLocale(String messageKey) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, null, locale);
    }
}
