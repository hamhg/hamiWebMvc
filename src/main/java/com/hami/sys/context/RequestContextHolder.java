package com.hami.sys.context;

/**
 * <pre>
 * <li>Program Name : RequestContextHolder
 * <li>Description  :
 * <li>History      : 2018. 2. 13.
 * </pre>
 *
 * @author HHG
 */
public class RequestContextHolder {
    private static ThreadLocal requestContext = new ThreadLocal();

    public static RequestContext getRequestContext() {

        if (requestContext.get() == null)
            requestContext.set(new RequestContext());

        return (RequestContext) requestContext.get();
    }

    public static void remove() {
        requestContext.remove();
    }
}
