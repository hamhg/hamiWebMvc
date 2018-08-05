package com.hami.sys.jdbc.record;

import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * <li>Program Name : MetadataRecordArray
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class MetadataRecordArray extends AbstractMetadataRecordSet {

    private static final long serialVersionUID = -371389943L;
    private Object dataArray[][];
    private int size;

    public MetadataRecordArray(RecordMetadata metadata, int length) {
        super(metadata);
        dataArray = new Object[length][];
    }

    public MetadataRecordArray(String columnNames[], int length) {
        this(new RecordMetadata(columnNames), length);
    }

    protected MetadataRecordArray(RecordMetadata metadata, Object dataArray[][]) {
        super(metadata);
        this.dataArray = dataArray;
    }

    public void clear() {
        Arrays.fill(((Object[]) (dataArray)), null);
        size = 0;
    }

    public int getRowCount() {
        return size;
    }

    protected int addRow(Object row[]) {
        if (size < dataArray.length) {
            dataArray[size] = row;
        } else {
            Object temp[][] = new Object[dataArray.length + 5][];
            System.arraycopy(((Object) (dataArray)), 0, ((Object) (temp)), 0, dataArray.length);
            temp[size] = row;
            dataArray = temp;
        }
        size++;
        return size - 1;
    }

    public Object[] getRow(int index) {
        return dataArray[index];
    }

    public Object getValue(int row, int column) {
        Object valueArray[] = getRow(row);
        return valueArray[column];
    }

    public List subList(int fromIndex, int toIndex) {
        int length = toIndex - fromIndex;
        Object temp[][] = new Object[length][];
        System.arraycopy(((Object) (dataArray)), 0, ((Object) (temp)), 0, length);
        MetadataRecordArray clone = new MetadataRecordArray(getMetadata(), temp);
        clone.size = length;
        return clone;
    }

    public Object[] toArray() {
        Object result[] = new Object[size];
        for (int i = 0; i < size; i++)
            result[i] = getRecord(i);

        return result;
    }

    public Object[] toArray(Object obj[]) {
        Class componentType = ((Object) obj).getClass().getComponentType();
        if (obj.length < size) {
            for (int i = 0; i < obj.length; i++)
                if (ClassUtils.isAssignable(componentType, Record.class))
                    obj[i] = getRecord(i);
                else
                    obj[i] = RecordUtils.convertToBean(getRecord(i), componentType);

            return obj;
        }
        for (int i = 0; i < size; i++)
            if (ClassUtils.isAssignable(componentType, Record.class))
                obj[i] = getRecord(i);
            else
                obj[i] = RecordUtils.convertToBean(getRecord(i), componentType);

        if (obj.length > size)
            obj[size] = null;
        return obj;
    }
}
