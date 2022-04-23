package com.piatnitsa.config.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ExceptionMessageTranslator {
    private static MessageSource messageSource;

    @Autowired
    public ExceptionMessageTranslator(MessageSource messageSource) {
        ExceptionMessageTranslator.messageSource = messageSource;
    }

    public static String toLocale(String messageKey) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, null, locale);
    }
}
