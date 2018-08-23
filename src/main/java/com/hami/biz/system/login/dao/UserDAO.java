package com.hami.biz.system.login.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hami.sys.exception.BizException;
import com.hami.sys.support.BizDao;
/**
 * <pre>
 * <li>Program Name : UserDAO
 * <li>Description  :
 * <li>History      : 2018. 1. 8.
 * </pre>
 *
 * @author HHG
 */
@Repository
public class UserDAO extends BizDao {
    public List getUserInfo(Map paramMap) throws SQLException, BizException {
    	return super.queryForList(this, "search01", paramMap);
    }    
}