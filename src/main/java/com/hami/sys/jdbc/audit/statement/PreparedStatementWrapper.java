package com.hami.sys.jdbc.audit.statement;

import com.hami.sys.context.ApplicationContextHolder;
import com.hami.sys.jdbc.audit.control.JdbcControlService;
import com.hami.sys.jdbc.audit.rs.ResultSetWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

import static java.awt.SystemColor.control;

/**
 * <pre>
 * <li>Program Name : PreparedStatementWrapper
 * <li>Description  :
 * <li>History      : 2018. 2. 19.
 * </pre>
 *
 * @author HHG
 */
public class PreparedStatementWrapper extends AbstractStatement implements PreparedStatement {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    private PreparedStatement prepareStatement;

    public PreparedStatementWrapper(PreparedStatement ps, String sql) {
        super(sql);
        prepareStatement = ps;
        if (ApplicationContextHolder.containsBean("jdbc.control")) {
            JdbcControlService jdbcControl = (JdbcControlService) ApplicationContextHolder.getBean("jdbc.control", JdbcControlService.class);
            try {
                prepareStatement.setFetchSize(jdbcControl.getFetchSize());
                prepareStatement.setQueryTimeout(jdbcControl.getQueryTimeout());
            } catch (SQLException sqle) {
                log.warn("Failed to set jdbc control properties");
            }
        }
    }

    public boolean execute() throws SQLException {
        long startTime = System.currentTimeMillis();

        boolean flag;
        try {
            flag = this.prepareStatement.execute();
        } finally {
            this.executionCompleted(startTime);
        }

        return flag;
    }

    public boolean execute(String sql) throws SQLException {
        long startTime = System.currentTimeMillis();

        boolean flag;
        try {
            flag = this.prepareStatement.execute(sql);
        } finally {
            this.executionCompleted(startTime, sql);
        }

        return flag;
    }

    public int[] executeBatch() throws SQLException {
        long startTime = System.currentTimeMillis();

        int[] cnt;
        try {
            cnt = this.prepareStatement.executeBatch();
        } finally {
            this.executionCompleted(startTime);
        }

        return cnt;
    }

    public ResultSet executeQuery() throws SQLException {
        long startTime = System.currentTimeMillis();

        ResultSetWrapper rs;
        try {
            rs = new ResultSetWrapper(this.prepareStatement.executeQuery(), this.sql);
        } finally {
            this.executionCompleted(startTime);
        }

        return rs;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        long startTime = System.currentTimeMillis();

        ResultSetWrapper rs;
        try {
            rs = new ResultSetWrapper(this.prepareStatement.executeQuery(sql), sql);
        } finally {
            this.executionCompleted(startTime, sql);
        }

        return rs;
    }

    public int executeUpdate() throws SQLException {
        long startTime = System.currentTimeMillis();

        int cnt;
        try {
            cnt = this.prepareStatement.executeUpdate();
        } finally {
            this.executionCompleted(startTime);
        }

        return cnt;
    }

    public int executeUpdate(String sql) throws SQLException {
        long startTime = System.currentTimeMillis();

        int cnt;
        try {
            cnt = this.prepareStatement.executeUpdate(sql);
        } finally {
            this.executionCompleted(startTime);
        }

        return cnt;
    }

    public Connection getConnection() throws SQLException {
        return this.prepareStatement.getConnection();
    }

    public int getFetchDirection() throws SQLException {
        return this.prepareStatement.getFetchDirection();
    }

    public int getFetchSize() throws SQLException {
        return this.prepareStatement.getFetchSize();
    }

    public int getMaxFieldSize() throws SQLException {
        return this.prepareStatement.getMaxFieldSize();
    }

    public int getMaxRows() throws SQLException {
        return this.prepareStatement.getMaxRows();
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return this.prepareStatement.getMetaData();
    }

    public boolean getMoreResults() throws SQLException {
        return this.prepareStatement.getMoreResults();
    }

    public int getQueryTimeout() throws SQLException {
        return this.prepareStatement.getQueryTimeout();
    }

    public ResultSet getResultSet() throws SQLException {
        return this.prepareStatement.getResultSet();
    }

    public int getResultSetConcurrency() throws SQLException {
        return this.prepareStatement.getResultSetConcurrency();
    }

    public int getResultSetType() throws SQLException {
        return this.prepareStatement.getResultSetType();
    }

    public int getUpdateCount() throws SQLException {
        return this.prepareStatement.getUpdateCount();
    }

    public SQLWarning getWarnings() throws SQLException {
        return this.prepareStatement.getWarnings();
    }

    public void setArray(int i, Array x) throws SQLException {
        this.prepareStatement.setArray(i, x);
        this.addParameter(i, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.prepareStatement.setAsciiStream(parameterIndex, x, length);
        this.addParameter(parameterIndex, x);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        this.prepareStatement.setBigDecimal(parameterIndex, x);
        this.addParameter(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.prepareStatement.setBinaryStream(parameterIndex, x, length);
        this.addParameter(parameterIndex, x);
    }

    public void setBlob(int i, Blob x) throws SQLException {
        this.prepareStatement.setBlob(i, x);
        this.addParameter(i, x);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.prepareStatement.setBoolean(parameterIndex, x);
        this.addParameter(parameterIndex, new Boolean(x));
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.prepareStatement.setByte(parameterIndex, x);
        this.addParameter(parameterIndex, new Integer(x));
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.prepareStatement.setBytes(parameterIndex, x);
        this.addParameter(parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        this.prepareStatement.setCharacterStream(parameterIndex, reader, length);
        this.addParameter(parameterIndex, reader);
    }

    public void setClob(int i, Clob x) throws SQLException {
        this.prepareStatement.setClob(i, x);
        this.addParameter(i, x);
    }

    public void setCursorName(String name) throws SQLException {
        this.prepareStatement.setCursorName(name);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.prepareStatement.setDate(parameterIndex, x);
        this.addParameter(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        this.prepareStatement.setDate(parameterIndex, x, cal);
        this.addParameter(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.prepareStatement.setDouble(parameterIndex, x);
        this.addParameter(parameterIndex, new Double(x));
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        this.prepareStatement.setEscapeProcessing(enable);
    }

    public void setFetchDirection(int direction) throws SQLException {
        this.prepareStatement.setFetchDirection(direction);
    }

    public void setFetchSize(int rows) throws SQLException {
        this.prepareStatement.setFetchSize(rows);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.prepareStatement.setFloat(parameterIndex, x);
        this.addParameter(parameterIndex, new Float(x));
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        this.prepareStatement.setInt(parameterIndex, x);
        this.addParameter(parameterIndex, new Integer(x));
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        this.prepareStatement.setLong(parameterIndex, x);
        this.addParameter(parameterIndex, new Long(x));
    }

    public void setMaxFieldSize(int max) throws SQLException {
        this.prepareStatement.setMaxFieldSize(max);
    }

    public void setMaxRows(int max) throws SQLException {
        this.prepareStatement.setMaxRows(max);
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.prepareStatement.setNull(parameterIndex, sqlType);
        this.addParameter(parameterIndex, (Object) null);
    }

    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
        this.prepareStatement.setNull(paramIndex, sqlType, typeName);
        this.addParameter(paramIndex, (Object) null);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        this.prepareStatement.setObject(parameterIndex, x);
        this.addParameter(parameterIndex, x);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        this.prepareStatement.setObject(parameterIndex, x, targetSqlType);
        this.addParameter(parameterIndex, x);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
        this.prepareStatement.setObject(parameterIndex, x, targetSqlType, scale);
        this.addParameter(parameterIndex, x);
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        this.prepareStatement.setQueryTimeout(seconds);
    }

    public void setRef(int i, Ref x) throws SQLException {
        this.prepareStatement.setRef(i, x);
        this.addParameter(i, x);
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        this.prepareStatement.setShort(parameterIndex, x);
        this.addParameter(parameterIndex, new Integer(x));
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        this.prepareStatement.setString(parameterIndex, x);
        this.addParameter(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.prepareStatement.setTime(parameterIndex, x);
        this.addParameter(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        this.prepareStatement.setTime(parameterIndex, x, cal);
        this.addParameter(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        this.prepareStatement.setTimestamp(parameterIndex, x);
        this.addParameter(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        this.prepareStatement.setTimestamp(parameterIndex, x, cal);
        this.addParameter(parameterIndex, x);
    }

    @Deprecated
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.prepareStatement.setUnicodeStream(parameterIndex, x, length);
        this.addParameter(parameterIndex, x);
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return this.prepareStatement.getParameterMetaData();
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        this.prepareStatement.setAsciiStream(parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.prepareStatement.setAsciiStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        this.prepareStatement.setBinaryStream(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.prepareStatement.setBinaryStream(parameterIndex, x, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        this.prepareStatement.setBlob(parameterIndex, inputStream);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        this.prepareStatement.setBlob(parameterIndex, inputStream, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        this.prepareStatement.setCharacterStream(parameterIndex, reader);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        this.prepareStatement.setCharacterStream(parameterIndex, reader, length);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.prepareStatement.setClob(parameterIndex, reader);
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.prepareStatement.setClob(parameterIndex, reader, length);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        this.prepareStatement.setNCharacterStream(parameterIndex, value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        this.prepareStatement.setNCharacterStream(parameterIndex, value, length);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.prepareStatement.setNClob(parameterIndex, value);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.prepareStatement.setNClob(parameterIndex, reader);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.prepareStatement.setNClob(parameterIndex, reader, length);
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        this.prepareStatement.setNString(parameterIndex, value);
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.prepareStatement.setRowId(parameterIndex, x);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        this.prepareStatement.setSQLXML(parameterIndex, xmlObject);
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.prepareStatement.setURL(parameterIndex, x);
    }

    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        long startTime = System.currentTimeMillis();

        boolean flag;
        try {
            flag = this.prepareStatement.execute(sql, autoGeneratedKeys);
        } finally {
            this.executionCompleted(startTime);
        }

        return flag;
    }

    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        long startTime = System.currentTimeMillis();

        boolean flag;
        try {
            flag = this.prepareStatement.execute(sql, columnIndexes);
        } finally {
            this.executionCompleted(startTime);
        }

        return flag;
    }

    public boolean execute(String sql, String[] columnNames) throws SQLException {
        long startTime = System.currentTimeMillis();

        boolean flag;
        try {
            flag = this.prepareStatement.execute(sql, columnNames);
        } finally {
            this.executionCompleted(startTime);
        }

        return flag;
    }

    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        long startTime = System.currentTimeMillis();

        int cnt;
        try {
            cnt = this.prepareStatement.executeUpdate(sql, autoGeneratedKeys);
        } finally {
            this.executionCompleted(startTime);
        }

        return cnt;
    }

    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        long startTime = System.currentTimeMillis();

        int cnt;
        try {
            cnt = this.prepareStatement.executeUpdate(sql, columnIndexes);
        } finally {
            this.executionCompleted(startTime);
        }

        return cnt;
    }

    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        long startTime = System.currentTimeMillis();

        int cnt;
        try {
            cnt = this.prepareStatement.executeUpdate(sql, columnNames);
        } finally {
            this.executionCompleted(startTime);
        }

        return cnt;
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return this.prepareStatement.getGeneratedKeys();
    }

    public boolean getMoreResults(int current) throws SQLException {
        return this.prepareStatement.getMoreResults();
    }

    public int getResultSetHoldability() throws SQLException {
        return this.prepareStatement.getResultSetHoldability();
    }

    public boolean isClosed() throws SQLException {
        return this.prepareStatement.isClosed();
    }

    public boolean isPoolable() throws SQLException {
        return this.prepareStatement.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        this.prepareStatement.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return prepareStatement.isCloseOnCompletion();
    }

    public void setPoolable(boolean poolable) throws SQLException {
        this.prepareStatement.setPoolable(poolable);
    }

    public boolean isWrapperFor(Class iface) throws SQLException {
        return this.prepareStatement.isWrapperFor(iface);
    }

    public Object unwrap(Class iface) throws SQLException {
        return this.prepareStatement.unwrap(iface);
    }

    public void addBatch() throws SQLException {
        this.prepareStatement.addBatch();
    }

    public void addBatch(String sql) throws SQLException {
        this.prepareStatement.addBatch(sql);
    }

    public void cancel() throws SQLException {
        this.prepareStatement.cancel();
    }

    public void clearBatch() throws SQLException {
        this.prepareStatement.clearBatch();
    }

    public void clearParameters() throws SQLException {
        this.prepareStatement.clearParameters();
    }

    public void clearWarnings() throws SQLException {
        this.prepareStatement.clearWarnings();
    }

    public void close() throws SQLException {
        this.prepareStatement.close();
    }
}
