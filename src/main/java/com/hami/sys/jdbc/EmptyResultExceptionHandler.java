package com.hami.sys.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * <pre>
 * <li>Program Name : EmptyResultExceptionHandler
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public interface EmptyResultExceptionHandler {
    public abstract Object handle(String s, EmptyResultDataAccessException emptyresultdataaccessexception);
}
