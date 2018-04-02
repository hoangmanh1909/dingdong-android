package com.vinatti.dingdong.utiles;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Macbook on 3/6/17.
 */

public class NumberUtils {
  private static final float EPSILON = 0.00000001f;
  private static final String NUMBER_FORMAT = "#,###,###.##";
  private static final String NUMBER_FORMAT_1 = "#.###.###,##";
  private static final String NUMBER_FORMAT_2 = "#,###,###";
  private static final String NUMBER_FORMAT_3 = "#.###.###.##";
  private static volatile DecimalFormat sValueFormat;
  private static DecimalFormat sCommonFormat;

  private NumberUtils() {

  }

  public static boolean isZero(float num) {
    return Math.abs(num - 0f) < EPSILON;
  }

  public static String formatValue(Double value) {
    if (value == null){
      return "0";
    }
    return getValueFormat().format(value);
  }

  public static String formatValue(Long value) {
    if (value == null){
      return "0";
    }
    return getValueFormat().format(value);
  }

  public static String formatValue(Integer value) {
    if (value == null){
      return "0";
    }
    return getValueFormat().format(value);
  }

  public static String formatVinatti(Integer value) {
    return formatVinattiNumber(value);
  }
  public static String formatVinatti(double value) {
    return formatVinattiNumber(value);
  }
  public static String formatVinatti(Long value) {
    return formatVinattiNumber(value);
  }

  private static String formatVinattiNumber(Object value) {
    if (value == null){
      return "0";
    }
    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
    otherSymbols.setDecimalSeparator(',');
    otherSymbols.setGroupingSeparator('.');
    return new DecimalFormat(NUMBER_FORMAT, otherSymbols).format(value);
  }

  public static String formatPriceNumber(int price){
    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
    otherSymbols.setGroupingSeparator('.');
    return new DecimalFormat(NUMBER_FORMAT_2).format(price).replace(",",".");
  }

  public static String formatPriceNumber(long price){
    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
    otherSymbols.setGroupingSeparator('.');
    return new DecimalFormat(NUMBER_FORMAT_2).format(price).replace(",",".");
  }

  private static DecimalFormat getValueFormat() {
    if (sValueFormat == null) {
      sValueFormat = new DecimalFormat(NUMBER_FORMAT, getFormatSymbols());
    }

    return sValueFormat;
  }

  public static String formatPriceDouble(String price){
    try {
      DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
      otherSymbols.setGroupingSeparator('.');
      String end = new DecimalFormat(NUMBER_FORMAT).format(Double.valueOf(price));
//    String[] strings = end.split("[,]");
//    if(strings.length > 1){
//      end = strings[0].replace(",", ".") + "," + strings[1];
//    }
      return end;
    } catch (Exception e){
      MyLogger.log(e);
      return price;
    }

  }

  public static boolean isZero(double num) {
    return Math.abs(num - 0f) < EPSILON;
  }

  public static boolean isEqual(double f1, double f2) {
    return isZero(f1 - f2);
  }

  private static DecimalFormatSymbols getFormatSymbols() {
    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
    otherSymbols.setDecimalSeparator('.');
    otherSymbols.setGroupingSeparator(',');
    return otherSymbols;
  }

  private static final NavigableMap<Double, String> SUFFIXES = new TreeMap<>();
  private static final String NUMBER_SHORTER_FORMAT = "%1s%2$s";
  static {
    SUFFIXES.put(1_000D, "K");
    SUFFIXES.put(1_000_000D, "M");
    SUFFIXES.put(1_000_000_000D, "G");
    SUFFIXES.put(1_000_000_000_000D, "T");
    SUFFIXES.put(1_000_000_000_000_000D, "P");
    SUFFIXES.put(1_000_000_000_000_000_000D, "E");
  }

  /**
   * Format number with suffix K, M, G... if posible
   */
  public static String formatShorter(double value) {
    //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
    if (isEqual(value,Double.MIN_VALUE)) {
      return formatShorter(Double.MIN_VALUE + 1);
    }
    if (value < 0){
      return "-" + formatShorter(-value);
    }
    if (value < 1000){
      return formatValue(value); //deal with easy case
    }

    Map.Entry<Double, String> e = SUFFIXES.floorEntry(value);
    Double divideBy = e.getKey();
    String suffix = e.getValue();

    double truncated = value / (divideBy / 10); //the number part of the output times 10
    boolean hasDecimal = truncated < 100 && !isEqual(truncated / 10d, truncated / 10);
    return hasDecimal ? String.format(NUMBER_SHORTER_FORMAT, (truncated / 10d), suffix) :
        String.format(NUMBER_SHORTER_FORMAT, formatValue(truncated / 10), suffix);
  }

  public static String convertVietNamPhoneNumber(String phoneNumber){
    String mPhoneNumber;
    if (phoneNumber.length() < 6){
      return phoneNumber;
    }

    if (!StringUtils.isNumeric(phoneNumber)){
      return phoneNumber;
    }

    if ("84".equals(phoneNumber.trim().substring(0,2))){
      mPhoneNumber = "0" + phoneNumber.trim().substring(2);
    } else if ("+84".equals(phoneNumber.trim().substring(0,3))){
      mPhoneNumber = "0" + phoneNumber.trim().substring(3);
    } else if (!"0".equals(phoneNumber.trim().substring(0,1))){
      mPhoneNumber = "0" + phoneNumber.trim();
    } else {
      mPhoneNumber = phoneNumber;
    }
    return mPhoneNumber;
  }

  public static synchronized DecimalFormat getCommonFormat() {
    if (sCommonFormat == null) {
      sCommonFormat = new DecimalFormat(NUMBER_FORMAT, getFormatSymbols());
    }

    return sCommonFormat;
  }
}
