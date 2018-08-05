package com.hami.biz.common.user.service;

import com.hami.biz.common.user.dao.UserDAO;
import com.hami.biz.common.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * <pre>
 * <li>Program Name : CustomUserDetailsService
 * <li>Description  :
 * <li>History      : 2018. 1. 8.
 * </pre>
 *
 * @author HHG
 */
@Service
@Transactional(readOnly = true)
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userInfo = userDAO.getUserInfo(username);

        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getAuthority());
        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(userInfo.getUserid(),
                userInfo.getPassword(), Arrays.asList(authority));

        return userDetails;
    }
}