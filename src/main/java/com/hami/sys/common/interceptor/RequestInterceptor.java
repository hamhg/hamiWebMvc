package com.hami.sys.common.interceptor;

import com.hami.sys.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : RequestInterceptor
 * <li>Description  :
 * <li>History      : 2017. 8. 3.
 * </pre>
 *
 * @author HHG
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //로그인 유저정보 로그에 추가, properties.local.properties.logback MDC
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) MDC.put("username", StringUtils.nvl(auth.getName()));

        //파라미터 로그
        log.info("================================     START    ================================");
        log.info(" * Referer URI \t:  " + request.getHeader("referer"));
        log.info(" * Request URI \t:  " + request.getRequestURI());
        log.info(" * Methods     \t:  " + request.getMethod());
        log.info(" * SessionId   \t:  " + request.getSession().getId());

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.info(" * Client IP  \t:  " + ip);

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = (String) paramNames.nextElement();
            String value;
            if("password".equals(key)) value = "[HIDDEN VALUE FOR SECURITY]";
            else value = request.getParameter(key);
            log.debug(" ** Param == " + key + " \t: " + value);
        }

        return super.preHandle(request, response, handler);

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        logModelMap(modelAndView);

        log.info("================================     END      ================================");
    }

    private void logModelMap(ModelAndView modelAndView) throws Exception {
        if(modelAndView != null){
            Map modelMap = modelAndView.getModel();
            for (Object modelKey : modelMap.keySet()) {
                Object modelValue = modelMap.get(modelKey);
                log.debug(" ** Model == " + modelKey + " \t: " + modelValue);
            }
        }
    }
}
