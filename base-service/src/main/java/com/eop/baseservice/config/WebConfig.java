package com.eop.baseservice.config;

import com.eop.baseservice.helper.StringToSortByConverterHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    private final StringToSortByConverterHelper stringToSortByConverter;

    public WebConfig(StringToSortByConverterHelper stringToSortByConverter) {
        this.stringToSortByConverter = stringToSortByConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToSortByConverter);
    }
}