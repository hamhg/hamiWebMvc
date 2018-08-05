package com.hami.sys.jdbc.sql;

import com.hami.sys.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * <pre>
 * <li>Program Name : SqlUtil
 * <li>Description  :
 * <li>History      : 2017. 12. 26.
 * </pre>
 *
 * @author HHG
 */
public class SqlUtil {

    protected static final Logger log = LoggerFactory.getLogger("com.hami.fw.jdbc.sql.SqlUtil");

    /*
    public static void setConnectionCryptKey(Connection conn) throws SQLException
    {
        String CRYPT_KEY = new hunelServletChecker().getCryptKey();
        //HashMap<String, String> sessionVarMap = new HashMap<String, String>();// 세션 임시테이블에 저장할 데이터(암복호화키, LANG_CD 등)
        if (conn instanceof ConnectionWrapper)
        {
            ConnectionWrapper conn_wrapper = (ConnectionWrapper) conn;
            conn_wrapper.setSessionVariable("CRYPT_KEY", CRYPT_KEY);
        }
        CUDSQLManager cud = new CUDSQLManager(conn);
        cud.setDebug(false);
        cud.setTable("SESSION_VAR");
        cud.addKey("VAR_NM", "CRYPT_KEY");
        cud.addField("VAR_VAL", CRYPT_KEY);
        cud.insert();
    }
    */

    /**
     * 새로운 VarStatement생성하여 반환한다.
     */
    public static SqlStatement newStatement(Connection conn, String query) throws SQLException
    {
        return new SqlStatement(conn, query);
    }

    /**
     * Connection의 AutoCommit 여부를 설정한다.
     */
    public static void setAutoCommit(Connection conn, boolean b) throws SQLException
    {
        if (conn != null)
        {
            conn.setAutoCommit(b);
        }
    }

    /**
     * 해당 Connection을 commit한다.
     */
    public static void commit(Connection conn) throws SQLException
    {
        if (conn != null) conn.commit();
    }

    /**
     * 해당 Connection을 rollback한다.
     */
    public static void rollback(Connection conn)
    {
        if (conn != null)
        {
            try
            {
                conn.rollback();
            }
            catch (Exception e)
            {
                //ignore
            }
        }
    }

    /**
     * 해당 Connection을 commit하고 close한다.
     */
    public static void releaseConnection(Connection conn)
    {
        if (conn != null)
        {
            try
            {
                conn.setAutoCommit(true);
            }
            catch (Exception e)
            {
                //ignore
            }
            finally
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    //ignore
                }
            }
        }
    }

    /**
     * ResultSet을 반환하고 VarStatement를 종료한다.
     */
    public static MetaResultSet getResultSetWithClose(SqlStatement vstmt) throws SQLException
    {
        try
        {
            return MetaResultSet.make(vstmt.executeQuery());
        }
        finally
        {
            vstmt.close();
        }
    }

    /**
     * ResultSet을 반환한다.
     */
    public static MetaResultSet getResultSet(SqlStatement vstmt) throws SQLException, IOException
    {
        return MetaResultSet.make(vstmt.executeQuery());
    }

    /**
     * MemResultSet데이터를 delimCol과 delimRow로 구분한 문자열로 변환 후 반환한다.
     */
    public static String makeList(MetaResultSet mrs, String delimCol, String delimRow)
    {
        int columnCount = mrs.getColumnCount();
        int rowCount = mrs.getRowCount();
        StringBuffer sb = new StringBuffer();
        int row = 0;
        while (mrs.next())
        {
            for (int n = 0; n < columnCount; n++)
            {
                sb.append(StringUtils.nvl(mrs.getString(n + 1), " "));
                if (n + 1 < columnCount) sb.append(delimCol);
            }
            if (row + 1 < rowCount) sb.append(delimRow);
            row++;
        }
        return sb.toString();
    }

    /**
     * MemResultSet데이터를 List<Map> 변환 후 반환한다.
     */
    public static List<Map> makeList(MetaResultSet mrs)
    {
        int columnCount = mrs.getColumnCount();
        List<Map> result = new ArrayList<>();
        while (mrs.next())
        {
            Map<String, Object> resultMap = new HashMap<>();
            for (int n = 0; n < columnCount; n++)
            {
                resultMap.put( mrs.getColumnName(n+1), StringUtils.nvl(mrs.getString(n+1)) );
            }
            result.add(resultMap);
        }
        return result;
    }

    /**
     * 데이터 조회결과를 MemResultSet형태로 반환한다.
     */
    public static MetaResultSet search(Connection conn, String query, Map paramMap) throws SQLException
    {
        SqlStatement vstmt = new SqlStatement(conn, query);
        if (paramMap != null)
        {
            Iterator iter = paramMap.keySet().iterator();
            String param, value;
            while (iter.hasNext())
            {
                param = (String) iter.next();
                value = (String) paramMap.get(param);
                vstmt.setParameter(param, value);
            }
        }
        log.debug(vstmt.getQueryString());
        return getResultSetWithClose(vstmt);
    }
}
