package com.hami.biz.system.config;

import com.hami.biz.system.interceptor.RequestInterceptor;
import com.hami.biz.system.resolver.ExcelViewResolver;
import com.hami.biz.system.resolver.JsonViewResolver;
import com.hami.biz.system.resolver.PdfViewResolver;
import com.hami.sys.jdbc.audit.interceptor.DataSourceBeanNameAutoProxyCreator;
import com.hami.sys.jdbc.audit.interceptor.DataSourceInterceptor;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <pre>
 * <li>Program Name : WebConfig
 * <li>Description  :
 * <li>History      : 2017. 7. 20.
 * </pre>
 *
 * @author HHG
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.hami.biz.**"},
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Service.class),
                           @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Repository.class) }
)
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_500").setViewName("com/exception/500.html");
        registry.addViewController("/error_404").setViewName("com/exception/404.html");
        registry.addViewController("/error_403").setViewName("com/exception/403.html");
        registry.addViewController("/error_401").setViewName("com/exception/401.html");
    }
    
    //ResourceHandler
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/libs/**").addResourceLocations("/resources/libs/");
        registry.addResourceHandler("/images/**").addResourceLocations("/resources/img/");
        registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/resources/fonts/");
    }

    //s: DataSourceInterceptor Config
    @Bean
    public DataSourceInterceptor dataSourceInterceptor(){
        return new DataSourceInterceptor();
    }
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new DataSourceBeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("dataSource");
        beanNameAutoProxyCreator.setInterceptorNames("dataSourceInterceptor");
        return beanNameAutoProxyCreator;
    }
    //e: DataSourceInterceptor Config

    //s: RequestInterceptor Config
    @Bean
    RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor());
    }
    //e: RequestInterceptor Config

    //s: Thymeleaf config
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/views/templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setViewNames(new String[]{"*.html"});
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(1);
        return viewResolver;
    }
    //e: Thymeleaf config

    //ViewResolver Config
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setContentNegotiationManager(manager);

        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

        InternalResourceViewResolver vr01 = new InternalResourceViewResolver();
        vr01.setPrefix("/WEB-INF/views/jsp/");
        vr01.setContentType("text/html; cherset=UTF-8");
        //vr1.setSuffix("*.jsp");
        vr01.setViewClass(JstlView.class);
        resolvers.add(vr01);

        resolvers.add(jsonViewResolver());
        resolvers.add(excelViewResolver());
        resolvers.add(pdfViewResolver());

        viewResolver.setViewResolvers(resolvers);
        viewResolver.setOrder(2);
        return viewResolver;
    }

    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }

    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }

    @Bean
    public ViewResolver pdfViewResolver() {
        return new PdfViewResolver();
    }

    //MessageSource Config
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);// # -1 : never reload, 0 always reload
        return messageSource;
    }

    /*
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }
    */
}
