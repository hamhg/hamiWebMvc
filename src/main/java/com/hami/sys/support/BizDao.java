package com.hami.sys.support;

import com.hami.biz.exception.BizException;
import com.hami.sys.util.ContextUtil;
import com.hami.sys.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;

/**
 * <pre>
 * <li>Program Name : BizDao
 * <li>Description  :
 * <li>History      : 2018. 1. 20.
 * </pre>
 *
 * @author HHG
 */
public class BizDao extends DaoSupport {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public int queryForInt(Object obj, String sqlName, Map bindParams) throws BizException {
        Map commonBindParams = setCommonParams(bindParams);
        String sql = getSql(obj, sqlName, commonBindParams);
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValues(commonBindParams);
        return super.queryForInt(sql, sqlParams);
    }

    public long queryForLong(Object obj, String sqlName, Map bindParams) throws BizException {
        Map commonBindParams = setCommonParams(bindParams);
        String sql = getSql(obj, sqlName, commonBindParams);
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValues(commonBindParams);
        return super.queryForLong(sql, sqlParams);
    }

    public Map queryForMap(Object obj, String sqlName, Map bindParams) throws BizException {
        Map commonBindParams = setCommonParams(bindParams);
        String sql = getSql(obj, sqlName, commonBindParams);
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValues(commonBindParams);
        return (Map) super.queryForObject(sql, sqlParams, new ColumnMapRowMapper());
    }

    public List queryForList(Object obj, String sqlName, Map bindParams) throws BizException {
        Map commonBindParams = setCommonParams(bindParams);
        String sql = getSql(obj, sqlName, commonBindParams);
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValues(commonBindParams);
        return super.queryForList(sql, sqlParams, new ColumnMapRowMapper());
    }

    public int update(Object obj, String sqlName, Map bindParams) throws BizException {
        Map commonBindParams = setCommonParams(bindParams);
        String sql = getSql(obj, sqlName, commonBindParams);
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValues(commonBindParams);
        return super.update(sql, sqlParams);
    }

    public int updateByRequest(Object obj, String sqlName, Map bindParams) throws BizException {
        String sql = getSql(obj, sqlName, bindParams);
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValues(bindParams);
        return super.update(sql, sqlParams);
    }

    public int[] batchUpdate(Object obj, String sqlName, List listBindParams) throws BizException {
        String sql = getSql(obj, sqlName);
        SqlParameterSource sqlParams[] = new SqlParameterSource[listBindParams.size()];
        for (int i = 0; i < listBindParams.size(); i++)
            if (listBindParams.get(i) instanceof Map) {
                Map bindParams = (Map) listBindParams.get(i);
                MapSqlParameterSource mapParams = new MapSqlParameterSource();
                Map commonBindParams = setCommonParams(bindParams);
                mapParams.addValues(commonBindParams);
                sqlParams[i] = mapParams;
            } else {
                Object bindParams = listBindParams.get(i);
                sqlParams[i] = new BeanPropertySqlParameterSource(bindParams);
            }

        return super.batchUpdate(sql, sqlParams);
    }

    public int[] batchUpdateByRequest(Object obj, String sqlName, List listBindParams) throws BizException {
        String sql = getSql(obj, sqlName);
        SqlParameterSource sqlParams[] = new SqlParameterSource[listBindParams.size()];
        for (int i = 0; i < listBindParams.size(); i++)
            if (listBindParams.get(i) instanceof Map) {
                Map bindParams = (Map) listBindParams.get(i);
                MapSqlParameterSource mapParams = new MapSqlParameterSource();
                mapParams.addValues(bindParams);
                sqlParams[i] = mapParams;
            } else {
                Object bindParams = listBindParams.get(i);
                sqlParams[i] = new BeanPropertySqlParameterSource(bindParams);
            }

        return super.batchUpdate(sql, sqlParams);
    }

    public int batchUpdate2ByRequest(Object obj, String sqlName, List listBindParams) throws BizException {
        int arrCount[] = batchUpdateByRequest(obj, sqlName, listBindParams);
        int countSum = 0;
        for (int i = 0; i < arrCount.length; i++)
            if (arrCount[i] == -1 || arrCount[i] > 0)
                countSum += countSum;

        return countSum;
    }

    private Map setCommonParams(Map bindParams) {
        //log.debug("[BizDAO.setCommonParams] Start");
        Map commonBindParams = new HashMap();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        Calendar calendar = Calendar.getInstance(currentLocale);
        Date currDate = calendar.getTime();
        String wrkrNo = "";
        String corpCd = "";
        String headerLangCd = "";
        String regiIp = "";
        String persInfoMask = "";
        String maskAuth = "";
        if (ContextUtil.getTransactionHeader() != null) {
            wrkrNo = (String) ContextUtil.getTransactionHeaderValue("WRKR_NO");
            corpCd = (String) ContextUtil.getTransactionHeaderValue("CORP_CD");
            persInfoMask = (String) ContextUtil.getTransactionHeaderValue("PERS_INFO_MASK");
            maskAuth = (String) ContextUtil.getTransactionHeaderValue("MASK_AUTH");
        }
        if (ContextUtil.getSystemHeader() != null) {
            headerLangCd = (String) ContextUtil.getSystemHeaderValue("LANG_CD");
            regiIp = (String) ContextUtil.getSystemHeaderValue("STN_TMSG_IP");
        }
        if (bindParams == null) {
            if (!StringUtils.isNull(corpCd))
                commonBindParams.put("CORP_CD", corpCd);
            if (!StringUtils.isNull(regiIp))
                commonBindParams.put("REGI_IP", regiIp);
            if (!StringUtils.isNull(headerLangCd))
                commonBindParams.put("HEADER_LANG_CD", headerLangCd);
            if (!StringUtils.isNull(wrkrNo)) {
                commonBindParams.put("CRATR_EMP_NO", wrkrNo);
                commonBindParams.put("CHNGR_EMP_NO", wrkrNo);
            }
            commonBindParams.put("CRAT_DS", currDate);
            commonBindParams.put("CHNG_DS", currDate);
            commonBindParams.put("HEADER_PERS_INFO_MASK", persInfoMask);
            commonBindParams.put("HEADER_MASK_AUTH", maskAuth);
        } else {
            commonBindParams.putAll(bindParams);
            if (!StringUtils.isNull(corpCd))
                commonBindParams.put("CORP_CD", corpCd);
            if (!StringUtils.isNull(regiIp))
                commonBindParams.put("REGI_IP", regiIp);
            if (!StringUtils.isNull(headerLangCd))
                commonBindParams.put("HEADER_LANG_CD", headerLangCd);
            if (!StringUtils.isNull(wrkrNo)) {
                commonBindParams.put("CRATR_EMP_NO", wrkrNo);
                commonBindParams.put("CHNGR_EMP_NO", wrkrNo);
            }
            commonBindParams.put("CRAT_DS", currDate);
            commonBindParams.put("CHNG_DS", currDate);
            commonBindParams.put("HEADER_PERS_INFO_MASK", persInfoMask);
            commonBindParams.put("HEADER_MASK_AUTH", maskAuth);
        }
        log.debug(" * CommonParams : "+commonBindParams.toString());
        //log.debug("[BizDAO.setCommonParams] End");
        return commonBindParams;
    }
}
