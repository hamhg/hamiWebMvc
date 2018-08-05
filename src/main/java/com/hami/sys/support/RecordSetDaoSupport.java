package com.hami.sys.support;

import com.hami.sys.jdbc.*;
import com.hami.sys.jdbc.record.*;
import com.hami.sys.jdbc.sql.CudHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : RecordSetDaoSupport
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class RecordSetDaoSupport implements BeanFactoryAware, InitializingBean {
    public final Logger log = LoggerFactory.getLogger(this.getClass());
    protected static final String DATASOURCE_BEANNAME = "dataSource";
    protected static final String DEF_KEY_GENERATOR_BEANNAME = "com.hami.fw.jdbc.sql.KeyGenerator";
    private static final String EXCEPTION_HANDLER_BEANNAME = "com.hami.fw.jdbc.sql.EmptyResultExceptionHandler";
    private KeyGenerator keyGenerator;
    private RecordSetJdbcOperations recordSetJdbcOperations;
    private EmptyResultExceptionHandler emptyResultExceptionHandler;
    private BeanFactory beanFactory;
    private DataSource dataSource;
    private ColumnNameConvertor columnNameConvertor;
    protected CudHandler cud;

    public void afterPropertiesSet() throws Exception {
        if (recordSetJdbcOperations == null) {
            if (dataSource == null && beanFactory.containsBean(DATASOURCE_BEANNAME))
                dataSource = beanFactory.getBean(DATASOURCE_BEANNAME, DataSource.class);
            else
                throw new BeanCreationException("DaoSupport에 dataSource나 recordSetJdbcOperations가 지정되지 않았습니다.");

            initTemplate(dataSource);
        }

        if (keyGenerator == null && beanFactory.containsBean(DEF_KEY_GENERATOR_BEANNAME))
            keyGenerator = beanFactory.getBean(DEF_KEY_GENERATOR_BEANNAME, KeyGenerator.class);

        if (emptyResultExceptionHandler == null && beanFactory.containsBean(EXCEPTION_HANDLER_BEANNAME))
            emptyResultExceptionHandler = beanFactory.getBean(EXCEPTION_HANDLER_BEANNAME,EmptyResultExceptionHandler.class);
        else
            emptyResultExceptionHandler = new DefaultEmptyResultExceptionHandler();

        if (columnNameConvertor != null)
            recordSetJdbcOperations.setColumnNameConvertor(columnNameConvertor);
        else
            recordSetJdbcOperations.setColumnNameConvertor(new ColumnNameCamelizer());
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public void setRecordSetJdbcOperations(RecordSetJdbcOperations recordSetJdbcOperations) {
        this.recordSetJdbcOperations = recordSetJdbcOperations;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    public ColumnNameConvertor getColumnNameConvertor() {
        return columnNameConvertor;
    }

    public void setColumnNameConvertor(ColumnNameConvertor columnNameConvertor) {
        this.columnNameConvertor = columnNameConvertor;
    }

    protected JdbcOperations getJdbcOperations() {
        return recordSetJdbcOperations.getJdbcOperations();
    }

    protected KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    protected RecordSetJdbcOperations getRecordSetJdbcOperations() {
        return recordSetJdbcOperations;
    }

    protected void initTemplate(DataSource dataSource) throws SQLException {
        recordSetJdbcOperations = new RecordSetJdbcTemplate(dataSource);
        cud = new CudHandler(dataSource);
    }

    protected int[] batchUpdate(String sql, RecordSet recordSet) {
        return recordSetJdbcOperations.batchUpdate(sql, recordSet);
    }

    protected int[] batchUpdate(String sql, SqlParameterSource batchArgs[]) {
        return recordSetJdbcOperations.batchUpdate(sql, batchArgs);
    }

    protected void execute(String sql) throws DataAccessException {
        recordSetJdbcOperations.getJdbcOperations().execute(sql);
    }

    protected Object execute(String callString, CallableStatementCallback action) throws DataAccessException {
        return recordSetJdbcOperations.getJdbcOperations().execute(callString, action);
    }

    protected Object execute(String sql, PreparedStatementCallback action) throws DataAccessException {
        return recordSetJdbcOperations.getJdbcOperations().execute(sql, action);
    }

    protected Object execute(String sql, SqlParameterSource paramSource, PreparedStatementCallback action)
            throws DataAccessException {
        return recordSetJdbcOperations.execute(sql, paramSource, action);
    }

    protected Object query(String sql, SqlParameterSource paramSource, ResultSetExtractor rse)
            throws DataAccessException {
        return recordSetJdbcOperations.query(sql, paramSource, rse);
    }

    protected void query(String sql, SqlParameterSource paramSource, RowCallbackHandler rch)
            throws DataAccessException {
        recordSetJdbcOperations.query(sql, paramSource, rch);
    }

    protected int queryForInt(String sql, Record parameters) throws DataAccessException {
        return ((Integer) recordSetJdbcOperations.queryForObject(sql, parameters, Integer.TYPE));
    }

    protected int queryForInt(String sql, SqlParameterSource sqlParams) throws DataAccessException {
        return ((Integer) recordSetJdbcOperations.queryForObject(sql, sqlParams, Integer.TYPE));
    }

    protected long queryForLong(String sql, Record parameters) throws DataAccessException {
        return ((Long) recordSetJdbcOperations.queryForObject(sql, parameters, Long.TYPE));
    }

    protected long queryForLong(String sql, SqlParameterSource sqlParams) throws DataAccessException {
        return ((Long) recordSetJdbcOperations.queryForObject(sql, sqlParams, Long.TYPE));
    }

    protected List queryForList(String sql, SqlParameterSource paramSource, Class elementType)
            throws DataAccessException {
        return recordSetJdbcOperations.queryForList(sql, paramSource, elementType);
    }

    protected List queryForList(String sql, SqlParameterSource paramSource, RowMapper rowMapper)
            throws DataAccessException {
        return recordSetJdbcOperations.query(sql, paramSource, rowMapper);
    }

    protected Object queryForObject(String sql, SqlParameterSource paramSource, Class requiredType)
            throws DataAccessException {
        try {
            return recordSetJdbcOperations.queryForObject(sql, paramSource, requiredType);
        } catch (EmptyResultDataAccessException e) {
            return emptyResultExceptionHandler.handle(sql, e);
        }
    }

    protected Object queryForObject(String sql, SqlParameterSource paramSource, RowMapper rowMapper)
            throws DataAccessException {
        try {
            return recordSetJdbcOperations.queryForObject(sql, paramSource, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return emptyResultExceptionHandler.handle(sql, e);
        }
    }

    protected Record queryForRecord(String sql, Record parameters) {
        return recordSetJdbcOperations.queryForRecord(sql, parameters);
    }

    protected Record queryForRecord(String sql, Record parameters, RecordMetadata metadata) {
        return recordSetJdbcOperations.queryForRecord(sql, parameters, metadata);
    }

    protected Record queryForRecord(String sql, SqlParameterSource sqlParam) {
        return recordSetJdbcOperations.queryForRecord(sql, sqlParam);
    }

    protected Record queryForRecord(String sql, SqlParameterSource sqlParam, RecordMetadata metadata) {
        return recordSetJdbcOperations.queryForRecord(sql, sqlParam, metadata);
    }

    protected RecordSet queryForRecordSet(String sql, Record parameters) {
        return recordSetJdbcOperations.queryForRecordSet(sql, parameters);
    }

    protected RecordSet queryForRecordSet(String sql, SqlParameterSource sqlParam) {
        return recordSetJdbcOperations.queryForRecordSet(sql, sqlParam);
    }

    protected RecordSet queryForRecordSet(String sql, SqlParameterSource sqlParam, RecordMetadata metadata) {
        return recordSetJdbcOperations.queryForRecordSet(sql, sqlParam, metadata);
    }

    protected int update(String sql, Record record) {
        return recordSetJdbcOperations.update(sql, (Map) record);
    }

    protected int update(String sql, SqlParameterSource sqlParam) throws DataAccessException {
        return recordSetJdbcOperations.update(sql, sqlParam);
    }
}
