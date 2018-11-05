package com.hami.biz.system.login.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hami.sys.jdbc.sql.QueryLoader;
import com.hami.sys.util.StringUtils;

import org.springframework.context.ApplicationContextException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.hami.biz.system.login.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * <pre>
 * <li>Program Name : CustomUserDetailsManager
 * <li>Description  :
 * <li>History      : 2018. 1. 24.
 * </pre>
 *
 * @author HHG
 */
public class CustomUserDetailsService extends JdbcDaoSupport implements UserDetailsService, MessageSourceAware {


    // =====================================================================================
    // ~ Static fields/initializers

    // ~ Instance fields
    // ================================================================================================
    // =====================================================================================
    private QueryLoader queryLoader = QueryLoader.getInstance();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private String authoritiesByUsernameQuery;
    private String groupAuthoritiesByUsernameQuery;
    private String usersByUsernameQuery;
    private String rolePrefix = "";
    private boolean usernameBasedPrimaryKey = true;
    private boolean enableAuthorities = true;
    private boolean enableGroups;

    // ~ Constructors
    // ===================================================================================================

    public CustomUserDetailsService() {
        String path = this.getClass().getResource("").getPath();
        String filePath = path.replace("service", "dao") + "Auth.xml";
        this.usersByUsernameQuery = queryLoader.getElementWithPath(filePath,"DEF_USERS_BY_USERNAME_QUERY",null);
        this.authoritiesByUsernameQuery = queryLoader.getElementWithPath(filePath,"DEF_AUTHORITIES_BY_USERNAME_QUERY",null);
        this.groupAuthoritiesByUsernameQuery = queryLoader.getElementWithPath(filePath,"DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY",null);
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * @return the messages
     */
    protected MessageSourceAccessor getMessages() {
        return this.messages;
    }

    /**
     * Allows subclasses to add their own granted authorities to the list to be returned
     * in the <tt>UserDetails</tt>.
     *
     * @param username the username, for use by finder methods
     * @param authorities the current granted authorities, as populated from the
     * <code>authoritiesByUsername</code> mapping
     */
    protected void addCustomAuthorities(String username,
                                        List<GrantedAuthority> authorities) {
    }

    public String getUsersByUsernameQuery() {
        return this.usersByUsernameQuery;
    }

    @Override
    protected void initDao() throws ApplicationContextException {
        Assert.isTrue(this.enableAuthorities || this.enableGroups,
                "Use of either authorities or groups must be enabled");
    }

    @Override
    public User loadUserByUsername(String username)
            throws UsernameNotFoundException {
        List<UserDetails> users = loadUsersByUsername(username);

        if (users.size() == 0) {
            this.logger.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(
                    this.messages.getMessage("CustomUserDetailsService.notFound",
                            new Object[] { username }, "Username {0} not found"));
        }

        User user = (User) users.get(0); // contains no GrantedAuthority[]

        Map<String, Object> userInfo = user.getUserInfo();
        
        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        if (this.enableAuthorities) {
            dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        }

        if (this.enableGroups) {
            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
        }

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            this.logger.debug("User '" + username
                    + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(this.messages.getMessage(
                    "CustomUserDetailsService.noAuthority", new Object[] { username },
                    "User {0} has no GrantedAuthority"));
        }

        return createUserDetails(username, user, dbAuths, userInfo);
    }

    /**
     * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails
     * objects. There should normally only be one matching user.
     */
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(this.usersByUsernameQuery,
                new String[] { username }, new RowMapper<UserDetails>() {
                    @Override
                    public UserDetails mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        String ccd = rs.getString(1);
                        String username = rs.getString(2);
                        String password = rs.getString(3);
                        boolean enabled = rs.getBoolean(4);
                        
                        ResultSetMetaData meta = rs.getMetaData();
                        int colCount = meta.getColumnCount();
                        Map<String, Object> userInfo = new HashMap<String, Object>();
                        for (int col=1; col <= colCount; col++) 
                        {
                            if(!"PASSWORD".equals(meta.getColumnName(col)))
                            userInfo.put(meta.getColumnName(col), StringUtils.nvl(rs.getString(col),""));
                        }
                        
                        return new User(ccd, username, password, enabled, true, true, true,
                                AuthorityUtils.NO_AUTHORITIES, userInfo);
                    }

                });
    }

    /**
     * Loads authorities by executing the SQL from <tt>authoritiesByUsernameQuery</tt>.
     *
     * @return a list of GrantedAuthority objects for the user
     */
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return getJdbcTemplate().query(this.authoritiesByUsernameQuery,
                new String[] { username }, new RowMapper<GrantedAuthority>() {
                    @Override
                    public GrantedAuthority mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        String roleName = CustomUserDetailsService.this.rolePrefix + rs.getString(3);

                        return new SimpleGrantedAuthority(roleName);
                    }
                });
    }

    /**
     * Loads authorities by executing the SQL from
     * <tt>groupAuthoritiesByUsernameQuery</tt>.
     *
     * @return a list of GrantedAuthority objects for the user
     */
    protected List<GrantedAuthority> loadGroupAuthorities(String username) {
        return getJdbcTemplate().query(this.groupAuthoritiesByUsernameQuery,
                new String[] { username }, new RowMapper<GrantedAuthority>() {
                    @Override
                    public GrantedAuthority mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        String roleName = getRolePrefix() + rs.getString(3);

                        return new SimpleGrantedAuthority(roleName);
                    }
                });
    }

    /**
     * Can be overridden to customize the creation of the final UserDetailsObject which is
     * returned by the <tt>loadUserByUsername</tt> method.
     *
     * @param username the name originally passed to loadUserByUsername
     * @param userFromUserQuery the object returned from the execution of the
     * @param combinedAuthorities the combined array of authorities from all the authority
     * loading queries.
     * @return the final UserDetails which should be used in the system.
     */
    protected User createUserDetails(String username,
                                     User userFromUserQuery, List<GrantedAuthority> combinedAuthorities, Map<String, Object> userInfo) {
        String returnUsername = userFromUserQuery.getUsername();

        if (!this.usernameBasedPrimaryKey) {
            returnUsername = username;
        }

        return new User(userFromUserQuery.getCcd(), returnUsername, userFromUserQuery.getPassword(),
                userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities, userInfo);
    }

    /**
     * Allows the default query string used to retrieve authorities based on username to
     * be overridden, if default table or column names need to be changed. The default
     * query is {@link #DEF_AUTHORITIES_BY_USERNAME_QUERY}; when modifying this query,
     * ensure that all returned columns are mapped back to the same column names as in the
     * default query.
     *
     * @param queryString The SQL query string to set
     */
    public void setAuthoritiesByUsernameQuery(String queryString) {
        this.authoritiesByUsernameQuery = queryString;
    }

    protected String getAuthoritiesByUsernameQuery() {
        return this.authoritiesByUsernameQuery;
    }

    /**
     * Allows the default query string used to retrieve group authorities based on
     * username to be overridden, if default table or column names need to be changed. The
     * default query is {@link #DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY}; when modifying
     * this query, ensure that all returned columns are mapped back to the same column
     * names as in the default query.
     *
     * @param queryString The SQL query string to set
     */
    public void setGroupAuthoritiesByUsernameQuery(String queryString) {
        this.groupAuthoritiesByUsernameQuery = queryString;
    }

    /**
     * Allows a default role prefix to be specified. If this is set to a non-empty value,
     * then it is automatically prepended to any roles read in from the db. This may for
     * example be used to add the <tt>ROLE_</tt> prefix expected to exist in role names
     * (by default) by some other Spring Security classes, in the case that the prefix is
     * not already present in the db.
     *
     * @param rolePrefix the new prefix
     */
    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    protected String getRolePrefix() {
        return this.rolePrefix;
    }

    /**
     * If <code>true</code> (the default), indicates the
     * {@link #getUsersByUsernameQuery()} returns a username in response to a query. If
     * <code>false</code>, indicates that a primary key is used instead. If set to
     * <code>true</code>, the class will use the database-derived username in the returned
     * <code>UserDetails</code>. If <code>false</code>, the class will use the
     * {@link #loadUserByUsername(String)} derived username in the returned
     * <code>UserDetails</code>.
     *
     * @param usernameBasedPrimaryKey <code>true</code> if the mapping queries return the
     * username <code>String</code>, or <code>false</code> if the mapping returns a
     * database primary key.
     */
    public void setUsernameBasedPrimaryKey(boolean usernameBasedPrimaryKey) {
        this.usernameBasedPrimaryKey = usernameBasedPrimaryKey;
    }

    protected boolean isUsernameBasedPrimaryKey() {
        return this.usernameBasedPrimaryKey;
    }

    /**
     * Allows the default query string used to retrieve users based on username to be
     * overridden, if default table or column names need to be changed. The default query
     * is {@link #DEF_USERS_BY_USERNAME_QUERY}; when modifying this query, ensure that all
     * returned columns are mapped back to the same column names as in the default query.
     * If the 'enabled' column does not exist in the source database, a permanent true
     * value for this column may be returned by using a query similar to
     *
     * <pre>
     * &quot;select username,password,'true' as enabled from users where username = ?&quot;
     * </pre>
     *
     * @param usersByUsernameQueryString The query string to set
     */
    public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
        this.usersByUsernameQuery = usersByUsernameQueryString;
    }

    protected boolean getEnableAuthorities() {
        return this.enableAuthorities;
    }

    /**
     * Enables loading of authorities (roles) from the authorities table. Defaults to true
     */
    public void setEnableAuthorities(boolean enableAuthorities) {
        this.enableAuthorities = enableAuthorities;
    }

    protected boolean getEnableGroups() {
        return this.enableGroups;
    }

    /**
     * Enables support for group authorities. Defaults to false
     * @param enableGroups
     */
    public void setEnableGroups(boolean enableGroups) {
        this.enableGroups = enableGroups;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
