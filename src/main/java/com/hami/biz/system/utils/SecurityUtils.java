package com.hami.biz.system.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hami.biz.login.model.User;

/**
 * <pre>
 * <li>Program Name : HttpUtil
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
public class SecurityUtils {

    /**
     * If the login in from remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    public static boolean isRememberMeAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        return RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
    }
    
    /**
     * If the login in from remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || "anonymousUser".equals(auth.getName())) {
            return false;
        } else {
            return auth.isAuthenticated();
        }
    }
    
    /**
     * If the login in from remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    public static User getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    /**
     * save targetURL in session
     */
    public static void setRememberMeTargetUrlToSession(HttpServletRequest request){
        String targetUrl;
        HttpSession session = request.getSession(false);
        if(session!=null){
            targetUrl = session.getAttribute("targetUrl")==null?"":session.getAttribute("targetUrl").toString();
            session.setAttribute("targetUrl", targetUrl);
        }
    }

    /**
     * get targetURL from session
     */
    public static String getRememberMeTargetUrlFromSession(HttpServletRequest request){
        String targetUrl = "";
        HttpSession session = request.getSession(false);
        if(session!=null){
            targetUrl = session.getAttribute("targetUrl")==null?"":session.getAttribute("targetUrl").toString();
        }
        return targetUrl;
    }
}
