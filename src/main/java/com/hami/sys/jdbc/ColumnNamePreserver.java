package com.hami.sys.jdbc;

import org.springframework.jdbc.core.ColumnMapRowMapper;

/**
 * <pre>
 * <li>Program Name : ColumnNamePreserver
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class ColumnNamePreserver extends ColumnMapRowMapper implements ColumnNameConvertor {

    public String convert(String columnName) {
        return columnName;
    }

    protected String getColumnKey(String columnName) {
        return convert(columnName);
    }
}
