package com.hami.biz.system.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hami.biz.system.login.dao.UserDAO;
import com.hami.biz.system.login.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	List<Map> userInfo = new ArrayList<Map>();
    	Map<String,Object> param = new HashMap();
    	userInfo = userDAO.getUserInfo(param);
         
        User userInfo = BindMap

        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getAuthority());
        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(userInfo.getUserid(),
                userInfo.getPassword(), Arrays.asList(authority));

        return userDetails;
    }
}