package com.hami.biz.common.service;

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
    Map<String,Object> getCommonCodeByCd(Map<String, Object> paramMap) throws Exception;
    Map<String,Object> getCommonService(Map<String, Object> paramMap) throws Exception;
}
