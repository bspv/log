package com.bazzi.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public final class DateUtil {
    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss:SS";

    /**
     * 去掉日期中的时分秒毫秒
     *
     * @param date 日期
     * @return 去掉时分秒毫秒的日期
     */
    public static Date clearHHmmssms(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 去掉strDate日期中的时分秒毫秒
     *
     * @param strDate 字符串格式日期
     * @param format  日期的格式
     * @return 去掉时分秒毫秒的日期
     */
    public static Date clearHHmmssms(String strDate, String format) {
        Date d = getDate(strDate, format);
        return clearHHmmssms(d);
    }

    /**
     * 获取日期的最后一秒，即yyyy-MM-dd 23:59:59
     *
     * @param date 日期
     * @return 该日期最后一秒的日期
     */
    public static Date lastSecondOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 根据strDate获取该日期的最后一秒，即yyyy-MM-dd 23:59:59
     *
     * @param strDate 字符串格式日期
     * @param format  日期的格式
     * @return 该日期最后一秒的日期
     */
    public static Date lastSecondOfDay(String strDate, String format) {
        Date d = getDate(strDate, format);
        return lastSecondOfDay(d);
    }

    /**
     * 获取相隔n天的日期，n正数往后，n负数则往前
     *
     * @param date 日期
     * @param n    n正数往后，n负数则往前
     * @return 相隔n天的日期
     */
    public static Date getNextDay(Date date, int n) {
        if (date == null) {
            return null;
        }
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        calDate.set(Calendar.YEAR, calDate.get(Calendar.YEAR));
        calDate.set(Calendar.MONTH, calDate.get(Calendar.MONTH));
        calDate.set(Calendar.DAY_OF_MONTH, calDate.get(Calendar.DAY_OF_MONTH) + n);
        return calDate.getTime();
    }

    /**
     * 将日期格式化成format格式的字符串
     *
     * @param date   日期
     * @param format 日期的格式
     * @return 字符串日期
     */
    public static String formatDate(Date date, String format) {
        if (date == null || format == null || "".equals(format))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将format格式的字符串格式化成日期
     *
     * @param strDate 字符串格式的日期
     * @param format  日期的格式
     * @return 返回日期
     */
    public static Date getDate(String strDate, String format) {
        if (strDate == null || "".equals(strDate) || format == null || "".equals(format))
            return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(strDate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取年月日，yyyy-MM-dd格式字符串
     *
     * @param date 日期
     * @return yyyy-MM-dd格式的年月日
     */
    public static String getYmd(Date date) {
        return formatDate(date, YMD_FORMAT);
    }

    /**
     * 获取当前小时
     *
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        return calDate.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取小时，按照两位进行返回
     *
     * @param date 日期
     * @return 小时
     */
    public static String getHH(Date date) {
        if (date == null)
            return null;
        int hour = getHour(date);
        if (hour < 0 || hour > 23)
            return null;
        if (hour < 10)
            return "0" + hour;
        return String.valueOf(hour);
    }

}
