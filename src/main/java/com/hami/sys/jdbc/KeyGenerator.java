package com.hami.sys.jdbc;

import java.math.BigDecimal;

/**
 * <pre>
 * <li>Program Name : KeyGenerator
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public interface KeyGenerator {

    public abstract void createSequence(String s);

    public abstract void createSequence(String s, BigDecimal bigdecimal);

    public abstract BigDecimal getNextValue(String s);

    public abstract long getNextLongValue(String s);

    public abstract String getNextStringValue(String s);

    /**
     * @deprecated Method getNextStringValue is deprecated
     */
    public abstract String getNextStringValue(String s, String s1);
}
