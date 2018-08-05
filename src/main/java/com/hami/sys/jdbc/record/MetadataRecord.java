package com.hami.sys.jdbc.record;

import org.springframework.util.Assert;

import java.util.*;

/**
 * <pre>
 * <li>Program Name : MetadataRecord
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class MetadataRecord extends Record implements RecordMetadataDefined {
    private static final long serialVersionUID = 6205868657998959151L;
    private RecordMetadata metadata;
    private Object[] dataArray;
    private EntrySet entrySet;
    private boolean dataExist;

    public MetadataRecord(RecordMetadata metadata) {
        Assert.notNull(metadata, "RecordMetadata must not be null");
        this.metadata = metadata;
        this.dataArray = new Object[metadata.size()];
    }

    public MetadataRecord(String[] columns) {
        this(new RecordMetadata(columns));
    }

    public boolean hasData() {
        return this.dataExist;
    }

    public boolean isEmpty() {
        return !this.dataExist;
    }

    public void clear() {
        super.clear();
        Arrays.fill(this.dataArray, (Object) null);
        this.dataExist = false;
    }

    public void setData(Object[] row) {
        this.dataArray = row;
        this.dataExist = true;
    }

    public RecordMetadata getMetadata() {
        return this.metadata;
    }

    public Object[] getValues() {
        Object[] row = new Object[this.dataArray.length];
        System.arraycopy(this.dataArray, 0, row, 0, this.dataArray.length);
        return row;
    }

    public int getColumnCount() {
        return this.metadata.size();
    }

    public Iterable<String> getColumns() {
        return this.metadata;
    }

    public Object getObject(String fieldName, Object def) {
        Object value = this.getObject(fieldName);
        return value != null ? value : def;
    }

    protected int getKeyIndex(String columnName) {
        return this.metadata.getIndex(columnName);
    }

    public Object getObject(String fieldName) {
        int index = this.getKeyIndex(fieldName);
        return index > -1 ? this.dataArray[index] : null;
    }

    public RecordMetadataDefined set(int index, Object value) {
        if (index > -1) {
            this.dataArray[index] = value;
            this.dataExist = true;
            return this;
        } else {
            throw new IllegalArgumentException("metadata field(=" + index + ") is not defined.");
        }
    }

    public Object get(int index) {
        return this.dataArray[index];
    }

    public Record set(String fieldName, Object value) {
        int index = this.getKeyIndex(fieldName);
        if (index > -1) {
            this.dataArray[index] = value;
            this.dataExist = true;
            return this;
        } else {
            throw new IllegalArgumentException("metadata field(=" + fieldName + ") is not defined.");
        }
    }

    public void remove(String fieldName) {
        this.set(fieldName, (Object) null);
    }

    public void copyTo(MetadataRecord other) {
        if (this.metadata.equals(other.metadata)) {
            System.arraycopy(this.dataArray, 0, other.dataArray, 0, this.dataArray.length);
            this.dataExist = true;
        } else {
            throw new IllegalArgumentException(
                    "RecordMetadata can copy itself to other, only when other has same metadata.");
        }
    }

    public boolean containsKey(Object key) {
        return key instanceof String ? this.metadata.contains((String) key) : false;
    }

    public boolean containsValue(Object value) {
        return Arrays.binarySearch(this.dataArray, value) > 0;
    }

    public Set<Entry> entrySet() {
        Set es = this.entrySet;
        return es != null ? es : (this.entrySet = new EntrySet());
    }

    public Set<String> keySet() {
        return this.metadata;
    }

    public void putAll(Map t) {
        Iterator it = this.metadata.iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = t.get(key);
            if (value != null) {
                this.set(key, value);
            }
        }

    }

    public Object remove(Object key) {
        if (key instanceof String) {
            Object value = this.getObject((String) key);
            this.set((String) key, (Object) null);
            return value;
        } else {
            return null;
        }
    }

    public Collection<Object> values() {
        return Arrays.asList(this.dataArray);
    }

    private final class Entry implements java.util.Map.Entry {

        private String key;
        final MetadataRecord this$0;

        public Object getValue() {
            return getObject(key);
        }

        public Object setValue(Object value) {
            return set(key, value);
        }

        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (obj instanceof Entry) {
                Entry other = (Entry) obj;
                if (other.key.equals(key)) {
                    Object val = getValue();
                    Object val2 = other.getValue();
                    return val == val2 || val != null && val.equals(val2);
                }
            }
            return false;
        }

        public Object getKey() {
            return key;
        }

        public Entry(String key) {
            super();
            this$0 = MetadataRecord.this;
            this.key = key;
        }
    }

    private final class EntrySet extends AbstractSet {

        private Entry entries[];
        final MetadataRecord metadataRecord;

        private void init() {
            entries = new Entry[metadata.size()];
            for (int i = entries.length - 1; i >= 0; i--)
                entries[i] = new Entry(metadata.getKey(i));

        }

        public Iterator iterator() {
            Iterator iterator = new Iterator() {

                int cursor;
                final EntrySet entrySet;

                public void remove() {
                    if (cursor > -1 && cursor < entries.length)
                        MetadataRecord.this.remove(entries[cursor].key);
                }

                public java.util.Map.Entry nextEntries() {
                    cursor++;
                    return entries[cursor];
                }

                public boolean hasNext() {
                    return cursor + 1 < entries.length;
                }

                public Object next() {
                    return nextEntries();
                }

                {
                    entrySet = EntrySet.this;
                    cursor = -1;
                }
            };
            return iterator;
        }

        protected Entry getEntryAny(String key) {
            int index = metadata.getIndex(key);
            if (index > -1 && index < entries.length)
                return entries[index];
            else
                return null;
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            } else {
                Entry entry = (Entry) obj;
                Entry candidate = getEntryAny((String) entry.getKey());
                return candidate != null && candidate.equals(entry);
            }
        }

        public boolean remove(Object obj) {
            return false;
        }

        public int size() {
            return MetadataRecord.this.size();
        }

        public void clear() {
            MetadataRecord.this.clear();
        }

        public EntrySet() {
            super();
            metadataRecord = MetadataRecord.this;
            init();
        }
    }
}
