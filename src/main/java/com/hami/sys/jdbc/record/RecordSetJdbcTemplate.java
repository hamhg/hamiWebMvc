package com.hami.sys.jdbc.record;

import com.hami.sys.jdbc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;

/**
 * <pre>
 * <li>Program Name : RecordSetJdbcTemplate
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class RecordSetJdbcTemplate extends NamedParameterJdbcTemplate implements RecordSetJdbcOperations {

    protected final Logger log;
    private boolean richMetadata;
    private ColumnNameConvertor columnNameConvertor;

    public RecordSetJdbcTemplate(DataSource dataSource) {
        super(dataSource);
        log = LoggerFactory.getLogger(this.getClass());
    }

    public RecordSetJdbcTemplate(JdbcOperations classicJdbcTemplate) {
        super(classicJdbcTemplate);
        log = LoggerFactory.getLogger(this.getClass());
    }

    public void setRichMetadata(boolean rich) {
        richMetadata = rich;
    }

    public boolean isRichMetadata() {
        return richMetadata;
    }

    public ColumnNameConvertor getColumnNameConvertor() {
        return columnNameConvertor;
    }

    public void setColumnNameConvertor(ColumnNameConvertor columnNameConvertor) {
        this.columnNameConvertor = columnNameConvertor;
    }

    protected RecordSetRowHandler getRecordSetRowHandler(RecordMetadata metadata) {
        RecordSetRowHandler rowHandler = null;
        if (metadata == null) {
            if (richMetadata)
                rowHandler = new RichMetadataRecordSetRowHandler(metadata);
            else
                rowHandler = new SimpleRecordSetRowHandler();
        } else if (metadata instanceof DbRecordMetadata)
            rowHandler = new RichMetadataRecordSetRowHandler(metadata);
        else if (richMetadata)
            rowHandler = new RichMetadataRecordSetRowHandler(new DbRecordMetadata(metadata));
        else
            rowHandler = new NamedColumnRecordSetRowHandler(metadata);
        if (columnNameConvertor != null)
            rowHandler.setColumnNameConvertor(columnNameConvertor);
        return rowHandler;
    }

    protected RecordRowMapper getRecordRowMapper(RecordMetadata metadata) {
        RecordRowMapper rowMapper = null;
        if (metadata == null)
            rowMapper = new MapRecordRowMapper();
        else
            rowMapper = new MetadataRecordRowMapper(metadata);
        if (columnNameConvertor != null)
            rowMapper.setColumnNameConvertor(columnNameConvertor);
        return rowMapper;
    }

    public RecordSet queryForRecordSet(String sql, SqlParameterSource sqlParam, RecordSetRowHandler rowHandler) {
        query(sql, sqlParam, rowHandler);
        RecordSet recordSet = rowHandler.getRecordSet();
        return recordSet;
    }

    public RecordSet queryForRecordSet(String sql, SqlParameterSource sqlParam, RecordMetadata metadata) {
        RecordSetRowHandler rowHandler = getRecordSetRowHandler(metadata);
        return queryForRecordSet(sql, sqlParam, rowHandler);
    }

    public RecordSet queryForRecordSet(String sql, SqlParameterSource sqlParam) {
        RecordSetRowHandler rowHandler = getRecordSetRowHandler(null);
        return queryForRecordSet(sql, sqlParam, rowHandler);
    }

    public RecordSet queryForRecordSet(String sql, Record parameters, RecordSetRowHandler rowHandler) {
        SqlParameterSource sqlParam = RecordSetDaoUtils.getSqlParameterSource(parameters);
        query(sql, sqlParam, rowHandler);
        RecordSet recordSet = rowHandler.getRecordSet();
        return recordSet;
    }

    public RecordSet queryForRecordSet(String sql, Record parameters, RecordMetadata metadata) {
        RecordSetRowHandler rowHandler = getRecordSetRowHandler(metadata);
        return queryForRecordSet(sql, parameters, rowHandler);
    }

    public RecordSet queryForRecordSet(String sql, Record parameters) {
        if (parameters instanceof RecordMetadataDefined) {
            return queryForRecordSet(sql, (RecordMetadataDefined) parameters);
        } else {
            RecordSetRowHandler rowHandler = getRecordSetRowHandler(null);
            return queryForRecordSet(sql, parameters, rowHandler);
        }
    }

    public RecordSet queryForRecordSet(String sql, RecordMetadataDefined parameters) {
        SqlParameterSource sqlParam = RecordSetDaoUtils.getSqlParameterSource(parameters);
        RecordSetRowHandler rowHandler = getRecordSetRowHandler(parameters.getMetadata());
        return queryForRecordSet(sql, sqlParam, rowHandler);
    }

    public Record queryForRecord(String sql, Record parameters, RecordMetadata metadata) {
        SqlParameterSource sqlParam = RecordSetDaoUtils.getSqlParameterSource(parameters);
        return queryForRecord(sql, sqlParam, metadata);
    }

    public Record queryForRecord(String sql, SqlParameterSource sqlParam, RecordMetadata metadata) {
        RecordRowMapper rowMapper = getRecordRowMapper(metadata);
        try {
            return (Record) queryForObject(sql, sqlParam, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.warn((new StringBuilder()).append("Empty ResultData at queryForObject[").append(sql).append("]").toString());
        }
        return null;
    }

    public Record queryForRecord(String sql, Record parameters) {
        if (parameters instanceof RecordMetadataDefined) {
            return queryForRecord(sql, parameters, ((RecordMetadataDefined) parameters).getMetadata());
        } else {
            SqlParameterSource sqlParam = RecordSetDaoUtils.getSqlParameterSource(parameters);
            return queryForRecord(sql, sqlParam);
        }
    }

    public Record queryForRecord(String sql, SqlParameterSource sqlParam) {
        RecordRowMapper rowMapper = getRecordRowMapper(null);
        try {
            return (Record) queryForObject(sql, sqlParam, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.warn((new StringBuilder()).append("Empty ResultData at queryForObject[").append(sql).append("]").toString());
        }
        return null;
    }

    public int[] batchUpdate(String sql, RecordSet recordSet) {
        SqlParameterSource params[] = RecordSetDaoUtils.getSqlParameterSources(recordSet);
        return batchUpdate(sql, params);
    }

    public int update(String sql, Record parameters) {
        SqlParameterSource sqlParam = RecordSetDaoUtils.getSqlParameterSource(parameters);
        return update(sql, sqlParam);
    }
}
