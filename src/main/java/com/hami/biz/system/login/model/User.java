package com.hami.biz.system.login.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : User
 * <li>Description  :
 * <li>History      : 2018. 1. 8.
 * </pre>
 *
 * @author HHG
 */
public @Data class User implements UserDetails {
    private String ccd;
    private String username;
    private String password;
    private String authority;
    boolean enabled;
    boolean accountNonExpired;
    boolean accountNonLocked;    
    boolean credentialsNonExpired;
    private GrantedAuthority authorities;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(authority));
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
    
}
