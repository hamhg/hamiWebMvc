package com.hami.sys.Resolver;

import com.hami.biz.views.BizMappingJacksonJsonView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * <pre>
 * <li>Program Name : JsonViewResolver
 * <li>Description  :
 * <li>History      : 2017. 12. 21.
 * </pre>
 *
 * @author HHG
 */
public class JsonViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        BizMappingJacksonJsonView view = new BizMappingJacksonJsonView();
        view.setPrettyPrint(true);
        return view;
    }
}
