package com.ems.dingdong.utiles;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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

  /*  public static boolean isNumber(String num) {
        try {
            Float.parseFloat(num);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }*/

    public static String formatValue(Double value) {
        if (value == null) {
            return "0";
        }
        return getValueFormat().format(value);
    }

    public static String formatValue(Long value) {
        if (value == null) {
            return "0";
        }
        return getValueFormat().format(value);
    }

    public static String formatValue(Integer value) {
        if (value == null) {
            return "0";
        }
        return getValueFormat().format(value);
    }


    public static String formatAmount(long price) {
        NumberFormat nf = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ROOT));
        return nf.format(price);
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
        if (value == null) {
            return "0";
        }
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        return new DecimalFormat(NUMBER_FORMAT, otherSymbols).format(value);
    }

    public static String formatPriceNumber(int price) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setGroupingSeparator('.');
        return new DecimalFormat(NUMBER_FORMAT_2).format(price).replace(",", ".");
    }

    public static String formatPriceNumber(long price) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setGroupingSeparator('.');
        return new DecimalFormat(NUMBER_FORMAT_2).format(price).replace(",", ".");
    }

    private static DecimalFormat getValueFormat() {
        if (sValueFormat == null) {
            sValueFormat = new DecimalFormat(NUMBER_FORMAT, getFormatSymbols());
        }

        return sValueFormat;
    }

    public static String formatDecimal(String str) {
        if (!TextUtils.isEmpty(str)) {
            BigDecimal parsed = new BigDecimal(str);
            // example pattern VND #,###.00
            DecimalFormat formatter = new DecimalFormat(NUMBER_FORMAT_2, new DecimalFormatSymbols(Locale.US));
            formatter.setRoundingMode(RoundingMode.DOWN);
            return formatter.format(parsed);
        }
        return "0";
    }

    public static String formatPriceDouble(String price) {
        try {
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            otherSymbols.setGroupingSeparator('.');
            String end = new DecimalFormat(NUMBER_FORMAT).format(Double.valueOf(price));
//    String[] strings = end.split("[,]");
//    if(strings.length > 1){
//      end = strings[0].replace(",", ".") + "," + strings[1];
//    }
            return end;
        } catch (Exception e) {
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
        if (isEqual(value, Double.MIN_VALUE)) {
            return formatShorter(Double.MIN_VALUE + 1);
        }
        if (value < 0) {
            return "-" + formatShorter(-value);
        }
        if (value < 1000) {
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

    public static String convertVietNamPhoneNumber(String phoneNumber) {
        String mPhoneNumber;
        if (phoneNumber.length() < 6) {
            return phoneNumber;
        }

        if (!StringUtils.isNumeric(phoneNumber)) {
            return phoneNumber;
        }

        if ("84".equals(phoneNumber.trim().substring(0, 2))) {
            mPhoneNumber = "0" + phoneNumber.trim().substring(2);
        } else if ("+84".equals(phoneNumber.trim().substring(0, 3))) {
            mPhoneNumber = "0" + phoneNumber.trim().substring(3);
        } else if (!"0".equals(phoneNumber.trim().substring(0, 1))) {
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

    static String[] operatorCode9y = new String[]{
            "90", // – MobiFone
            "91", // – Vinaphone
            "92", // – Vietnamobile (previously known as HT Mobile)
            "93", // – MobiFone
            "94", // – Vinaphone
            "95", // – S-Fone
            "96", // – previously EVN Telecom, now Viettel Mobile
            "97", // – Viettel Mobile
            "98"  // – Viettel Mobile
    };
    static String[] operatorCode10 = new String[]{
            "70",//MobiFone
            "79",//MobiFone
            "77",//MobiFone
            "76",//MobiFone
            "78",//MobiFone
            "89",//MobiFone
            "32",//Viettel
            "33",//Viettel
            "34",//Viettel
            "35",//Viettel
            "36",//Viettel
            "37",//Viettel
            "38",//Viettel
            "39",//Viettel
            "86",//Viettel
            "83",//Vinaphone
            "84",//Vinaphone
            "85",//Vinaphone
            "81",//Vinaphone
            "82",//Vinaphone
            "88",//Vinaphone
            "87",//Vinaphone
            "56",//Vietnamobile
            "58",//Vietnamobile
            "59",//Gtel
            "99",//Gtel


    };
    static String[] operatorCode9yy = new String[]{
            "992", // – VSAT
            "996", // – Gmobile (traded as Beeline)
            "997", // – Gmobile (traded as Beeline)
            "998", // - Indochina Telecom
            "999"  // - Indochina Telecom
    };
    static String[] operatorCode1yy = new String[]{
            "120", // – MobiFone
            "121", // – MobiFone
            "122", // – MobiFone
            "123", // – Vinaphone
            "124", // – Vinaphone
            "125", // – Vinaphone
            "126", // – MobiFone
            "127", // – Vinaphone
            "128", // – MobiFone
            "129", // – Vinaphone
            "162", // – Viettel Mobile
            "163", // – Viettel Mobile
            "164", // – Viettel Mobile
            "165", // – Viettel Mobile
            "166", // – Viettel Mobile
            "167", // – Viettel Mobile
            "168", // – Viettel Mobile
            "169", // – Viettel Mobile
            "186", // – Vietnamobile
            "188", // – Vietnamobile (previously known as HT Mobile)
            "199"  // – GTel (traded as Beeline)
    };
    static String[] operatorCode1 = new String[]
            {
                    "24",// Hà Nội
                    "28" // HCM
            };
    static String[] operatorCode2 = new String[]{
            "20",   //Lào Cai
            "22",   //Sơn La
            "25",   //Lạng Sơn
            "26",   //Cao Bằng
            "27",   //Tuyên Quang
            "29",   //Yên Bái
            "30",   //Ninh Bình
            "31",   //TP Hải Phòng
            "33",   //Quảng Ninh
            "36",   //Thái Bình
            "37",   //Thanh Hóa
            "38",   //Nghệ An
            "39",   //Hà Tĩnh
            "52",   //Quảng Bình
            "53",   //Quảng Trị
            "54",   //Thừa Thiên - Huế
            "55",   //Quảng Ngãi
            "56",   //Bình Định
            "57",   //Phú Yên
            "58",   //Khánh Hòa
            "59",   //Gia Lai
            "60",   //Kon Tum
            "61",   //Đồng Nai
            "62",   //Bình Thuận
            "63",   //Lâm Đồng
            "64",   //Vũng tàu
            "66",   //Tây Ninh
            "67",   //Đồng tháp
            "68",   //Ninh thuận
            "69",   //Quân đội - công an
            "70",   //Vĩnh Long
            "72",   //Long An
            "73",   //Tiền Giang
            "74",   //Trà Vinh
            "75",   //Bến Tre
            "76",   //An Giang
            "77",   //Kiên Giang
            "79"    //Sóc Trăng
    };
    static String[] operatorCode3 = new String[]{
            "210",  //Phú thọ
            "211",  //Vĩnh Phúc
            "218",  //Hòa Bình
            "219",  //Hà Giang
            "230",  //Điện Biên
            "231",  //Lai Châu
            "240",  //Bắc Giang
            "241",  //Bắc Ninh
            "280",  //Thái Nguyên
            "281",  //Bắc Kan
            "320",  //Hải Dương
            "321",  //Hưng Yên
            "350",  //Nam Định
            "351",  //Hà Nam
            "500",  //Đắc Lắc
            "501",  //Đak Nông
            "510",  //Quảng Nam
            "511",  //Đà nẵng
            "650",  //Bình Dương
            "651",  //Bình Phước
            "710",  //Cần Thơ
            "711",  //Hậu Giang
            "780",  //Cà Mau
            "781"   //Bạc Liêu
    };

    private static boolean isInArray(String item, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumber(String mobileNumber) {
        try {
            Long.parseLong(mobileNumber);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean checkNumber(String mobileNumber) {
        boolean _tryNumber = isNumber(mobileNumber);
        if (_tryNumber && mobileNumber.length() >= 3 && mobileNumber.length() <= 20) {
            return true;
        } else
            return false;
    }

    public static boolean checkMobileNumber(String mobileNumber) {
        boolean _tryNumber = isNumber(mobileNumber);
        if (!_tryNumber) {
            return false;
        } else {
            String _leading = mobileNumber.substring(0, 1);
            if (_leading.equals("0")) {
               /* String _operator1 = mobileNumber.substring(1, 2);
                String _operator2 = mobileNumber.substring(1, 3);
                String _operator3 = mobileNumber.substring(1, 4);
                if (isInArray(_operator1, operatorCode1) || isInArray(_operator2, operatorCode2) || isInArray(_operator3, operatorCode3)) {
                    return true;
                }*/

                if (mobileNumber.length() == 10) {
                    /*if (isInArray(_operator2, operatorCode9y)
                            || isInArray(_operator3, operatorCode9yy)
                            || isInArray(_operator2, operatorCode10)
                            || isInArray(_operator3, operatorCode10)

                    ) {*/
                    return true;
                } else {
                    return false;
                }
             /*else if (mobileNumber.length() == 11) {
                    if (isInArray(_operator3, operatorCode1yy)
                            || isInArray(_operator3, operatorCode10)
                    ) {
                        return true;
                    } else {
                        return false;
                    }
                } */
            } else {
                if (mobileNumber.startsWith("+84")) {
                    if (mobileNumber.length() == 12) {
                        return true;
                    } else return false;
                }
                return false;
            }
        }
    }


    public static boolean checkActiveCode(String activeCode) {
        if (activeCode.length() == 6) {
            boolean _tryNumber = isNumber(activeCode);
            if (!_tryNumber) {
                return false;
            } else {
                if (Long.parseLong(activeCode) >= 100000)
                    return true;
                else
                    return false;
            }
        } else {
            return false;
        }
    }
}
