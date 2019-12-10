package com.ems.dingdong.utiles;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by namnh40 on 6/16/2015.
 */
public class Utilities {

    public static String convertStringFormat(String money) {
        String moneyFormat = "";
        if (money == null) {
            return moneyFormat;
        }
        Locale lc_vn = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getInstance(lc_vn);

        try {
            moneyFormat = formatter.format(Double.valueOf(money));
        } catch (NumberFormatException e) {
            Log.printStackTrace(e);
            moneyFormat = "0";
        }
        return moneyFormat;

    }

    public static String getDecode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32
            // chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            MyLogger.log(e);
            return "";
        }
    }





    /* *//**
     * Kiem tra xem app co ton tai tren thiet bi hay ko
     *
     * @param packageName
     *//*
    @SuppressLint("WrongConstant")
    public static boolean isExistApp(Context ctx, String packageName) {
        try {
            PackageManager manager = ctx.getPackageManager();
          ApplicationInfo appInfo = manager.getApplicationInfo(packageName, ApplicationInfo.FLAG_INSTALLED);

            if (appInfo == null)
                return false;

            return true;
        } catch (Exception e) {
            MyLogger.log(e);
            return false;
        }
    }*/

    /**
     * Xu ly khi muon vao URL
     *
     * @param ctx
     */
    public static void gotoUrl(Context ctx, String url) {
        try {
            Intent intent;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } catch (Exception e) {
            MyLogger.log(e);
        }
    }

    public static String readFileContent(Context context, String file) {
        String line;
        String result = "";
        InputStreamReader readFileIsr = null;
        try {
            readFileIsr = new InputStreamReader(context
                    .getAssets().open(file));
            BufferedReader in = new BufferedReader(readFileIsr);
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (IOException e) {
            MyLogger.log(e);
        } catch (Exception e) {
            MyLogger.log(e);
        } finally {
            if (readFileIsr != null) {
                try {
                    readFileIsr.close();
                } catch (IOException e) {
                    MyLogger.log(e);
                }
            }
        }

        return result;
    }


  /*  public static void gotoMarket(Context context, String packageName, String label) {
        String appName = packageName;
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse("market://details?id=" + appName)));
            ApplicationController.getInstance().trackingEvent(R.string.ga_category_app,
                    R.string.ga_action_instal_app, label);
        } catch (android.content.ActivityNotFoundException anfe) {
            MyLogger.log(anfe);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse(Constants.LINK_GOOGLE_PLAY + appName)));

            ApplicationController.getInstance().trackingEvent(R.string.ga_category_app,
                    R.string.ga_action_instal_app, label);
        } catch (Exception e) {
            MyLogger.log(e);
        }

    }*/

    static public String twoDigit(int d) {
        NumberFormat formatter = new DecimalFormat("#00");
        return formatter.format(d);
    }

   /* public static String getIMEI(Context context) {
        try {
            SharedPref pref = new SharedPref(context);
            String tmDevice = pref.getString(Constants.GETIMEI, "");
            if (!"".equals(tmDevice)) {
                return tmDevice;
            }
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            tmDevice = tm.getDeviceId();
            if (tmDevice == null || "000000000000000".equals(tmDevice)) {
                WifiManager wifiMan = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInf = wifiMan.getConnectionInfo();
                String macAddr = wifiInf.getMacAddress();
                //   Log.i("Utilities", "getIMEI MAC:" + macAddr);
                return macAddr;
            }

            // Log.i("Utilities", "getIMEI Imei:" + tmDevice);
            pref.putString(Constants.GETIMEI, tmDevice);
            return tmDevice;
        } catch (Exception e) {
            MyLogger.log(e);
            String androidId = ""
                    + android.provider.Settings.Secure.getString(
                    context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
            return androidId;
        }
    }

    public static String getMac(Context context) {// ANDROID MAC WIFI
        String base ;//="com.vttm.vietteldiscovery";
        SharedPref pref = new SharedPref(context);
        String getMac = pref.getString(Constants.GETMAC, "");
        if ("".equals(getMac)) {
            try {
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                String macAddr;
                if (wifiManager.isWifiEnabled()) {
                    // WIFI ALREADY ENABLED. GRAB THE MAC ADDRESS HERE
                    WifiInfo info = wifiManager.getConnectionInfo();
                    macAddr = info.getMacAddress();

                    // NOW DISABLE IT AGAIN
                    // wifiManager.setWifiEnabled(true);
                } else {
                    // ENABLE THE WIFI FIRST
                    wifiManager.setWifiEnabled(true);

                    // WIFI IS NOW ENABLED. GRAB THE MAC ADDRESS HERE
                    WifiInfo info = wifiManager.getConnectionInfo();
                    macAddr = info.getMacAddress();

                    // NOW DISABLE IT AGAIN
                    wifiManager.setWifiEnabled(false);
                }
                base = macAddr;
            } catch (Exception e) {
                MyLogger.log(e);
                base ="";
            }
            if (TextUtils.isEmpty(base)) {
                base = "com.vttm.vietteldiscovery";
            }
            getMac = base;
            pref.putString(Constants.GETMAC, getMac);
            return getMac;
        } else {
            return getMac;
        }
    }*/


  /*  @SuppressLint("DefaultLocale")
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf
                        .getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port
                                // suffix
                                return delim < 0 ? sAddr : sAddr.substring(0,
                                        delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return "";
    }*/

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static String toMD5(String str) {
        String hashtext = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, resultByte);
            hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32
            // chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return hashtext;
    }

    public static Map<String, String> splitQuery(URL url)
            throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    private static final long ONE_MINUTE = 60000;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;
    private static final long SEVEN_DAY = ONE_DAY * 7;

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            MyLogger.log(e);
            return 0;
        }
    }

    public static String readFileAsset(Context ctx, String filename) {
        String line;
        String result = "";
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(ctx.getAssets().open(filename));
            BufferedReader in = new BufferedReader(isr);
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (IOException e) {
            MyLogger.log(e);
        } catch (Exception e) {
            MyLogger.log(e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    MyLogger.log(e);
                }
            }
        }

        return result;
    }

    public static void hideKeyboard(Activity context) {
        try {
            if (context != null) {
                context.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }

        } catch (Exception e) {
            MyLogger.log(e);
        }
    }

    public static void hideKeyboard(View focusingView, Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (focusingView != null) {
                imm.hideSoftInputFromWindow(focusingView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                imm.hideSoftInputFromWindow(context.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            MyLogger.log(e);
        }
    }

    public static void showKeyboard(View focusingView, Context context) {
        try {
            focusingView.requestFocus();
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(focusingView, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            MyLogger.log(e);
        }
    }

    public static String fixPhoneNumb(String str) {
        String fixPhoneNumbTo84 = fixPhoneNumbTo84(str);
        if (fixPhoneNumbTo84.length() < 3) {
            return "";
        }

        return fixPhoneNumbTo84.substring(2);
    }

    public static String fixPhoneNumbTo0(String str) {
        String fixPhoneNumb = fixPhoneNumb(str);

        return "0" + fixPhoneNumb;
    }

    @SuppressWarnings("squid:ForLoopCounterChangedCheck")
    public static String fixPhoneNumbTo84(String str) {
        if (str == null || "".equals(str) || str.length() < 3)
            return "";

        String x = "0123456789";
        for (int i = 0; i < str.length(); i++) {

            if (x.indexOf("" + str.charAt(i)) < 0) {

                str = str.replace("" + str.charAt(i), "");
                i--;
            }
        }

        if (str.startsWith("084")) {
            str = str.substring(1);
        } else if (str.startsWith("0")) {
            str = "84" + str.substring(1);
        } else if (!str.startsWith("84")) {
            str = "84" + str;
        }

        return str.trim();
    }

    @SuppressWarnings("deprecation")
    public static String getToday() {
        String str;
        Date dte = new Date();
        str = "" + dte.getDay() + "" + (dte.getMonth() + 1) + "" + (dte.getYear() + 1900);
        return str;
    }


    private static String overUrl(String url) {

        int start = url.indexOf("_");
        int fin = url.indexOf(".html");
        if (start == -1) {
            return "";
        }
        if (fin == -1) {
            return "";
        }
        String temp = url.substring(start + 1, fin);

        return temp;
    }


    public static String getStringOfDate(Date date) {
        String dateStr = "";
        try {
            dateStr = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
        } catch (Exception e) {
            MyLogger.log(e);
        }
        return dateStr;
    }


    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg, null);
            return false;
        }

        return true;
    }


    public static String fomarPhoneNumber(String number) {
        String TEMP = "0123456789+";
        int i = 0;
        while (i < number.length()) {
            if (TEMP.contains(number.charAt(i) + "")) {
                i++;
            } else {
                number = number.replaceAll(Pattern.quote(number.charAt(i) + ""), "");
            }
        }
        return number;
    }

    public static String formatPhoneNumberRemove00(String number) {
        if (number.startsWith("00") && number.length() > 2) {
            number = number.substring(2);
        }
        return number;
    }

    public static String fomarPhoneNumberRemove84(String number) {

        /*if (number != null) {
            number = number.replaceAll("84", "");
            return number;
        }
        return "";*/

        if (number != null) {
            if (number.startsWith("84")) {
                number = number.substring(2, number.length());
            }
            return number;
        }
        return "";
    }

    public static String fomarPhoneNumber2(String number) {

        /*if (number != null) {
            number = number.replaceAll("84", "");
            return number;
        }
        return "";*/

        if (number != null) {
            String str = number.replaceAll(Pattern.quote(" "), "");
            str = str.replaceAll(Pattern.quote("-"), "");
            if (str.startsWith("84")) {
                return str.replaceFirst(Pattern.quote("84"), "");
            } else if (str.startsWith("+84")) {
                return str.replaceFirst(Pattern.quote("+84"), "");
            } else if (str.startsWith("0")) {
                return str.replaceFirst(Pattern.quote("0"), "");
            } else {
                return str;
            }
        }
        return "";
    }

    public static String fomarPhoneNumber84(String number) {

        /*if (number != null) {
            number = number.replaceAll("84", "");
            return number;
        }
        return "";*/

        if (!TextUtils.isEmpty(number)) {
            String str = number.replaceAll(Pattern.quote(" "), "");
            str = str.replaceAll(Pattern.quote("-"), "");
            str = str.replaceAll(Pattern.quote(","), "");
            if (str.startsWith("84")) {
                return "84" + str.replaceFirst(Pattern.quote("84"), "");
            } else if (str.startsWith("+84")) {
                return "84" + str.replaceFirst(Pattern.quote("+84"), "");
            } else if (str.startsWith("0")) {
                return "84" + str.replaceFirst(Pattern.quote("0"), "");
            } else {
                return "84" + str;
            }
        }
        return "";
    }

    public static String fomarPhoneNumberRemove0(String number) {

        if (number != null) {
            if (number.startsWith("0")) {
                number = number.substring(1, number.length());
            }
            return number;
        }
        return "";

    }


    public static long getContactIDFromNumber(String contactNumber, Context context) {
        String UriContactNumber = Uri.encode(contactNumber);
        long phoneContactID = new Random().nextInt();
        Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, UriContactNumber),
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
        while (contactLookupCursor.moveToNext()) {
            phoneContactID = contactLookupCursor.getLong(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
        }
        contactLookupCursor.close();

        return phoneContactID;
    }

    /**
     * @param mContext
     * @param url
     */
    public static void openBroswer(Context mContext, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        mContext.startActivity(i);
    }

    /**
     * @param base64
     * @return
     */
    public static Bitmap convertBase64ToBitmap(String base64) {
        byte[] decodedString = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);

        return decodedByte;
    }

    /**
     * Returns the consumer friendly device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    /**
     * get random number
     */
    public static int getRandomNumber(int min, int max) {
        Random r = new Random();
        int result = r.nextInt(max - min) + min;
        return result;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static void showUIShift(FragmentActivity activity) {
        SharedPref sharedPref = new SharedPref(activity);
        ArrayList<ShiftInfo> list = (ArrayList<ShiftInfo>) sharedPref.getListShift();
        ItemBottomSheetPickerUIFragment pickerShift = null;
        ArrayList<Item> items = new ArrayList<>();
        for (ShiftInfo item : list) {
            items.add(new Item(item.getShiftId(), item.getShiftName()));
        }
        pickerShift = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                (item, position) -> Constants.SHIFT = item.getValue(), 0);
        pickerShift.show(activity.getSupportFragmentManager(), pickerShift.getTag());

    }
}

