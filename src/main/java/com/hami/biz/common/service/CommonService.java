package com.hami.biz.common.service;

import java.sql.SQLException;
import java.util.Map;

import com.hami.sys.exception.BizException;

/**
 * <pre>
 * <li>Program Name : CommonService
 * <li>Description  :
 * <li>History      : 2018. 1. 12.
 * </pre>
 *
 * @author HHG
 */
public interface CommonService {
    Map<String,Object> commonCodeByCd(Map<String, Object> paramMap) throws SQLException, BizException;
    Map<String,Object> commonService(Map<String, Object> paramMap) throws SQLException, BizException;
}
