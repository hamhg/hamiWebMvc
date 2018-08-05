package com.hami.sys.jdbc.record;

import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <pre>
 * <li>Program Name : NamedColumnRecordSetRowHandler
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class NamedColumnRecordSetRowHandler extends RecordSetRowHandler {
    private int columnIndices[];
    private String fieldNames[];
    private int fieldLength;

    public NamedColumnRecordSetRowHandler(RecordMetadata metadata) {
        super(metadata);
    }

    protected RecordSet beforeProcessing(ResultSet rs, RecordMetadata metadata) throws SQLException {
        applySettings(rs);
        ResultSetMetaData meta = rs.getMetaData();
        int colLength = meta.getColumnCount();
        int rowLength = rs.getFetchSize();
        columnIndices = new int[colLength];
        fieldNames = new String[metadata.size()];
        fieldLength = 0;
        for (int i = colLength; i > 0; i--) {
            String resultSetColumnName = meta.getColumnLabel(i);
            String fieldName = getMetadataFieldName(resultSetColumnName, metadata);
            if (fieldName != null) {
                fieldNames[fieldLength] = fieldName;
                columnIndices[fieldLength] = i;
                fieldLength++;
            }
        }

        RecordSet recordSet = createRecordSet(metadata, rowLength);
        return recordSet;
    }

    protected String getMetadataFieldName(String resultSetColumnName, RecordMetadata metadata) {
        String camelized = convertColumnName(resultSetColumnName);
        if (metadata.contains(camelized))
            return camelized;
        if (metadata.contains(resultSetColumnName))
            return resultSetColumnName;
        else
            return null;
    }

    protected void processRowInternal(ResultSet rs, RecordMetadata metadata, RecordSet recordSet) throws SQLException {
        Record record = recordSet.addRecord();
        for (int i = fieldLength - 1; i >= 0; i--) {
            Object value = JdbcUtils.getResultSetValue(rs, columnIndices[i]);
            record.set(fieldNames[i], value);
        }

        if (getRecordDataHandler() != null)
            getRecordDataHandler().handle(record);
    }
}
