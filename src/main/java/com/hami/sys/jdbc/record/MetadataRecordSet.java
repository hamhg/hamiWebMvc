package com.hami.sys.jdbc.record;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * <li>Program Name : MetadataRecordSet
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class MetadataRecordSet extends AbstractMetadataRecordSet {
    private static final long serialVersionUID = 685299939L;
    private List rowList;

    public MetadataRecordSet(RecordMetadata metadata) {
        super(metadata);
        rowList = new ArrayList();
    }

    public MetadataRecordSet(String columnNames[]) {
        super(columnNames);
        rowList = new ArrayList();
    }

    public MetadataRecordSet(Class recordClass) {
        super(recordClass);
        rowList = new ArrayList();
    }

    public void clear() {
        rowList.clear();
    }

    public int getRowCount() {
        return rowList.size();
    }

    public Object[] getRow(int index) {
        return (Object[]) rowList.get(index);
    }

    protected int addRow(Object row[]) {
        rowList.add(((Object) (row)));
        return rowList.size() - 1;
    }

    public Object getValue(int row, int column) {
        Object valueArray[] = (Object[]) (Object[]) rowList.get(row);
        return valueArray[column];
    }

    public List subList(int fromIndex, int toIndex) {
        MetadataRecordSet clone = new MetadataRecordSet(getMetadata());
        clone.rowList.addAll(rowList.subList(fromIndex, toIndex));
        return clone;
    }

    public Object[] toArray() {
        Object result[] = new Object[rowList.size()];
        for (int i = 0; i < rowList.size(); i++)
            result[i] = getRecord(i);

        return result;
    }
}
