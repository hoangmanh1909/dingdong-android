package com.vinatti.dingdong.utiles;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by TiepBM on 12/2/2016.
 */
public class DateTimeUtils {
	
    public static final String TAG = DateTimeUtils.class.getSimpleName();
    public static final String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT4 = "MM/dd/yyyy HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT2 = "dd-MM-yyyy HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT3 = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";
    public static final String SIMPLE_DATE_FORMAT1 = "yyyy-MM-dd";
    public static final String SIMPLE_DATE_FORMAT2 = "yyyy/MM/dd";
    public static final String SIMPLE_DATE_FORMAT_HOUR = "HH:mm:ss";
    public static final String SIMPLE_DATE_FORMAT_HOUR1 = "mm:ss";
    public static final String BIRTHDAY_DATE_NULL = "0000-00-00";
    public static final String SIMPLE_DATE_FORMAT3= "dd-MM-yyyy";
    public static final String SIMPLE_DATE_FORMAT4 = "MM/yyyy";
    public static final String DEFAULT_DATETIME_FORMAT5 = "HH:mm:ss, dd/MM";
    public static final String DEFAULT_DATETIME_FORMAT6 = "dd/MM";
    public static final String DEFAULT_DATETIME_FORMAT7 = "HH:mm:ss";
    public static final String SIMPLE_DATE_FORMAT5 = "yyyyMMdd";
    public static final String SIMPLE_DATE_FORMAT6 =   "HHmmss";

    public static boolean isValidDate(String inDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static Date convertStringToDate(String dateStr, String format) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = formatter.parse(dateStr);
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return date;
    }

    public static String convertStringToDatToString(String dateStr, String format) {
        Date date = new Date();
        String str ;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = formatter.parse(dateStr);
            return convertDateToString(date, format);
        } catch (Exception e) {
            MyLogger.log(e);
            str="";
        }
        return str;
    }

    public static String convertStringToDatToString(String dateStr, String format, String format2) {
        Date date = new Date();
        String str;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = formatter.parse(dateStr);
            return convertDateToString(date, format2);
        } catch (Exception e) {
            MyLogger.log(e);
            str = "";
        }
        return str;
    }

    public static String convertDateToString(Date date, String format) {
        String dateStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            dateStr = sdf.format(date);
        } catch (Exception ex) {
            MyLogger.log(ex);
        }
        return dateStr;
    }

    public static int convertStringToInt(String string, String format){
        long datelong = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(string);
            datelong = date.getTime();
        } catch (Exception e){
            MyLogger.log(e);
        }
        return (int)datelong;
    }

    /**
     * if current day --> Hom nay
     *
     * @return
     */
    public static String convertDateToStringToDay(Date date) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        String dateStr = "";
        try {
            dateStr = df.format(date);
            if (dateStr.equals(formattedDate))
                dateStr = "Hôm nay";
        } catch (Exception ex) {
            MyLogger.log(ex);
        }
        return dateStr;

    }

    public static Date getCurrentDate(String format) {
        Date date = new Date();
        convertLongToDate(System.currentTimeMillis(), format);
        return date;
    }

    public static String convertLongToString(long millisecond, String format) {
        try {
            Date callDayTime = new Date(millisecond);
            String date = new SimpleDateFormat(format).format(callDayTime);
            return date;
        } catch (Exception e){
            MyLogger.log(e);
            return "";
        }

    }

    public static Date convertLongToDate(long millisecond, String format) {
        Date date = new Date();
        String dateString = new SimpleDateFormat(format).format(new Date(millisecond));
        convertStringToDate(dateString, format);
        return date;
    }

    public static String convertLongToData(long now) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
//        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(now * 1000L);
        return formatter.format(calendar.getTime());
    }


    public static String getTimeContestExpireDate(String str){

        String result="";
        Date dateCurent = DateTimeUtils.getCurrentDate(DateTimeUtils.DEFAULT_DATETIME_FORMAT3);
        Date date  = DateTimeUtils.convertStringToDate(str,DateTimeUtils.DEFAULT_DATETIME_FORMAT3);

        //in milliseconds
        long diff = date.getTime() - dateCurent.getTime();

        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if(diffDays>0){
            result = diffDays+" ngày ";
            if(diffHours>0){
                result = result+diffHours+" giờ";
            }
        }else{
            if(diffHours>0){
                result = result+diffHours+" giờ";
            }else{
                result = result+diffMinutes+" phút";
            }
        }
        return result;
    }

    public static String formatDate(String dateStr, String oldFormat, String newFormat) {
        return convertDateToString(convertStringToDate(dateStr, oldFormat), newFormat);
    }

    public static String formatDate(String dateStr) {
        return convertDateToString(convertStringToDate(dateStr, DEFAULT_DATETIME_FORMAT), SIMPLE_DATE_FORMAT);
    }

    public static String formatBirthday(String dateStr) {
        if (BIRTHDAY_DATE_NULL.equals(dateStr))
            return "";
        return dateStr;
        // return convertDateToString(convertStringToDate(dateStr, BIRTHDAY_DATE_FORMAT), SIMPLE_DATE_FORMAT);
    }

    public static boolean compare2Date(String matchDate) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            long timeCurrent = cal.getTimeInMillis();
            cal.setTime(sdf.parse(matchDate));
            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.MINUTE, 90);
            long timeMatchesFavorite = cal.getTimeInMillis();
            if (timeMatchesFavorite < timeCurrent) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            MyLogger.log(e);
            return false;
        }
    }

    public static int compareToDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date1).compareTo(sdf.format(date2));
    }

    public static String convertStringToDateTime(String str, String format1, String format2) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format1, Locale.ENGLISH);
            SimpleDateFormat sdf1 = new SimpleDateFormat(format2);
            cal.setTime(sdf.parse(str));
            str = sdf1.format(cal.getTime());
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return str;
    }

    public static String getTime(String str) {
        try {
            str = str.substring(8, 10) + ":" + str.substring(10, str.length());
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return str;
    }

    public static String getDate(String str) {
        try {
            str = str.substring(6, 8) + "/" + str.substring(4, 6) + "/" + str.substring(0, 4);
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return str;
    }

    public static String getDayMonth(String str) {
        try {
            str = str.substring(6, 8) + "/" + str.substring(4, 6);
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return str;
    }

    public static Timestamp getTimeStampFromString(String dateStr, String format) {
        Date date = convertStringToDate(dateStr, format);
        return new Timestamp(date.getTime());
    }

    public static String getMonthFromStringDate(String date, String dateFormat){
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            d = formatter.parse(date);
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return new SimpleDateFormat("MM").format(d);
    }
    public static int getCurrentMonth(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return Integer.parseInt(format.format(date));
    }

    public static int getCurrentYear(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return Integer.parseInt(format.format(date));
    }

    public static String convertDateStringToDateString(String date, String dateFormat1, String dateFormat2){
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat1);
        try {
            d = formatter.parse(date);
        } catch (Exception e) {
            MyLogger.log(e);
        }
        SimpleDateFormat format = new SimpleDateFormat(dateFormat2);
        return format.format(d);
    }

    public static String getYearFromStringDate(String date, String dateFormat) {
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            d = formatter.parse(date);
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return new SimpleDateFormat("yyyy").format(d);
    }

    public static long getDays(Date end) throws ParseException {
        long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

        long mEnd = end.getTime();
        long current = new Date().getTime(); // 2nd date want to compare
        long diff = (mEnd - current) / (MILLIS_PER_DAY);
        return  (diff == 0) ? 1 : diff;
    }

    public static int getDay(String date) {
        int day = 0;
        try {
            String[] arr = date.split("/");
            if (arr.length > 0) {
                day = Integer.parseInt(arr[0]);
            }
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return day;
    }
}
