package com.hami.sys.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <pre>
 * <li>Program Name : FormatUtil
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
public class FormatUtil {
    /**
     * 정수 양식
     */
    static DecimalFormat sm_DF = new DecimalFormat("###,###,###.##########");
    /**
     * 년월일시분초 양식
     */
    static SimpleDateFormat sm_SDF_YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 년월일 양식
     */
    static SimpleDateFormat sm_SDF_YMD = new SimpleDateFormat("yyyyMMdd");

    /**
     * format comma
     *
     * @param num
     * @return
     */
    public static String formatComma(double num)
    {
        return sm_DF.format(num);
    }

    /**
     * format comma with format
     *
     * @param num
     * @param format
     * @return
     */
    public static String formatComma(double num, String format)
    {
        return new DecimalFormat(format).format(num);
    }

    /**
     * format comma with scale
     *
     * @param num
     * @param scale
     * @return
     */
    public static String formatCommaScale(double num, int scale)
    {
        return new DecimalFormat("###,###,###." + StringUtils.repeat("0", scale)).format(num);
    }

    /**
     * format date with format
     *
     * @param cal
     * @param format
     * @return
     */
    public static String formatDate(String format, Calendar cal)
    {
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * format date with format
     *
     * @param cal
     * @param format
     * @return
     */
    public static String formatDate(String format)
    {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    /**
     * format current time to yyyyMMddHHmmss
     *
     * @return
     */
    public static String getYmdhms()
    {
        return sm_SDF_YMDHMS.format(Calendar.getInstance().getTime());
    }

    /**
     * format current time to yyyyMMddHHmmss
     *
     * @return
     */
    public static String getYmd()
    {
        return sm_SDF_YMD.format(Calendar.getInstance().getTime());
    }

    /**
     * format last day of current month to yyyyMMddHHmmss
     *
     * @return
     */
    public static String getLastYmdOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        return sm_SDF_YMD.format(cal.getTime());
    }

    /**
     * make yyyyMMddHHmmss to time
     *
     * @param yyyyMMddHHmmss
     * @return
     */
    public static Calendar makeCalendar(String yyyyMMddHHmmss)
    {
        Calendar cal = Calendar.getInstance();
        // 반드시
        cal.set(Calendar.YEAR, Integer.parseInt(yyyyMMddHHmmss.substring(0, 4)));
        cal.set(Calendar.MONTH, Integer.parseInt(yyyyMMddHHmmss.substring(4, 6)) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(yyyyMMddHHmmss.substring(6, 8)));
        // 옵션
        cal.set(Calendar.HOUR_OF_DAY, yyyyMMddHHmmss.length() >= 10 ? Integer.parseInt(yyyyMMddHHmmss.substring(8, 10)) : 0);
        cal.set(Calendar.MINUTE, yyyyMMddHHmmss.length() >= 12 ? Integer.parseInt(yyyyMMddHHmmss.substring(10, 12)) : 0);
        cal.set(Calendar.SECOND, yyyyMMddHHmmss.length() >= 14 ? Integer.parseInt(yyyyMMddHHmmss.substring(12, 14)) : 0);
        return cal;
    }

    /**
     * make string to decimal format
     * @param numStr
     * @param format
     * @return
     */
    public static String formatCommaString(String numStr, String format)
    {
        try
        {
            return new DecimalFormat(format).format(Double.parseDouble(numStr));
        }
        catch (Exception e)
        {
            return numStr;
        }
    }

    /**
     * make string to decimal format
     * @param numStr
     * @param format
     * @return
     */
    public static String formatDateString(String dateStr, String format)
    {
        try
        {
            return formatDate(format, makeCalendar(dateStr));
        }
        catch (Exception e)
        {
            return dateStr;
        }
    }

    public static void main(String[] args)
    {
        System.out.println(formatCommaString("33432432432421321", "###,###"));
        System.out.println(formatDateString("20060101", "yyyy.MM.dd"));
        System.out.println(getLastYmdOfMonth());
        System.out.println(makeCalendar("20061205230507"));
    }
}
