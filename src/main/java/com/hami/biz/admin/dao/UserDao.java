package com.hami.biz.admin.dao;

import com.hami.biz.system.utils.SecurityUtils;
import com.hami.sys.exception.BizException;
import com.hami.sys.support.BizDao;

import freemarker.template.utility.SecurityUtilities;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : CommonCodeImpl
 * <li>Description  :
 * <li>History      : 2018. 1. 1.
 * </pre>
 *
 * @author HHG
 */
@Repository
public class UserDao extends BizDao {

    @SuppressWarnings("unchecked")
    public List<Map> search01(Map paramMap) throws Exception {
        return super.queryForList(this, "search01", paramMap);
    }

    @SuppressWarnings("unchecked")
    public List<Map> save01(Map<String,Object>paramMap) throws Exception {
        
        List<Map> resultList = new ArrayList<Map>();
        List<Map> saveRows = new ArrayList<Map>();
        String wkUser = SecurityUtils.getUser().getUsername();
        
        saveRows = (List<Map>) paramMap.get("ds_save");
        
        for(Map<String, Object> row: saveRows){
            
            cud.setTable("TSYAU0001");
            cud.addKey("C_CD",         (String)row.get("C_CD"));
            cud.addKey("LOGIN_ID",     (String)row.get("LOGIN_ID"));
            cud.addKey("USER_ID",      (String)row.get("USER_ID"));
            cud.addField("USER_NM",    (String)row.get("USER_NM"));
            cud.addField("STA_YMD",    (String)row.get("STA_YMD"));
            cud.addField("END_YMD",    (String)row.get("END_YMD"));
            cud.addField("PWD_U_DATE", (String)row.get("PWD_U_DATE"));
            cud.addField("ENABLED",    (String)row.get("ENABLED"));
            cud.addField("LOGIN_DATE", (String)row.get("LOGIN_DATE"));
            cud.addField("LOGIN_IP",   (String)row.get("LOGIN_IP"));
            cud.addField("ERR_CNT",    (String)row.get("ERR_CNT"));
            cud.addField("SKIN_CD",    (String)row.get("SKIN_CD"));
            cud.addField("LOCK_YN",    (String)row.get("LOCK_YN"));
            cud.addField("LOCK_DATE",  (String)row.get("LOCK_DATE"));
            cud.addField("SA_YN",      (String)row.get("SA_YN"));
            cud.addField("USER_CD",    (String)row.get("USER_CD"));
            cud.addField("NOTE",       (String)row.get("NOTE"));
            cud.addField("WK_MENU_ID", "SYSTEM");
            cud.addField("MOD_USER_ID", wkUser);
            cud.addFieldRaw("MOD_DATE", "SYSDATE");
            
            if ("C".equals((String)row.get("_CUD")) ) {
                cud.addField("INS_USER_ID", wkUser);
                cud.addFieldRaw("INS_DATE", "SYSDATE");
                cud.insert();
            } else if ("U".equals((String)row.get("_CUD"))){
                cud.update();
            } else if ("D".equals((String)row.get("_CUD"))){
                cud.delete();
            }
        }    

        return resultList;
    }

}
