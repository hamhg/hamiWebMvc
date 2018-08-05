package com.hami.sys.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <pre>
 * <li>Program Name : DBConfigPrd
 * <li>Description  :
 * <li>History      : 2017. 7. 20.
 * </pre>
 * @author HHG
 */
@Configuration
@Profile("prd")
@PropertySource("classpath:properties/prod/system.properties")
@EnableTransactionManagement
public class DBConfigPrd {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Bean
    //@Resource(name="jdbc/hamiDB")
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource(env.getProperty("jndi"));

        log.debug(":::: Prd Datasource ::::");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}