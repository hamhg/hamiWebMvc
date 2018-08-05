package com.hami.sys.annotation;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : ServiceDescriptor
 * <li>Description  :
 * <li>History      : 2018. 2. 4.
 * </pre>
 *
 * @author HHG
 */
public @Data class ServiceDescriptor {
    private String serviceId;
    private String beanId;
    private String methodName;
    private String returnType;
    private String parameterTypeNames[] = {};
    private String Description;

    public ServiceDescriptor(String serviceId, String beanId, String methodName, String returnType,
                               String parameterTypeNames[], String Description) {
        this.serviceId = serviceId;
        this.beanId = beanId;
        this.serviceId = serviceId;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypeNames = parameterTypeNames;
        this.Description = Description;
    }
}
