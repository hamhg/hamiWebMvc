package com.hami.sys.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * <pre>
 * <li>Program Name : DefaultEmptyResultExceptionHandler
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class DefaultEmptyResultExceptionHandler implements EmptyResultExceptionHandler {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public Object handle(String sql, EmptyResultDataAccessException e) {
        log.warn((new StringBuilder()).append("Empty ResultData at queryForObject[").append(sql).append("]").toString());
        return null;
    }
}
