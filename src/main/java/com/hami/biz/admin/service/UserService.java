package com.hami.biz.admin.service;

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
public interface UserService {
    Map<String,Object> getUserInfo(Map<String, Object> paramMap) throws Exception;
    Map<String,Object> updUserInfo(Map<String, Object> paramMap) throws Exception;
}
