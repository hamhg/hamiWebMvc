package com.hami.biz.login.service;

import com.hami.sys.jdbc.sql.QueryLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * <li>Program Name : CustomUserDetailsManager
 * <li>Description  :
 * <li>History      : 2018. 1. 24.
 * </pre>
 *
 * @author HHG
 */
public class CustomJdbcTokenRepositoryImpl extends JdbcDaoSupport implements PersistentTokenRepository {
    // ~ Instance fields
    // ================================================================================================
    private QueryLoader queryLoader = QueryLoader.getInstance();
    protected final Log logger = LogFactory.getLog(getClass());

    String path = this.getClass().getResource("").getPath();
    String filePath = path.replace("service", "dao") + "Token.xml";
    
    private String createTableSql = queryLoader.getElementWithPath(filePath,"CREATE_TABLE_SQL",null);
    private String tokensBySeriesSql = queryLoader.getElementWithPath(filePath,"DEF_TOKEN_BY_SERIES_SQL",null);
    private String insertTokenSql = queryLoader.getElementWithPath(filePath,"DEF_INSERT_TOKEN_SQL",null);
    private String updateTokenSql = queryLoader.getElementWithPath(filePath,"DEF_UPDATE_TOKEN_SQL",null);
    private String removeUserTokensSql = queryLoader.getElementWithPath(filePath,"DEF_REMOVE_USER_TOKENS_SQL",null);
    private boolean createTableOnStartup;

    protected void initDao() {
        if (createTableOnStartup) {
            getJdbcTemplate().execute(createTableSql);
        }
    }

    public void createNewToken(PersistentRememberMeToken token) {
        getJdbcTemplate().update(insertTokenSql, token.getUsername(), token.getSeries(),
                token.getTokenValue(), token.getDate());
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {
        getJdbcTemplate().update(updateTokenSql, tokenValue, lastUsed, series);
    }

    /**
     * Loads the token data for the supplied series identifier.
     *
     * If an error occurs, it will be reported and null will be returned (since the result
     * should just be a failed persistent login).
     *
     * @param seriesId
     * @return the token matching the series, or null if no match found or an exception
     * occurred.
     */
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return getJdbcTemplate().queryForObject(tokensBySeriesSql,
                    new RowMapper<PersistentRememberMeToken>() {
                        public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return new PersistentRememberMeToken(rs.getString(1), rs
                                    .getString(2), rs.getString(3), rs.getTimestamp(4));
                        }
                    }, seriesId);
        }
        catch (EmptyResultDataAccessException zeroResults) {
            if (logger.isDebugEnabled()) {
                logger.debug("Querying token for series '" + seriesId
                        + "' returned no results.", zeroResults);
            }
        }
        catch (IncorrectResultSizeDataAccessException moreThanOne) {
            logger.error("Querying token for series '" + seriesId
                    + "' returned more than one value. Series" + " should be unique");
        }
        catch (DataAccessException e) {
            logger.error("Failed to load token for series " + seriesId, e);
        }

        return null;
    }

    public void removeUserTokens(String username) {
        getJdbcTemplate().update(removeUserTokensSql, username);
    }

    /**
     * Intended for convenience in debugging. Will create the persistent_tokens database
     * table when the class is initialized during the initDao method.
     *
     * @param createTableOnStartup set to true to execute the
     */
    public void setCreateTableOnStartup(boolean createTableOnStartup) {
        this.createTableOnStartup = createTableOnStartup;
    }
    
}
