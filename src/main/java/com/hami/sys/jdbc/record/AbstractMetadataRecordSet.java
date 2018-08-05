package com.hami.sys.jdbc.record;

import java.util.*;

/**
 * <pre>
 * <li>Program Name : AbstractMetadataRecordSet
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public abstract class AbstractMetadataRecordSet implements RecordSet {
    protected class RowIterator implements ListIterator {

        int currentIndex;
        int length;
        private RowCursor enumCursor;
        final AbstractMetadataRecordSet abstractMetadataRecordSet;

        public boolean hasNext() {
            return currentIndex + 1 < length && length > 0;
        }

        public void reset() {
            currentIndex = -1;
        }

        public void remove() {
            throw new UnsupportedOperationException("RowIterator.remove is unsupported operation.");
        }

        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        public void add(Record row) {
            throw new UnsupportedOperationException("RowIterator.add is not supported.");
        }

        public int nextIndex() {
            return currentIndex + 1;
        }

        public int previousIndex() {
            return currentIndex - 1;
        }

        public void set(Record row) {
            throw new UnsupportedOperationException("RowIterator.set is not supported.");
        }

        public void add(Object row) {
            add((Record) row);
        }

        public void set(Object row) {
            set((Record) row);
        }

        public Object previous() {
            return previous();
        }

        public Object next() {
            return next();
        }

        RowIterator() {
            super();
            abstractMetadataRecordSet = AbstractMetadataRecordSet.this;
            currentIndex = -1;
            enumCursor = new RowCursor();
            length = getRowCount();
        }
    }

    public class RowCursor extends Record implements RecordMetadataDefined {

        private static final long serialVersionUID = 51251079L;
        Object currentRow[];
        final AbstractMetadataRecordSet abstractMetadataRecordSet;

        public void clear() {
            super.clear();
            currentRow = null;
        }

        public RecordMetadata getMetadata() {
            return metadata;
        }

        public Object[] getValues() {
            return currentRow;
        }

        void setRow(Object row[]) {
            currentRow = row;
        }

        public Iterable getColumns() {
            return metadata;
        }

        public int getColumnCount() {
            return metadata.size();
        }

        public Object getObject(String fieldName, Object def) {
            Object value = getObject(fieldName);
            return value == null ? def : value;
        }

        public Object getObject(String fieldName) {
            int index = getKeyIndex(fieldName);
            return getObjectByIndex(index);
        }

        public Object getObjectByIndex(int index) {
            if (index > -1)
                return currentRow[index];
            else
                return null;
        }

        public void remove(String fieldName) {
            set(fieldName, null);
        }

        public Record set(String fieldName, Object value) {
            int index = getKeyIndex(fieldName);
            if (index > -1)
                currentRow[index] = value;
            return this;
        }

        public RecordMetadataDefined set(int index, Object value) {
            if (index > -1)
                currentRow[index] = value;
            else
                throw new IllegalArgumentException((new StringBuilder()).append("metadata field(=").append(index)
                        .append(") is not defined.").toString());
            return this;
        }

        public Object get(int index) {
            return currentRow[index];
        }

        public boolean containsKey(Object key) {
            if (key instanceof String)
                return metadata.contains((String) key);
            else
                return false;
        }

        public boolean containsValue(Object value) {
            return Arrays.binarySearch(currentRow, value) > 0;
        }

        public Set entrySet() {
            return (Set) metadata.getKeys();
        }

        public Set keySet() {
            return metadata;
        }

        public void putAll(Map t) {
            Iterator i$ = metadata.iterator();
            do {
                if (!i$.hasNext()) break;
                String key = (String) i$.next();
                Object value = t.get(key);
                if (value != null) set(key, value);
            } while (true);
        }

        public Object remove(Object key) {
            throw new UnsupportedOperationException("MetadataRecordSet#RowCursor.remove is not supported.");
        }

        public Collection values() {
            return Arrays.asList(currentRow);
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Record[");
            int len = getColumnCount();
            for (int i = 0; i < len; i++)
                buffer.append("\n\t").append(metadata.getKey(i)).append("=").append(currentRow[i]);

            buffer.append("\n]");
            return buffer.toString();
        }

        protected RowCursor() {
            super();
            abstractMetadataRecordSet = AbstractMetadataRecordSet.this;
        }

        protected RowCursor(Object row[]) {
            super();
            abstractMetadataRecordSet = AbstractMetadataRecordSet.this;
            currentRow = row;
        }
    }

    private RecordMetadata metadata;

    public AbstractMetadataRecordSet(RecordMetadata metadata) {
        setMetadata(metadata);
    }

    public AbstractMetadataRecordSet(String columnNames[]) {
        setMetadata(columnNames);
    }

    public AbstractMetadataRecordSet(Class recordClass) {
        setMetadata(recordClass);
    }

    protected final void setMetadata(RecordMetadata metadata) {
        if (metadata == null) {
            throw new IllegalArgumentException("this RecordSet needs  RecordMetadata set, before using this object.");
        } else {
            this.metadata = metadata;
            return;
        }
    }

    protected final void setMetadata(String columnNames[]) {
        if (columnNames == null) {
            throw new IllegalArgumentException("this RecordSet needs  RecordMetadata set, before using this object.");
        } else {
            setMetadata(new RecordMetadata(columnNames));
            return;
        }
    }

    protected final void setMetadata(Class recordClass) {
        RecordMetadata metadata = RecordUtils.generateRecordMetadata(recordClass);
        if (metadata == null) {
            throw new IllegalArgumentException("this RecordSet needs  RecordMetadata set, before using this object.");
        } else {
            setMetadata(metadata);
            return;
        }
    }

    public final RecordMetadata getMetadata() {
        return metadata;
    }

    public final Iterable getColumns() {
        return metadata;
    }

    public final int getColumnCount() {
        return metadata.size();
    }

    protected final int getKeyIndex(String columnName) {
        return metadata.getIndex(columnName);
    }

    public final Record getRecord(int rowIndex) {
        Object row[] = (Object[]) getRow(rowIndex);
        return new RowCursor(row);
    }

    protected abstract int addRow(Object aobj[]);

    public final Record addRecord() {
        Object row[] = new Object[metadata.size()];
        int index = addRow(row);
        return getRecord(index);
    }

    public Iterator iterator() {
        return listIterator();
    }

    public Record set(int index, Record element) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.set is not supported.");
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.toArray is not supported.");
    }

    public Object[] toArray(Object a[]) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.toArray is not supported.");
    }

    public void add(int index, Record element) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.add is not supported.");
    }

    public boolean add(Record row) {
        Record record = addRecord();
        record.putAll(row);
        return true;
    }

    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.addAll is not supported.");
    }

    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.addAll is not supported.");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.contains is not supported.");
    }

    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.containsAll is not supported.");
    }

    public Record get(int index) {
        return getRecord(index);
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.indexOf is not supported.");
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.lastIndexOf is not supported.");
    }

    public ListIterator listIterator() {
        return new RowIterator();
    }

    public ListIterator listIterator(int index) {
        RowIterator it = new RowIterator();
        it.currentIndex = index;
        return it;
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.remove is not supported.");
    }

    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.removeAll is not supported.");
    }

    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("AbstractMetadataRecordSet.retainAll is not supported.");
    }

    public int size() {
        return getRowCount();
    }

    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Record.subList is not supported.");
    }

    public Object remove(int index) {
        return remove(index);
    }

    public void add(int index, Object row) {
        add(index, (Record) row);
    }

    public Object set(int index, Object row) {
        return set(index, (Record) row);
    }

    public boolean add(Object row) {
        return add((Record) row);
    }
}
