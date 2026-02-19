package com.appswave.membership.common.i18n;


import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String getMessage(String key) {
    	
        return getMessage(key, LocaleContextHolder.getLocale());
    }
    
    public String getMessage(String key, Object... args) {
        return getMessage(key, Locale.getDefault(),args);
    }

    public String getMessage(String key, Locale locale, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }
}
