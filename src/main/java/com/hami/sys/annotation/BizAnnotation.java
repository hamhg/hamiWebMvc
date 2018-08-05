package com.hami.sys.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <pre>
 * <li>Program Name : BizAnnotation
 * <li>Description  :
 * <li>History      : 2018. 1. 11.
 * </pre>
 *
 * @author HHG
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizAnnotation {
    String id();
    String description() default "no description";
}
