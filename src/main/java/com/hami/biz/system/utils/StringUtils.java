package com.hami.biz.system.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <li>Program Name : StringUtils
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
public class StringUtils extends com.hami.sys.util.StringUtils {
    /**
     * Parameter를 Json문자열로 반환한다.
     */
    public static String makeParamToJson(HttpServletRequest request, String param_names) throws UnsupportedEncodingException {
        param_names = StringUtils.isEmpty(param_names)?"":param_names;
        String[] param_name_array;
        if (param_names.length() > 0)
        {
            param_name_array = param_names.split(",");
        }
        else
        {
            ArrayList param_name_list = new ArrayList();
            Enumeration param_name_enum = request.getParameterNames();
            String param_name;
            while (param_name_enum.hasMoreElements())
            {
                param_name = (String) param_name_enum.nextElement();
                param_name_list.add(param_name);
                //System.out.println(param_name_list.size());
            }
            param_name_array = new String[param_name_list.size()];
            param_name_list.toArray(param_name_array);
        }
        String param_name;
        String[] param_value_array;
        StringBuffer sb = new StringBuffer();
        for (int n = 0, nlen = param_name_array.length; n < nlen; n++)
        {
            param_name = param_name_array[n];
            param_value_array = request.getParameterValues(param_name);
            sb.append(param_name).append(": [");
            if (param_value_array != null && param_value_array.length > 0)
            {
                for (int m = 0, mlen = param_value_array.length; m < mlen; m++)
                {
                    sb.append("\"").append(URLEncoder.encode(param_value_array[m],"UTF-8")).append("\"");
                    if (m < mlen - 1) sb.append(",");
                }
            }
            sb.append("]");
            if (n < nlen - 1) sb.append(",");
        }
        return sb.toString();
    }
}
