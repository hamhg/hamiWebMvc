package com.hami.sys.annotation;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * <pre>
 * <li>Program Name : ServiceMethod
 * <li>Description  :
 * <li>History      : 2018. 2. 4.
 * </pre>
 *
 * @author HHG
 */
public @Data class ServiceMethod {
    private ServiceDescriptor descriptor;
    private Object beanObject;
    private Method method;

    public ServiceMethod(ServiceDescriptor descriptor, Object beanObject, Method method) {
        this.beanObject = beanObject;
        this.method = method;
        this.descriptor = descriptor;
    }

    public Object getBeanObject() {
        return beanObject;
    }

    public void setBeanObject(Object beanObject) {
        this.beanObject = beanObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
