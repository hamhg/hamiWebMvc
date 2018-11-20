package com.hami.biz.common.service;

import com.hami.sys.annotation.BizAnnotation;
import com.hami.sys.exception.BizException;
import com.hami.sys.support.BizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hami.biz.common.dao.CommonCodeDao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    @BizAnnotation(id="CommonCodeByCd", description="공통코드")
    public Map<String, Object> getCommonCodeByCd(Map<String, Object> paramMap) throws SQLException, BizException {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("ds_result", commonCodeDao.search01(getSchParam(paramMap)));

        return resultMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    @BizAnnotation(id="CommonService", description="공통서비스")
    public Map<String, Object> getCommonService(Map<String, Object> paramMap) throws SQLException, BizException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("ds_result", commonCodeDao.search01(getSchParam(paramMap)));
        
        return resultMap;
    }

}
