package com.hami.biz.system.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hami.sys.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * <pre>
 * <li>Program Name : HttpUtil
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
public class HttpUtil {
    /**
     * Http Servlet Request
     */
    protected HttpServletRequest m_request;
    /**
     * Http Servlet Response
     */
    protected HttpServletResponse m_response;

    /**
     * new instance를 반환한다.
     */
    public static HttpUtil newInstance(HttpServletRequest req, HttpServletResponse res)
    {
        return new HttpUtil(req, res);
    }

    /**
     * 생성자
     */
    public HttpUtil(HttpServletRequest req, HttpServletResponse res)
    {
        m_request = req;
        m_response = res;
    }

    /**
     * 쿠키설정한다.
     */
    public void setCookieValue(String key, String value)
    {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        m_response.addCookie(cookie);
    }

    /**
     * 쿠키삭제한다.
     */
    public void deleteCookie(String key)
    {
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        m_response.addCookie(cookie);
    }

    /**
     * 쿠키가져온다.
     */
    public String getCookieValue(String key)
    {
        Cookie[] cookies = m_request.getCookies();
        for (int n = 0; n < cookies.length; n++)
        {
            if (key.equals(cookies[n].getName())) { return cookies[n].getValue(); }
        }
        return null;
    }

    /**
     * 세션 속성 가져온다.
     */
    public Object getSessionAttribute(String key)
    {
        HttpSession session = m_request.getSession();
        if (session == null) return null;
        return session.getAttribute(key);
    }

    /**
     * url forward
     */
    public void forward(String url) throws ServletException, IOException
    {
        m_request.getRequestDispatcher(url).forward(m_request, m_response);
    }

    /**
     * url include
     */
    public void include(String url) throws ServletException, IOException
    {
        m_request.getRequestDispatcher(url).include(m_request, m_response);
    }

    /**
     * nocache설정한다.
     */
    public void setNoCache()
    {
        m_response.setHeader("cache-control", "no-cache");
        m_response.setHeader("expires", "0");
        m_response.setHeader("pragma", "no-cache");
    }

    /**
     * 파라미터를 문자열로 반환한다.
     */
    public String makeParameters() throws UnsupportedEncodingException {
        Enumeration enum1 = m_request.getParameterNames();
        StringBuffer sb = new StringBuffer();
        String pName;
        String[] pValues;
        while (enum1.hasMoreElements())
        {
            pName = (String) enum1.nextElement();
            pValues = m_request.getParameterValues(pName);
            for (int n = 0, len = pValues.length; n < len; n++)
            {
                sb.append("&").append(pName).append("=").append(URLEncoder.encode(pValues[n],"UTF-8"));
            }
        }
        return sb.toString();
    }

    /**
     * 파라미터를 가져온다.
     */
    public String getParameter(String pName)
    {
        return StringUtils.nvl(m_request.getParameter(pName));
    }

    /**
     * Request정보를 가져온다.
     */
    public String getRequestInfo()
    {
        char NL = '\n';
        HttpServletRequest request = m_request;
        StringBuffer sb = new StringBuffer();
        // sb.append("request.getAttributeNames() : ").append(request.getAttributeNames() ).append(NL);
        sb.append("request.getAuthType()                   : ").append(request.getAuthType()).append(NL);
        sb.append("request.getCharacterEncoding()          : ").append(request.getCharacterEncoding()).append(NL);
        sb.append("request.getContentLength()              : ").append(request.getContentLength()).append(NL);
        sb.append("request.getContentType()                : ").append(request.getContentType()).append(NL);
        sb.append("request.getContextPath()                : ").append(request.getContextPath()).append(NL);
        // sb.append("request.getCookies() : ").append(request.getCookies() ).append(NL);
        // sb.append("request.getHeaderNames() : ").append(request.getHeaderNames() ).append(NL);
        sb.append("request.getLocalAddr()                  : ").append(request.getLocalAddr()).append(NL);
        sb.append("request.getLocale()                     : ").append(request.getLocale()).append(NL);
        sb.append("request.getLocales()                    : ").append(request.getLocales()).append(NL);
        sb.append("request.getLocalName()                  : ").append(request.getLocalName()).append(NL);
        sb.append("request.getLocalPort()                  : ").append(request.getLocalPort()).append(NL);
        sb.append("request.getMethod()                     : ").append(request.getMethod()).append(NL);
        // sb.append("request.getParameterNames() : ").append(request.getParameterNames() ).append(NL);
        sb.append("request.getPathInfo()                   : ").append(request.getPathInfo()).append(NL);
        sb.append("request.getPathTranslated()             : ").append(request.getPathTranslated()).append(NL);
        sb.append("request.getProtocol()                   : ").append(request.getProtocol()).append(NL);
        sb.append("request.getQueryString()                : ").append(request.getQueryString()).append(NL);
        sb.append("request.getRemoteAddr()                 : ").append(request.getRemoteAddr()).append(NL);
        sb.append("request.getRemoteHost()                 : ").append(request.getRemoteHost()).append(NL);
        sb.append("request.getRemotePort()                 : ").append(request.getRemotePort()).append(NL);
        sb.append("request.getRemoteUser()                 : ").append(request.getRemoteUser()).append(NL);
        sb.append("request.getRequestedSessionId()         : ").append(request.getRequestedSessionId()).append(NL);
        sb.append("request.getRequestURI()                 : ").append(request.getRequestURI()).append(NL);
        sb.append("request.getRequestURL()                 : ").append(request.getRequestURL()).append(NL);
        sb.append("request.getScheme()                     : ").append(request.getScheme()).append(NL);
        sb.append("request.getServerName()                 : ").append(request.getServerName()).append(NL);
        sb.append("request.getServerPort()                 : ").append(request.getServerPort()).append(NL);
        sb.append("request.getServletPath()                : ").append(request.getServletPath()).append(NL);
        sb.append("request.isRequestedSessionIdFromCookie(): ").append(request.isRequestedSessionIdFromCookie()).append(NL);
        sb.append("request.isRequestedSessionIdFromURL()   : ").append(request.isRequestedSessionIdFromURL()).append(NL);
        sb.append("request.isRequestedSessionIdValid()     : ").append(request.isRequestedSessionIdValid()).append(NL);
        sb.append("request.isSecure()                      : ").append(request.isSecure()).append(NL);
        return sb.toString();
    }

    /**
     * Response정보를 가져온다.
     */
    public String getResponseInfo()
    {
        char NL = '\n';
        HttpServletResponse response = m_response;
        StringBuffer sb = new StringBuffer();
        sb.append("response.getBufferSize()       : ").append(response.getBufferSize()).append(NL);
        sb.append("response.getCharacterEncoding(): ").append(response.getCharacterEncoding()).append(NL);
        sb.append("response.getContentType()      : ").append(response.getContentType()).append(NL);
        sb.append("response.getLocale()           : ").append(response.getLocale()).append(NL);
        return sb.toString();
    }

    /**
     * Session정보를 가져온다.
     */
    public String getSessionInfo()
    {
        char NL = '\n';
        HttpSession session = m_request.getSession();
        StringBuffer sb = new StringBuffer();
        sb.append("session.getId()                 : ").append(session.getId()).append(NL);
        sb.append("session.getLastAccessedTime()   : ").append(session.getLastAccessedTime()).append(NL);
        sb.append("session.getMaxInactiveInterval(): ").append(session.getMaxInactiveInterval()).append(NL);
        return sb.toString();
    }

    /**
     * ServletContext정보를 가져온다.
     */
    public String getServletContextInfo()
    {
        char NL = '\n';
        ServletContext context = m_request.getSession().getServletContext();
        StringBuffer sb = new StringBuffer();
        sb.append("context.getInitParameterNames(): ").append(context.getInitParameterNames()).append(NL);
        sb.append("context.getMajorVersion()      : ").append(context.getMajorVersion()).append(NL);
        sb.append("context.getMinorVersion()      : ").append(context.getMinorVersion()).append(NL);
        sb.append("context.getServerInfo()        : ").append(context.getServerInfo()).append(NL);
        return sb.toString();
    }

    /**
     * RequestHeader정보를 가져온다.
     */
    public String getRequestHeaderInfo()
    {
        char NL = '\n';
        StringBuffer sb = new StringBuffer();
        HttpServletRequest request = m_request;
        Enumeration headerNameEnum = request.getHeaderNames();
        String headerName;
        while (headerNameEnum.hasMoreElements())
        {
            headerName = (String) headerNameEnum.nextElement();
            sb.append(headerName).append(": ").append(StringUtils.joinEnum(request.getHeaders(headerName), ",", "'", "'")).append(NL);
        }
        return sb.toString();
    }
}
