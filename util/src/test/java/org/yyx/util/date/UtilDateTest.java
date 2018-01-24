package org.yyx.util.date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class UtilDateTest {

    /**
     * UtilDateTest 日志控制器
     * Create by 叶云轩 at 2018/1/24 18:44
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilDateTest.class);

    @Test
    public void stringToJavaUtilDate() {
        LOGGER.info("[date] {}", UtilDate.stringToJavaUtilDate("2018-01-24 18:54:58"));
    }

    @Test
    public void stringToJavaUtilDateWithPattern() {
        LOGGER.info("[date] {}", UtilDate.stringToJavaUtilDate("2018-01-24","yyyy-MM-dd"));
    }

    @Test
    public void javaUtilDateToString() {
        LOGGER.info("[str] {}", UtilDate.javaUtilDateToString(new Date()));
    }

    @Test
    public void javaUtilDateToStringWithPattern() {
        LOGGER.info("[str] {}", UtilDate.javaUtilDateToString(new Date(), "yyyy:MM:dd HH-mm-sss"));
    }

    @Test
    public void sqlDateToUtilDate() {
        java.sql.Date date = new java.sql.Date(11111232);
        LOGGER.info("[util-date] {}", UtilDate.sqlDateToUtilDate(date));
    }

    @Test
    public void javaUtilDateToSqlDate() {
        LOGGER.info("[sql-date] {}", UtilDate.javaUtilDateToSqlDate(new Date()));
    }

    @Test
    public void dateTimeToSqlDate() {
        LOGGER.info("[sql] {}", UtilDate.dateTimeToSqlDate(DateTime.now()));
    }
}