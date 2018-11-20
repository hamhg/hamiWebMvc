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
public interface MenuService {
    Map<String,Object> getTopMenuList01(Map<String, Object> paramMap) throws Exception;
    Map<String,Object> getLeftMenuList01(Map<String, Object> paramMap) throws Exception;
    Map<String,Object> getQuickMenuList01(Map<String, Object> paramMap) throws Exception;
}
