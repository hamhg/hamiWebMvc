package com.hami.sys.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : BizAnnotationHandler
 * <li>Description  :
 * <li>History      : 2018. 2. 4.
 * </pre>
 *
 * @author HHG
 */
public class BizAnnotationHandler implements ApplicationContextAware, InitializingBean {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private ApplicationContext applicationContext;
    private Map ServiceMethodMap;

    public BizAnnotationHandler() {
        ServiceMethodMap = new HashMap();
    }

    public Map getServiceMethodMap() {
        return ServiceMethodMap;
    }

    public void setServiceMethodMap(Map serviceMethodMap) {
        ServiceMethodMap = serviceMethodMap;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        this.loadAnnotaion();
    }

    public void loadAnnotaion() {
        log.debug("====================Annotation is now scan====================");
        Map ServiceClassMaps = applicationContext.getBeansWithAnnotation(Service.class);

        for (Iterator iterator = ServiceClassMaps.keySet().iterator(); iterator.hasNext();) {
            String beanId = iterator.next().toString();
            Object beanObject = ServiceClassMaps.get(beanId);

            try {
                Class cls = beanObject.getClass();

                if (AopUtils.isAopProxy(beanObject) || AopUtils.isCglibProxy(beanObject))
                    cls = AopUtils.getTargetClass(beanObject);

                Method allDeclaredMethods[] = ReflectionUtils.getAllDeclaredMethods(cls);

                int mCnt = (allDeclaredMethods).length;

                for (Method method : allDeclaredMethods) {
                    if (method.isAnnotationPresent(BizAnnotation.class)) {
                        BizAnnotation bizAnnotation = (BizAnnotation) method.getAnnotation(BizAnnotation.class);
                        String ServiceClassName = cls.getName();
                        String MethodName = method.getName();
                        String svcId = bizAnnotation.id();

                        if (ServiceMethodMap.containsKey(svcId)) {
                            log.error((new StringBuilder("Service id key duplicate,[")).append(svcId).append("]")
                                    .append(",[").append(beanId).append("] is not serviced").toString());
                            throw new Exception((new StringBuilder("Service id key duplicate,[")).append(svcId).append("]")
                                    .append(",[").append(beanId).append("] is not serviced").toString());
                        }

                        String Description = bizAnnotation.description();

                        if (Description.length() <= 0) {
                            log.error((new StringBuilder("Description is neccessary is required,[")).append(svcId)
                                    .append("] is not serviced").toString());
                            throw new Exception((new StringBuilder("Description is neccessary is required,["))
                                    .append(svcId).append("]  is not serviced").toString());
                        }

                        if (svcId.length() <= 0) {
                            log.error((new StringBuilder("Service id is neccessary is required,[")).append(beanId)
                                    .append("] is not serviced").toString());
                            throw new Exception((new StringBuilder("Service id is neccessary is required,[")).append(beanId)
                                    .append("]  is not serviced").toString());
                        }

                        String parameterTypeNames[] = {"", ""};
                        Class parameterTypes[] = method.getParameterTypes();

                        if (parameterTypes != null && parameterTypes.length > 0) {
                            parameterTypeNames = new String[parameterTypes.length];
                            for (int i = 0; i < parameterTypes.length; i++)
                                parameterTypeNames[i] = parameterTypes[i].getName();

                        }

                        ServiceDescriptor descriptor = new ServiceDescriptor(svcId, beanId, method.getName(),
                                method.getReturnType().getName(), parameterTypeNames, Description);

                        ServiceMethod serviceMethod = new ServiceMethod(descriptor, beanObject, method);

                        ServiceMethodMap.put(svcId, serviceMethod);

                        log.debug((new StringBuilder(String.valueOf(svcId))).append(" : ").append(serviceMethod)
                                .append("is Registered ").toString());

                    } else {
                        String ServiceClassName = cls.getName();
                        String s = method.getName();
                    }
                }

            } catch (Throwable e) {
                e.printStackTrace();
                log.error((new StringBuilder("Service Registry fail,[")).append(beanId).append("]").toString(), e);
            }
        }

    }
}
