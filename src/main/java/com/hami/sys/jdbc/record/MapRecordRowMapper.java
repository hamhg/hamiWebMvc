package com.hami.sys.jdbc.record;

/**
 * <pre>
 * <li>Program Name : MapRecordRowMapper
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class MapRecordRowMapper extends RecordRowMapper {
    protected MapRecord createRecord() {
        return new MapRecord();
    }
}
