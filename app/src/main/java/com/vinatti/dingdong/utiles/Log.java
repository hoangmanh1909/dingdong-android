package com.vinatti.dingdong.utiles;


import com.vinatti.dingdong.BuildConfig;

/**
 * Created by namnh40 on 6/15/2015.
 */
public class Log {

    private static final boolean ENABLE_LOG = BuildConfig.DEBUG;
    private static final String LOG_STRING = "hnip.vtdis --- ";
    private static final String LOG_PREFIX = "Vinatti";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static void i(String tag, String string) {
        if (ENABLE_LOG) {
            android.util.Log.i(tag, LOG_STRING + string);
        }
    }

    public static void i(String tag, String string, Throwable throwable) {
        if (ENABLE_LOG) {
            android.util.Log.i(tag, LOG_STRING + string, throwable);
        }
    }

    public static void e(String tag, String string) {
        if (ENABLE_LOG) {
            android.util.Log.e(tag, LOG_STRING + string);
        }
    }

    public static void e(String tag, String string, Throwable throwable) {
        if (ENABLE_LOG) {
            android.util.Log.e(tag, LOG_STRING + string, throwable);
        }
    }

    public static void d(String tag, String string) {
        if (ENABLE_LOG) {
            android.util.Log.d(tag, LOG_STRING + string);
        }
    }

    public static void d(String tag, String string, Throwable throwable) {
        if (ENABLE_LOG) {
            android.util.Log.d(tag, LOG_STRING + string, throwable);
        }
    }

    public static void v(String tag, String string) {
        if (ENABLE_LOG) {
            android.util.Log.v(tag, LOG_STRING + string);
        }
    }

    public static void v(String tag, String string, Throwable throwable) {
        if (ENABLE_LOG) {
            android.util.Log.v(tag, LOG_STRING + string, throwable);
        }
    }

    public static void w(String tag, String string) {
        if (ENABLE_LOG) {
            android.util.Log.w(tag, LOG_STRING + string);
        }
    }

    public static void w(String tag, String string, Throwable throwable) {
        if (ENABLE_LOG) {
            android.util.Log.w(tag, LOG_STRING + string, throwable);
        }
    }

    public static void out(String tag, String message) {
        if (ENABLE_LOG) {
            System.out.print(tag + "==> " + message);
        }
    }
    public static void printStackTrace(Exception e) {
        if (ENABLE_LOG) {
            android.util.Log.e("Exception", e.getMessage());
        }
    }

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }
        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

}
