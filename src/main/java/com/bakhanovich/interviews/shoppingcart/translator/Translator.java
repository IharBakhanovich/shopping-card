package com.bakhanovich.interviews.shoppingcart.translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Translates messages to the defined by locale language.
 *
 * @author Ihar Bakhanovich
 */
@Component
public class Translator {
    private ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Fetch messages by the message code from message.properties file.
     *
     * @param msgCode the code of the message.
     * @return {@link String} that is the value of the {@param msgCode}.
     */
    public String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }
}
