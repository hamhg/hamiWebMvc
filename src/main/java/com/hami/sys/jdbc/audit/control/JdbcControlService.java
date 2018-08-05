package com.hami.sys.jdbc.audit.control;

/**
 * <pre>
 * <li>Program Name : JdbcControlService
 * <li>Description  :
 * <li>History      : 2018. 2. 19.
 * </pre>
 *
 * @author HHG
 */
public interface JdbcControlService {
    public static final String JDBCCONTROL_BEANNAME = "jdbc.control";
    public static final String SQL_LOGGER_NAME = "jdbc.sql";
    public static final String RESULTSET_LOGGER_NAME = "jdbc.resultset";

    public abstract int getFetchSize();

    public abstract void setFetchSize(int i);

    public abstract int getMaxRows();

    public abstract void setMaxRows(int i);

    public abstract int getQueryTimeout();

    public abstract void setQueryTimeout(int i);
}
