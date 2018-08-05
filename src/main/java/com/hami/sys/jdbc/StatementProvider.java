package com.hami.sys.jdbc;

import com.hami.sys.jdbc.sql.QueryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * <li>Program Name : StatementProvider
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class StatementProvider {
    private static final Logger log = LoggerFactory.getLogger("com.hami.fw.jdbc.StatementProvider");
    private QueryLoader queryLoader = QueryLoader.getInstance();

    public String geStatement(Object obj, String sqlName) {
        String sql = queryLoader.getElement(obj, sqlName, null);
        return sql;
    }
}
