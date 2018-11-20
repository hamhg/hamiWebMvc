package com.hami.biz.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hami.sys.support.BizDao;

/**
 * <pre>
 * <li>Program Name : MenuDaoImpl
 * <li>Description  :
 * <li>History      : 2018. 1. 1.
 * </pre>
 *
 * @author HHG
 */
@Repository
public class MenuDao extends BizDao {

    public List getTopMenu01(Map paramMap) throws Exception {
        return super.queryForList(this, "getTopMenu01", paramMap);
    }

    public List getLeftMenu01(Map paramMap) throws Exception {
        return super.queryForList(this, "getLeftMenu01", paramMap);
    }
    
    public List getLocationMenuList01(Map paramMap) throws Exception {
        return super.queryForList(this, "getLocationMenuList01", paramMap);
    }
    
    public List getQuickMenu01(Map paramMap) throws Exception {
        return super.queryForList(this, "getQuickMenu01", paramMap);
    }

    public List<Map> save01(Map<String,Object>paramMap) throws Exception {
        List<Map> resultList = new ArrayList<Map>();

        cud.setTable("SY9110");
        cud.addKey("C_CD", (String)paramMap.get("C_CD"));
        cud.addKey("MAIL_NO", (String)paramMap.get("S_MAIL_NO"));
        cud.addField("MAIL_TXT", (String)paramMap.get("S_MAIL_TXT"));
        cud.addField("MOD_USER_ID", (String)paramMap.get("USER_ID"));
        cud.addFieldRaw("MOD_YMDHMS", "SYSDATE");
        if ( cud.update() == 0 ) cud.insert();

        return resultList;
    }

}
