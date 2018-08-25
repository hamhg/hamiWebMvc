package com.hami.biz.system.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.hami.sys.exception.BizException;

import java.sql.SQLException;
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
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    public final Logger log = LoggerFactory.getLogger(getClass());
    
    @SuppressWarnings("unchecked")
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	List<Map> userList = new ArrayList<Map>();
    	Map<String,Object> paramMap = new HashMap();
    	paramMap.put("USERNAME", username);
    	try {
    		log.debug("paramMap:"+paramMap.toString());
    		userList = userDAO.getUserInfo(paramMap);
			log.debug("userList:"+userList.toString());
			
		} catch (BizException | SQLException e) {
			log.debug(e.getStackTrace().toString());
		}
         
        User userInfoVo = new User();
        BeanUtils.copyProperties(userList, userInfoVo);

        GrantedAuthority authority = new SimpleGrantedAuthority(userInfoVo.getAuthority());
        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(userInfoVo.getUsername(),
        		userInfoVo.getPassword(), Arrays.asList(authority));

        return userDetails;
    }
    
    private UserDetails userInfo(User user) {
        GrantedAuthority authorities = new SimpleGrantedAuthority(user.getAuthority());
        User userDetails = new User();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setEnabled(user.isEnabled());
        userDetails.setAuthority(user.getAuthority());
        userDetails.setAuthorities(authorities);
        return userDetails;
    }
}