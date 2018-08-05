package com.hami.sys.jdbc.record;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

/**
 * <pre>
 * <li>Program Name : RecordMetadata
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class RecordMetadata extends AbstractSet implements Serializable, Iterable {
    private class StringArrayIterator implements Iterator {

        String array[];
        int cursor;
        final RecordMetadata rmd;

        public boolean hasNext() {
            return cursor + 1 < array.length;
        }

        public void remove() {
            throw new RuntimeException("remove operation is not supported.");
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException("StringEnumerator excess list size.");
            } else {
                cursor++;
                return array[cursor];
            }
        }

        public StringArrayIterator(String array[]) {
            rmd = RecordMetadata.this;
            cursor = -1;
            if (array == null)
                array = RecordMetadata.EMPTY_STRING_ARRAY;
            this.array = array;
        }
    }

    private static class Entry implements Serializable {

        private static final long serialVersionUID = -368387423L;
        Object key;
        int index;
        Entry next;

        Entry(Object key, int index, Entry next) {
            this.key = key;
            this.index = index;
            this.next = next;
        }
    }

    private static final long serialVersionUID = -136043300L;
    public static final String EMPTY_STRING_ARRAY[] = new String[0];
    private String keyArray[];
    private Entry entryArray[];

    public RecordMetadata(String keys[]) {
        init(keys);
    }

    protected RecordMetadata(RecordMetadata other) {
        init(other.getKeyArray());
    }

    private void init(String keys[]) {
        if (keys == null)
            throw new IllegalArgumentException("keys must not be null");
        keyArray = keys;
        entryArray = new Entry[(int) ((double) keyArray.length * 1.2D)];
        for (int i = keyArray.length - 1; i >= 0; i--)
            setIndex(keyArray[i], i);

    }

    protected String[] getKeyArray() {
        return keyArray;
    }

    private void setIndex(String key, int index) {
        int hash = key.hashCode();
        int entryIdx = (hash & 2147483647) % entryArray.length;
        Entry e;
        for (e = entryArray[entryIdx]; e != null; e = e.next)
            if (e.key == key || e.key.equals(key))
                throw new IllegalArgumentException("Attribute key must be unique.");

        e = new Entry(key, index, entryArray[entryIdx]);
        entryArray[entryIdx] = e;
    }

    public final boolean contains(String key) {
        return getIndexAny(key) > -1;
    }

    protected final int getIndexAny(String key) {
        int hash = key.hashCode();
        int index = (hash & 2147483647) % entryArray.length;
        for (Entry e = entryArray[index]; e != null; e = e.next)
            if (e.key == key || e.key.equals(key))
                return e.index;

        return -1;
    }

    public final int getIndex(String key) {
        int index = getIndexAny(key);
        if (index < 0)
            throw new IllegalArgumentException(
                    (new StringBuilder()).append("Record key[").append(key).append("] is not registered.").toString());
        else
            return index;
    }

    public final String getKey(int index) {
        return keyArray[index];
    }

    public final Iterator getKeys() {
        return iterator();
    }

    public Iterator iterator() {
        return new StringArrayIterator(keyArray);
    }

    public final int size() {
        return keyArray.length;
    }

    public boolean equals(RecordMetadata other) {
        if (other != null) {
            if (other.hashCode() == hashCode())
                return true;
            else
                return Arrays.equals(keyArray, other.keyArray);
        } else {
            return false;
        }
    }
}
