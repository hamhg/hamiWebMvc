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
}
