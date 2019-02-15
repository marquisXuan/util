package org.yyx.xf.tool.date.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.yyx.xf.commons.domain.exception.ParamException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期相关的工具类
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/1/24 18:26
 */
public class UtilDate {

    /**
     * private constructor
     * 私有构造方法
     */
    private UtilDate() {
    }

    /**
     * org.joda.DateTime to java.sql.Date
     * <p>
     * org.joda.DateTime日期类型对象转换为java.sql.Date对象
     *
     * @param dateTime DateTime date 待转换日期对象
     * @return java.sql.Date 转换后的日期对象
     */
    @Deprecated
    public static java.sql.Date dateTimeToSqlDate(DateTime dateTime) {
        long millis = dateTime.getMillis();
        return new java.sql.Date(millis);
    }

    /**
     * java.util.date transform java.sql.date
     * <p>
     * java.util.Date日期类型对象转换为java.sql.Date对象
     *
     * @param date java.util.Date date 待转换日期对象
     * @return java.sql.Date 转换后的日期对象
     */
    public static java.sql.Date javaUtilDateToSqlDate(Date date) {
        long time = date.getTime();
        return new java.sql.Date(time);
    }

    /**
     * parse java.util.date to String
     * default pattern value is "yyyy-MM-dd HH:mm:SS"
     * <p>
     * 将java.util.Date类型的日期转换为日期字符串
     * 默认转换日期格式为"yyyy-MM-dd HH:mm:SS"
     *
     * @param date java.util.date 待转换日期
     * @return format date String 转换后的日期字符串
     */
    public static String javaUtilDateToString(Date date) {
        return javaUtilDateToString(date, null);
    }

    /**
     * java.util.Date format to java.util.String
     * the default value of pattern is yyyy-MM-dd HH:mm:SS
     * <p>
     * 将java.util.Date类型的日期转换为日期字符串
     * 默认转换日期格式为"yyyy-MM-dd HH:mm:SS"
     *
     * @param date    date 待转换日期
     * @param pattern pattern 转换规则 默认转换日期格式为"yyyy-MM-dd HH:mm:SS"
     * @return format date String 转换后的日期字符串
     */
    public static String javaUtilDateToString(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:SS";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            throw new ParamException("转换规则输入错误：" + pattern);
        }
    }

    /**
     * Date type transform method
     * <p>
     * java.sql.Date日期类型对象转换为java.util.Date对象
     *
     * @param sqlDate java.sql.Date 待转换日期对象
     * @return java.util.Date 转换后的日期对象
     */
    public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) {
        long time = sqlDate.getTime();
        return new Date(time);
    }

    /**
     * parse dateString to java.util.Date
     * default pattern value is "yyyy-MM-dd HH:mm:SS"
     * <p>
     * 将日期字符串转换为java.util.Date类型的日期
     * 默认日期格式为"yyyy-MM-dd HH:mm:SS"
     *
     * @param dateStr dateString 待转换日期字符串
     * @return java.util.Date 转换后的日期
     */
    public static Date stringToJavaUtilDate(String dateStr) {
        return stringToJavaUtilDate(dateStr, null);
    }

    /**
     * parse dateString to java.util.Date
     * <p>
     * 将日期字符串转换为java.util.Date类型的日期
     *
     * @param dateStr dateString 待转换日期字符串
     * @param pattern parse rule 转换格式 默认日期格式为"yyyy-MM-dd HH:mm:SS"
     * @return java.util.Date 转换后的日期
     */
    public static Date stringToJavaUtilDate(String dateStr, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:SS";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new ParamException("转换规则输入错误：" + pattern);
        }
    }
}
