package com.ems.dingdong.utiles;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.network.NetWorkController;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class SharedPref {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean autoCommit = true;

    public static final String FILE_NAME = "butler";

    public static final String CONUT_FRAGMENT_HOME_NEW = "count_fragment_home_new";

    private static final String ENABLE_MULTI_ACC = "enable_multi_acc";

    private static final String NUMBER_OPEN_APP = "number_open_app";

    private static final String HAS_DATA_TET = "has_data_tet";

    public static final String CURRENT_NEED_OTP = "current_need_otp";

    private static volatile SharedPref instance;

    /**
     * @param context ApplicationContext
     * @return
     */
    public static SharedPref getInstance(Context context) {
        if (instance == null)
            instance = new SharedPref(context);
        return instance;
    }

    public SharedPref(Context activity) {
        this.autoCommit = true;
        // pref = PreferenceManager.getDefaultSharedPreferences(mActivityBase);
        pref = activity.getSharedPreferences(Constants.KEY_SHARE_PREFERENCES, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SharedPref(Context activity, String name) {
        this.autoCommit = true;
        // pref = PreferenceManager.getDefaultSharedPreferences(mActivityBase);
        pref = activity
                .getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SharedPref(Context activity, boolean autoCommit) {
        this.autoCommit = autoCommit;
        // pref = PreferenceManager.getDefaultSharedPreferences(mActivityBase);
        pref = activity
                .getSharedPreferences(Constants.KEY_SHARE_PREFERENCES, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }


    // String----------------------------------------------------------------//
    public void putString(String key, String value) {
        editor.putString(key, value);
        if (autoCommit) {
            commit();
        }

    }

    public void clear() {
        try {

            editor.remove("UniqueID");
            editor.remove(Constants.KEY_HOTLINE_NUMBER);
            editor.remove(Constants.KEY_USER_INFO);
            editor.remove(Constants.KEY_MOBILE_NUMBER_SIGN_CODE);
            editor.remove(Constants.KEY_POST_OFFICE);
            editor.remove(Constants.KEY_POST_SHIFT);
            editor.remove(Constants.KEY_ROUTE_INFO);
            editor.remove(Constants.ACCESS_CALL_TOKEN);
            editor.remove(Constants.KEY_PAYMENT_TOKEN);
            editor.remove(Constants.KEY_AUTH_TOKEN);

            editor.commit();
        } catch (Exception ex) {
            Logger.w(ex);
        }
    }

    public void remove(String key) {
        try {

            editor.remove(key);
            editor.commit();
        } catch (Exception ex) {
            Logger.w(ex);
        }
    }

    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    // Int------------------------------------------------------------------//
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public int getInt(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    // Long-----------------------------------------------------------------//
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public long getLong(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    // Float------------------------------------------------------------------//
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public float getFloat(String key, float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }

    // Boolean-------------------------------------------------------------//
    public boolean getBoolean(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        if (autoCommit) {
            commit();
        }
    }

    // ListString----------------------------------------------------------------//
    public int putListString(String key, ArrayList<String> listString) {
        for (int i = 0; i < listString.size(); i++)
            editor.putString("List#String" + i + key, listString.get(i));
        if (autoCommit)
            commit();
        return listString.size();
    }

    public ArrayList<String> getListString(String key, int sizeList,
                                           String defaultValue) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < sizeList; i++)
            result.add(pref.getString("List#String" + i + key, defaultValue));
        return result;
    }

    // ListInt----------------------------------------------------------------//
    public int putListInt(String key, int[] listInt) {
        for (int i = 0; i < listInt.length; i++)
            editor.putInt("List#Int" + i + key, listInt[i]);
        if (autoCommit)
            commit();
        return listInt.length;
    }

    public int[] getListInt(String key, int sizeList, int defaultValue) {
        int[] result = new int[sizeList];
        for (int i = 0; i < sizeList; i++)
            result[i] = pref.getInt("List#Int" + i + key, defaultValue);
        return result;
    }

    // Commnit-------------------------------------------------------------//
    public void commit() {
        editor.commit();
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }
    // ---------------------------------------------------------------------//
// List shift
    public  List<ShiftInfo> getListShift()
    {
        String data = pref.getString(Constants.KEY_POST_SHIFT, "");
        if(!data.isEmpty())
        {
            return NetWorkController.getGson().<ArrayList<ShiftInfo>>fromJson(data, new TypeToken<List<ShiftInfo>>(){}.getType());
        }
        else {
            return new ArrayList<>();
        }

    }

    public  List<RouteInfo> getListRoute()
    {
        String data = pref.getString(Constants.KEY_ROUTE_INFO, "");
        if(!data.isEmpty())
        {
            return NetWorkController.getGson().<ArrayList<RouteInfo>>fromJson(data, new TypeToken<List<RouteInfo>>(){}.getType());
        }
        else {
            return new ArrayList<>();
        }

    }

    public void saveCountFragmentHomeNewCreate(int count) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(CONUT_FRAGMENT_HOME_NEW, count);
        editor.commit();
    }

    public int getCountFragmentHomeNewCreate() {
        return pref.getInt(CONUT_FRAGMENT_HOME_NEW, 0);
    }

    public void saveEnableMultiAcc(int enale) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(ENABLE_MULTI_ACC, enale);
        editor.commit();
    }

    public int getEnableMultiAcc() {
        return pref.getInt(ENABLE_MULTI_ACC, 0);
    }

    public void saveNumberOpenApp(int number) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(NUMBER_OPEN_APP, number);
        editor.commit();
    }

    public int getNumberOpenApp() {
        return pref.getInt(NUMBER_OPEN_APP, 1);
    }

    public void saveHasDatatet(boolean hasDatatet) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(HAS_DATA_TET, hasDatatet);
        editor.commit();
    }

    public boolean getHasDatatet() {
        return pref.getBoolean(HAS_DATA_TET, false);
    }

    public void saveCurrentNeedOtp(int needOtp) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(CURRENT_NEED_OTP, needOtp);
        editor.commit();
    }

    public int getCurrentNeedOtp() {
        return pref.getInt(CURRENT_NEED_OTP, 0);
    }
}

