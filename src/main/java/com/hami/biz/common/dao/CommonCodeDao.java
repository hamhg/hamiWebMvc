package com.hami.biz.common.dao;

import com.hami.sys.exception.BizException;
import com.hami.sys.support.BizDao;

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
public class CommonCodeDao extends BizDao {

    public List search01(Map paramMap) throws Exception {
        return super.queryForList(this, "search01", paramMap);
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
