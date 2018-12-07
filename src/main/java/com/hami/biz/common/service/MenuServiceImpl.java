package com.hami.biz.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hami.biz.common.dao.MenuDao;
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
public class MenuServiceImpl extends BizService implements MenuService{

    @Autowired
    MenuDao menuDao;

    @Override
    @BizAnnotation(id="getTopMenuList01", description="상단메뉴조회")
    public Map<String, Object> getTopMenuList01(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("ds_result", menuDao.getTopMenu01(getSchParam(paramMap)));

        return resultMap;
    }

    @Override
    @BizAnnotation(id="getLeftMenuList01", description="메뉴조회")
    public Map<String, Object> getLeftMenuList01(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("ds_result", menuDao.getLeftMenu01(getSchParam(paramMap)));
        
        return resultMap;
    }
    
    @Override
    @BizAnnotation(id="getLocationMenuList01", description="로케이션조회")
    public Map<String, Object> getLocationMenuList01(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("ds_result", menuDao.getLocationMenuList01(getSchParam(paramMap)));
        
        return resultMap;
    }
    
    @Override
    @BizAnnotation(id="getProgram01", description="프로그램조회")
    public Map<String, Object> getProgram01(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("ds_result", menuDao.getProgram01(getSchParam(paramMap)));
        
        return resultMap;
    }
    
    @Override
    @BizAnnotation(id="getQuickMenuList01", description="퀵메뉴조회")
    public Map<String, Object> getQuickMenuList01(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("ds_result", menuDao.getQuickMenu01(getSchParam(paramMap)));
        
        return resultMap;
    }

}
