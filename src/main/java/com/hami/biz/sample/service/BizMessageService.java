package com.hami.biz.sample.service;

import java.util.Map;

/**
 * <pre>
 * <li>Program Name : BizMessageService
 * <li>Description  :
 * <li>History      : 2018. 2. 13.
 * </pre>
 *
 * @author HHG
 */
public interface BizMessageService {
    Map<String,Object> selectMessages(Map<String, Object> paramMap) throws Exception;
    Map<String,Object> selectMsg(Map<String, Object> paramMap) throws Exception;
}
