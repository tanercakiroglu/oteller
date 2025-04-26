package com.otel.reservation_service.common_config;

import java.util.Arrays;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public LocaleResolver localeResolver() {
    final var resolver = new AcceptHeaderLocaleResolver();
    resolver.setSupportedLocales(Arrays.asList(Locale.UK, Locale.US));
    resolver.setDefaultLocale(Locale.UK);
    return resolver;
  }

}
