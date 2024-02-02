package com.ame.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class DateUtils {

    public static final String SIMPLE_DATE_FORMAT = "MM-dd";
    public static final String DATE_SHORT = "yyyy-MM-dd";
    public static final String DATETIME_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATETIME_FULL_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_SHORT_SLASH = "yyyy/MM/dd";
    public static final String DATE_SHORT_NUM = "yyyyMMdd";
    public static final String TIME_FULL = "HH:mm:ss";
    public static final String TIME_HH_MM = "HH:mm";

    private static final long HOURS_PER_DAY = 24L;
    private static final long MINUTES_PER_HOUR = 60L;
    private static final long SECONDS_PER_MINUTE = 60L;
    private static final long MILLION_SECONDS_PER_SECOND = 1000L;
    private static final long MILLION_SECONDS_PER_MINUTE = 60000L;
    private static final long MILLION_SECONDS_SECOND_PER_DAY = 86400000L;
    private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfDateNum = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat sdfDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    private DateUtils() {}

    public static Date getDate(String date) {
        return getDate(date, "yyyy-MM-dd", null);
    }

    public static Date getMaxDate() {
        return getDate("9999-12-31", "yyyy-MM-dd", null);
    }

    public static Date getDateTime(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        } else {
            date = date.replaceAll("/", "-");
            return getDate(date, "yyyy-MM-dd HH:mm:ss", null);
        }
    }

    public static long getDateMilles(Date date, String format) {
        String formateDate = (new SimpleDateFormat(format)).format(date);

        try {
            return (new SimpleDateFormat(format)).parse(formateDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static Date getDate(String date, String format) {
        return getDate(date, format, null);
    }

    public static Date getDate(String date, String format, Date defVal) {
        Date d;
        try {
            d = (new SimpleDateFormat(format)).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            d = defVal;
        }

        return d;
    }

    public static Date getDate(LocalDate localDate) {
        if (localDate != null) {
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public static Date getDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate getLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime getLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd", null);
    }

    public static String formatDate(Date date, String format) {
        return formatDate(date, format, null);
    }

    public static String forDatetime(Date date) {
        return date != null ? formatDate(date, "yyyy-MM-dd HH:mm:ss", null) : null;
    }

    public static String formatTime(Date date) {
        return formatDate(date, "HH:mm:ss", null);
    }

    public static String formatTime(Date date, String format) {
        return formatDate(date, format, null);
    }

    public static String formatDateTimeZone(Date date, String format, TimeZone timeZone) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(timeZone);
            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String formatDate(Date date, String format, String defVal) {
        String ret;
        try {
            ret = (new SimpleDateFormat(format)).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            ret = defVal;
        }

        return ret;
    }

    public static Date plusDays(Date date, int days) {
        if (date == null) {
            date = getToday();
        }

        return changeDays(date, days);
    }

    public static Date plusHours(Date date, int hours) {
        if (date == null) {
            date = getToday();
        }

        return changeHours(date, hours);
    }

    public static Date plusMinute(Date date, int minutes) {
        if (date == null) {
            date = getToday();
        }

        return changeMinute(date, minutes);
    }

    public static Date plusMonth(Date date, int months) {
        if (date == null) {
            date = getToday();
        }

        return changeMonth(date, months);
    }

    public static Date plusYear(Date date, int years) {
        if (date == null) {
            date = getToday();
        }

        return changeYear(date, years);
    }

    private static Date changeMinute(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    private static Date changeHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    private static Date changeDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    private static Date changeYear(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    private static Date changeMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    public static Date getToday() {
        return new Date();
    }

    public static long currentTimeMillis() {
        return getToday().getTime();
    }

    public static java.sql.Date getTodaySqlDate() {
        return new java.sql.Date(getToday().getTime());
    }

    public static String getTodayStr(Date date, String format) {
        if (date == null) {
            date = getToday();
        }

        if (null == format || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }

        return formatDate(date, format);
    }

    public static int intervalDay(Date d1, Date d2) {
        if (d1 == null) {
            d1 = getToday();
        }

        long intervalMillSecond = getBeginOfDate(d1).getTime() - getBeginOfDate(d2).getTime();
        return (int)(intervalMillSecond / MILLION_SECONDS_SECOND_PER_DAY);
    }

    public static int intervalMinutes(Date date1, Date date2) {
        long intervalMillSecond = date1.getTime() - date2.getTime();
        return (int)(intervalMillSecond / MILLION_SECONDS_PER_MINUTE
            + (long)(intervalMillSecond % MILLION_SECONDS_PER_MINUTE > 0L ? 1 : 0));
    }

    public static int intervalSeconds(Date date1, Date date2) {
        long intervalMillSecond = date1.getTime() - date2.getTime();
        return (int)(intervalMillSecond / MILLION_SECONDS_PER_SECOND
            + (long)(intervalMillSecond % MILLION_SECONDS_PER_SECOND > 0L ? 1 : 0));
    }

    public static Date getBeginOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getDateStatus() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 12) {
            return "morning";
        } else if (hour >= 12 && hour < 18) {
            return "noon";
        } else {
            return hour >= 18 && hour < 24 ? "evning" : "midnight";
        }
    }

    public static int getAge(Date birthday) {
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        int year = birth.get(Calendar.YEAR);
        int age = now.get(Calendar.YEAR) - year;
        now.set(Calendar.YEAR, year);
        age = now.before(birth) ? age - 1 : age;
        return age;
    }

    public static boolean isSameDate(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTimeInMillis(d1.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(d2.getTime());
            return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
        } else {
            return false;
        }
    }

    public static boolean isContinueDay(Date d1, Date d2) {
        return d1 != null && d2 != null && intervalDay(d1, d2) == 1;

    }

    public static Date truncDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date truncDateHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getCnDecade(Date input) {
        return formatDate(input).replaceAll("01日", "上旬").replaceAll("11日", "中旬").replaceAll("21日", "下旬");
    }

    public static Date getTheDayBefore(Date date) {
        return new Date(date.getTime() - MILLION_SECONDS_SECOND_PER_DAY);
    }

    public static Date[] getTenDayBefore() {
        Date[] ret = new Date[2];
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        int day = c.get(Calendar.DATE);
        if (day < 10) {
            c.set(Calendar.DATE, 1);
            ret[1] = new Date(c.getTime().getTime());
            c.setTime(getTheDayBefore(c.getTime()));
            c.set(Calendar.DATE, 21);
            ret[0] = new Date(c.getTime().getTime());
        } else if (10 < day && day <= 20) {
            c.set(Calendar.DATE, 1);
            ret[0] = new Date(c.getTime().getTime());
            c.set(Calendar.DATE, 11);
            ret[1] = new Date(c.getTime().getTime());
        } else {
            c.set(Calendar.DATE, 11);
            ret[0] = new Date(c.getTime().getTime());
            c.set(Calendar.DATE, 21);
            ret[1] = new Date(c.getTime().getTime());
        }

        return ret;
    }

    public static Date[] getCurrentTenDay(Date input) {
        Date[] ret = new Date[2];
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        int day = c.get(Calendar.DATE);
        if (day < 10) {
            c.set(Calendar.DATE, 1);
            ret[0] = new Date(c.getTime().getTime());
            c.set(Calendar.DATE, 11);
            ret[1] = new Date(c.getTime().getTime());
        } else if (10 < day && day <= 20) {
            c.set(Calendar.DATE, 11);
            ret[0] = new Date(c.getTime().getTime());
            c.set(Calendar.DATE, 21);
            ret[1] = new Date(c.getTime().getTime());
        } else {
            c.set(Calendar.DATE, 21);
            ret[0] = new Date(c.getTime().getTime());
            ret[1] = getNextMonthFirst(c.getTime());
        }

        return ret;
    }

    public static Date getNextMonthFirst(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        return c.getTime();
    }

    public static Date[] getTheMonthBefore(Date date) {
        Date[] ret = new Date[2];
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DATE, 1);
        ret[1] = new Date(c.getTime().getTime());
        c.setTime(getTheDayBefore(c.getTime()));
        c.set(Calendar.DATE, 1);
        ret[0] = new Date(c.getTime().getTime());
        return ret;
    }

    public static Integer getCurrentQuarter() {
        int month = Integer.parseInt(formatDate(new Date(), "MM"));
        int quarter = 0;
        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else if (month >= 10 && month <= 12) {
            quarter = 4;
        }

        return quarter;
    }

    public static Map<String, String> getQuarterToYearMonthDay(Integer year, Integer quarter) {
        if (year != null && year > 0 && quarter != null && quarter > 0) {
            Map<String, String> map = new HashMap<>();
            if (quarter == 1) {
                map.put("startTime", year + "-01-" + getMonthDays(year, 1) + " 00:00:00");
                map.put("endTime", year + "-03-" + getMonthDays(year, 3) + " 23:59:59");
            } else if (quarter == 2) {
                map.put("startTime", year + "-04-" + getMonthDays(year, 4) + " 00:00:00");
                map.put("endTime", year + "-06-" + getMonthDays(year, 6) + " 23:59:59");
            } else if (quarter == 3) {
                map.put("startTime", year + "-07-" + getMonthDays(year, 7) + " 00:00:00");
                map.put("endTime", year + "-09-" + getMonthDays(year, 9) + " 23:59:59");
            } else if (quarter == 4) {
                map.put("startTime", year + "-10-" + getMonthDays(year, 10) + " 00:00:00");
                map.put("endTime", year + "-12-" + getMonthDays(year, 12) + " 23:59:59");
            }

            return map;
        } else {
            return null;
        }
    }

    public static Integer getMonthDays(Integer year, Integer month) {
        if (year != null && year > 0 && month != null && month > 0) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DATE, 1);
            c.add(Calendar.DATE, -1);
            return c.get(Calendar.DATE);
        } else {
            return 0;
        }
    }

    public static String getTimeDiffText(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime()) / MILLION_SECONDS_PER_SECOND;
        long minuteSeconds = SECONDS_PER_MINUTE;
        long hourSeconds = minuteSeconds * MINUTES_PER_HOUR;
        long daySeconds = hourSeconds * HOURS_PER_DAY;
        long weekSeconds = daySeconds * 7L;
        Date min = date1.compareTo(date2) < 0 ? date1 : date2;
        if (diff >= weekSeconds) {
            return formatDate(min);
        } else if (diff >= daySeconds) {
            return diff / daySeconds + "天前";
        } else if (diff >= hourSeconds) {
            return diff / hourSeconds + "小时前";
        } else {
            return diff >= minuteSeconds ? diff / minuteSeconds + "分钟前" : diff + "秒前";
        }
    }

    public static int getWeekDay(Date dt) {
        int[] week = new int[] {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return week[w];
    }

    public static Date getCurrentDate(String datePattern) {
        try {
            return (new SimpleDateFormat(datePattern)).parse(getCurrentDateByString(datePattern));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCurrentDateByString(String datePattern) {
        return (new SimpleDateFormat(datePattern)).format(System.currentTimeMillis());
    }

    public static String getCurrentDateByString(Date date, String datePattern) {
        return (new SimpleDateFormat(datePattern)).format(date);
    }

    public static boolean beforeDate(Date date1, Date date2) {
        return date1.before(date2);
    }

    public static boolean beforeDate(String date1, String date2) {
        return beforeDate(getDateTime(date1), getDateTime(date2));
    }

    public static boolean isInDateScope(String date, String from, String end) {
        return date != null && from != null && end != null && !beforeDate(date, from) && beforeDate(date, end);
    }

    public static boolean isInTimeRange(String time, String startRange, String endRange) {
        String[] s = startRange.split(":");
        int totalStart = Integer.parseInt(s[0]) * 3600 + Integer.parseInt(s[1]) * 60 + Integer.parseInt(s[2]);
        String[] e = endRange.split(":");
        int totalEnd = Integer.parseInt(e[0]) * 3600 + Integer.parseInt(e[1]) * 60 + Integer.parseInt(e[2]);
        String[] t = time.split(":");
        int timeTotal = Integer.parseInt(t[0]) * 3600 + Integer.parseInt(t[1]) * 60 + Integer.parseInt(t[2]);
        return timeTotal >= totalStart && timeTotal <= totalEnd;
    }

    public static Date getCurrentMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getLastMonthDayBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, -30);
        return calendar.getTime();
    }

    public static String getYear() {
        return sdfYear.format(new Date());
    }

    public static String getDay() {
        return sdfDate.format(new Date());
    }

    public static String getTime() {
        return sdfDatetime.format(new Date());
    }

    public static String getDays() {
        return sdfDateNum.format(new Date());
    }

    public static boolean compareDate(String s, String e) {

        return formatDate(s) != null && formatDate(e) != null && formatDate(s).getTime() >= formatDate(e).getTime();

    }

    public static boolean compareDate(Date d1, Date d2) {
        return d1.getTime() >= d2.getTime();
    }

    public static Date formatDate(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isValidDate(String s) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return (int)((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime())
                / MILLION_SECONDS_SECOND_PER_DAY / 365L);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getDaySub(String beginDateStr, String endDateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (endDate.getTime() - beginDate.getTime()) / MILLION_SECONDS_SECOND_PER_DAY;
    }

    public static String getSpecifiedDayBefore(String specifiedDay, String format, String defaultStr) {
        try {
            Calendar c = Calendar.getInstance();
            Date date = (new SimpleDateFormat(format)).parse(specifiedDay);
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day - 1);
            return (new SimpleDateFormat(format)).format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultStr;
        }
    }

    // public static void main(String[] args) {
    //
    // System.out.print(plusDays(new Date(), 10));
    //
    // }
}
