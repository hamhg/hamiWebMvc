package com.hami.sys.jdbc.audit.interceptor;

import com.hami.sys.jdbc.audit.conn.ConnectionWrapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * <pre>
 * <li>Program Name : DataSourceInterceptor
 * <li>Description  :
 * <li>History      : 2018. 2. 19.
 * </pre>
 *
 * @author HHG
 */
public class DataSourceInterceptor implements MethodInterceptor {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public DataSourceInterceptor() {
        log.info("Datasource proxy has been created.");
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object obj = invocation.proceed();
        if (obj instanceof Connection) {
            Connection conn = (Connection)obj;
            if (!ConnectionWrapper.class.isAssignableFrom(conn.getClass())) {
                return new ConnectionWrapper(conn);
            }
        }
        return new ConnectionWrapper((Connection)obj);
    }
}
