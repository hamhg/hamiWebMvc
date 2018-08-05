package com.hami.sys.jdbc.record;

import com.hami.sys.jdbc.ColumnNameConvertor;
import com.hami.sys.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <pre>
 * <li>Program Name : RecordSetRowHandler
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public abstract class RecordSetRowHandler implements RowCallbackHandler {

    protected static class ColumnNameCamelizer implements ColumnNameConvertor {
        public String convert(String columnName) {
            return StringUtils.camelize(columnName);
        }
    }

    public final Logger log = LoggerFactory.getLogger(this.getClass());
    protected static final ColumnNameConvertor DEFAULT_COL_NAME_CONVERTER = new ColumnNameCamelizer();
    private RecordSet recordSet;
    private RecordMetadata metadata;
    private ColumnNameConvertor columnNameConvertor;
    private RecordDataHandler recordDataHandler;
    private int fetchSize;

    protected RecordSetRowHandler(RecordMetadata metadata) {
        columnNameConvertor = DEFAULT_COL_NAME_CONVERTER;
        fetchSize = 0;
        setMetadata(metadata);
    }

    protected void setMetadata(RecordMetadata metadata) {
        this.metadata = metadata;
    }

    public void setColumnNameConvertor(ColumnNameConvertor columnNameConvertor) {
        this.columnNameConvertor = columnNameConvertor;
    }

    public ColumnNameConvertor getColumnNameConvertor() {
        return columnNameConvertor;
    }

    public void setRecordDataHandler(RecordDataHandler recordDataHandler) {
        this.recordDataHandler = recordDataHandler;
    }

    public RecordDataHandler getRecordDataHandler() {
        return recordDataHandler;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    protected void applySettings(ResultSet rs) {
        if (fetchSize > 0)
            try {
                rs.setFetchSize(fetchSize);
                log.debug(new StringBuilder().append("apply settings : fetchSize to ").append(fetchSize).toString());
            } catch (SQLException e) {
                log.warn("Failed to set fetch size to ResultSet", e);
            }
    }

    protected String convertColumnName(String columnName) {
        return columnNameConvertor.convert(columnName);
    }

    public final void processRow(ResultSet rs) throws SQLException {
        if (recordSet == null)
            recordSet = beforeProcessing(rs, metadata);
        processRowInternal(rs, metadata, recordSet);
    }

    protected abstract RecordSet beforeProcessing(ResultSet resultset, RecordMetadata recordmetadata)
            throws SQLException;

    protected abstract void processRowInternal(ResultSet resultset, RecordMetadata recordmetadata, RecordSet recordset)
            throws SQLException;

    protected RecordSet createRecordSet(RecordMetadata metadata, int rowSize) {
        return new MetadataRecordArray(metadata, rowSize);
    }

    public final RecordSet getRecordSet() {
        if (recordSet == null)
            return new MetadataRecordArray(new RecordMetadata(new String[0]), 0);
        else
            return recordSet;
    }
}
