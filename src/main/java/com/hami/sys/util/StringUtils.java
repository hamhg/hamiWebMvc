package com.hami.sys.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * <li>Program Name : StringUtils
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
public abstract class StringUtils extends org.springframework.util.StringUtils {
    /**
     * 문자열이 null이면 defaultStr를 반환한다.
     */
    public static String nvl(String str, String defaultStr)
    {
        return str == null || str.length() == 0 ? defaultStr : str;
    }

    /**
     * 문자열이 null이면 ""(empty string)을 반환한다.
     */
    public static String nvl(String str)
    {
        return nvl(str, "");
    }

    /**
     * null인지 판단하여 true/false를 반환한다.
     */
    public static boolean isNull(String str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * str을 cnt만큼 반복한다.
     */
    public static String repeat(String str, int cnt)
    {
        StringBuffer sb = new StringBuffer();
        for (int n = cnt; n > 0; n--)
        {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 왼쪽 패딩
     */
    public static String lpad(String str, int size, char pad)
    {
        str = nvl(str);
        int cnt = size - str.length();
        return repeat(String.valueOf(pad), cnt) + str;
    }

    /**
     * 오른쪽 패딩
     */
    public static String rpad(String str, int size, char pad)
    {
        str = StringUtils.nvl(str);
        int cnt = size - str.length();
        return str + repeat(String.valueOf(pad), cnt);
    }

    /**
     * 왼쪽 byte padding
     */
    public static String lpadb(String str, int size, char pad)
    {
        int cnt = size - str.getBytes().length;
        return repeat(String.valueOf(pad), cnt) + str;
    }

    /**
     * 오른쪽 byte padding
     */
    public static String rpadb(String str, int size, char pad)
    {
        str = StringUtils.nvl(str);
        int cnt = size - str.getBytes().length;
        return str + repeat(String.valueOf(pad), cnt);
    }

    /**
     * 문자열 배열을 join해서 문자열로 반환한다.
     */
    public static String join(String[] arrStr, String delim, String start, String end)
    {
        StringBuffer sb = new StringBuffer();
        for (int n = 0; n < arrStr.length; n++)
        {
            if (n != 0) sb.append(delim);
            if (start != null) sb.append(start);
            sb.append(arrStr[n]);
            if (end != null) sb.append(end);
        }
        return sb.toString();
    }

    /**
     * 문자열 배열에 str이 포함되있으면 true를 반환한다.
     */
    public static boolean arrContains(String[] arrStr, String str)
    {
        for (int n = 0; n < arrStr.length; n++)
        {
            if (str.equals(arrStr[n])) { return true; }
        }
        return false;
    }

    /**
     * 문자열에 str이 포함되있으면 true를 반환한다.
     */
    public static boolean contains(String s, String pattern) {
        if (!hasText(s) || !hasText(pattern))
            return false;
        else
            return Pattern.compile(pattern).matcher(s).find();
    }

    /**
     * 문자열에 str이 포함되있으면 true를 반환한다. (대소문자 구분 없이)
     */
    public static boolean containsCaseInsensitive(String str, String pattern) {
        if (!hasText(str) || !hasText(pattern))
            return false;
        else
            return Pattern.compile(pattern, 2).matcher(str).find();
    }

    /**
     * 문자열에 str이 포함되있으면 true를 반환한다.
     */
    public static boolean matches(String str, String pattern) {
        if (!hasText(str) || !hasText(pattern))
            return false;
        else
            return Pattern.compile(pattern).matcher(str).matches();
    }

    public static boolean containsNumber(String str) {
        return contains(str, "[0-9]");
    }

    public static boolean containsAlphabet(String str) {
        return contains(str, "[a-zA-Z]");
    }

    public static boolean containsUpperCase(String str) {
        return contains(str, "[A-Z]");
    }

    public static boolean containsLowerCase(String str) {
        return contains(str, "[a-z]");
    }

    public static boolean containsKorean(String str) {
        return contains(str, "[ㄱ-ㅎㅏ-ㅣ가-힣]");
    }

    public static boolean isAlphabet(String str) {
        return matches(str, "[a-zA-Z]*");
    }

    public static boolean isUpperCase(String str) {
        return matches(str, "[A-Z]*");
    }

    public static boolean isLowerCase(String str) {
        return matches(str, "[a-z]*");
    }

    public static boolean isKorean(String str) {
        return matches(str, "[ㄱ-ㅎㅏ-ㅣ가-힣]*");
    }

    public static boolean isAlphaNumeric(String str) {
        return matches(str, "[\\s\\w]*");
    }

    public static boolean isMixedAlphaNumeric(String str) {
        return containsNumber(str) && containsAlphabet(str);
    }
    
    /**
     * 문자열을 잘라서 반환한다. JavaScript의 substr과 동일하다.
     */
    public static String substr(String str, int startIdx, int cnt)
    {
        if (str == null) return str;
        int strLen = str.length();
        if (startIdx < 0) startIdx = 0;
        if (startIdx > strLen) startIdx = strLen;
        if (cnt < 0) cnt = 0;
        int endIdx = startIdx + cnt;
        if (endIdx > strLen) endIdx = strLen;
        return str.substring(startIdx, endIdx);
    }

    /**
     * 100으로 나눠 백분율로 변경한 값을 반환한다.
     */
    public static String perc2rate(String str)
    {
        if (str == null) return str;
        return String.valueOf(Double.parseDouble(str) / 100.0);
    }

    /**
     * 문자열이 null인지 확인하고 null인 경우 지정된 문자열로 바꾸는 함수.
     *
     * <pre>
     *  String id1 = strUtil.isNull(request.getParameter(&quot;id1&quot;),&quot;&quot;);
     *  서블릿 요청 파라메터 id1에 대한 값이 null이면 &quot;&quot; 로 바꾼다.
     * </pre>
     *
     * @param source
     *            원본 문자열
     * @param value
     *            null일경우 바뀔 문자열
     * @return 문자열
     */
    public static String isNull(String source, String value)
    {
        String retVal;
        if (source == null || source.trim().equals("") || source.trim().equals("null"))
        {
            retVal = value;
        }
        else
        {
            retVal = source.trim();
        }
        return retVal;
    }

    public static boolean isNullString(String s) {
        return hasText(s) && s.trim().equals("null");
    }

    /**
     * 문자열을 int형으로 변환하고, null인지 확인하여 null인 경우 지정된 int로 바꾸는 함수.
     *
     * <pre>
     *  int id1 = strUtil.isNull(request.getParameter(&quot;id1&quot;),0);
     *  서블릿 요청 파라메터 id1에 대한 값이 null이면 0으로 바꾼다.
     *  null이 아니면 int형으로 변환하고, 변환 불가능하면 지정값 0으로 되돌린다.
     * </pre>
     *
     * @param source
     *            원본 문자열
     * @param val
     *            null일경우 바뀔 문자열
     * @return 정수
     */
    public static int isNull(String source, int value)
    {
        int retVal;
        try
        {
            if (source == null || source.trim().equals(""))
            {
                retVal = value;
            }
            else
            {
                retVal = Integer.parseInt(source);
            }
        }
        catch (Exception e)
        {
            retVal = value;
        }
        return retVal;
    }

    /**
     * source를 long으로 변환시 null이면 value를 반환한다.
     */
    public static long isNull(String source, long value)
    {
        long retVal;
        try
        {
            if (source == null || source.trim().equals(""))
            {
                retVal = value;
            }
            else
            {
                retVal = Long.parseLong(source);
            }
        }
        catch (Exception e)
        {
            retVal = value;
        }
        return retVal;
    }

    /**
     * source를 float로 변환시 null이면 value를 반환한다.
     */
    public static float isNull(String source, float value)
    {
        float retVal;
        try
        {
            if (source == null || source.trim().equals(""))
            {
                retVal = value;
            }
            else
            {
                retVal = Float.parseFloat(source);
            }
        }
        catch (Exception e)
        {
            retVal = value;
        }
        return retVal;
    }

    /**
     * source를 double로 변환시 null이면 value를 반환한다.
     */
    public static double isNull(String source, double value)
    {
        double retVal;
        try
        {
            if (source == null || source.trim().equals(""))
            {
                retVal = value;
            }
            else
            {
                retVal = Double.parseDouble(source);
            }
        }
        catch (Exception e)
        {
            retVal = value;
        }
        return retVal;
    }

    /**
     * 문자열을 구분자로 나눠 문자열배열로 변환한다.
     */
    public static String[] split(String source, String separator) throws NullPointerException
    {
        String[] rtnVal = null;
        int cnt = 1;
        int index = source.indexOf(separator);
        int index0 = 0;
        while (index >= 0)
        {
            cnt++;
            index = source.indexOf(separator, index + 1);
        }
        rtnVal = new String[cnt];
        cnt = 0;
        index = source.indexOf(separator);
        while (index >= 0)
        {
            rtnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        rtnVal[cnt] = source.substring(index0);
        return rtnVal;
    }

    /**
     * 년월일을 문자열화 한다.
     */
    public static String formatDate(String d)
    {
        if (d == null) { return ""; }
        d = d.trim();
        if (d.length() == 8)
        {
            return d.substring(0, 4) + "." + d.substring(4, 6) + "." + d.substring(6, 8);
        }
        else
        {
            return d;
        }
    }

    /**
     * 년월일을 지정한 포멧으로 문자열화 한다.
     */
    public static String formatDate(String d, String format)
    {
        if (d == null) { return ""; }
        d = d.trim();
        if (format == null) return d;
        if (format != null && !"".equals(format))
        {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < d.length(); i++)
            {
                if (!String.valueOf(d.charAt(i)).trim().equals(format))
                {
                    buffer.append(d.charAt(i));
                }
            }
            return buffer.toString();
        }
        else
        {
            return d;
        }
    }
    public static String[] splitByRegExp(String src, String delimExp) {
        if (!hasText(src))
            return null;
        if (!hasText(delimExp))
            return (new String[]{src});
        else
            return src.split(delimExp);
    }

    public static int repetitionCount(String target, String pattern) {
        int count;
        try {
            if (!isContained(target, pattern))
                return 0;
        } catch (Exception e) {
            return -1;
        }
        count = 0;
        for (String dest = target; dest.indexOf(pattern) >= 0;) {
            dest = dest.replaceFirst(pattern, "");
            count++;
        }

        return count;
    }

    public static boolean isContained(String target, String pattern) {
        return contains(target, pattern);
    }

    public static String trimSpecifiedString(String target, String pattern[]) {
        String result = null;
        if (target == null)
            return null;
        if (pattern == null)
            return target;
        for (int i = 0; i < pattern.length; i++)
            if (target.endsWith(pattern[i]))
                result = target.replaceAll(pattern[i], "");

        result = result.trim();
        return result;
    }

    public static boolean isForwardContinousLetter(String target, int permissableCount) {
        return isContinousLetter(target, permissableCount, true);
    }

    public static boolean isBackwardContinousLetter(String target, int permissableCount) {
        return isContinousLetter(target, permissableCount, false);
    }

    public static boolean isContinousLetter(String target, int permissableCount, boolean isForward) {
        if (!hasText(target))
            return false;
        char tempChar = '\0';
        int tempCount = 0;
        permissableCount--;
        int i = 0;
        for (int targetLength = target.length(); i < targetLength; i++) {
            char currentChar = target.charAt(i);
            if (isForward) {
                if (i != 0 && currentChar == tempChar + 1)
                    tempCount++;
                else
                    tempCount = 0;
            } else if (i != 0 && currentChar == tempChar - 1)
                tempCount++;
            else
                tempCount = 0;
            if (tempCount == permissableCount)
                return true;
            tempChar = currentChar;
        }

        return false;
    }

    public static final String defaultString(String s) {
        return defaultString(s, "");
    }

    public static final String defaultString(String s, String defaultString) {
        if (s == null)
            return defaultString;
        else
            return s;
    }

    public static String printFormattedToHexString(String target) {
        return printFormattedToHexString(target.getBytes());
    }

    public static String printFormattedToHexString(byte target[]) {
        StringBuffer buffer = new StringBuffer("[");
        for (int i = 0; i < target.length; i++) {
            buffer.append(toHexString(target[i]));
            if (i != target.length - 1)
                buffer.append(",");
        }

        buffer.append("]");
        return buffer.toString();
    }

    public static String printHexString(String target, int lineShowCharSize) {
        return printHexString(target.getBytes(), lineShowCharSize);
    }

    public static String printHexString(byte target[], int lineShowCharSize) {
        byte cloneTarget[] = new byte[target.length];
        for (int i = 0; i < cloneTarget.length; i++)
            cloneTarget[i] = target[i] < 0 || target[i] >= 32 ? target[i] : 46;

        StringBuffer buffer = new StringBuffer(prettyPrint(lineShowCharSize));
        buffer.append("Source bytes Length : [").append(target.length).append("]\n");
        buffer.append(prettyPrint(lineShowCharSize));
        buffer.append("[   Row Number]");
        for (int i = 1; i <= lineShowCharSize; i++)
            buffer.append(" ").append(lpad(Integer.toString(i), 2, '0'));

        buffer.append(" | [").append(lpad("DATA]", lineShowCharSize - 1, ' '));
        buffer.append("\n");
        buffer.append(prettyPrint(lineShowCharSize));
        int printCount = target.length % lineShowCharSize;
        printCount = printCount != 0 ? target.length / lineShowCharSize + 1 : target.length / lineShowCharSize;
        for (int i = 0; i < printCount; i++) {
            buffer.append("[").append(lpad(Integer.toString(i * lineShowCharSize + 1), 6, '0')).append("-");
            if (i == printCount)
                buffer.append(lpad(Integer.toString(target.length), 6, '0'));
            else
                buffer.append(lpad(Integer.toString((i + 1) * lineShowCharSize), 6, '0'));
            buffer.append("]");
            for (int j = 0; j < lineShowCharSize && i * lineShowCharSize + j != target.length; j++)
                buffer.append(" ").append(toHexString(target[i * lineShowCharSize + j]));

            if (target.length % lineShowCharSize != 0 && i == printCount - 1) {
                int appendCount = (lineShowCharSize - target.length % lineShowCharSize) * 3;
                buffer.append(rpad("", appendCount, ' '));
                buffer.append(" | ");
                buffer.append(new String(cloneTarget, i * lineShowCharSize, target.length % lineShowCharSize));
            } else {
                buffer.append(" | ");
                buffer.append(new String(cloneTarget, i * lineShowCharSize, lineShowCharSize));
            }
            buffer.append("\n");
        }

        buffer.append(prettyPrint(lineShowCharSize));
        return buffer.toString();
    }

    private static String prettyPrint(int lineShowCharSize) {
        StringBuffer buffer = new StringBuffer("===============");
        for (int i = 1; i <= lineShowCharSize; i++)
            buffer.append("===");

        buffer.append("===");
        for (int i = 1; i <= lineShowCharSize; i++)
            buffer.append("=");

        buffer.append("\n");
        return buffer.toString();
    }

    public static String toHexString(byte target) {
        int i = target >= 0 ? ((int) (target)) : target & 255;
        String targetString = Integer.toHexString(i).toUpperCase();
        return lpad(targetString, 2, '0');
    }

    public static String toHexString(String target) {
        if (target == null)
            return null;
        else
            return toHexString(target.getBytes());
    }

    public static String toHexString(byte target[]) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < target.length; i++)
            buffer.append(Integer.toHexString(target[i]));

        return buffer.toString();
    }

    public static String makeTemplateString(int size, char initialChar) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++)
            buffer.append(initialChar);

        return buffer.toString();
    }
    public static String normalizeWhiteSpace(String src) {
        if (src == null)
            return null;
        else
            return src.replaceAll("\\s", "");
    }

    public static String convertCamalCase(String src) {
        return convertCamalCase(src, "_");
    }

    public static String convertCamalCase(String src, String delimiter) {
        if (!hasText(src))
            return src;
        String words[] = tokenizeToStringArray(src.toLowerCase(), delimiter);
        StringBuffer buffer = new StringBuffer();
        buffer.append(words[0]);
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            buffer.append(capitalize(word));
        }

        return buffer.toString();
    }

    public static String convertUnderScore(String name) {
        StringBuffer result = new StringBuffer();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toLowerCase());
            boolean firstDigit = true;
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                if (Pattern.compile("[A-Z]").matcher(s).find()) {
                    result.append("_");
                    result.append(s.toLowerCase());
                    continue;
                }
                if (Pattern.compile("[0-9]").matcher(s).find() && firstDigit) {
                    firstDigit = false;
                    result.append("_");
                    result.append(s.toLowerCase());
                } else {
                    result.append(s);
                }
            }

        }
        return result.toString();
    }

    public static String toDoubleByteCharSetString(String halfString) {
        String fullString = "";
        if (hasText(halfString)) {
            int len = halfString.length();
            for (int i = 0; i < len; i++) {
                char c = halfString.charAt(i);
                if (c == ' ') {
                    fullString = (new StringBuilder()).append(fullString).append(String.valueOf('　')).toString();
                    continue;
                }
                if (c >= '가' && c <= '힣')
                    fullString = (new StringBuilder()).append(fullString).append(c).toString();
                else
                    fullString = (new StringBuilder()).append(fullString).append(String.valueOf((char) (c + 65248)))
                            .toString();
            }

        }
        return fullString;
    }

    public static String toMixedCharSetString(String fullString) {
        String halfString = "";
        if (hasText(fullString)) {
            int len = fullString.length();
            for (int i = 0; i < len; i++) {
                char c = fullString.charAt(i);
                if (c == '　') {
                    halfString = (new StringBuilder()).append(halfString).append(" ").toString();
                    continue;
                }
                if (c >= '가' && c <= '힣') {
                    halfString = (new StringBuilder()).append(halfString).append(c).toString();
                    continue;
                }
                if (c >= '＀' && c <= '￯')
                    halfString = (new StringBuilder()).append(halfString).append(String.valueOf((char) (c - 65248)))
                            .toString();
                else
                    halfString = (new StringBuilder()).append(halfString).append(c).toString();
            }

        }
        return halfString;
    }

    public static String delete(String str, String pattern) {
        if (!hasText(pattern))
            return str;
        else
            return replace(str, pattern, "");
    }

    public static String camelize(String str, String splitPattern) {
        if (!hasText(str))
            return str;
        String parts[] = str.split(splitPattern);
        if (parts.length == 1)
            return parts[0].toLowerCase();
        StringBuffer camelized = new StringBuffer();
        for (int i = 0; i < parts.length; i++)
            if (hasText(parts[i])) {
                camelized.append(Character.toUpperCase(parts[i].charAt(0)));
                camelized.append(parts[i].substring(1).toLowerCase());
            }

        camelized.setCharAt(0, Character.toLowerCase(camelized.charAt(0)));
        return camelized.toString();
    }

    public static String camelize(String str) {
        return camelize(str, "_");
    }

    public static String decamelize(String str, String splitPattern, boolean toUpperCase) {
        if (!hasText(str))
            return str;
        StringBuffer decamelized = new StringBuffer();
        if (toUpperCase)
            decamelized.append(Character.toUpperCase(str.charAt(0)));
        else
            decamelized.append(Character.toLowerCase(str.charAt(0)));
        int i = 1;
        for (int length = str.length(); i < length; i++) {
            char c = str.charAt(i);
            if (i != 0 && Character.isUpperCase(c))
                decamelized.append(splitPattern);
            if (toUpperCase)
                decamelized.append(Character.toUpperCase(c));
            else
                decamelized.append(Character.toLowerCase(c));
        }

        return decamelized.toString();
    }

    public static String decamelize(String str) {
        return decamelize(str, "_", true);
    }

    public static final String escapeEntities(String text) {
        if (!hasLength(text))
            return text;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '<') {
                sb.append("&lt;");
                continue;
            }
            if (c == '>') {
                sb.append("&gt;");
                continue;
            }
            if (c == '"') {
                sb.append("&#034;");
                continue;
            }
            if (c == '\'') {
                sb.append("&#039;");
                continue;
            }
            if (c == '&')
                sb.append("&amp;");
            else
                sb.append(c);
        }

        return sb.toString();
    }

    /**
     * 문자열을 치환한다.
     */
    public static String replace(String str, HashMap hashMap, String sPattern, boolean null2empty)
    {
        //"@@((\\w|[ㄱ-힝])+)@@"
        sPattern = nvl(sPattern, "@@((\\w|[ㄱ-힝])+)@@");
        Pattern pattern = Pattern.compile(sPattern);
        String value;
        String param;
        int prevEnd = 0;
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            param = matcher.group(1);
            value = (String) hashMap.get(param);
            if (value == null)
            {
                value = null2empty ? "" : matcher.group(0);
            }
            //System.out.println(param);
            //System.out.println(value);
            sb.append(str.substring(prevEnd, matcher.start()));
            sb.append(value);
            prevEnd = matcher.end();
        }
        // matcher.appendTail(sb);
        sb.append(str.substring(prevEnd));
        return sb.toString();
    }

    /**
     * \를 \\로 치환한다.
     */
    public static String quoto(String str)
    {
        if (str == null) return str;
        if (str.indexOf('\\') >= 0 || str.indexOf('"') >= 0 || str.indexOf('\'') >= 0 || str.indexOf('\n') >= 0 || str.indexOf('\r') >= 0)
        {
            return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\\"", "\\\\\"").replaceAll("'", "\\\\'").replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
        }
        else return str;
    }

    /**
     * 문자열을 치환한다.
     */
    public static String replaceByPattern(String str, Pattern pattern, HashMap hashMap)
    {
        Object value;
        String param;
        int prevEnd = 0;
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            param = matcher.group(1).toUpperCase();
            value = hashMap.get(param);
            sb.append(str.substring(prevEnd, matcher.start()));
            sb.append(value instanceof String[] ? ":" + param : value == null ? "null" : "'" + value.toString() + "'");
            prevEnd = matcher.end();
            // matcher.appendReplacement(sb, value instanceof String[] ?
            // ":"+param :
            // value == null ? "null" : "'"+value.toString()+"'");
        }
        // matcher.appendTail(sb);
        sb.append(str.substring(prevEnd));
        return sb.toString();
    }

    /**
     * Enumeration를 문자열로 반환한다.
     */
    public static String joinEnum(Enumeration strEnum, String delim, String start, String end)
    {
        StringBuffer sb = new StringBuffer();
        int n = 0;
        while (strEnum.hasMoreElements())
        {
            if (n != 0) sb.append(delim);
            if (start != null) sb.append(start);
            sb.append((String) strEnum.nextElement());
            if (end != null) sb.append(end);
            n++;
        }
        return sb.toString();
    }

    public static String makeParamToJson(HttpServletRequest request, String param_names) throws UnsupportedEncodingException {
        param_names = StringUtils.nvl(param_names);
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

    /**
     * HashMap 을 Properties 로 변환
     * @param hashMap
     * @return
     */
    public static Properties makeHashMapToProperties(HashMap hashMap)
    {
        Properties prop = new Properties();
        Iterator key_iter = hashMap.keySet().iterator();
        String key, val;
        while (key_iter.hasNext())
        {
            key = (String) key_iter.next();
            val = (String) hashMap.get(key);
            prop.setProperty(key, val);
        }
        return prop;
    }

    /**
     * jsp 내에서 직접표시되는 스트링의 XSS(크로스 사이트 스크립틍) 방지위한 치환
     * @param String
     * @return
     */
    public static String xss(String srcStr)
    {
        //black list
        //<
        //&
        //>
        //"
        //'
        //&
        //따옴표가 없는 곳에서의 공백 또는 탭
        //줄바꿈
        //%
        return srcStr == null ? null : srcStr.replaceAll("[<>\"'\n\r\f]", "");
    }

    /**
     * nvl + replaceXss
     * @param String
     * @return
     */
    public static String nvlXss(String srcStr, String defaultStr)
    {
        return xss(nvl(srcStr, defaultStr));
    }

    /**
     * nvl + replaceXss
     * @param String
     * @return
     */
    public static String nvlXss(String srcStr)
    {
        return xss(nvl(srcStr));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException
    {
        //System.out.println(quoto("1\"2\'3\n4\\"));
        /*
        HashMap hashMap = new HashMap();
        hashMap.put("abc", "one two three");
        System.out.println(replace("111@@abc@@222@@abcd@@", hashMap, null, false));
        */
        //System.out.println(MD5("a=b&b=c"));
        /*
                String str1 = "abc123가나다";
                for (int n = 0, nlen = str1.length(); n < nlen; n++)
                {
                    System.out.println((int) str1.charAt(n));
                }
        */
        System.out.println(xss("abc<>\ndeff\rxx'''d\"f"));
        System.out.println(xss(""));
        System.out.println(xss(""));
        System.out.println(xss(""));
        System.out.println(xss(""));
    }
}
