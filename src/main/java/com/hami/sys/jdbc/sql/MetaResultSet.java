package com.hami.sys.jdbc.sql;

import com.hami.sys.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <pre>
 * <li>Program Name : MetaResultSet
 * <li>Description  :
 * <li>History      : 2017. 12. 26.
 * </pre>
 *
 * @author HHG
 */
public class MetaResultSet
{
    /**
     * LOG
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     *  rset를 갖고 생성자를 실행한다.
     */
    public static MetaResultSet make(ResultSet rset) throws SQLException
    {
        return new MetaResultSet(rset);
    }

    /**
     * 컬럼갯수
     */
    int m_columnCount = 0;
    /**
     * 로우갯수
     */
    int m_rowCount = 0;
    /**
     * 현재 row, data 는 1 부터 시작
     */
    int m_currRow = 0;
    /**
     * 컬럼명으로 컬럼인덱스 찾기 한 맵
     */
    HashMap m_mapColNmIdx = null;
    /**
     * 컬럼명을 담을 string array
     */
    String[] m_columnNames = null;
    /**
     * column types
     */
    int[] m_columnTypes = null;
    /**
     * 레코드를 담을 list
     */
    ArrayList m_listRec = null;
    /**
     * 컬럼값 꺼내올때 에러를 무시할지 결정한다.
     */
    boolean m_ingnoreColumnError = false;


    /**
     * 생성자
     */
    public MetaResultSet()
    {
        //
    }

    /**
     * m_ingnoreColumnError값을 b로 설정한다.
     */
    public void setIgnoreColumnError(boolean b)
    {
        m_ingnoreColumnError = b;
    }

    /**
     * 생성자
     * @param rset
     * @param bCloseAfterParse true 이면 파싱 후에 rs.close(); 한다.
     * @throws SQLException
     */
    public MetaResultSet(ResultSet rset, boolean bCloseAfterParse) throws SQLException
    {
        parse(rset, bCloseAfterParse);
    }

    /**
     * 생성자, 파싱 후에 rs.close(); 한다.
     * @param rset
     * @throws SQLException
     */
    public MetaResultSet(ResultSet rset) throws SQLException
    {
        parse(rset, true);
    }

    /**
     * ResultSet을 메모리에 담는다.
     * @param rset
     * @throws SQLException
     */
    public void parse(ResultSet rs, boolean bCloseAfterParse) throws SQLException
    {
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();// 메타데이타
            int columnCount = rsmd.getColumnCount();// column count
            // mapping column name to column index
            HashMap mapColNmIdx = new HashMap();
            String[] columnNames = new String[columnCount];
            int[] columnTypes = new int[columnCount];
            String columnName;
            for (int col = 1; col <= columnCount; col++)
            {
                columnName = rsmd.getColumnName(col).toUpperCase();// 컬럼명
                //System.out.println(columnName);
                columnNames[col - 1] = columnName;//배열에 세팅
                columnTypes[col - 1] = rsmd.getColumnType(col);//배열에 세팅
                mapColNmIdx.put(columnName, String.valueOf(col));
            }
            // save data
            ArrayList listRec = new ArrayList();
            Object[] rowData;
            //int rowcnt = 0;
            while (rs.next())
            {
                //rowcnt++;
                //log.debug(rowcnt);
                rowData = new Object[columnCount];
                for (int col = 1; col <= columnCount; col++)
                {
                    if (columnTypes[col - 1] == java.sql.Types.CLOB)
                    {
                        rowData[col - 1] = readClobData(rs.getCharacterStream(col));
                        //System.out.println(rowData[col - 1]);
                    }
                    else
                    {
                        rowData[col - 1] = rs.getObject(col);
                    }
                    //System.out.println(rs.getObject(col));
                }
                listRec.add(rowData);
            }
            // set member variable
            m_columnNames = columnNames;
            m_columnTypes = columnTypes;
            m_columnCount = columnCount;
            m_mapColNmIdx = mapColNmIdx;
            m_listRec = listRec;
            m_rowCount = m_listRec.size();
        }
        catch (java.io.IOException ioe)
        {
            throw new SQLException(ioe.getMessage());
        }
        finally
        {
            if (bCloseAfterParse) {
                try { rs.close(); } catch (Exception e) { }
            }
        }
    }

    /**
     * 컬럼명을 반환한다.
     */
    public String getColumnName(int col)
    {
        return m_columnNames[col - 1];
    }

    /**
     * 컬럼명 배열을 반환한다.
     */
    public String[] getColumnNameArray()
    {
        return m_columnNames;
    }

    /**
     * row count를 반환한다.
     */
    public int getRowCount()
    {
        return m_rowCount;
    }

    /**
     * column count를 반환한다.
     */
    public int getColumnCount()
    {
        return m_columnCount;
    }

    /**
     * row data(Object Array)를 반환한다.
     */
    public Object[] getRowData(int row)
    {
        if (row - 1 < 0 || row - 1 > m_listRec.size() - 1) throw new RuntimeException("row is not in range, maybe execute next()...");
        return (Object[]) m_listRec.get(row - 1);
    }

    /**
     * 현재 row data(Object Array)를 반환한다.
     */
    public Object[] getRowData()
    {
        return getRowData(m_currRow);
    }

    /**
     * 지정한 row, col의 데이터(Object)를 반환한다.
     */
    public Object getObject(int row, int col)
    {
        return getRowData(row)[col - 1];
    }

    /**
     * Object getObject(int row, int col)을 실행한다.
     */
    public Object getObject(int row, String colNm)
    {
        if (m_ingnoreColumnError)
        {
            try
            {
                return getObject(row, findColumn(colNm));
            }
            catch (Exception e)
            {
                if (row == 1)
                {
                    log.debug("Column Not Found : -------------> " + colNm);
                }
                return null;
            }
        }
        else
        {
            return getObject(row, findColumn(colNm));
        }
    }

    /**
     * Object getObject(current row, int col)을 실행한다.
     */
    public Object getObject(int col)
    {
        return getObject(m_currRow, col);
    }

    /**
     * Object getObject(current row, String colNm)을 실행한다.
     */
    public Object getObject(String colNm)
    {
        return getObject(m_currRow, colNm);
    }


    /**
     * 지정한 row, col의 데이터(Object)를 문자열로 반환한다.
     */
    public String getString(int row, int col)
    {
        Object o = getObject(row, col);
        return o == null ? null : o.toString();
    }

    /**
     * 지정한 row, colNm의 데이터(Object)를 문자열로 반환한다.
     */
    public String getString(int row, String colNm)
    {
        Object o = getObject(row, colNm);
        return o == null ? null : o.toString();
    }

    /**
     * current row, 지정한 col의 데이터(Object)를 문자열로 반환한다.
     */
    public String getString(int col)
    {
        Object o = getObject(col);
        return o == null ? null : o.toString();
    }

    /**
     * current row, 지정한 colNm의 데이터(Object)를 문자열로 반환한다.
     */
    public String getString(String colNm)
    {
        Object o = getObject(colNm);
        return o == null ? null : o.toString();
    }

    /**
     * getString(colNm)결과를 int로 반환한다.
     * @param colNm
     * @return
     */
    public int getInt(String colNm)
    {
        String val = getString(colNm);
        return val == null ? 0 : Integer.parseInt(val);
    }

    /**
     * getString(col)결과를 int로 반환한다.
     * @param col
     * @return
     */
    public int getInt(int col)
    {
        String val = getString(col);
        return val == null ? 0 : Integer.parseInt(val);
    }

    /**
     * getString(colNm)결과를 double로 반환한다.
     * @param colNm
     * @return
     */
    public double getDouble(String colNm)
    {
        String val = getString(colNm);
        return val == null ? 0.0 : Double.parseDouble(val);
    }

    /**
     * getString(col)결과를 double로 반환한다.
     * @param col
     * @return
     */
    public double getDouble(int col)
    {
        String val = getString(col);
        return val == null ? 0.0 : Double.parseDouble(val);
    }

    ////////////////////////////////////// 아래는 java.sql.ResultSet method interface
    /**
     * Moves the cursor to the given row number in this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean absolute(int row)
    {
        // beforeFirst 에서 afterLast 이면 row 를 현재 row 에 set 하고
        if (row >= 0 && row <= m_rowCount + 1)
        {
            m_currRow = row;
        }
        // 데이타 검색가능 row 이면 true
        return isDataRow(row);
    }

    /**
     * Moves the cursor to the end of this ResultSet object, just after the last row.
     * (cf. java.sql.ResultSet method interface)
     */
    public void afterLast()
    {
        m_currRow = m_rowCount + 1;
    }

    /**
     * Moves the cursor to the front of this ResultSet object, just before the first row.
     * (cf. java.sql.ResultSet method interface)
     */
    public void beforeFirst()
    {
        m_currRow = 0;
    }

    /**
     * Releases this ResultSet object's database and JDBC resources immediately instead of waiting for this to happen when it is automatically closed.
     * (cf. java.sql.ResultSet method interface)
     */
    public void close()
    {
    }

    /**
     * Maps the given ResultSet column name to its ResultSet column index.
     * (cf. java.sql.ResultSet method interface)
     */
    public int findColumn(String columnName)
    {
        try
        {
            return Integer.parseInt(m_mapColNmIdx.get(columnName.toUpperCase()).toString());
        }
        catch (Exception e)
        {
            throw new RuntimeException("findColumn Error: " + columnName);
        }
    }

    /**
     * Moves the cursor to the first row in this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean first()
    {
        return absolute(1);
    }

    /**
     * Retrieves the current row number.
     * (cf. java.sql.ResultSet method interface)
     */
    public int getRow()
    {
        return m_currRow;
    }

    /**
     * Retrieves whether the cursor is after the last row in this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean isAfterLast()
    {
        return m_currRow == m_rowCount + 1;
    }

    /**
     * Retrieves whether the cursor is before the first row in this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean isBeforeFirst()
    {
        return m_currRow == 0;
    }

    /**
     * Retrieves whether the cursor is on the first row of this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean isFirst()
    {
        return m_rowCount > 0 && m_currRow == 1;
    }

    /**
     * Retrieves whether the cursor is on the last row of this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean isLast()
    {
        return m_rowCount > 0 && m_currRow == m_rowCount;
    }

    /**
     * Moves the cursor to the last row in this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean last()
    {
        return absolute(m_rowCount);
    }

    /**
     * Moves the cursor down one row from its current position.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean next()
    {
        return absolute(m_currRow + 1);
    }

    /**
     * Moves the cursor to the previous row in this ResultSet object.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean previous()
    {
        return absolute(m_currRow - 1);
    }

    /**
     * Moves the cursor a relative number of rows, either positive or negative.
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean relative(int rows)
    {
        return absolute(m_currRow + rows);
    }

    /**
     * row 가 data fetch 가 가능한 row 인지
     * (cf. java.sql.ResultSet method interface)
     */
    public boolean isDataRow(int row)
    {
        return m_rowCount > 0 && (row >= 1 && row <= m_rowCount);
    }

    /**
     * makeHeaderString & makeRowDataString를 실행한다.
     * 1개 레코드의 컬럼명과 컬럼데이터를 문자열로 반환한다.
     */
    public String makeResult(String delimCol, String delimRow)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(makeHeaderString(delimCol));
        sb.append(delimRow);
        //데이타
        for (int row = 0; next(); row++)
        {
            makeRowDataString(delimCol);
            sb.append(delimRow);
        }
        beforeFirst();
        return sb.toString();
    }

    /**
     * 1개 레코드의 컬럼명을 반환한다.
     */
    public String makeHeaderString(String dCol)
    {
        StringBuffer sb = new StringBuffer();
        int cntCol = getColumnCount();
        //컬럼
        for (int col = 0; col < cntCol; col++)
        {
            if (col > 0) sb.append(dCol);
            sb.append(getColumnName(col + 1));
        }
        return sb.toString();
    }

    /**
     * 1개 레코드의 컬럼데이터를 반환한다.
     */
    public String makeRowDataString(String dCol)
    {
        StringBuffer sb = new StringBuffer();
        int cntCol = getColumnCount();
        // 컬럼
        for (int col = 0; col < cntCol; col++)
        {
            if (col > 0) sb.append(dCol);
            sb.append(StringUtils.nvl(getString(col + 1)));
        }
        return sb.toString();
    }

    /**
     * m_columnNames를 반환한다.
     */
    public String[] getColumnNames()
    {
        return m_columnNames;
    }

    /**
     * reader를 문자열로 반환한다.
     */
    public static String readClobData(Reader reader) throws IOException
    {
        StringBuffer data = new StringBuffer();
        char[] buf = new char[1024];
        int cnt = 0;
        if (null != reader)
        {
            while ((cnt = reader.read(buf)) != -1)
            {
                data.append(buf, 0, cnt);
            }
        }
        return data.toString();
    }
}
