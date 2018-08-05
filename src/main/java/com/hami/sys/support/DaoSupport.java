package com.hami.sys.support;

import com.hami.biz.exception.BizException;
import com.hami.sys.jdbc.StatementProvider;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * <pre>
 * <li>Program Name : DaoSupport
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class DaoSupport extends RecordSetDaoSupport {
    protected static final String DEF_STMT_PROVIDER_BEANNAME = "com.hami.fw.jdbc.StatementProvider";
    private StatementProvider statementProvider;

    public void setStatementProvider(StatementProvider statementProvider) {
        this.statementProvider = statementProvider;
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (statementProvider == null)
            if (getBeanFactory().containsBean(DEF_STMT_PROVIDER_BEANNAME))
                statementProvider = (StatementProvider) getBeanFactory().getBean(DEF_STMT_PROVIDER_BEANNAME, StatementProvider.class);
            else
                statementProvider =  new StatementProvider();
    }

    protected StatementProvider getSqlStatementProvider() {
        return statementProvider;
    }

    protected String getSql(Object obj, String qname) throws BizException {
        return getSql(obj, qname, null);
    }

    protected String getSql(Object obj, String qname, Object parameters) throws BizException {
        String sql = statementProvider.geStatement(obj, qname);
        if (sql.contains("${") || sql.contains("<#") || sql.contains("<@")) {
            Template template = null;
            Configuration configuration =  new Configuration();
            StringReader reader = new StringReader(sql);

            try {
                template = new Template(obj.getClass().getName(), reader, configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (template != null) {
                StringWriter writer = new StringWriter();
                try {
                    Object parameterChecked = parameters;
                    if (parameters instanceof SqlParameterSource)
                        if (parameters instanceof MapSqlParameterSource)
                            parameterChecked = ((MapSqlParameterSource) parameters).getValues();

                    template.process(parameterChecked, writer);
                    sql = writer.toString();
                } catch (TemplateException | IOException e) {
                    throw new IllegalArgumentException((new StringBuilder()).append("Processing dynamic sql ").append(obj.getClass().getName()).toString(), e);
                }
            }
        }
        //log.debug(sql);
        return sql;
    }
}
