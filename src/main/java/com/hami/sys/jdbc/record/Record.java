package com.hami.sys.jdbc.record;

import com.hami.sys.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : Record
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public abstract class Record implements Serializable, Map {
    public void recycle() {
        clear();
    }

    public void clear() {
    }

    public abstract int getColumnCount();

    public abstract Iterable getColumns();

    public abstract Record set(String s, Object obj);

    public abstract Object getObject(String s);

    public abstract Object getObject(String s, Object obj);

    public String getString(String fieldName) {
        return getString(fieldName, null);
    }

    public String getString(String fieldName, String def) {
        Object value = getObject(fieldName);
        return value == null ? def : value.toString();
    }

    public String getTrimmed(String fieldName) {
        return getTrimmed(fieldName, null);
    }

    public String getTrimmed(String fieldName, String def) {
        Object value = getObject(fieldName);
        return StringUtils.normalizeWhiteSpace(value == null ? def : value.toString());
    }

    public void setTrimmed(String fieldName, String value) {
        if (value != null)
            value = value.trim();
        set(fieldName, value);
    }

    public boolean getBoolean(String fieldName) {
        return getBoolean(fieldName, false);
    }

    public boolean getBoolean(String fieldName, boolean def) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof Boolean)
                return ((Boolean) value).booleanValue();
            else
                return value.equals("true") || value.equals("yes");
        } else {
            return def;
        }
    }

    public int getInt(String fieldName) {
        return getInt(fieldName, 0);
    }

    public int getInt(String fieldName, int def) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof Number)
                return ((Number) value).intValue();
            String numStr = value.toString();
            if (!StringUtils.hasText(numStr))
                return def;
            else
                return Integer.parseInt(numStr);
        } else {
            return def;
        }
    }

    public byte getByte(String fieldName) {
        byte def = 0;
        return getByte(fieldName, def);
    }

    public byte getByte(String fieldName, byte def) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof Byte)
                return ((Byte) value).byteValue();
            String str = value.toString();
            if (!StringUtils.hasText(str))
                return def;
            else
                return Byte.parseByte(str);
        } else {
            return def;
        }
    }

    public float getFloat(String fieldName) {
        return getFloat(fieldName, 0.0F);
    }

    public float getFloat(String fieldName, float def) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof Float)
                return ((Float) value).floatValue();
            if (value instanceof Number)
                return ((Number) value).floatValue();
            String numStr = value.toString();
            if (!StringUtils.hasText(numStr))
                return def;
            else
                return Float.parseFloat(numStr);
        } else {
            return def;
        }
    }

    public double getDouble(String fieldName) {
        return getDouble(fieldName, 0.0D);
    }

    public double getDouble(String fieldName, double def) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof Double)
                return ((Double) value).doubleValue();
            if (value instanceof Number)
                return ((Number) value).doubleValue();
            String numStr = value.toString();
            if (!StringUtils.hasText(numStr))
                return def;
            else
                return Double.parseDouble(numStr);
        } else {
            return def;
        }
    }

    public long getLong(String fieldName) {
        return getLong(fieldName, 0L);
    }

    public long getLong(String fieldName, long def) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof Number)
                return ((Number) value).longValue();
            String numStr = value.toString();
            if (!StringUtils.hasText(numStr))
                return def;
            else
                return Long.parseLong(numStr);
        } else {
            return def;
        }
    }

    public BigDecimal getBigDecimal(String fieldName) {
        return getBigDecimal(fieldName, BigDecimal.ZERO);
    }

    public BigDecimal getBigDecimal(String fieldName, BigDecimal def) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof BigDecimal)
                return (BigDecimal) value;
            if (value instanceof Number)
                return BigDecimal.valueOf(((Number) value).doubleValue());
            String numStr = value.toString();
            if (!StringUtils.hasText(numStr))
                return def;
            else
                return new BigDecimal(numStr);
        } else {
            return def;
        }
    }

    public Date getDate(String fieldName, long def) {
        Object value = getObject(fieldName);
        if (value instanceof Date)
            return (Date) value;
        else
            return new Date(def);
    }

    public Date getDate(String fieldName) {
        Object value = getObject(fieldName);
        if (value != null) {
            if (value instanceof Date)
                return (Date) value;
            else
                throw new RuntimeException((new StringBuilder()).append(fieldName).append("[").append(value).append("]가 Date형이 아닙니다.").toString());
        } else {
            return null;
        }
    }

    public Timestamp getTimestamp(String fieldName, long def) {
        Object value = getObject(fieldName);
        if (value instanceof Timestamp)
            return (Timestamp) value;
        if (value instanceof Date) {
            Date date = (Date) value;
            return new Timestamp(date.getTime());
        } else {
            return new Timestamp(def);
        }
    }

    public Timestamp getTimestamp(String fieldName) {
        Object value = getObject(fieldName);
        if (value instanceof Timestamp)
            return (Timestamp) value;
        if (value instanceof Date) {
            Date date = (Date) value;
            return new Timestamp(date.getTime());
        } else {
            return null;
        }
    }

    public Object get(Object key) {
        if (key instanceof String)
            return getObject((String) key);
        else
            return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public Object put(String key, Object value) {
        set(key, value);
        return value;
    }

    public Object put(Object key, Object value) {
        return put((String) key, value);
    }

    public int size() {
        return getColumnCount();
    }

}
