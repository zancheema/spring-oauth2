package com.zaincheema.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String secretKey;
    private Long tokenExpirationMsec;

    public String getSecretKey() {
        return secretKey;
    }

    public Long getTokenExpirationMsec() {
        return tokenExpirationMsec;
    }

    public void setTokenExpirationMsec(Long tokenExpirationMsec) {
        this.tokenExpirationMsec = tokenExpirationMsec;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
