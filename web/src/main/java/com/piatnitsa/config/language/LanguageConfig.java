package com.piatnitsa.config.language;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This class contains Spring configuration for web subproject to work with locale.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@Configuration
public class LanguageConfig extends AcceptHeaderLocaleResolver {
    List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("ru"));

    /**
     * Returns current locale.
     * @param request {@link HttpServletRequest} request from client.
     * @return current {@link Locale} instance.
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    /**
     * Create bean of {@link MessageSource} implementation which will be used to get info from properties files.
     * @return the {@link MessageSource} bean implementation.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
