package com.ame.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {

    public static final String DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public final static String FormatterDefineOracle = "yyyy-mm-dd hh24:mi:ss";

    public final static String DEFAULT_DATE = "yyyy-MM-dd";

    /**
     * 格式化ZonedDateTime 默认格式yyyy-MM-dd HH:mm:ss 默认时区
     *
     * @param zonedDateTime
     * @return
     */
    public static String format(ZonedDateTime zonedDateTime) {
        return format(zonedDateTime, ZoneId.systemDefault(), DateTimeFormatter.ofPattern(DEFAULT_DATETIME));
    }

    /**
     * 根据指定时区格式化ZonedDateTime 默认格式yyyy-MM-dd HH:mm:ss
     *
     * @param zonedDateTime
     * @param zoneId
     * @return
     */
    public static String format(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        return format(zonedDateTime, zoneId, DateTimeFormatter.ofPattern(DEFAULT_DATETIME));
    }

    /**
     * 根据指定的格式，格式化ZonedDateTime 默认时区
     *
     * @param zonedDateTime
     * @param format
     * @return
     */
    public static String format(ZonedDateTime zonedDateTime, String format) {
        return format(zonedDateTime, ZoneId.systemDefault(), DateTimeFormatter.ofPattern(format));
    }

    /**
     * 根据指定的格式和时区，格式化ZonedDateTime
     *
     * @param zonedDateTime
     * @param zoneId
     * @param format
     * @return
     */
    public static String format(ZonedDateTime zonedDateTime, ZoneId zoneId, String format) {
        return format(zonedDateTime, zoneId, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 根据时区格式化日期Date
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static String format(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME));
    }

    /**
     * 根据时区和格式，格式化日期zonedDateTime
     *
     * @param zonedDateTime
     * @param zoneId
     * @param dateTimeFormatter
     * @return
     */
    public static String format(ZonedDateTime zonedDateTime, ZoneId zoneId, DateTimeFormatter dateTimeFormatter) {
        return Objects.isNull(zonedDateTime) ? "" : zonedDateTime.withZoneSameInstant(zoneId).format(dateTimeFormatter);
    }

    /**
     * LocalDate转Date， 默认时区
     *
     * @param localDate
     * @return
     */
    public static Date getDate(LocalDate localDate) {
        return getDate(localDate, ZoneId.systemDefault());
    }

    /**
     * LocalDate转Date， 指定时区
     *
     * @param localDate
     * @param zoneId
     * @return
     */
    public static Date getDate(LocalDate localDate, ZoneId zoneId) {
        return Objects.isNull(localDate) ? null : Date.from(localDate.atStartOfDay().atZone(zoneId).toInstant());
    }

    /**
     * LocalDateTime转Date， 默认时区
     *
     * @param localDateTime
     * @return
     */
    public static Date getDate(LocalDateTime localDateTime) {
        return getDate(localDateTime, ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转Date， 指定时区
     *
     * @param localDateTime
     * @param zoneId
     * @return
     */
    public static Date getDate(LocalDateTime localDateTime, ZoneId zoneId) {
        return Objects.isNull(localDateTime) ? null : Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * ZonedDateTime转Date
     *
     * @param zonedDateTime
     * @return
     */
    public static Date getDate(ZonedDateTime zonedDateTime) {
        return Objects.isNull(zonedDateTime) ? null : Date.from(zonedDateTime.toInstant());
    }

    /**
     * Date转LocalDate， 默认时区
     *
     * @param date
     * @return
     */
    public static LocalDate getLocalDate(Date date) {
        return getLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * Date转LocalDate， 指定时区
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDate getLocalDate(Date date, ZoneId zoneId) {
        return Objects.isNull(date) ? null : date.toInstant().atZone(zoneId).toLocalDate();
    }

    /**
     * LocalDateTime转LocalDate
     *
     * @param localDateTime
     * @return
     */
    public static LocalDate getLocalDate(LocalDateTime localDateTime) {
        return Objects.isNull(localDateTime) ? null : localDateTime.toLocalDate();
    }

    /**
     * Date转LocalDateTime， 默认时区
     *
     * @param date
     * @return
     */
    public static LocalDateTime getLocalDateTime(Date date) {
        return getLocalDateTime(date, ZoneId.systemDefault());
    }

    /**
     * Date转LocalDateTime， 指定时区
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDateTime getLocalDateTime(Date date, ZoneId zoneId) {
        return Objects.isNull(date) ? null : LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }

    /**
     * LocalDate转LocalDateTime
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getLocalDateTime(LocalDate localDate) {
        return Objects.isNull(localDate) ? null : localDate.atStartOfDay();
    }

    /**
     * Date转ZonedDateTime，默认时区
     *
     * @param date
     * @return
     */
    public static ZonedDateTime getZonedDateTime(Date date) {
        return getZonedDateTime(date, ZoneId.systemDefault());
    }

    /**
     * Date转ZonedDateTime，指定时区
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static ZonedDateTime getZonedDateTime(Date date, ZoneId zoneId) {
        return Objects.isNull(date) ? null : ZonedDateTime.ofInstant(date.toInstant(), zoneId);
    }

    /**
     * LocalDateTime转ZonedDateTime，默认时区
     *
     * @param localDateTime
     * @return
     */
    public static ZonedDateTime getZonedDateTime(LocalDateTime localDateTime) {
        return Objects.isNull(localDateTime) ? null : getZonedDateTime(localDateTime, ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转ZonedDateTime，指定时区
     *
     * @param localDateTime
     * @param zoneId
     * @return
     */
    public static ZonedDateTime getZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return Objects.isNull(localDateTime) ? null : localDateTime.atZone(zoneId);
    }

    /**
     * LocalDate转ZonedDateTime，默认时区
     *
     * @param localDate
     * @return
     */
    public static ZonedDateTime getZonedDateTime(LocalDate localDate) {
        return Objects.isNull(localDate) ? null : getZonedDateTime(localDate, ZoneId.systemDefault());
    }

    /**
     * LocalDate转ZonedDateTime，指定时区
     *
     * @param localDate
     * @param zoneId
     * @return
     */
    public static ZonedDateTime getZonedDateTime(LocalDate localDate, ZoneId zoneId) {
        return Objects.isNull(localDate) ? null : localDate.atStartOfDay(zoneId);
    }

    /**
     * 按指定格式解析时间字符串
     *
     * @param text
     * @param formatter
     * @return
     */
    public static ZonedDateTime parse(CharSequence text, String formatter) {
        return ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(formatter));
    }

    /**
     * 按指定格式解析时间字符串
     *
     * @param text
     * @param formatter
     * @return
     */
    public static LocalDate parseToLocalDate(CharSequence text, String formatter) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(formatter));
    }

    /**
     * 按指定格式，指定时区解析时间字符串
     *
     * @param text
     * @param formatter
     * @param zoneId
     * @return
     */
    public static ZonedDateTime parse(CharSequence text, String formatter, ZoneId zoneId) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter).withZone(zoneId);
        return ZonedDateTime.parse(text, dateTimeFormatter);
    }

}
