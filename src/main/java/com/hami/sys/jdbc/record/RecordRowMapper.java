package com.hami.sys.jdbc.record;

import com.hami.sys.jdbc.ColumnNameConvertor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <pre>
 * <li>Program Name : RecordRowMapper
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public abstract class RecordRowMapper implements RowMapper {
    protected ColumnNameConvertor columnNameConvertor;

    protected RecordRowMapper() {
        columnNameConvertor = new RecordSetRowHandler.ColumnNameCamelizer();
    }

    public ColumnNameConvertor getColumnNameConvertor() {
        return columnNameConvertor;
    }

    public void setColumnNameConvertor(ColumnNameConvertor columnNameConvertor) {
        this.columnNameConvertor = columnNameConvertor;
    }

    public final Record mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Record record = createRecord();
        for (int i = 1; i <= columnCount; i++) {
            String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
            Object obj = getColumnValue(rs, i);
            record.set(key, obj);
        }

        if (record instanceof RecordMetadataDefined) {
            RecordMetadata metadata = ((RecordMetadataDefined) record).getMetadata();
            if (metadata instanceof DbRecordMetadata) {
                DbRecordMetadata dbMeta = (DbRecordMetadata) metadata;
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i <= columnCount; i++) {
                    String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
                    dbMeta.consumeResultSetMetaData(key, i, resultSetMetaData);
                }

            }
        }
        return record;
    }

    protected abstract Record createRecord();

    protected String getColumnKey(String columnName) {
        return columnNameConvertor.convert(columnName);
    }

    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index);
    }

}
