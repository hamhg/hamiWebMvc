package com.hami.biz.system.resolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.hami.biz.system.views.ExcelView;

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
public class PdfViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        ExcelView view = new ExcelView();
        return view;
    }
}
