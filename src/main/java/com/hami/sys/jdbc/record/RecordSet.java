package com.hami.sys.jdbc.record;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * <li>Program Name : RecordSet
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public interface RecordSet extends Serializable, List {
    public abstract RecordMetadata getMetadata();

    public abstract int getRowCount();

    public abstract int getColumnCount();

    public abstract Iterable getColumns();

    public abstract Record getRecord(int i);

    public abstract Object[] getRow(int i);

    public abstract Record addRecord();

    public abstract Object getValue(int i, int j);
}
