package com.hami.sys.config;

import com.hami.sys.common.filter.CORSFilter;
import com.hami.sys.annotation.BizAnnotationHandler;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.hami.biz.config.SecurityConfig;
import com.hami.biz.config.WebConfig;

import javax.servlet.Filter;

/**
 * <pre>
 * <li>Program Name : WebXml
 * <li>Description  :
 * <li>History      : 2017. 7. 20.
 * </pre>
 *
 * @author HHG
 */
public class WebXml extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ DBConfigDev.class, DBConfigPrd.class, SecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ WebConfig.class, BizAnnotationHandler.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        Filter[] singleton = { new CORSFilter() };
        return singleton;
    }
}
