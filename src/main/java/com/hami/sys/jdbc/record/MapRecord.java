package com.hami.sys.jdbc.record;

import java.util.*;

/**
 * <pre>
 * <li>Program Name : MapRecord
 * <li>Description  :
 * <li>History      : 2018. 2. 18.
 * </pre>
 *
 * @author HHG
 */
public class MapRecord extends Record {
    private static final long serialVersionUID = 1842742543L;
    private HashMap dataMap;

    public MapRecord() {
        dataMap = new HashMap();
    }

    public MapRecord(Map map) {
        dataMap = new HashMap();
        dataMap.putAll(map);
    }

    public void clear() {
        super.clear();
        dataMap.clear();
    }

    public boolean isEmpty() {
        return dataMap.size() < 1;
    }

    public int getColumnCount() {
        return dataMap.size();
    }

    public Iterable getColumns() {
        return dataMap.keySet();
    }

    public Record set(String fieldName, Object value) {
        dataMap.put(fieldName, value);
        return this;
    }

    public Object getObject(String fieldName) {
        return getObject(fieldName, null);
    }

    public Object getObject(String fieldName, Object def) {
        Object value = dataMap.get(fieldName);
        return value == null ? def : value;
    }

    public void remove(String fieldName) {
        dataMap.remove(fieldName);
    }

    public Map getMap() {
        return dataMap;
    }

    public void putAll(Record record) {
        if (record instanceof MapRecord) {
            dataMap.putAll(((MapRecord) record).getMap());
        } else {
            String name;
            Object value;
            for (Iterator it = record.getColumns().iterator(); it.hasNext(); dataMap.put(name, value)) {
                name = (String) it.next();
                value = record.getObject(name);
            }

        }
    }

    public String toString() {
        return (new StringBuilder()).append(dataMap.size()).append(" ::").append(dataMap.toString()).toString();
    }

    public boolean containsKey(Object key) {
        return dataMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return dataMap.containsValue(value);
    }

    public Set entrySet() {
        return dataMap.entrySet();
    }

    public Object get(Object key) {
        return dataMap.get(key);
    }

    public Set keySet() {
        return dataMap.keySet();
    }

    public void putAll(Map m) {
        dataMap.putAll(m);
    }

    public Object remove(Object key) {
        return dataMap.remove(key);
    }

    public int size() {
        return dataMap.size();
    }

    public Collection values() {
        return dataMap.values();
    }
}
