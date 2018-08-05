package com.hami.sys.context;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * <pre>
 * <li>Program Name : BizBeanNameGenerator
 * <li>Description  :
 * <li>History      : 2018. 2. 5.
 * </pre>
 *
 * @author HHG
 */
public class BizBeanNameGenerator extends AnnotationBeanNameGenerator {
    protected String buildDefaultBeanName(BeanDefinition definition) {
        return definition.getBeanClassName();
    }
}
