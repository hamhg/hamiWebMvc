package com.hami.biz.system.config;

import com.hami.sys.config.DBConfigLocal;
import com.hami.sys.config.DBConfigDev;
import com.hami.sys.config.DBConfigPrd;
import com.hami.biz.system.filter.CORSFilter;
import com.hami.sys.annotation.BizAnnotationHandler;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

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
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "local");
    }
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ DBConfigLocal.class, DBConfigDev.class, DBConfigPrd.class, SecurityConfig.class };
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
    
    /*
    // 10MB
    private static final int MAX_UPLOAD_SIZE_IN_MB = 10 * 1024 * 1024;
    
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp", MAX_UPLOAD_SIZE_IN_MB, MAX_UPLOAD_SIZE_IN_MB * 2,
                MAX_UPLOAD_SIZE_IN_MB / 2);
        registration.setMultipartConfig(multipartConfigElement);
    }
    */
}
