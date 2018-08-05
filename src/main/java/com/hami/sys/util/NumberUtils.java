package com.hami.sys.util;

import java.text.DecimalFormat;

/**
 * <pre>
 * <li>Program Name : NumberUtils
 * <li>Description  :
 * <li>History      : 2018. 2. 5.
 * </pre>
 *
 * @author HHG
 */
public abstract class NumberUtils extends org.springframework.util.NumberUtils {
    public static boolean isNumber(String s) {
        return StringUtils.matches(s, "[0-9]*");
    }

    public static boolean isRealNumber(String s) {
        return StringUtils.matches(s, "[0-9]*[\\.]?[0-9]*");
    }

    public static String formatMoney(String s) {
        return formatNumber(s, "#,##0");
    }

    public static String formatNumber(String s, String pattern) {
        if (!StringUtils.hasText(s) || !isRealNumber(s))
            return "";
        String result = null;
        try {
            result = formatNumber(parseNumber(s, java.lang.Double.class), pattern);
            return result;
        } catch (NumberFormatException nfe) {
            return s;
        }
    }

    public static String formatNumber(Number number) {
        return formatNumber(number, "#,##0");
    }

    public static String formatNumber(Number number, String pattern) {
        if (number == null)
            return "";
        String formattedString = null;
        if (!StringUtils.hasText(pattern))
            pattern = "#,##0";
        DecimalFormat df = new DecimalFormat(pattern);
        formattedString = df.format(number);
        return formattedString;
    }

    public static int hexStringToInt(String hex) {
        int value = 0;
        if (StringUtils.hasText(hex)) {
            hex = hex.toLowerCase();
            if (hex.startsWith("0x"))
                hex = hex.substring(2);
            String max = "7fffffff";
            if (hex.length() > max.length() || hex.length() == max.length() && hex.charAt(0) > '7')
                throw new IllegalArgumentException("int의 최대값(2147483647)을 넘어가는 수 입니다.");
            char h[] = hex.toCharArray();
            for (int i = 0; i < h.length; i++)
                value = (int) ((double) value + (double) hexToInt(h[i]) * Math.pow(16D, h.length - i - 1));

        }
        return value;
    }

    public static int hexToInt(char c) {
        if (c >= 'a' && c <= 'f')
            return (c - 97) + 10;
        if (c >= 'A' && c <= 'F')
            return (c - 65) + 10;
        if (c == '0')
            return 0;
        else
            return (c - 49) + 1;
    }
}
