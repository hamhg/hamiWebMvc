package com.hami.sys.jdbc;

import com.hami.sys.util.StringUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;

/**
 * <pre>
 * <li>Program Name : ColumnNameCamelizer
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class ColumnNameCamelizer extends ColumnMapRowMapper implements ColumnNameConvertor {

    public String convert(String columnName) {
        String fieldName = StringUtils.camelize(columnName);
        return fieldName;
    }

    protected String getColumnKey(String columnName) {
        return convert(columnName);
    }
}
