package com.hami.biz.login.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final String ccd;
    
    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, String ccd) {
        super(principal, credentials);
        this.ccd= ccd;
    }
    
    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, String ccd, GrantedAuthority[] authorities) {
        super(principal, credentials, Arrays.asList(authorities));
        this.ccd= ccd;
    }
    
    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, String ccd, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.ccd = ccd;
    }
    
    /**
     * @return the extraParam
     */
    public String getCcd() {
        return ccd;
    }
    
}

