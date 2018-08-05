package com.hami.biz.common.service;

import com.hami.biz.exception.BizException;

import java.sql.SQLException;
import java.util.Map;

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
    Map<String,Object> doExcute(Map<String, Object> paramMap) throws SQLException, BizException;
}
