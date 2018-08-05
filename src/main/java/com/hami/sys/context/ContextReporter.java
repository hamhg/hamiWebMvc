package com.hami.sys.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * <li>Program Name : ContextReporter
 * <li>Description  :
 * <li>History      : 2018. 2. 5.
 * </pre>
 *
 * @author HHG
 */
public class ContextReporter implements ApplicationContextAware, InitializingBean {
    private static final String LINE_BIG = "=========================================================================================================================================\n";
    private static final String LINE_SMALL = "-----------------------------------------------------------------------------------------------------------------------------------------\n";
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private ApplicationContext applicationContext;
    private int beanCount;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        report();
    }

    public void report() {
        StringBuilder sb = new StringBuilder(
                "\n=========================================================================================================================================\n");
        sb.append("Context Loading Report\n");
        createHeader(sb);
        sb.append("\n");
        createBody(sb);
        sb.append("\n");
        createFooter(sb);
        sb.append(
                "=========================================================================================================================================\n");
        sb.append("\n");
        log.info(sb.toString());
    }

    private void createHeader(StringBuilder sb) {
        addField(sb, "* Application Name: ", new String[]{applicationContext.getApplicationName()});
        addField(sb, "* Display Name: ", new String[]{applicationContext.getDisplayName()});
        ApplicationContext parent = applicationContext.getParent();
        if (parent != null)
            addField(sb, "* Parent Context : ", new String[]{parent.getDisplayName()});
        String startupDate = getStartupDate(applicationContext.getStartupDate());
        addField(sb, "* Start Date: ", new String[]{startupDate});
        Environment env = applicationContext.getEnvironment();
        String activeProfiles[] = env.getActiveProfiles();
        if (activeProfiles.length > 0)
            addField(sb, "Active Profiles: ", activeProfiles);
    }

    private void createBody(StringBuilder sb) {
        addColumnHeaders(sb);
        addColumnValues(sb);
        sb.append(
                "-----------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    private void createFooter(StringBuilder sb) {
        addField(sb, "* Bean Count: ", new String[]{
                (new StringBuilder()).append(applicationContext.getBeanDefinitionCount()).append("").toString()});
        addField(sb, "* Bean Count without Sping: ",
                new String[]{(new StringBuilder()).append(beanCount).append("").toString()});
    }

    private void addField(StringBuilder sb, String name, String values[]) {
        sb.append(name);
        String arr[] = values;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            String val = arr[i];
            sb.append(val);
            sb.append(", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("\n");
    }

    private String getStartupDate(long startupDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return df.format(new Date(startupDate));
    }

    private void addColumnHeaders(StringBuilder sb) {
        sb.append(
                "-----------------------------------------------------------------------------------------------------------------------------------------\n");
        sb.append("Bean Name\t|\tSimple Name\t|\tSingleton\t|\tFull Class Name\n");
        sb.append(
                "-----------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    private void addColumnValues(StringBuilder sb) {
        String beanNames[] = applicationContext.getBeanDefinitionNames();
        String arr[] = beanNames;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            String name = arr[i];
            addRow(name, sb);
        }

    }

    private void addRow(String name, StringBuilder sb) {
        Object obj = applicationContext.getBean(name);
        String fullClassName = obj.getClass().getName();
        if (!fullClassName.contains("org.springframework")) {
            sb.append(name);
            sb.append("\t|\t");
            String simpleName = obj.getClass().getSimpleName();
            sb.append(simpleName);
            sb.append("\t|\t");
            boolean singleton = applicationContext.isSingleton(name);
            sb.append(singleton ? "YES" : "NO");
            sb.append("\t|\t");
            sb.append(fullClassName);
            sb.append("\n");
            beanCount = beanCount + 1;
        }
    }
}
