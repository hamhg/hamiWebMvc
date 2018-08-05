package com.hami.sys.jdbc.audit.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <pre>
 * <li>Program Name : AbstractStatement
 * <li>Description  :
 * <li>History      : 2018. 2. 19.
 * </pre>
 *
 * @author HHG
 */
public abstract class AbstractStatement {
    protected String sql;
    private String nativeSql;
    private List params;
    protected long elapsedTime;

    protected AbstractStatement(String sql) {
        this.sql = sql;
        params = new ArrayList();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    private void logSql(String sql) {
        Logger log = LoggerFactory.getLogger("jdbc.sql");
        if (log.isDebugEnabled())
            log.debug(String.format("Elapsedtime: %d, Sql: %s", getElapsedTime(), (sql != null)?sql:generateNativeSql()));
    }

    protected void executionCompleted(long startTime, String sql) {
        elapsedTime = System.currentTimeMillis() - startTime;
        logSql(sql);
    }

    protected void executionCompleted(long startTime) {
        elapsedTime = System.currentTimeMillis() - startTime;
        logSql(null);
    }

    protected String generateNativeSql() {
        if (params.size() == 0)
            return sql;
        StringBuilder buffer = new StringBuilder();
        int count = 0;
        Object value;
        for (StringTokenizer token = new StringTokenizer((new StringBuilder()).append(sql).append(" ").toString(),
                "?"); token.hasMoreTokens(); buffer.append((new StringBuilder()).append("").append(value).toString())) {
            String piece = token.nextToken();
            buffer.append(piece);
            value = null;
            if (params.size() > 1 + count) {
                value = params.get(1 + count++);
                continue;
            }
            if (token.hasMoreTokens())
                value = null;
            else
                value = "";
        }

        params.clear();
        nativeSql = buffer.toString().trim();
        return nativeSql;
    }

    protected void addParameter(int position, Object parameter) {
        String strValue;
        if ((parameter instanceof String) || (parameter instanceof Date))
            strValue = (new StringBuilder()).append("'").append(parameter).append("'").toString();
        else if (parameter == null)
            strValue = "null";
        else
            strValue = parameter.toString();
        for (; position >= params.size(); params.add(null));
        params.set(position, strValue);
    }
}
