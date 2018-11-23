package com.hami.biz.login.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.util.Assert;

import com.hami.biz.login.model.CustomPersistentRememberMeToken;
import com.hami.biz.system.utils.SecurityUtils;

public class CustomPersistentTokenBasedRememberMeServices extends AbstractRememberMeServices {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private CustomJdbcTokenRepositoryImpl tokenRepository = new CustomJdbcTokenRepositoryImpl();
    private SecureRandom random;

    public static final int DEFAULT_SERIES_LENGTH = 16;
    public static final int DEFAULT_TOKEN_LENGTH = 16;

    private int seriesLength = DEFAULT_SERIES_LENGTH;
    private int tokenLength = DEFAULT_TOKEN_LENGTH;

    public CustomPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService);
        random = new SecureRandom();
        this.tokenRepository = (CustomJdbcTokenRepositoryImpl) tokenRepository; 
    }

    /** 
     * Locates the presented cookie data in the token repository, using the series id. If
     * the data compares successfully with that in the persistent store, a new token is
     * generated and stored with the same series. The corresponding cookie value is set on
     * the response.
     *
     * @param cookieTokens the series and token values
     *
     * @throws RememberMeAuthenticationException if there is no stored token corresponding
     * to the submitted cookie, or if the token in the persistent store has expired.
     * @throws InvalidCookieException if the cookie doesn't have two tokens as expected.
     * @throws CookieTheftException if a presented series value is found, but the stored
     * token is different from the one presented.
     */
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {

        if (cookieTokens.length != 3) {
            throw new InvalidCookieException("Cookie token did not contain " + 3 + " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("presentedSeries, presentedToken, ccd:"+Arrays.asList(cookieTokens));
        }

        final String presentedSeries = cookieTokens[0];
        final String presentedToken = cookieTokens[1];

        CustomPersistentRememberMeToken token = tokenRepository.getTokenForSeries(presentedSeries);

        if (token == null) {
            // No series match, so we can't authenticate using this cookie
            //throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
            cancelCookie(request, response);
            logger.debug("No persistent token found for series id: " + presentedSeries);
            return getUserDetailsService().loadUserByUsername("!■!");
        } else {
            // We have a match for this user/series combination
            if (!presentedToken.equals(token.getTokenValue())) {
                // Token doesn't match series value. Delete all logins for this user and throw
                // an exception to warn them.
                tokenRepository.removeUserTokens(token.getCcd(), token.getUsername());
    
                cancelCookie(request, response);
                logger.debug(messages.getMessage("PersistentTokenBasedRememberMeServices.cookieStolen","Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
                //throw new CookieTheftException(messages.getMessage("PersistentTokenBasedRememberMeServices.cookieStolen","Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
            } 
            
            if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System.currentTimeMillis()) {
                cancelCookie(request, response);
                logger.debug("Remember-me login has expired");
                //throw new RememberMeAuthenticationException("Remember-me login has expired");
            }
            
            // Token also matches, so login is valid. Update the token value, keeping the
            // *same* series number.
            if (logger.isDebugEnabled()) {
                logger.debug("Refreshing persistent login token for user '" + token.getUsername() + "', series '" + token.getSeries() + "', ccd '" + token.getCcd() + "'");
            }

            CustomPersistentRememberMeToken newToken = new CustomPersistentRememberMeToken(token.getCcd(), token.getUsername(), token.getSeries(), generateTokenData(), new Date());
    
            try {
                tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(), newToken.getDate());
                addCookie(newToken, request, response);
                if (logger.isDebugEnabled()) {
                    logger.debug("Refreshing persistent login token(New) for user '" + newToken.getUsername() + "', series '" + newToken.getSeries() + "', ccd '" + newToken.getCcd() + "'");
                }
                
            }
            catch (Exception e) {
                logger.error("Failed to update token: ", e);
                throw new RememberMeAuthenticationException("Autologin failed due to data access problem");
            }
        }

        return getUserDetailsService().loadUserByUsername(token.getCcd()+"■"+token.getUsername());
    }

    /**
     * Creates a new persistent login token with a new series number, stores the data in
     * the persistent token repository and adds the corresponding cookie to the response.
     *
     */
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String username = successfulAuthentication.getName();
        String ccd = request.getParameter("ccd");

        logger.debug("Creating new persistent login for user " + username+", ccd: "+ccd );

        CustomPersistentRememberMeToken persistentToken = new CustomPersistentRememberMeToken(ccd, username, generateSeriesData(), generateTokenData(), new Date());
        try {
            tokenRepository.createNewToken(persistentToken);
            addCookie(persistentToken, request, response);
        }
        catch (Exception e) {
            logger.error("Failed to save persistent token ", e);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        super.logout(request, response, authentication);
        
        String ccd = SecurityUtils.getUser().getCcd();
        
        if (authentication != null) {
            cancelCookie(request, response);
            tokenRepository.removeUserTokens(ccd, authentication.getName());
        }
    }

    protected String generateSeriesData() {
        byte[] newSeries = new byte[seriesLength];
        random.nextBytes(newSeries);
        return new String(Base64.encode(newSeries));
    }

    protected String generateTokenData() {
        byte[] newToken = new byte[tokenLength];
        random.nextBytes(newToken);
        return new String(Base64.encode(newToken));
    }

    private void addCookie(CustomPersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[] { token.getSeries(), token.getTokenValue(), token.getCcd() }, getTokenValiditySeconds(), request, response);
    }

    @Override
    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        Assert.isTrue(tokenValiditySeconds > 0,"tokenValiditySeconds must be positive for this implementation");
        super.setTokenValiditySeconds(tokenValiditySeconds);
    }    

}
