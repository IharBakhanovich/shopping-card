package com.bakhanovich.interviews.shoppingcart.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The AppConfig class: for defining of the additional beans.
 *
 * @author Ihar Bakhanovich.
 */
@Configuration
@EntityScan("com.bakhanovich.interviews.shoppingcart.model")
@ComponentScan("com.bakhanovich.interviews.shoppingcart")
public class AppConfig extends AcceptHeaderLocaleResolver
        implements WebMvcConfigurer {
    private final Environment environment;
    List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("de"),
            new Locale("ru"));

    @Autowired
    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}
