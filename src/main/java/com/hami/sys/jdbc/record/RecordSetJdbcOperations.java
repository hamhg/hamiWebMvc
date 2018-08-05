package com.hami.sys.jdbc.record;

import com.hami.sys.jdbc.ColumnNameConvertor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * <pre>
 * <li>Program Name : RecordSetJdbcOperations
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public interface RecordSetJdbcOperations extends NamedParameterJdbcOperations {

    public abstract void setColumnNameConvertor(ColumnNameConvertor columnnameconvertor);

    public abstract void setRichMetadata(boolean flag);

    public abstract RecordSet queryForRecordSet(String sql, SqlParameterSource sqlparametersource, RecordSetRowHandler recordsetrowhandler);

    public abstract RecordSet queryForRecordSet(String sql, SqlParameterSource sqlparametersource, RecordMetadata recordmetadata);

    public abstract RecordSet queryForRecordSet(String sql, SqlParameterSource sqlparametersource);

    public abstract RecordSet queryForRecordSet(String sql, Record record, RecordSetRowHandler recordsetrowhandler);

    public abstract RecordSet queryForRecordSet(String sql, Record record, RecordMetadata recordmetadata);

    public abstract RecordSet queryForRecordSet(String sql, Record record);

    public abstract RecordSet queryForRecordSet(String sql, RecordMetadataDefined recordmetadatadefined);

    public abstract Record queryForRecord(String sql, SqlParameterSource sqlparametersource);

    public abstract Record queryForRecord(String sql, Record record, RecordMetadata recordmetadata);

    public abstract Record queryForRecord(String sql, Record record);

    public abstract Record queryForRecord(String sql, SqlParameterSource sqlparametersource, RecordMetadata recordmetadata);

    public abstract int[] batchUpdate(String sql, RecordSet recordset);

    public abstract int update(String sql, Record record);
}
