package com.ems.dingdong.utiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.model.response.StatisticDebitDetailResponse;
import com.ems.dingdong.model.response.StatisticDeliveryDetailResponse;
import com.ems.dingdong.model.response.StatisticDeliveryGeneralResponse;
import com.ems.dingdong.network.VinattiAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by HungNX on 3/24/16.
 */
public class Utils {

    private static volatile VinattiAPI vinattiAPI;

    protected static final String[] NUMBER_DAY = {"Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"};
    private static String a = "ĂẮẶẰÂẤẦẬăắằặâấầậÁÀẠáàạẲẴẳẵẨẪẩẫẢÃảã";
    private static String e = "éèẹÉÈẸÊẾỀỆêếềệẺẼẻẽỂỄểễ";
    private static String i = "íìịÍÌỊỈĨỉĩ";
    private static String o = "ÓÒỌóòọÔỐỒỘôốồộƠỚỜỢơớờợỎÕỏõỔỖổỗỞỖởỡ";
    private static String u = "ÚÙỤúùụƯỨỪỰưứừựỦŨủũỬỮửữ";
    private static String y = "ÝỲỴýỳỵỶỸỷỹ";
    private static String d = "Đđ";

    public static final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);

    public static VinattiAPI getVinattiAPI() {
        if (vinattiAPI == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(60, 60))
                    .build();
            vinattiAPI = retrofit.create(VinattiAPI.class);
        }
        return vinattiAPI;
    }


    public static String SHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String convertNumberToString(String number) {
        numberFormat.setMaximumFractionDigits(0);
        BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal(number);
        } catch (Exception e) {

            return number;
        }
        return numberFormat.format(bigDecimal);
    }

    public static void showKeybord(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity, EditText editText) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

  /*  public static String getTimeHour() {
        return TimeUtils.convertDateToString(new Date(), TimeUtils.DATE_FORMAT_10);
    }*/

    public static String getTimeDate() {
        Calendar calendar = Calendar.getInstance();
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
        int monthofyear = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return NUMBER_DAY[dayofweek - 1] + ", " + dayofmonth + " tháng " + monthofyear + ", " + year;
    }

    public static Bitmap createRoundedRectBitmap(@NonNull Bitmap bitmap,
                                                 float topLeftCorner, float topRightCorner,
                                                 float bottomRightCorner, float bottomLeftCorner) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = Color.WHITE;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        Path path = new Path();
        float[] radii = new float[]{
                topLeftCorner, topLeftCorner,
                topRightCorner, topRightCorner,
                bottomRightCorner, bottomRightCorner,
                bottomLeftCorner, bottomLeftCorner
        };

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    // check internet connection
    public static boolean checkInternetConnection(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertDistance(String distance) {
        int n = Integer.parseInt(distance);
        if (n < 1000) {
            return distance + "m";
        } else {
            n = (int) ((float) n / 100);
            if (n % 10 == 0) return (n / 10) + "km";
            else {
                return (n / 10) + "," + (n % 10) + "km";
            }
        }
    }

    public static void gotoAppId(Context context, String appId) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appId);
        if (launchIntent != null) {
            context.startActivity(launchIntent);//null pointer check in case package name was not found
        } else {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId)));
            } catch (android.content.ActivityNotFoundException anfe) {

                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appId)));
            }
        }
    }

    public static void rateApp(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {

            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


    public static String convertSecondToString(String second) {
        try {
            String str = "";
            int s = Integer.parseInt(second);
            if (s >= 3600) {
                str += s / 3600 + " giờ ";
                s %= 3600;
            }
            if (s >= 60) {
                str += (s / 60) + " phút ";
                s %= 60;
            }
            if (s > 0) {
                str += s + " giây";
            }
            return str;
        } catch (Exception e) {

            return second;
        }
    }


    public static String hmacDigest(String msg) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec(("5D6B464468B7E5D9C919E62EE8D74").getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {

        } catch (InvalidKeyException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        if (digest != null) return digest.toUpperCase();
        return digest;
    }

    public static char convertCharUTF8(char ch) {
        String tmp = "" + ch;
        if (a.contains(tmp)) return 'A';
        else if (e.contains(tmp)) return 'E';
        else if (i.contains(tmp)) return 'I';
        else if (o.contains(tmp)) return 'O';
        else if (u.contains(tmp)) return 'U';
        else if (y.contains(tmp)) return 'Y';
        else if (d.contains(tmp)) return 'D';
        else return Character.toUpperCase(ch);
    }

    public static boolean hasUTF8(char ch) {
        String tmp = "" + ch;
        if (a.contains(tmp) || e.contains(tmp) || i.contains(tmp)
                || o.contains(tmp) || u.contains(tmp) || y.contains(tmp) || d.contains(tmp))
            return true;
        else return false;
    }

    public static String convertStringUTF8(String str) {
        String tmp = "";
        for (int i = 0; i < str.length(); i++) {
            tmp += convertCharUTF8(str.charAt(i));
        }
        return tmp;
    }

    public static Calendar convertString2Calendar(String date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(date));
            return calendar;
        } catch (ParseException e) {

            return null;
        }
    }

    public static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null || calendar2 == null) {
            return false;
        }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    public static void saveStringAsFile(String filePath, String content) {
        FileOutputStream outputStream = null;
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                boolean isCreateSuccess = file.createNewFile();
                if (isCreateSuccess) {
                    try {
                        outputStream = new FileOutputStream(filePath, false);
                        outputStream.write(content.getBytes());
                        outputStream.close();
                    } catch (Exception e) {

                    } finally {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    }
                }
            } else {
                try {
                    outputStream = new FileOutputStream(filePath, false);
                    outputStream.write(content.getBytes());
                    outputStream.close();
                } catch (Exception e) {

                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            }

        } catch (IOException e1) {

        }
    }

    public static String getStringFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();
            }
        } catch (Exception e) {

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {

                }
            }
        }
        return sb.toString();
    }

    public static boolean checkEmailValid(String email) {

        String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        if (email.matches(emailPattern) && email.length() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static OkHttpClient getUnsafeOkHttpClientTimeout(int timeout) {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext ssContext = SSLContext.getInstance("TLS");
            ssContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = ssContext
                    .getSocketFactory();
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG)
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory)
                    //.addNetworkInterceptor(loggingInterceptor)
                    //.hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    .hostnameVerifier(hostnameVerifier);

            if (BuildConfig.DEBUG) {
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logInterceptor);
            }

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkHttpClient getUnsafeOkHttpClient(final String token) {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG)
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory)
                    //.addNetworkInterceptor(loggingInterceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();
                            String base64EncodedCredentials = Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
                            builder.addHeader("Authorization", "Basic " + base64EncodedCredentials);
                            return chain.proceed(builder.build());
                        }

                    })
                    .hostnameVerifier(hostnameVerifier);//org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkHttpClient getUnsafeOkHttpClient(int readTimeOut, int connectTimeOut) {

        try {
            // Create a trust manager that does not validate certificate chains

            //TrustManager
            final TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

            }};

            // Install the all-trusting trust manager
            final SSLContext tls = SSLContext.getInstance("TLS");

            tls.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = tls
                    .getSocketFactory();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
//                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(hostnameVerifier);//org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER

            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    String credentials = "lottnet:dms";
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Authorization", "Basic " + base64EncodedCredentials)
                            .addHeader("APIKey", BuildConfig.PRIVATE_KEY); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkHttpClient getUnsafeOkHttpClientSSL(int readTimeOut, int connectTimeOut, String token) {

        try {
            // Create a trust manager that does not validate certificate chains

            //TrustManager
            @SuppressLint("CustomX509TrustManager") final TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

            }};

            // Install the all-trusting trust manager
            final SSLContext tls = SSLContext.getInstance("SSL");

            tls.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = tls
                    .getSocketFactory();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
//                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(hostnameVerifier);//org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER

            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    String credentials = "lottnet:dms";
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .addHeader("APIKey", BuildConfig.PRIVATE_KEY); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkHttpClient getUnsafeOkHttpClient(int readTimeOut, int connectTimeOut, String userName) {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

            }};

            // Install the all-trusting trust manager
            final SSLContext tls = SSLContext.getInstance("TLS");
            tls.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = tls
                    .getSocketFactory();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
//                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(hostnameVerifier);//org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                   /* String credentials = "lottnet:dms";
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);*/
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Authorization", "Basic " + userName);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            builder.retryOnConnectionFailure(true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSDCardMounted() {
        boolean isMounted = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            isMounted = true;
        } else if (Environment.MEDIA_BAD_REMOVAL.equals(state)
                || Environment.MEDIA_CHECKING.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)
                || Environment.MEDIA_NOFS.equals(state)
                || Environment.MEDIA_REMOVED.equals(state)
                || Environment.MEDIA_UNMOUNTABLE.equals(state)
                || Environment.MEDIA_UNMOUNTED.equals(state)) {
            isMounted = false;
        }
        return isMounted;
    }

    public static boolean isDirectoryExists(final String filePath) {
        boolean isDirectoryExists;
        File mFilePath = new File(filePath);
        if (mFilePath.exists()) {
            isDirectoryExists = true;
        } else {
            isDirectoryExists = mFilePath.mkdirs();
        }
        return isDirectoryExists;
    }

    public static boolean deleteFile(final String filePath) {
        boolean isFileExists = false;
        File mFilePath = new File(filePath);
        if (mFilePath.exists()) {
            mFilePath.delete();
            isFileExists = true;
        }
        return isFileExists;
    }

    public static String getDataPath() {
        String returnedPath = "";
        final String mDirName = "tesseract";
        final String mDataDirName = "tessdata";
        if (isSDCardMounted()) {
            final String mSDCardPath = Environment.getExternalStorageDirectory() + File.separator + mDirName;
            if (isDirectoryExists(mSDCardPath)) {
                final String mSDCardDataPath = Environment.getExternalStorageDirectory() + File.separator + mDirName +
                        File.separator + mDataDirName;
                isDirectoryExists(mSDCardDataPath);
                return mSDCardPath;
            }
        }
        return returnedPath;
    }

    public static int getResourceDrawableId(Context context, String pVariableName) {
        try {
            return context.getResources().getIdentifier(pVariableName, "drawable", context.getPackageName());
        } catch (Exception e) {
            return -1;
        }
    }

    public static int getRandomServiceBanner(Context context) {
        String prefixName = "banner_dv_default_";
        Random random = new Random();
        int number = random.nextInt(8);
        return getResourceDrawableId(context, prefixName + number);
    }

    public static Bitmap loadBitmapFromView(View parent, View v) {
        Bitmap b = Bitmap.createBitmap(parent.getWidth(), parent.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        parent.layout(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom());
        v.draw(c);
        b = Bitmap.createBitmap(b, v.getLeft(), v.getTop(), v.getWidth(), v.getHeight());
        return b;
    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {

        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent newYoutubeIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.google.android.youtube", 0);
            if (applicationInfo.enabled) {
                Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                videoIntent.setData(uri);
//                videoIntent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
                return videoIntent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {

        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static boolean insertContact(Context cntx, String strDisplayName, String strNumber, String type) {
        boolean result = true;
//        Context contetx 	= cntx; //Application's context or Activity's context
//        String strDisplayName 	=  displayName; // Name of the Person to add
//        String strNumber 	=  number; //number of the person to add with the Contact

        ArrayList<ContentProviderOperation> cntProOper = new ArrayList<ContentProviderOperation>();
        int contactIndex = cntProOper.size();//ContactSize
        int mType = Phone.TYPE_MOBILE;
        if ("home" .equals(type)) {
            mType = Phone.TYPE_HOME;
        } else if ("work" .equals(type)) {
            mType = Phone.TYPE_WORK;
        }
        //Newly Inserted contact
        // A raw contact will be inserted ContactsContract.RawContacts table in contacts database.
        cntProOper.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)//Step1
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null).build());

        //Display name will be inserted in ContactsContract.Data table
        cntProOper.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)//Step2
                .withValueBackReference(Data.RAW_CONTACT_ID, contactIndex)
                .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.DISPLAY_NAME, strDisplayName) // Name of the contact
                .build());
        //Mobile number will be inserted in ContactsContract.Data table
        cntProOper.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)//Step 3
                .withValueBackReference(Data.RAW_CONTACT_ID, contactIndex)
                .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, strNumber) // Number to be added
                .withValue(Phone.TYPE, mType).build()); //Type like HOME, MOBILE etc
        try {
            cntx.getContentResolver().applyBatch(ContactsContract.AUTHORITY, cntProOper); //apply above data insertion into contacts list
        } catch (RemoteException exp) {

            result = false;
        } catch (OperationApplicationException exp) {

            result = false;
        }
        return result;
    }

    public static void updateContact(Activity activity, int contactId, String newNumber, int typePhone) {

        String where = Phone._ID + " = ? AND " +
                Data.MIMETYPE + " = ? AND " +
                String.valueOf(Phone.TYPE) + " = ? ";

        String[] params = new String[]{String.valueOf(contactId),
                Phone.CONTENT_ITEM_TYPE,
                String.valueOf(typePhone)};

        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();


        ops.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                .withSelection(where, params)
                .withValue(Phone.NUMBER, newNumber)
                // .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "Sample Name 21")

                .build());
        try {
            activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {

        }
    }

    public static ArrayList<StatisticDeliveryGeneralResponse> getGeneralList(ArrayList<StatisticDeliveryGeneralResponse> statisticList) {
        String service = "Tổng";
        long quantity = 0;
        long moneyCod = 0;
        long moneyC = 0;
        long moneyPPA = 0;
        int tienCuoc = 0;

        for (StatisticDeliveryGeneralResponse item : statisticList) {
            quantity += Long.parseLong(item.getQuantity());
            moneyCod += Long.parseLong(item.getQuantityCOD());
            moneyC += Long.parseLong(item.getQuantityC());
            moneyPPA += Long.parseLong(item.getQuantityPPA());

            if (item.getReceiveCollectFee() != null) {
                tienCuoc += Integer.parseInt(item.getReceiveCollectFee());
            }
            if (item.getFeeCollectLater() > 0) {
                tienCuoc += item.getFeeCollectLater();
            }
            if (item.getFeePA() > 0) {
                tienCuoc += item.getFeePA();
            }
            if (item.getFeePPA() > 0) {
                tienCuoc += item.getFeePPA();
            }
            if (item.getFeeShip() > 0) {
                tienCuoc += item.getFeeShip();
            }
        }
        StatisticDeliveryGeneralResponse total = new StatisticDeliveryGeneralResponse();
        total.setServiceName(service);
        total.setQuantity(String.valueOf(quantity));
        total.setQuantityC(String.valueOf(moneyC));
        total.setQuantityCOD(String.valueOf(moneyCod));
        total.setQuantityPPA(String.valueOf(moneyPPA));
        total.setTongtien(tienCuoc);
        statisticList.add(total);
        return statisticList;
    }

    public static ArrayList<StatisticDeliveryDetailResponse> getGeneralDeliveryDetailList(ArrayList<StatisticDeliveryDetailResponse> statisticList) {
        int tienCuoc = 0;

        for (StatisticDeliveryDetailResponse item : statisticList) {
            if (item.getFeePPA() > 0) {
                tienCuoc += item.getFeePPA();
            }
            if (item.getFeeCollectLater() > 0) {
                tienCuoc += item.getFeeCollectLater();
            }
            if (item.getFeePA() > 0) {
                tienCuoc += item.getFeePA();
            }
            if (item.getFeeShip() > 0) {
                tienCuoc += item.getFeeShip();
            }
            if (item.getReceiveCollectFee() != null) {
                tienCuoc += Integer.parseInt(item.getReceiveCollectFee());
            }
            if (item.getAmount() != null) {
                tienCuoc += Integer.parseInt(item.getAmount());
            }
        }
        StatisticDeliveryDetailResponse totalStatistic = new StatisticDeliveryDetailResponse();
        totalStatistic.setTongtien(tienCuoc);
        statisticList.add(totalStatistic);
        return statisticList;
    }

    public static ArrayList<StatisticDebitDetailResponse> getGeneralDebitDetailList(ArrayList<StatisticDebitDetailResponse> statisticList) {
        long totalAmount = 0;
        for (StatisticDebitDetailResponse item : statisticList) {
            totalAmount += Long.parseLong(item.getAmount());
        }
        StatisticDebitDetailResponse totalStatistic = new StatisticDebitDetailResponse();
        totalStatistic.setAmount(String.valueOf(totalAmount));
        statisticList.add(totalStatistic);
        return statisticList;
    }

    public static boolean isIncomingCallRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        return componentInfo.getClassName().equals("com.ems.dingdong.calls.IncomingCallActivity");
    }

    public static String getLocalTime(String format) {
        try {
            long time = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            return sdf.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().equals("");
    }

   /* public static String convertToNewMaVung(String phoneNumber,String oldCode,String newCode){
        String newPhone = Utilities.fomarPhoneNumber(phoneNumber);
        if(phoneNumber.startsWith("0" + oldCode)) {
            newPhone = newPhone.replaceFirst(Pattern.quote("0"+oldCode),Pattern.quote("0"+newCode));
        }
        if(phoneNumber.startsWith("84" + oldCode)) {
            newPhone = newPhone.replaceFirst(Pattern.quote("84"+oldCode),Pattern.quote("84"+newCode));
        }
        if(phoneNumber.startsWith("+84" + oldCode)) {
            newPhone = newPhone.replaceFirst(Pattern.quote("+84"+oldCode),Pattern.quote("+84"+newCode));
        }
        if(phoneNumber.startsWith("0084" + oldCode)) {
            newPhone = newPhone.replaceFirst(Pattern.quote("0084"+oldCode),Pattern.quote("0084"+newCode));
        }
        return newPhone;
    }
*/

}
