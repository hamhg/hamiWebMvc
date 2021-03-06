package com.hami.biz.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hami.biz.common.dao.CommonCodeDao;
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
public class CommonServiceImpl extends BizService implements CommonService{

    @Autowired
    CommonCodeDao commonCodeDao;
    
    protected ObjectMapper mapper = new ObjectMapper();
    
    @Override
    @BizAnnotation(id="CommonCodeByCd", description="공통코드")
    public Map<String, Object> getCommonCodeByCd(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("ds_result", commonCodeDao.search01(getSchParam(paramMap)));

        return resultMap;
    }

    @Override
    @BizAnnotation(id="CommonService", description="공통서비스")
    public Map<String, Object> getCommonService(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("ds_result", commonCodeDao.search01(getSchParam(paramMap)));
        
        return resultMap;
    }
    
}
