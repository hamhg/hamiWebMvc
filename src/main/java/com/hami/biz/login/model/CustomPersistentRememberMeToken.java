package com.hami.biz.login.model;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class CustomPersistentRememberMeToken extends PersistentRememberMeToken{
    private final String ccd;

    public CustomPersistentRememberMeToken(String username, String series, String tokenValue, Date date, String ccd) {
        super(username, series, tokenValue, date);
        this.ccd = ccd;
    }

    public String getCcd() {
        return ccd;
    }

}
