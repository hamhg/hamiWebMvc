package com.hami.sys.jdbc;

/**
 * <pre>
 * <li>Program Name : ColumnTypeConvertor
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public interface ColumnTypeConvertor {
    public abstract Class convert(int i, int j, int k);
}
