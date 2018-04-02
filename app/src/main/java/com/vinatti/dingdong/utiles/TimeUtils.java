package com.vinatti.dingdong.utiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by DUCNA on 5/26/2015.
 */
public class TimeUtils {
    public static final String DATE_FORMAT_1 = "MM";
    public static final String DATE_FORMAT_2 = "yyyy";
    public static final String DATE_FORMAT_3 = "MM/yyyy";
    public static final String DATE_FORMAT_4 = "MM/dd/yyyy HH:mm:ss aa";
    public static final String DATE_FORMAT_5 = "dd/MM/yyyy";
    public static final String DATE_FORMAT_6 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_7 = "d/M/yyyy";
    public static final String DATE_FORMAT_8 = "dd";
    public static final String DATE_FORMAT_9 = "dd/MM/yyyy HH:mm:ss"; //01/05/2015 00:00:00
    public static final String DATE_FORMAT_10 = "HH:mm";
    public static final String DATE_FORMAT_11 = "M - yyyy";
    public static final String DATE_FORMAT_12 = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_FORMAT_13 = "dd-MM-yyyy";
    public static final String DATE_FORMAT_14 = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    public static final String DATE_FORMAT_15 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_16 = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_17 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_18 = "yyyy/MM/dd";
    public static final String DATE_FORMAT_19 = "dd/MM/yyyy - HH:mm:ss";

    public static String convertDateToString(Date date, String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * @param date        the date from.
     * @param fromPattern sample yyyy-mm-dd.
     * @param toPattern   to date format.
     * @return format date string.
     */
    public static String convertDateToDate(final String date,
                                           final String fromPattern, final String toPattern) {
        String result;
        if (date == null) {
            result = "";
        } else {
            final SimpleDateFormat dfFrom = new SimpleDateFormat(fromPattern);
            final SimpleDateFormat dfTo = new SimpleDateFormat(toPattern);
            try {
                result = dfTo.format(dfFrom.parse(date));
            } catch (ParseException e) {
                result = date;
                Log.printStackTrace(e);
            }
        }
        return result;
    }

    /**
     * @param date        the date from.
     * @param fromPattern sample yyyy-mm-dd.
     * @param toPattern   to date format.
     * @param locale      to location
     * @return format date string.
     */
    public static String convertDateToDate(final String date,
                                           final String fromPattern, final String toPattern, Locale locale) {
        String result;
        if (date == null) {
            result = "";
        } else {
            final SimpleDateFormat dfFrom = new SimpleDateFormat(fromPattern);
            final SimpleDateFormat dfTo = new SimpleDateFormat(toPattern, locale);
            try {
                result = dfTo.format(dfFrom.parse(date));
            } catch (ParseException e) {
                Log.printStackTrace(e);
                result = "";
            }
        }
        return result;
    }

    /**
     * @param data
     * @param dateFormat
     * @return
     */
    public static Calendar convertStringToCalendar(String data, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            cal.setTime(sdf.parse(data));
        } catch (ParseException e) {
            MyLogger.log(e);
            cal = null;
        }
        return cal;
    }

    public static Calendar convertStringToCalendarWithTimeZone(String data, String dateFormat) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho Chi Minh");
        Calendar cal = Calendar.getInstance(tz);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setCalendar(cal);
        try {
            cal.setTime(sdf.parse(data));
        } catch (ParseException e) {
            MyLogger.log(e);
            cal = null;
        }
        return cal;
    }

    public static String getSpace(Calendar calendar1, Calendar calendar2) {
        long time = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
        if (time > 31536000000L) {
            return ((int) ((double) time / 31536000000L)) + " năm trước";
        } else if (time > 2592000000L) {
            return ((int) ((double) time / 2592000000L)) + " tháng trước";
        } else if (time > 86400000L) {
            return ((int) ((double) time / 86400000L)) + " ngày trước";
        } else if (time > 3600000L) {
            return ((int) ((double) time / 3600000L)) + " giờ trước";
        } else if (time > 60000L) {
            return ((int) ((double) time / 60000L)) + " phút trước";
        } else return time > 1000 ? (time / 1000) + " giây trước" : "Vừa xong";
    }
}
