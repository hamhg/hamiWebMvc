package com.hami.sys.jdbc.sql;

import com.hami.sys.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <pre>
 * <li>Program Name : CudHandler
 * <li>Description  :
 * <li>History      : 2017. 12. 26.
 * </pre>
 *
 * @author HHG
 */
public class CudHandler {
    /**
     * statement 가 max_cnt 초과되면 모두 close 한다.
     */
    protected static int MAX_CNT = 50;
    /**
     * 현재 만들어진 statement 수
     */
    protected int count = 0;
    /**
     * 로그
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * db connection
     */
    protected Connection m_conn;
    /**
     * 트랜잭션 대상 테이블명
     */
    protected String m_table;
    /**
     * 속성 필드 리스트
     */
    protected ArrayList m_fieldList;
    /**
     * 조건절 필드 리스트
     */
    protected ArrayList m_keyList;
    /**
     * 동일 statement 처리 위한 쿼리 문자열 보관
     */
    protected HashMap m_queryMap;
    /**
     * 기타 파라미터 필드
     */
    protected HashMap m_etcMap;
    /**
     * 쿼리 로그 여부
     */
    protected boolean m_debug;
    /**
     * 자동 컬럼(등록자, 등록일시, 수정자, 수정일시 등) 추가 여부
     */
    protected boolean m_autoFieldAdd;

    /**
     * 생성자
     * @param conn
     */
    public CudHandler(DataSource dataSource) throws SQLException {
        this.m_conn = dataSource.getConnection();
        m_fieldList = new ArrayList();
        m_keyList = new ArrayList();
        m_etcMap = new HashMap();
        m_queryMap = new HashMap();
        m_debug = true;
        m_autoFieldAdd = true;
    }

    /**
     * debug 여부 설정
     * @param debug
     */
    public void setDebug(boolean debug)
    {
        m_debug = debug;
    }

    /**
     * 테이블명 설정, 각 파라미터 초기화
     * @param table
     */
    public void setTable(String table)
    {
        m_table = table;
        m_fieldList.clear();
        m_keyList.clear();
        m_etcMap.clear();
    }

    /**
     * 속성 필드 추가
     * @param field
     * @param value
     */
    public void addField(String field, String value)
    {
        m_fieldList.add(new String[] { "1", field, value });
    }

    /**
     * Raw 속성 필드 추가
     * @param field
     * @param value
     */
    public void addFieldRaw(String field, String value)
    {
        m_fieldList.add(new String[] { "2", field, value });
    }

    /**
     * 키(조건필드) 추가
     * @param field
     * @param value
     */
    public void addKey(String field, String value)
    {
        m_keyList.add(new String[] { "1", field, value });
    }

    /**
     * Raw 키(조건필드) 추가
     * @param field
     * @param value
     */
    public void addKeyRaw(String field, String value)
    {
        m_keyList.add(new String[] { "2", field, value });
    }

    /**
     * 기타 조건 추가
     * @param where
     */
    public void addWhere(String where)
    {
        m_keyList.add(new String[] { "3", where, null });
    }

    /**
     * 기타 파라미터 추가
     * @param pName
     * @param pValue
     */
    public void addEtcParameter(String pName, String pValue)
    {
        m_etcMap.put(pName, pValue);
    }

    /**
     * insert
     */
    public int insert() throws SQLException
    {
        // insert 할때 자동으로 입력자, 입력일시 세팅
        if(false)//if (m_conn_wrapper != null)
        {
            String USER_ID = "";//StringUtils.nvl(m_conn_wrapper.getSessionVariable("USER_ID"));
            if (USER_ID.length() > 0 && this.m_autoFieldAdd)
            {
                if (!isFieldExists("INS_USER_ID")) addField("INS_USER_ID", USER_ID);
                if (!isFieldExists("INS_YMDHMS")) addFieldRaw("INS_YMDHMS", "SYSDATE");
            }
        }
        String query = makeInsertQuery();
        SqlStatement vstmt = getStatement(query);
        setParameters(vstmt, m_keyList, "K");// 추가됨
        setParameters(vstmt, m_fieldList, "F");
        setParameters(vstmt, m_etcMap);
        if (m_debug) log.debug(vstmt.getQueryString());
        return vstmt.executeUpdate();
    }

    /**
     * 필드가 존재하는지 검사
     * @param fieldName
     * @return
     */
    boolean isFieldExists(String fieldName)
    {
        String[] fieldSet;
        int keyCount = m_keyList.size();
        for (int n = 0, sz = keyCount; n < sz; n++, count++)
        {
            fieldSet = (String[]) m_keyList.get(n);
            if (fieldSet[1].equals(fieldName)) return true;
        }
        int fieldCount = m_fieldList.size();
        for (int n = 0, sz = fieldCount; n < sz; n++, count++)
        {
            fieldSet = (String[]) m_fieldList.get(n);
            if (fieldSet[1].equals(fieldName)) return true;
        }
        return false;
    }

    /**
     * update
     */
    public int update() throws SQLException
    {
        String query = makeUpdateQuery();
        SqlStatement vstmt = getStatement(query);
        /*
                if (m_ehrbean != null && this.m_autoFieldAdd)
                {
                    if (!isFieldExists("MOD_USER_ID")) addField("MOD_USER_ID", m_ehrbean.get("USER_ID"));
                    if (!isFieldExists("MOD_YMDHMS")) addFieldRaw("MOD_YMDHMS", "SYSDATE");
                }
        */
        setParameters(vstmt, m_fieldList, "F");
        setParameters(vstmt, m_keyList, "K");
        setParameters(vstmt, m_etcMap);
        if (m_debug) log.debug(vstmt.getQueryString());
        return vstmt.executeUpdate();
    }

    /**
     * delete
     */
    public int delete() throws SQLException
    {
        String query = makeDeleteQuery();
        SqlStatement vstmt = getStatement(query);
        setParameters(vstmt, m_keyList, "K");
        setParameters(vstmt, m_etcMap);
        if (m_debug) log.debug(vstmt.getQueryString());
        return vstmt.executeUpdate();
    }

    /**
     * 파라미터 세팅
     * @param vstmt
     * @param map
     */
    private void setParameters(SqlStatement vstmt, HashMap map)
    {
        String pName, pValue;
        Iterator iter = map.keySet().iterator();
        while (iter.hasNext())
        {
            pName = (String) iter.next();
            pValue = (String) map.get(pName);
            vstmt.setParameter(pName, pValue);
        }
    }

    /**
     * 쿼리 문자열로 저장된 statement를 가져온다.
     */
    SqlStatement getStatement(String query) throws SQLException
    {
        if (m_debug) log.debug("count: " + count);
        if (count == MAX_CNT)
        {
            closeAllStatement();
        }
        SqlStatement vstmt = (SqlStatement) m_queryMap.get(query);
        if (vstmt != null) return vstmt;
        vstmt = new SqlStatement(m_conn, query);
        m_queryMap.put(query, vstmt);
        count++;
        return vstmt;
    }

    /**
     * 모든 VarStatement를 종료한다.
     */
    void closeAllStatement()
    {
        SqlStatement vstmt;
        Iterator iter = m_queryMap.values().iterator();
        while (iter.hasNext())
        {
            vstmt = (SqlStatement) iter.next();
            try
            {
                vstmt.close();
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
            }
        }
        m_queryMap.clear();
        count = 0;
    }

    /**
     * insert 쿼리를 만든다
     * @return
     */
    String makeInsertQuery()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT ").append("\n");
        sb.append("  INTO ").append(m_table).append("\n");
        sb.append("(").append("\n");
        String[] fieldSet;
        int keyCount = m_keyList.size();
        int fieldCount = m_fieldList.size();
        int totCount = keyCount + fieldCount;
        int count = 0;
        for (int n = 0, sz = keyCount; n < sz; n++, count++)
        {
            fieldSet = (String[]) m_keyList.get(n);
            sb.append("  ").append(fieldSet[1]).append(count < totCount - 1 ? "," : "").append("\n");
        }
        for (int n = 0, sz = fieldCount; n < sz; n++, count++)
        {
            fieldSet = (String[]) m_fieldList.get(n);
            sb.append("  ").append(fieldSet[1]).append(count < totCount - 1 ? "," : "").append("\n");
        }
        sb.append(")").append("\n");
        sb.append("VALUES").append("\n");
        sb.append("(").append("\n");
        count = 0;
        for (int n = 0, sz = keyCount; n < sz; n++, count++)
        {
            fieldSet = (String[]) m_keyList.get(n);
            sb.append("  ").append("1".equals(fieldSet[0]) ? ":K" + n : fieldSet[2]).append(count < totCount - 1 ? "," : "").append("\n");
        }
        for (int n = 0, sz = fieldCount; n < sz; n++, count++)
        {
            fieldSet = (String[]) m_fieldList.get(n);
            sb.append("  ").append("1".equals(fieldSet[0]) ? ":F" + n : fieldSet[2]).append(count < totCount - 1 ? "," : "").append("\n");
        }
        sb.append(")").append("\n");
        return sb.toString();
    }

    /**
     * update 쿼리를 만든다
     * @return
     */
    String makeUpdateQuery()
    {
        if (m_keyList.size() == 0) { throw new RuntimeException("key must be set."); }
        String[] fieldSet;
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ").append(m_table).append(" SET\n");
        for (int n = 0, sz = m_fieldList.size(); n < sz; n++)
        {
            fieldSet = (String[]) m_fieldList.get(n);
            sb.append("       ").append(fieldSet[1]).append(" = ").append("1".equals(fieldSet[0]) ? ":F" + n : fieldSet[2]).append(n < sz - 1 ? "," : "").append("\n");
        }
        appendWhrereQuery(sb);
        return sb.toString();
    }

    /**
     * delete 쿼리를 만든다
     * @return
     */
    String makeDeleteQuery()
    {
        if (m_keyList.size() == 0) { throw new RuntimeException("key must be set."); }
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE ").append("\n");
        sb.append("  FROM ").append(m_table).append("\n");
        appendWhrereQuery(sb);
        return sb.toString();
    }

    /**
     * 기타 조건을 쿼리에 추가한다.
     * @param sb
     */
    void appendWhrereQuery(StringBuffer sb)
    {
        String[] fieldSet;
        sb.append(" WHERE 1=1").append("\n");
        for (int n = 0; n < m_keyList.size(); n++)
        {
            fieldSet = (String[]) m_keyList.get(n);
            sb.append("   AND ");
            if ("1".equals(fieldSet[0]))
            {
                sb.append(fieldSet[1]).append(" = ").append(":K" + n);
            }
            else if ("2".equals(fieldSet[0]))
            {
                sb.append(fieldSet[1]).append(" = ").append(fieldSet[2]);
            }
            else if ("3".equals(fieldSet[0]))
            {
                sb.append(fieldSet[1]);
            }
            sb.append("\n");
        }
    }

    /**
     * 필드(컬럼)별 값을 설정한다.
     */
    void setParameters(SqlStatement vstmt, ArrayList pList, String strPrefix)
    {
        String[] fieldSet;
        for (int n = 0, nlen = pList.size(); n < nlen; n++)
        {
            fieldSet = (String[]) pList.get(n);
            if ("1".equals(fieldSet[0]))
            {
                vstmt.setParameter((pList == m_fieldList ? strPrefix + n : strPrefix + n), fieldSet[2]);
            }
            else
            {
                // skip
            }
        }
    }

    /**
     * m_keyList 한줄, m_fieldList 한줄 로그에 출력한다.
     */
    public void logRow(String logType)
    {
        StringBuffer sb = new StringBuffer();
        for (int n = 0, nlen = m_keyList.size(); n < nlen; n++)
        {
            sb.append(StringUtils.nvl((String) m_keyList.get(n), "")).append("\t");
        }
        for (int n = 0, nlen = m_fieldList.size(); n < nlen; n++)
        {
            sb.append(((String[]) m_fieldList.get(n))[2]).append("\t");
        }
        log.debug(sb.toString());
    }

    public static void main(String[] args)
    {
        /*
         smngr.setTable("emp");
         smngr.addField("emp", "emp");
         smngr.addField("emp2", "emp");
         smngr.addFieldRaw("emp3", "sysdate");
         smngr.addKey("empid", "empid");
         smngr.addKey("empid2", "empid");
         smngr.addKeyRaw("empid3", "sysdate");
         smngr.addWhere("A is null");
         System.out.println(smngr.makeInsertQuery());
         System.out.println(smngr.makeDeleteQuery());
         System.out.println(smngr.makeUpdateQuery());
         */
        /*
         Connection conn = null;
         try
         {
         conn = SQLUtil.getConnection(null);
         SQLUtil.setAutoCommit(conn, false);


         SQLUtil.commit(conn);
         }
         catch(Exception e)
         {
         SQLUtil.rollback(conn);
         System.out.println(e.toString());
         }
         finally
         {
         SQLUtil.releaseConnection(conn);
         }
         */
    }
}
