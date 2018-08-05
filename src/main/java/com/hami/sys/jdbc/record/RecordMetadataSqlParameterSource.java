package com.hami.sys.jdbc.record;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

/**
 * <pre>
 * <li>Program Name : RecordMetadataSqlParameterSource
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class RecordMetadataSqlParameterSource implements SqlParameterSource {
    private RecordMetadata recordMetadata;
    private Object values[];

    public RecordMetadataSqlParameterSource(RecordMetadata recordMetadata, Object values[]) {
        this.recordMetadata = recordMetadata;
        this.values = values;
        Assert.notNull(recordMetadata, "RecordMetadata must not be null.");
        Assert.notNull(((Object) (values)), "values must not be null.");
        if (values.length < recordMetadata.size())
            throw new IllegalArgumentException("values length is shorter than recordMetadata's size.");
        else
            return;
    }

    public Object getValue(String paramName) throws IllegalArgumentException {
        int index = recordMetadata.getIndex(paramName);
        if (index < 0)
            throw new IllegalArgumentException(
                    (new StringBuilder()).append("No key '").append(paramName).append("' is registered.").toString());
        else
            return values[index];
    }

    public boolean hasValue(String paramName) {
        return recordMetadata.contains(paramName);
    }

    public int getSqlType(String paramName) {
        return -2147483648;
    }

    public String getTypeName(String paramName) {
        return null;
    }
}
