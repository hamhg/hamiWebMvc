package com.hami.sys.jdbc.record;

/**
 * <pre>
 * <li>Program Name : RecordMetadataDefined
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public interface RecordMetadataDefined {
    public abstract RecordMetadata getMetadata();

    public abstract Object[] getValues();

    public abstract RecordMetadataDefined set(int i, Object obj);

    public abstract Object get(int i);
}
