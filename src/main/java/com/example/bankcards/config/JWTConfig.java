package com.example.bankcards.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    private String secret;
    private long expiration;
    private String issuer;

    //getters
    public String getSecret() { return secret; }
    public long getExpiration() { return expiration; }
    public String getIssuer() { return issuer; }

    //setters
    public void setExpiration(long expiration) { this.expiration = expiration; }
    public void setSecret(String secret) { this.secret = secret; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
}
