package com.hami.sys.Resolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import com.hami.biz.views.ExcelView;

import java.util.Locale;

/**
 * <pre>
 * <li>Program Name : ExcelViewResolver
 * <li>Description  :
 * <li>History      : 2017. 11. 26.
 * </pre>
 *
 * @author HHG
 */
public class ExcelViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        ExcelView view = new ExcelView();
        return view;
    }
}
