package com.hami.biz.login.service;

import com.hami.sys.jdbc.sql.QueryLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
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
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
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
public class CustomUserDetailsManager extends CustomUserDetailsService implements UserDetailsManager,
        GroupManager {
    
    // ~ Instance fields
    // ================================================================================================
    private QueryLoader queryLoader = QueryLoader.getInstance();
    protected final Log logger = LogFactory.getLog(getClass());

    String path = this.getClass().getResource("").getPath();
    String filePath = path.replace("service", "dao") + "Auth.xml";
    // UserDetailsManager SQL
    private String createUserSql = queryLoader.getElementWithPath(filePath,"DEF_CREATE_USER_SQL",null);
    private String deleteUserSql = queryLoader.getElementWithPath(filePath,"DEF_DELETE_USER_SQL",null);
    private String updateUserSql = queryLoader.getElementWithPath(filePath,"DEF_UPDATE_USER_SQL",null);
    private String createAuthoritySql = queryLoader.getElementWithPath(filePath,"DEF_INSERT_AUTHORITY_SQL",null);
    private String deleteUserAuthoritiesSql = queryLoader.getElementWithPath(filePath,"DEF_DELETE_USER_AUTHORITIES_SQL",null);
    private String userExistsSql = queryLoader.getElementWithPath(filePath,"DEF_USER_EXISTS_SQL",null);
    private String changePasswordSql = queryLoader.getElementWithPath(filePath,"DEF_CHANGE_PASSWORD_SQL",null);

    // GroupManager SQL
    private String findAllGroupsSql = queryLoader.getElementWithPath(filePath,"DEF_FIND_GROUPS_SQL",null);
    private String findUsersInGroupSql = queryLoader.getElementWithPath(filePath,"DEF_FIND_USERS_IN_GROUP_SQL",null);
    private String insertGroupSql = queryLoader.getElementWithPath(filePath,"DEF_INSERT_GROUP_SQL",null);
    private String findGroupIdSql = queryLoader.getElementWithPath(filePath,"DEF_FIND_GROUP_ID_SQL",null);
    private String insertGroupAuthoritySql = queryLoader.getElementWithPath(filePath,"DEF_INSERT_GROUP_AUTHORITY_SQL",null);
    private String deleteGroupSql = queryLoader.getElementWithPath(filePath,"DEF_DELETE_GROUP_SQL",null);
    private String deleteGroupAuthoritiesSql = queryLoader.getElementWithPath(filePath,"DEF_DELETE_GROUP_AUTHORITIES_SQL",null);
    private String deleteGroupMembersSql = queryLoader.getElementWithPath(filePath,"DEF_DELETE_GROUP_MEMBERS_SQL",null);
    private String renameGroupSql = queryLoader.getElementWithPath(filePath,"DEF_RENAME_GROUP_SQL",null);
    private String insertGroupMemberSql = queryLoader.getElementWithPath(filePath,"DEF_INSERT_GROUP_MEMBER_SQL",null);
    private String deleteGroupMemberSql = queryLoader.getElementWithPath(filePath,"DEF_DELETE_GROUP_MEMBER_SQL",null);
    private String groupAuthoritiesSql = queryLoader.getElementWithPath(filePath,"DEF_GROUP_AUTHORITIES_QUERY_SQL",null);
    private String deleteGroupAuthoritySql = queryLoader.getElementWithPath(filePath,"DEF_DELETE_GROUP_AUTHORITY_SQL",null);

    private AuthenticationManager authenticationManager;

    private UserCache userCache = new NullUserCache();

    // ~ Methods
    // ========================================================================================================
    protected void initDao() throws ApplicationContextException {
        if (authenticationManager == null) {
            logger.info("No authentication manager set. Reauthentication of users when changing passwords will not be performed.");
        }
        logger.info("============ CustomUserDetailsManager ==================");
        super.initDao();
    }

    // ~ UserDetailsManager implementation
    // ==============================================================================
    public void createUser(final UserDetails user) {
        validateUserDetails(user);
        getJdbcTemplate().update(createUserSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setBoolean(3, user.isEnabled());
            }

        });

        if (getEnableAuthorities()) {
            insertUserAuthorities(user);
        }
    }

    public void updateUser(final UserDetails user) {
        validateUserDetails(user);
        getJdbcTemplate().update(updateUserSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getPassword());
                ps.setBoolean(2, user.isEnabled());
                ps.setString(3, user.getUsername());
            }
        });

        if (getEnableAuthorities()) {
            deleteUserAuthorities(user.getUsername());
            insertUserAuthorities(user);
        }

        userCache.removeUserFromCache(user.getUsername());
    }

    private void insertUserAuthorities(UserDetails user) {
        for (GrantedAuthority auth : user.getAuthorities()) {
            getJdbcTemplate().update(createAuthoritySql, user.getUsername(),
                    auth.getAuthority());
        }
    }

    public void deleteUser(String userid) {
        if (getEnableAuthorities()) {
            deleteUserAuthorities(userid);
        }
        getJdbcTemplate().update(deleteUserSql, userid);
        userCache.removeUserFromCache(userid);
    }

    private void deleteUserAuthorities(String userid) {
        getJdbcTemplate().update(deleteUserAuthoritiesSql, userid);
    }

    public void changePassword(String oldPassword, String newPassword)
            throws AuthenticationException {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("Can't change password as no Authentication object found in context for current user.");
        }

        String userid = currentUser.getName();

        // If an authentication manager has been set, re-authenticate the user with the
        // supplied password.
        if (authenticationManager != null) {
            logger.debug("Reauthenticating user '" + userid + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userid, oldPassword));
        }
        else {
            logger.debug("No authentication manager set. Password won't be re-checked.");
        }

        logger.debug("Changing password for user '" + userid + "'");

        getJdbcTemplate().update(changePasswordSql, newPassword, userid);

        SecurityContextHolder.getContext().setAuthentication(
                createNewAuthentication(currentUser, newPassword));

        userCache.removeUserFromCache(userid);
    }

    protected Authentication createNewAuthentication(Authentication currentAuth,
                                                     String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }

    public boolean userExists(String userid) {
        List<String> users = getJdbcTemplate().queryForList(userExistsSql,
                new String[] { userid }, String.class);

        if (users.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(
                    "More than one user found with name '" + userid + "'", 1);
        }

        return users.size() == 1;
    }

    // ~ GroupManager implementation
    // ====================================================================================
    public List<String> findAllGroups() {
        return getJdbcTemplate().queryForList(findAllGroupsSql, String.class);
    }

    public List<String> findUsersInGroup(String groupName) {
        Assert.hasText(groupName, "groupName should have text");
        return getJdbcTemplate().queryForList(findUsersInGroupSql,
                new String[] { groupName }, String.class);
    }

    public void createGroup(final String groupName,
                            final List<GrantedAuthority> authorities) {
        Assert.hasText(groupName, "groupName should have text");
        Assert.notNull(authorities, "authorities cannot be null");

        logger.debug("Creating new group '" + groupName + "' with authorities "
                + AuthorityUtils.authorityListToSet(authorities));

        getJdbcTemplate().update(insertGroupSql, groupName);

        final int groupId = findGroupId(groupName);

        for (GrantedAuthority a : authorities) {
            final String authority = a.getAuthority();
            getJdbcTemplate().update(insertGroupAuthoritySql,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ps.setInt(1, groupId);
                            ps.setString(2, authority);
                        }
                    });
        }
    }

    public void deleteGroup(String groupName) {
        logger.debug("Deleting group '" + groupName + "'");
        Assert.hasText(groupName, "groupName should have text");

        final int id = findGroupId(groupName);
        PreparedStatementSetter groupIdPSS = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        };
        getJdbcTemplate().update(deleteGroupMembersSql, groupIdPSS);
        getJdbcTemplate().update(deleteGroupAuthoritiesSql, groupIdPSS);
        getJdbcTemplate().update(deleteGroupSql, groupIdPSS);
    }

    public void renameGroup(String oldName, String newName) {
        logger.debug("Changing group name from '" + oldName + "' to '" + newName + "'");
        Assert.hasText(oldName,"oldName should have text");
        Assert.hasText(newName,"newName should have text");

        getJdbcTemplate().update(renameGroupSql, newName, oldName);
    }

    public void addUserToGroup(final String userid, final String groupName) {
        logger.debug("Adding user '" + userid + "' to group '" + groupName + "'");
        Assert.hasText(userid,"userid should have text");
        Assert.hasText(groupName,"groupName should have text");

        final int id = findGroupId(groupName);
        getJdbcTemplate().update(insertGroupMemberSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, userid);
            }
        });

        userCache.removeUserFromCache(userid);
    }

    public void removeUserFromGroup(final String userid, final String groupName) {
        logger.debug("Removing user '" + userid + "' to group '" + groupName + "'");
        Assert.hasText(userid,"userid should have text");
        Assert.hasText(groupName,"groupName should have text");

        final int id = findGroupId(groupName);

        getJdbcTemplate().update(deleteGroupMemberSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, userid);
            }
        });

        userCache.removeUserFromCache(userid);
    }

    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        logger.debug("Loading authorities for group '" + groupName + "'");
        Assert.hasText(groupName,"groupName should have text");;

        return getJdbcTemplate().query(groupAuthoritiesSql, new String[] { groupName },
                new RowMapper<GrantedAuthority>() {
                    public GrantedAuthority mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        String roleName = getRolePrefix() + rs.getString(3);

                        return new SimpleGrantedAuthority(roleName);
                    }
                });
    }

    public void removeGroupAuthority(String groupName, final GrantedAuthority authority) {
        logger.debug("Removing authority '" + authority + "' from group '" + groupName + "'");
        Assert.hasText(groupName,"groupName should have text");
        Assert.notNull(authority, "authority cannot be null");

        final int id = findGroupId(groupName);

        getJdbcTemplate().update(deleteGroupAuthoritySql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, authority.getAuthority());
            }
        });
    }

    public void addGroupAuthority(final String groupName, final GrantedAuthority authority) {
        logger.debug("Adding authority '" + authority + "' to group '" + groupName + "'");
        Assert.hasText(groupName,"groupName should have text");;
        Assert.notNull(authority, "authority cannot be null");

        final int id = findGroupId(groupName);
        getJdbcTemplate().update(insertGroupAuthoritySql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, authority.getAuthority());
            }
        });
    }

    private int findGroupId(String group) {
        return getJdbcTemplate().queryForObject(findGroupIdSql, Integer.class, group);
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setCreateUserSql(String createUserSql) {
        Assert.hasText(createUserSql,"createUserSql should have text");;
        this.createUserSql = createUserSql;
    }

    public void setDeleteUserSql(String deleteUserSql) {
        Assert.hasText(deleteUserSql,"deleteUserSql should have text");;
        this.deleteUserSql = deleteUserSql;
    }

    public void setUpdateUserSql(String updateUserSql) {
        Assert.hasText(updateUserSql,"updateUserSql should have text");;
        this.updateUserSql = updateUserSql;
    }

    public void setCreateAuthoritySql(String createAuthoritySql) {
        Assert.hasText(createAuthoritySql,"createAuthoritySql should have text");;
        this.createAuthoritySql = createAuthoritySql;
    }

    public void setDeleteUserAuthoritiesSql(String deleteUserAuthoritiesSql) {
        Assert.hasText(deleteUserAuthoritiesSql,"deleteUserAuthoritiesSql should have text");;
        this.deleteUserAuthoritiesSql = deleteUserAuthoritiesSql;
    }

    public void setUserExistsSql(String userExistsSql) {
        Assert.hasText(userExistsSql,"userExistsSql should have text");;
        this.userExistsSql = userExistsSql;
    }

    public void setChangePasswordSql(String changePasswordSql) {
        Assert.hasText(changePasswordSql,"changePasswordSql should have text");;
        this.changePasswordSql = changePasswordSql;
    }

    public void setFindAllGroupsSql(String findAllGroupsSql) {
        Assert.hasText(findAllGroupsSql,"findAllGroupsSql should have text");;
        this.findAllGroupsSql = findAllGroupsSql;
    }

    public void setFindUsersInGroupSql(String findUsersInGroupSql) {
        Assert.hasText(findUsersInGroupSql,"findUsersInGroupSql should have text");;
        this.findUsersInGroupSql = findUsersInGroupSql;
    }

    public void setInsertGroupSql(String insertGroupSql) {
        Assert.hasText(insertGroupSql,"insertGroupSql should have text");;
        this.insertGroupSql = insertGroupSql;
    }

    public void setFindGroupIdSql(String findGroupIdSql) {
        Assert.hasText(findGroupIdSql,"findGroupIdSql should have text");;
        this.findGroupIdSql = findGroupIdSql;
    }

    public void setInsertGroupAuthoritySql(String insertGroupAuthoritySql) {
        Assert.hasText(insertGroupAuthoritySql,"insertGroupAuthoritySql should have text");;
        this.insertGroupAuthoritySql = insertGroupAuthoritySql;
    }

    public void setDeleteGroupSql(String deleteGroupSql) {
        Assert.hasText(deleteGroupSql,"deleteGroupSql should have text");;
        this.deleteGroupSql = deleteGroupSql;
    }

    public void setDeleteGroupAuthoritiesSql(String deleteGroupAuthoritiesSql) {
        Assert.hasText(deleteGroupAuthoritiesSql,"deleteGroupAuthoritiesSql should have text");;
        this.deleteGroupAuthoritiesSql = deleteGroupAuthoritiesSql;
    }

    public void setDeleteGroupMembersSql(String deleteGroupMembersSql) {
        Assert.hasText(deleteGroupMembersSql,"deleteGroupMembersSql should have text");;
        this.deleteGroupMembersSql = deleteGroupMembersSql;
    }

    public void setRenameGroupSql(String renameGroupSql) {
        Assert.hasText(renameGroupSql,"renameGroupSql should have text");;
        this.renameGroupSql = renameGroupSql;
    }

    public void setInsertGroupMemberSql(String insertGroupMemberSql) {
        Assert.hasText(insertGroupMemberSql,"insertGroupMemberSql should have text");;
        this.insertGroupMemberSql = insertGroupMemberSql;
    }

    public void setDeleteGroupMemberSql(String deleteGroupMemberSql) {
        Assert.hasText(deleteGroupMemberSql,"deleteGroupMemberSql should have text");;
        this.deleteGroupMemberSql = deleteGroupMemberSql;
    }

    public void setGroupAuthoritiesSql(String groupAuthoritiesSql) {
        Assert.hasText(groupAuthoritiesSql,"groupAuthoritiesSql should have text");;
        this.groupAuthoritiesSql = groupAuthoritiesSql;
    }

    public void setDeleteGroupAuthoritySql(String deleteGroupAuthoritySql) {
        Assert.hasText(deleteGroupAuthoritySql,"deleteGroupAuthoritySql should have text");;
        this.deleteGroupAuthoritySql = deleteGroupAuthoritySql;
    }

    /**
     * Optionally sets the UserCache if one is in use in the application. This allows the
     * user to be removed from the cache after updates have taken place to avoid stale
     * data.
     *
     * @param userCache the cache used by the AuthenticationManager.
     */
    public void setUserCache(UserCache userCache) {
        Assert.notNull(userCache, "userCache cannot be null");
        this.userCache = userCache;
    }

    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "userid may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }
}
