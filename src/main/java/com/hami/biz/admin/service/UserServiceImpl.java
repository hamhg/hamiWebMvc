package com.hami.biz.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hami.biz.admin.dao.UserDao;
import com.hami.sys.annotation.BizAnnotation;
import com.hami.sys.support.BizService;

/**
 * <pre>
 * <li>Program Name : CommonServiceImpl
 * <li>Description  :
 * <li>History      : 2018. 1. 12.
 * </pre>
 *
 * @author HHG
 */
@Service
public class UserServiceImpl extends BizService implements UserService{

    @Autowired
    UserDao userDao;
    
    protected ObjectMapper mapper = new ObjectMapper();
    
    @Override
    @BizAnnotation(id="getUserInfo", description="로그인정보 조회")
    public Map<String,Object> getUserInfo(Map<String, Object> paramMap) throws Exception {
        Map<String,Object> resultMap = new HashMap<String, Object>();

        resultMap.put("ds_result", userDao.search01(getSchParam(paramMap)));

        return resultMap;
    }
    
    @Override
    @BizAnnotation(id="updUserInfo", description="로그인정보 저장")
    public Map<String,Object> updUserInfo(Map<String, Object> paramMap) throws Exception {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("ds_result", userDao.save01(paramMap));
        
        return resultMap;
    }
    
}
