package com.ems.dingdong.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.ems.dingdong.functions.mainhome.notify.ListNotifyActivity;
import com.ems.dingdong.functions.mainhome.notify.NotifiActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.model.SaveIDVmapModel;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.vmapmode.CreateUserVmap;
import com.ems.dingdong.model.vmapmode.LoginVmap;
import com.ems.dingdong.model.vmapmode.PushGPSVmap;
import com.ems.dingdong.model.vmapmode.ValuesVmap;
import com.ems.dingdong.model.vmapmode.respone.CreateUserVmapRespone;
import com.ems.dingdong.model.vmapmode.respone.LoginResult;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.network.vmap.NetWorkVmapController;
import com.ems.dingdong.network.vmap.VmapAPI;
import com.ems.dingdong.notification.cuocgoictel.NotiCtelActivity;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.CustomToast;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;

import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.units.qual.C;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class HelloService extends Service {
    private static final String TAG = "HelloService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000 * 60 * 3;
    private static final float LOCATION_DISTANCE = 10f;
    LoginResult loginResult;
    String VMapId = "";


    // login lấy token
    @SuppressLint("CheckResult")
    private void ddCallLogin(int type) {
        String username = "gpstracking@vietmap.vn";
        String password = "Vietmap123";
        LoginVmap vmap = new LoginVmap();
        vmap.setPassword(password);
        vmap.setUsername(username);
        NetWorkVmapController.ddLoginVmap(vmap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(simpleResult -> {
            loginResult = new LoginResult();
            loginResult = simpleResult;
            if (type == 1 && (VMapId == null || VMapId.isEmpty())) ddCreateUserVmap();
        }, throwable -> {
            if (throwable.getMessage().contains("401")) {
            } else new ApiDisposable(throwable, getApplicationContext());
        });
    }

    // đẩy tọa độ sang vmap
    @SuppressLint("CheckResult")
    private void ddPushGPSVmap(String lat, String lon, String id, int speed, String hedding) {
        PushGPSVmap gpsVmap = new PushGPSVmap();
        long milliSeconds = System.currentTimeMillis();
        // để tính giây chúng ta sẽ
        // lấy giá trị biến milliSeconds chia cho 1000L (tương ứng với số long)
        gpsVmap.setTs(milliSeconds / 1000L);
        ValuesVmap vmap = new ValuesVmap();
        vmap.setX(lon);
        vmap.setY(lat);
        vmap.setSpeed(speed);
        vmap.setHeading(0);
        gpsVmap.setValues(vmap);
        if (loginResult != null)
            NetWorkVmapController.ddPushGPSVmap(gpsVmap, id, loginResult.getToken()).
                    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<T>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.e(TAG, "Disposable: ");
                        }

                        @Override
                        public void onSuccess(T t) {
                            Log.e(TAG, "onSuccess: ");
//                            Toast.showToast(getApplicationContext(), "Thanh COng");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e(TAG, "Throwable: ");
//                            Toast.showToast(getApplicationContext(),"Khiem : "+ throwable.getMessage());
                            if (throwable.getMessage().contains("401")) {
                                ddCallLogin(2);
                            }
                        }
                    });

        Log.d("THANHKHIE123123190", new Gson().toJson(gpsVmap) +
                "Thời gian thực thi : " + DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        NetWorkController.emsLoginCountV1(new Gson().toJson(gpsVmap) + "Thời gian thực thi : " + DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT)).delay(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(dataResult -> {
//            Toast.showToast(getApplicationContext(), "Thanh COng");
        }, throwable -> {
        });
    }

    // tạo user vmap
    @SuppressLint("CheckResult")
    private void ddCreateUserVmap() {
        SharedPref sharedPref = new SharedPref(getApplicationContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            final String postmanID = userInfo.getiD();
            CreateUserVmap createUserVmap = new CreateUserVmap();
            createUserVmap.setName(postmanID);
            createUserVmap.setLabel(userInfo.getUserName());
            createUserVmap.setType("type");
            NetWorkVmapController.ddCreateUserVmap(createUserVmap, loginResult.getToken()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(simpleResult -> {
                CreateUserVmapRespone createUserVmapRespone = simpleResult;
                VMapId = simpleResult.getId().getId();
                ddSaveVmapID(VMapId);
            }, throwable -> {
                if (throwable.getMessage().contains("400")) {
//                        new IOSDialog.Builder(getApplicationContext())
//                                .setMessage("401! Kết nối hệ thống Vmap lỗi")
//                                .setNegativeButton("Đóng", null).show();
                } else new ApiDisposable(throwable, getApplicationContext());
            });
        }
    }

    @SuppressLint("CheckResult")
    private void ddSaveVmapID(String id) {
        SharedPref sharedPref = new SharedPref(getApplicationContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            final String postmanID = userInfo.getUserName();
            SaveIDVmapModel createUserVmap = new SaveIDVmapModel();
            createUserVmap.setPostmanCode(postmanID);
            createUserVmap.setVMapId(id);
            NetWorkControllerGateWay.ddSaveIDVmap(createUserVmap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(simpleResult -> {

            }, throwable -> {
                new ApiDisposable(throwable, getApplicationContext());
            });
        }
    }

    boolean isRunning = false;
    Location mLastLocation;

    private class LocationListenerV1 implements LocationListener {
//        Location mLastLocation;
        public LocationListenerV1(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            if (mLastLocation != null)
                mLastLocation.set(location);

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String returnedValue = (String) msg.obj;
            //do something with the value sent by the background thread here ...
            Log.d("getViewContext()", "Tran Khie,");
        }
    };
    HelloService.LocationListenerV1[] mLocationListeners = new HelloService.LocationListenerV1[]
            {new HelloService.LocationListenerV1(LocationManager.GPS_PROVIDER),
                    new HelloService.LocationListenerV1(LocationManager.NETWORK_PROVIDER)};

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        final int countMax = intent.getIntExtra("size", 0);


        long milliSeconds = System.currentTimeMillis();

        // để tính giây chúng ta sẽ
        // lấy giá trị biến milliSeconds chia cho 1000L (tương ứng với số long)
        System.out.println("KKKASDASDASDASD : " + milliSeconds / 1000L);
        Log.d("KKKASDASDASDASD", " onStartCommand");

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id)).setContentTitle("Running Vmap").setSmallIcon(R.mipmap.ic_launcher).setOnlyAlertOnce(true).setOngoing(true).setProgress(countMax, 0, false);

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startMyOwnForeground();
        else startForeground(1, notification.build());


        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        // chay nen
        String NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID;
        String channelName = getString(R.string.notification_channel_id);
        NotificationChannel chan = null;
        chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("App is running in background").setPriority(NotificationManager.IMPORTANCE_MIN).setCategory(Notification.CATEGORY_SERVICE).build();
        startForeground(2, notification);
    }

    private String channelID = "HelloService";
    private static final int SERVICE_NOTIFICATION = 31414;

    private void showServiceNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0/*requestCode*/, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, channelID);
        } else {
            builder = new Notification.Builder(this);
        }
        builder.setSmallIcon(R.drawable.ic_logo_ding_dong).setContentText("Service Running").setContentIntent(contentIntent).build();// getNotification()
        startForeground(SERVICE_NOTIFICATION, builder.build());
//        registerToServer();
    }

    Thread background;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        SharedPref sharedPref = new SharedPref(getApplicationContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            VMapId = userInfo.getVMapId();
        }
        ddCallLogin(1);
//        if (VMapId.isEmpty()) {
//            Log.d("Thanhkhiem12323123", VMapId);
//        }
        try {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
        background = new Thread(new Runnable() {
            public void run() {
                try {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mLastLocation != null) {
                                try {
                                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);
                                } catch (java.lang.SecurityException ex) {
                                    Log.i(TAG, "fail to request location update, ignore", ex);
                                } catch (IllegalArgumentException ex) {
                                    Log.d(TAG, "network provider does not exist, " + ex.getMessage());
                                }
                                try {
                                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[0]);
                                } catch (java.lang.SecurityException ex) {
                                    Log.i(TAG, "fail to request location update, ignore", ex);
                                } catch (IllegalArgumentException ex) {
                                    Log.d(TAG, "gps provider does not exist " + ex.getMessage());
                                }
                                Log.d("LogService", "run: " + new Date().toString());
                                ddPushGPSVmap(mLastLocation.getLatitude() + "", mLastLocation.getLongitude() + "", VMapId, 30, mLastLocation.getProvider());
                            }
//                            if ()
                            mHandler.postDelayed(this, 3000);
                        }
                    }, 1000);
                } catch (Throwable t) {
                    //just end the background thread
                }
            }//run
        });//background
        isRunning = true;
        background.start();
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }


}
