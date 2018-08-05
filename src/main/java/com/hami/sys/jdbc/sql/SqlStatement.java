package com.hami.sys.jdbc.sql;

import com.hami.sys.util.FormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * <li>Program Name : SqlStatement
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
public class SqlStatement {
    /**
     * LOG
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * Connection
     */
    protected Connection memConn;
    /**
     * CallableStatement
     */
    protected CallableStatement memStmt;
    /**
     * 변수 바인딩 SQL
     */
    protected String memQuery;
    /**
     * 순서 바인딩 SQL
     */
    protected String memStmtQuery;
    /**
     * 파라미터 변수 : 파라미터 값
     */
    protected HashMap memParameterMap;
    /**
     * 순서대로 파라미터 변수를 저장한다.
     */
    protected ArrayList memParameterList;
    /**
     * default_pattern
     */
    // static Pattern pattern = Pattern.compile("\\$\\{((\\w|[ㄱ-힝])+)\\}");// ${key}
    public static final Pattern default_pattern = Pattern.compile("\\:((\\w|[ㄱ-힝])+)");// :key
    /**
     * pattern
     */
    Pattern pattern = null;

    /**
     * 생성자, 3번째 인수에 null을 넣어 다른 생성자를 호출한다.
     */
    public SqlStatement(Connection conn, String query) throws SQLException
    {
        this(conn, query, null);
    }

    /**
     * 생성자, setRealStatement에서 실행한다.
     */
    public SqlStatement(Connection conn, String query, String sPattern) throws SQLException
    {
        if (query == null) throw new RuntimeException("SqlStatement: Query is null...");
        memConn = conn;
        memQuery = query;
        memParameterList = new ArrayList();
        memParameterMap = new HashMap();
        pattern = sPattern == null ? default_pattern : Pattern.compile(sPattern);
        memStmtQuery = makePStmtQuery(memQuery);
        setRealStatement(memStmtQuery);
        //setCommonParameters();
    }

    /**
     * 쿼리를 실행하고 에러가 발생하면 log에 출력한다.
     */
    private void setRealStatement(String stmtQeury) throws SQLException
    {
        try
        {
            memStmt = memConn.prepareCall(stmtQeury);
        }
        catch (SQLException e)
        {
            log.debug(stmtQeury);
            throw e;
        }
    }

    /**
     * 쿼리를 String으로 변환한여 반환한다.
     */
    protected String makePStmtQuery(String varQuery)
    {
        Matcher matcher = pattern.matcher(varQuery);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            matcher.appendReplacement(sb, "?");
            memParameterList.add(matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().trim();
    }

    /**
     * memParameterList변수에 문자열 파라미터를 설정한다.
     */
    public void setParameter(String param, String value)
    {
        param = param.toUpperCase();
        if (!memParameterList.contains(param)) { log.debug("parameter[{}] no exists.", param); }
        else memParameterMap.put(param, new String[] { "STRING", value });
    }

    /**
     * memParameterList변수에 CLOB 파라미터를 설정한다.
     */
    public void setClobParameter(String param, String value)
    {
        param = param.toUpperCase();
        if (!memParameterList.contains(param)) { log.debug("parameter[{}] no exists.", param); }
        memParameterMap.put(param, new String[] { "CLOB", value });
    }

    /**
     * memParameterList변수에 문자열 반환 파라미터를 설정한다.
     */
    public void registerOutputParameter(String param) throws SQLException
    {
        param = param.toUpperCase();
        if (!memParameterList.contains(param)) { log.debug("parameter[{}] no exists.", param); }
        memParameterMap.put(param, new String[] { "OUTPUT", "STRING" });
    }

    /**
     * memParameterList변수에 사용자정의 반환 파라미터를 설정한다. 자료형을 사용자가 지정한다.
     */
    public void registerOutputParameter(String param, int sqlType) throws SQLException
    {
        param = param.toUpperCase();
        if (!memParameterList.contains(param)) { log.debug("parameter[{}] no exists.", param); }
        memParameterMap.put(param, new String[] { "OUTPUT", String.valueOf(sqlType) });
    }

    /**
     * 변수의 값을 순서대로 Statement에 바인딩한다.
     * @throws SQLException
     *             sql 실행 실패시 발생
     */
    protected void setParameters() throws SQLException
    {
        String[] typeValuePair;
        String param, strValue;
        Reader reader;
        int sz = memParameterList.size();
        for (int n = 0, col = 1; n < sz; n++, col++)
        {
            param = memParameterList.get(n).toString();
            if (!memParameterMap.containsKey(param)) { throw new RuntimeException("parameter[" + param + "] no set."); }
            typeValuePair = (String[]) memParameterMap.get(param);
            if ("OUTPUT".equals(typeValuePair[0]))
            {
                if ("STRING".equals(typeValuePair[1]))
                {
                    memStmt.registerOutParameter(col, Types.VARCHAR);
                }
                else
                {
                    memStmt.registerOutParameter(col, Integer.valueOf(typeValuePair[1]).intValue());
                }
            }
            else
            {
                strValue = typeValuePair[1];
                if ("CLOB".equals(typeValuePair[0]))
                {
                    reader = new StringReader(strValue.toString());
                    memStmt.setCharacterStream(col, reader, strValue.length());
                }
                else if (strValue != null && strValue.length() > 1000)
                {
                    reader = new StringReader(strValue.toString());
                    memStmt.setCharacterStream(col, reader, strValue.length());
                }
                else
                {
                    memStmt.setString(col, strValue);
                }
            }
        }
    }

    /**
     * 변수값을 Statement에 설정하고 executeUpdate한다.
     * executeUpdate 처리된 리턴카운트를 반환한다.
     */
    public int executeUpdate() throws SQLException
    {
        setCommonParameters();
        setParameters();
        int executeCount = memStmt.executeUpdate();
        //log.debug("executeCount: "+executeCount);
        //setCommonParameters();
        return executeCount;
    }

    protected void setCommonParameters()
    {
        if (memQuery.indexOf(":SYS_YMD") > -1) this.setParameter("SYS_YMD", FormatUtil.formatDate("yyyyMMdd"));
    }

    /**
     * 변수값을 Statement에 설정하고 executeQuery한다.
     * executeQuery 처리된 ResultSet을 반환한다.
     */
    public ResultSet executeQuery() throws SQLException
    {
        setCommonParameters();
        setParameters();
        return memStmt.executeQuery();
    }

    /**
     * memParameterList변수의 지정된 파라미터를 문자열값으로 반환한다.
     */
    public String getString(String param) throws SQLException
    {
        return memStmt.getString(memParameterList.indexOf(param.toUpperCase()) + 1);
    }

    /**
     * VarStatement를 종료한다.
     */
    public void close() throws SQLException
    {
        if (memStmt != null) memStmt.close();
    }

    /**
     * 실행할 QUERY를 문자열로 반환한다.
     */
    public String getQueryString()
    {
        String[] typeValuePair;
        String param;
        int prevEnd = 0;
        Matcher matcher = pattern.matcher(memQuery);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            param = matcher.group(1).toUpperCase();
            typeValuePair = (String[]) memParameterMap.get(param);
            sb.append(memQuery.substring(prevEnd, matcher.start()));
            if ( typeValuePair == null )
            {
                log.debug("param["+param+"] is not set...");
            }
            sb.append(typeValuePair == null || "CRYPT_KEY".equals(param) || "OUTPUT".equals(typeValuePair[0]) ? ":" + param : typeValuePair[1] == null ? "null" : "'" + typeValuePair[1] + "'");
            prevEnd = matcher.end();
            // matcher.appendReplacement(sb, value instanceof String[] ?
            // ":"+param :
            // value == null ? "null" : "'"+value.toString()+"'");
        }
        // matcher.appendTail(sb);
        sb.append(memQuery.substring(prevEnd));
        return sb.toString();
    }

    /**
     * memParameterList변수를 문자열배열로 변환하여 반환한다.
     */
    public String[] getParameterNames()
    {
        String arrStr[] = new String[this.memParameterList.size()];
        this.memParameterList.toArray(arrStr);
        return arrStr;
    }
}
