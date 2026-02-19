package com.appswave.membership.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class I18nConfig {

    public static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
    		Locale.ENGLISH,
            Locale.forLanguageTag("ar")
    );

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String lang = request.getHeader("Accept-Language");
                if (lang == null || lang.isBlank()) {
                    return Locale.ENGLISH; // default
                }
                Locale locale = Locale.forLanguageTag(lang);
                if (!SUPPORTED_LOCALES.contains(locale)) {
                    return Locale.ENGLISH; // default
                }
                return locale;
            }
        };
    }
}
