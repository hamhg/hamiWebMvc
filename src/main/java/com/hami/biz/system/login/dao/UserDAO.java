package com.hami.biz.system.login.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	public final Logger log = LoggerFactory.getLogger(getClass());
	
    public List getUserInfo(Map paramMap) throws SQLException, BizException {
    	log.debug("=== UserDao ===");
    	return super.queryForList(this, "search01", paramMap);
    }    
}