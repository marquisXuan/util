package org.yyx.util.date;

import org.joda.time.DateTime;
import org.yyx.exception.ParamException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期相关的工具类
 * Create by 叶云轩 at 2018/1/24 18:26
 * Concat at yCountJavaXuan@outlook.com
 */
public class UtilDate {

    /**
     * private constructor
     */
    private UtilDate() {
    }

    /**
     * parse dateString to java.util.Date
     * default pattern value is "yyyy-MM-dd HH:mm:SS"
     *
     * @param dateStr dateString
     * @return java.util.Date
     */
    public static Date stringToJavaUtilDate(String dateStr) {
        return stringToJavaUtilDate(dateStr, null);
    }

    /**
     * parse dateString to java.util.Date
     *
     * @param dateStr dateString
     * @param pattern parse rule
     * @return java.util.Date
     */
    public static Date stringToJavaUtilDate(String dateStr, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:SS";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new ParamException("转换规则输入错误：" + pattern);
        }

    }

    /**
     * parse java.util.date to String
     * default pattern value is "yyyy-MM-dd HH:mm:SS"
     *
     * @param date java.util.date
     * @return format date String
     */
    public static String javaUtilDateToString(Date date) {
        return javaUtilDateToString(date, null);
    }

    /**
     * java.util.Date format to java.util.String
     * the default value of pattern is yyyy-MM-dd HH:mm:SS
     *
     * @param date    date
     * @param pattern pattern
     * @return format date
     */
    public static String javaUtilDateToString(Date date, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:SS";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String format = simpleDateFormat.format(date);
            return format;
        } catch (Exception e) {
            throw new ParamException("转换规则输入错误：" + pattern);
        }
    }

    /**
     * Date type transform method
     *
     * @param sqlDate java.sql.Date
     * @return java.util.Date
     */
    public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) {
        long time = sqlDate.getTime();
        return new Date(time);
    }

    /**
     * java.util.date transform java.sql.date
     *
     * @param date java.util.Date date
     * @return java.sql.Date
     */
    public static java.sql.Date javaUtilDateToSqlDate(Date date) {
        long time = date.getTime();
        return new java.sql.Date(time);
    }

    /**
     * org.joda.DateTime to java.sql.Date
     *
     * @param dateTime DateTime date
     * @return java.sql.Date
     */
    public static java.sql.Date dateTimeToSqlDate(DateTime dateTime) {
        long millis = dateTime.getMillis();
        return new java.sql.Date(millis);
    }
}
