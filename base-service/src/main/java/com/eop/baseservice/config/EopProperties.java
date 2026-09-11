package com.eop.baseservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eop")
@Getter
@Setter
public class EopProperties {

    private Church church = new Church();
    private Locale locale = new Locale();

    @Getter
    @Setter
    public static class Church {
        private String name;
        private String code;
        private String address;
        private String phone;
        private String email;
        private String website;
        private String conference;
        private String union;
        private String division;
        private String logoPath;
    }

    @Getter
    @Setter
    public static class Locale {
        private String timezone;
        private String language;
        private String currency;
    }
}