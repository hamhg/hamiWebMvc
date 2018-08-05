package com.hami.sys.jdbc.audit.interceptor;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;

/**
 * <pre>
 * <li>Program Name : DataSourceBeanNameAutoProxyCreator
 * <li>Description  :
 * <li>History      : 2018. 2. 19.
 * </pre>
 *
 * @author HHG
 */
public class DataSourceBeanNameAutoProxyCreator extends BeanNameAutoProxyCreator {
    private static final long serialVersionUID = 1L;

    protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, TargetSource targetSource) {
        return super.getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
    }
}
