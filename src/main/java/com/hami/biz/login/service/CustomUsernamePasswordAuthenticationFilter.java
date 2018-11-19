package com.hami.biz.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hami.sys.util.StringUtils;


/**
 * <pre>
 * <li>Program Name : CustomUserDetailsManager
 * <li>Description  :
 * <li>History      : 2018. 1. 24.
 * </pre>
 *
 * @author HHG
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private boolean postOnly = true;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = StringUtils.nvl(request.getParameter("userid"),"");
        String password = StringUtils.nvl(request.getParameter("password"),"");
        String ccd = StringUtils.nvl(request.getParameter("ccd"),"");

        username = username.trim();
        
        String ccdAndUsername = String.format("%s%s%s", ccd, "■", username.trim());
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(ccdAndUsername, password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
}
