package com.hami.sys.jdbc.audit.control;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : JdbcControlServiceImpl
 * <li>Description  :
 * <li>History      : 2018. 2. 19.
 * </pre>
 *
 * @author HHG
 */
public @Data class JdbcControlServiceImpl implements JdbcControlService {

    private int fetchSize;
    private int maxRows;
    private int queryTimeout;

    public JdbcControlServiceImpl() {
        fetchSize = 0;
        maxRows = 0;
        queryTimeout = 0;
    }
}
