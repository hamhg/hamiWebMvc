package com.hami.sys.jdbc.record;

import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <pre>
 * <li>Program Name : SimpleRecordSetRowHandler
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class SimpleRecordSetRowHandler extends RecordSetRowHandler {
    private String fieldNames[];

    public SimpleRecordSetRowHandler() {
        super((RecordMetadata) null);
    }

    protected RecordSet beforeProcessing(ResultSet rs, RecordMetadata metadata) throws SQLException {
        applySettings(rs);
        ResultSetMetaData meta = rs.getMetaData();
        int colLength = meta.getColumnCount();
        int rowLength = rs.getFetchSize();
        fieldNames = new String[colLength];
        for (int i = colLength; i > 0; i--) {
            String colName = meta.getColumnLabel(i);
            String fieldName = convertColumnName(colName);
            fieldNames[i - 1] = fieldName;
        }

        metadata = new RecordMetadata(fieldNames);
        RecordSet recordSet = createRecordSet(metadata, rowLength);
        return recordSet;
    }

    protected void processRowInternal(ResultSet rs, RecordMetadata metadata, RecordSet recordSet) throws SQLException {
        Record record = recordSet.addRecord();
        for (int i = fieldNames.length - 1; i >= 0; i--) {
            Object value = JdbcUtils.getResultSetValue(rs, i + 1);
            record.set(fieldNames[i], value);
        }

        if (getRecordDataHandler() != null)
            getRecordDataHandler().handle(record);
    }
}
