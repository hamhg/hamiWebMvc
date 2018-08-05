package com.hami.sys.context;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : RequestContext
 * <li>Description  :
 * <li>History      : 2018. 2. 13.
 * </pre>
 *
 * @author HHG
 */
public class RequestContext {
    private Map attributes;

    RequestContext() {
        attributes = new HashMap();
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Iterable getKeys() {
        return attributes.keySet();
    }
}
