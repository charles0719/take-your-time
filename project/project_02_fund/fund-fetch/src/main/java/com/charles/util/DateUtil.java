package com.charles.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    /**
     * 日期格式（yyyy-MM-dd）
     */
    public static String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * 日期格式（"yyyy-MM-dd HH:mm:ss"）
     */
    public static String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取日期和时间(对象,时、分、秒不为0)
     *
     * @return
     */
    public static Date dateTime() {
        return new Date();
    }

    /**
     * 获取日期(字符串)
     *
     * @param pattern 时间字符串格式
     * @return
     */
    public static String get(String pattern) {
        return new SimpleDateFormat(pattern).format(dateTime());
    }

    // *********************************************************

    /**
     * 获取日期(字符串) 例如：2015-01-01
     *
     * @return
     */
    public static String getDate() {
        return get(PATTERN_DATE);
    }

    /**
     * 获取日期和时间(字符串) 例如：2015-01-01 01:59:59
     *
     * @return
     */
    public static String getDateTime() {
        return get(PATTERN_DATETIME);
    }

    // *********************************************************

    /**
     * 获取日期(对象,时、分、秒为0)
     *
     * @return
     */
    public static Date date(String date, String pattern) {
        try {
            if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(pattern)) {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                return sdf.parse(date);
            }
        } catch (ParseException e) {
            // e.printStackTrace(); 这里不打印
            // 如果throw java.text.ParseException，就说明格式不对
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期(对象,时、分、秒为0)
     *
     * @return
     */
    public static Date date(String date) {
        return date(date, PATTERN_DATE);
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static Date dateTime(String date) {
        return date(date, PATTERN_DATETIME);
    }

    /**
     * 获取日期(对象,时、分、秒为0)
     *
     * @return
     */
    public static Date date() {
        return date(getDate());
    }

    // *********************************************************

    /**
     * 获取指定日期(字符串)
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formt(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取指定日期(字符串)
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formtDate(Date date) {
        return formt(date, PATTERN_DATE);
    }

    /**
     * 获取指定日期和时间(字符串)
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formtDateTime(Date date) {
        return formt(date, PATTERN_DATETIME);
    }
}
