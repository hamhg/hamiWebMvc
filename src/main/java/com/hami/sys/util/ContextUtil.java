package com.hami.sys.util;

import com.hami.sys.context.RequestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : ContextUtil
 * <li>Description  :
 * <li>History      : 2018. 2. 13.
 * </pre>
 *
 * @author HHG
 */
public class ContextUtil {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ETC_MSG = "ETC_MSG";

    public static Map getSystemHeader() {
        return (Map) RequestContextHolder.getRequestContext().getAttribute("systemHeader");
    }

    public static Object getSystemHeaderValue(String key) {
        return ((Map) RequestContextHolder.getRequestContext().getAttribute("systemHeader")).get(key);
    }

    public static Map getTransactionHeader() {
        return (Map) RequestContextHolder.getRequestContext().getAttribute("transactionHeader");
    }

    public static Object getTransactionHeaderValue(String key) {
        return ((Map) RequestContextHolder.getRequestContext().getAttribute("transactionHeader")).get(key);
    }

    public static Map getMessageHeader() {
        return (Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader");
    }

    public static Object getMessageHeaderValue(String key) {
        return ((Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader")).get(key);
    }

    public static Map getReturnMessageCode() {
        Map msg = (Map) RequestContextHolder.getRequestContext().getAttribute(ETC_MSG);
        if (msg == null)
            msg = new HashMap();
        return msg;
    }

    public static void setSystemHeader(Object value) {
        RequestContextHolder.getRequestContext().setAttribute("systemHeader", value);
    }

    public static void setTransactionHeader(Object value) {
        RequestContextHolder.getRequestContext().setAttribute("transactionHeader", value);
    }

    public static void setMessageHeader(Object value) {
        RequestContextHolder.getRequestContext().setAttribute("messageHeader", value);
    }

    public static void setMessageHeaderByPrcsRsltCd(String value) {
        ((Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader")).put("MSG_PRCS_RSLT_CD", value);
    }

    public static void setMessageHeaderByIndcCd(String value) {
        if (((Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader")).get("MSG_DATA_SUB") == null) {
            List subMsg = new ArrayList();
            Map msg = new HashMap();
            msg.put("MSG_CD", "");
            msg.put("MSG_CTNS", "");
            msg.put("MSG_INDC_CD", value);
            subMsg.add(msg);
            ((Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader")).put("MSG_DATA_SUB", subMsg);
            ((Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader")).put("MSG_DATA_SUB_RPTT_CNT", Integer.valueOf(1));
        } else {
            ((Map) ((List) ((Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader")).get("MSG_DATA_SUB")).get(0)).put("MSG_INDC_CD", value);
        }
    }

    public static void setMessageHeaderValues(String msgPrcsRsltCd, String msgIndcCd) {
        ((Map) RequestContextHolder.getRequestContext().getAttribute("messageHeader")).put("MSG_PRCS_RSLT_CD", msgPrcsRsltCd);
        setMessageHeaderByIndcCd(msgIndcCd);
    }

    public static void addReturnMessage(String msgCd) {
        addReturnMessage(msgCd, null);
    }

    public static void addReturnMessage(String msgCd, String args[]) {
        Map msg = new HashMap();
        msg.put("MSG_CD", msgCd);
        msg.put("MSG_ARGS", args);
        RequestContextHolder.getRequestContext().setAttribute(ETC_MSG, msg);
    }
}
