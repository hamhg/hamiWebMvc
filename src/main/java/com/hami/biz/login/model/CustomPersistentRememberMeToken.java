package com.hami.biz.login.model;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class CustomPersistentRememberMeToken extends PersistentRememberMeToken{
    private final String ccd;

    public CustomPersistentRememberMeToken(String ccd, String username, String series, String tokenValue, Date date) {
        super(username, series, tokenValue, date);
        this.ccd = ccd;
    }

    public String getCcd() {
        return ccd; 
    }

}
