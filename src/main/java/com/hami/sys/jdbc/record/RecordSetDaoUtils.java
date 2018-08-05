package com.hami.sys.jdbc.record;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * <pre>
 * <li>Program Name : RecordSetDaoUtils
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public final class RecordSetDaoUtils {
    private RecordSetDaoUtils() {
    }

    public static SqlParameterSource getSqlParameterSource(RecordMetadataDefined metadataRecord) {
        return new RecordMetadataSqlParameterSource(metadataRecord.getMetadata(), metadataRecord.getValues());
    }

    public static SqlParameterSource getSqlParameterSource(MapRecord mapRecord) {
        return new MapSqlParameterSource(mapRecord);
    }

    public static SqlParameterSource getSqlParameterSource(Record record) {
        if (record instanceof RecordMetadataDefined)
            return getSqlParameterSource((RecordMetadataDefined) record);
        else
            return new MapSqlParameterSource(record);
    }

    public static SqlParameterSource[] getSqlParameterSources(RecordSet recordSet) {
        RecordMetadata recordMetadata = recordSet.getMetadata();
        int length = recordSet.size();
        RecordMetadataSqlParameterSource paramArray[] = new RecordMetadataSqlParameterSource[length];
        for (int i = length - 1; i >= 0; i--)
            paramArray[i] = new RecordMetadataSqlParameterSource(recordMetadata, recordSet.getRow(i));

        return paramArray;
    }
}
