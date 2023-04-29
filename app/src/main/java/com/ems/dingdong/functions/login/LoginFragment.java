package com.ems.dingdong.functions.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.CallLogUtils;
import com.ems.dingdong.callback.DialogVersionCallback;
import com.ems.dingdong.callback.RouteOptionCallBack;
import com.ems.dingdong.callback.RouteOptionCallBackV1;
import com.ems.dingdong.dialog.DialogLogin;
import com.ems.dingdong.dialog.DialogText;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiFragment;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.ems.dingdong.functions.mainhome.main.MainFragment;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.ModeCA;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.GetVersionResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.api.ApiService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.CustomToast;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ringme.ott.sdk.listener.RingmeChatLoginListener;
import com.ringme.ott.sdk.listener.UpdateUserInfoListener;
import com.ringme.ott.sdk.RingmeOttSdk;
//import com.ringme.ott.sdk.listener.RingmeChatLoginListener;
//import com.ringme.ott.sdk.listener.UpdateUserInfoListener;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.listener.RingmeChatLoginListener;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;

import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.lock.qual.LockHeld;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Login Fragment
 */
public class LoginFragment extends ViewFragment<LoginContract.Presenter> implements LoginContract.View {

    //    @BindView(R.id.tv_version)
//    TextView tvVersion;
//    @BindView(R.id.tv_phone)
//    CustomMediumTextView tvPhone;
//    @BindView(R.id.tv_status)
//    CustomTextView tvStatus;
//    private SharedPref mSharedPref;
    @BindView(R.id.ll_login)
    View llLogin;
    @BindView(R.id.ll_xacthuc)
    View llXacthuc;
    @BindView(R.id.ll_sms)
    View llSms;
    @BindView(R.id.edt_sdt)
    EditText edtSdt;
    @BindView(R.id.edt_sms)
    EditText edtSms;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    String mPhone = "";
    boolean isVersion = true;
    private SharedPref mSharedPref;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CONTACTS, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;

    UserInfo userInfo1 = null;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getActivity().getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_v1;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        try {
            mSharedPref = new SharedPref(getActivity());
            isVersion = true;
            llLogin.setVisibility(View.VISIBLE);
            llXacthuc.setVisibility(View.GONE);
            llSms.setVisibility(View.GONE);
            String values = mSharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
            if (TextUtils.isEmpty(values)) {
//                showXacThuc();
                tvTitle.setText("Xác thực ứng dụng lần đầu sử dụng");
            } else {
                mPhone = values.split(";")[0];
                tvLogin.setText(mPhone);
                tvTitle.setText("Truy cập để tiếp tục sử dụng");
                showLogin();
            }
            tvVersion.setText("Phiên bản: " + BuildConfig.VERSION_NAME);
//        CheckCall.checkPermissionCall(getViewContext());

            if (BuildConfig.DEBUG) {
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0941178861;7B5D73A88B118F0362969B3F58419A23C09F40238240F8B2941F9C2974D218F0");//dev EMS
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0973222902;A0F0033A62B4FB523F85F25C0469F41F35AABCCE42165823EB9E11D42C91D427");// pro vinatti
//         mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0947278893;73AA9207BC84142291421BC028955CBC6637BF01CE20948531B259787B5C74CE");// dev vinatti
//         product
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0846043045;8BA5730A24D18630D3B442451CBCE4950BE906606BAA9796D49D238C03A2EF9F");// dev UAT
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0386429889;DA64BB2CC38718125DF731ED355695D88207E78544332F9F9E4B4153A0FED175");// pre UAT
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0935271867;7790BAABB8FFC68592EE7F53045706EC326AC735B52B78339CE1D2D1791013F9");// pre UAT
                // dev vinatti
//                mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0919440179;86C1162DC057618E721048A6711DB73EE9FDADE0470851262DA591E3D9EA90AA"); // dev
//
            }
//        loginSipCmc();

//        String a = "05/10/2022 17:11:19"; // thowif gian dong bo
//        String b = "03/02/2023 10:10:11"; // thoi gian goi
////        String c = "01/02/2022 10:10:11"; // thoi gian goi
////        if (b.compareTo(c) >= 0 && b.compareTo(a) <= 0) {
////            Toast.showToast(getViewContext(), "if dung");
////        } else Toast.showToast(getViewContext(), "if sai");
//
//        try {
//            @SuppressLint("SimpleDateFormat") Date date1 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse("05/10/2022 17:11:19");
//            @SuppressLint("SimpleDateFormat") Date date2 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse("03/02/2023 10:10:11");
//            System.out.println(date1.compareTo(date2) + "metmitoi112" + a.compareTo(b));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
            checkPermissionCall();
        } catch (Exception e) {
            Toast.showToast(getViewContext(), e.getMessage());
        }
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission21 = getActivity().checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
            int hasPermission2 = getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int hasPermission3 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasPermission4 = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            int hasPermission5 = getActivity().checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            int hasPermission6 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE);
            int hasPermission7 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
            int hasPermission8 = getActivity().checkSelfPermission(Manifest.permission.INTERNET);
            int hasPermission9 = getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            int hasPermission10 = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasPermission11 = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            int hasPermission12 = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS);
            int hasReadExternalPermission1 = getActivity().checkSelfPermission(Manifest.permission.WRITE_CALL_LOG);
            if (
//                    hasPermission1 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission21 != PackageManager.PERMISSION_GRANTED || hasPermission2 != PackageManager.PERMISSION_GRANTED || hasPermission3 != PackageManager.PERMISSION_GRANTED || hasPermission4 != PackageManager.PERMISSION_GRANTED || hasPermission5 != PackageManager.PERMISSION_GRANTED || hasPermission6 != PackageManager.PERMISSION_GRANTED || hasPermission7 != PackageManager.PERMISSION_GRANTED || hasPermission8 != PackageManager.PERMISSION_GRANTED || hasPermission9 != PackageManager.PERMISSION_GRANTED || hasPermission10 != PackageManager.PERMISSION_GRANTED || hasPermission11 != PackageManager.PERMISSION_GRANTED || hasReadExternalPermission1 != PackageManager.PERMISSION_GRANTED || hasPermission12 != PackageManager.PERMISSION_GRANTED || hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                loadData();
            } else {
                Toast.showToast(getViewContext(), "Bạn đã từ chối quyền " + grantResults[0]);
            }
        }
    }

    @OnClick({R.id.btn_login, R.id.btn_tieptheo, R.id.btn_sms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.ddGetVersion();
                llSms.setVisibility(View.GONE);
                break;
            case R.id.btn_tieptheo:
                mPhone = edtSdt.getText().toString();
                if (TextUtils.isEmpty(mPhone)) {
                    showErrorToast("Vui lòng nhập số điện thoại.");
                    return;
                }
                if (!NumberUtils.checkMobileNumber(mPhone)) {
                    showErrorToast("Số điện thoại không hợp lệ.");
                    return;
                }
                mPresenter.ddXacThuc(mPhone);
                break;
            case R.id.btn_sms:
                String code = edtSms.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    showErrorToast("Xin vui lòng nhập mã kích hoạt.");
                    return;
                }

                if (!NumberUtils.checkActiveCode(code)) {
                    showErrorToast("Mã kích hoạt của bạn là số có 6 chữ số.");
                    return;
                }
                String codeDeviceActive = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                SharedPref sharedPref = new SharedPref(getActivity());
                String token = sharedPref.getString(Constants.KEY_PUSH_NOTIFICATION, "");
                if (TextUtils.isEmpty(token)) {
                    token = FirebaseInstanceId.getInstance().getToken();
                    if (TextUtils.isEmpty(token)) {
                        token = codeDeviceActive;
                        Logger.i("==== token: " + codeDeviceActive);
                    }
                    Logger.i("token: " + token);
                }
                mPresenter.ddLoginSms(mPhone, code, token);
                break;
        }
    }

    @Override
    public void showThanhCong() {
        try {
            SharedPref sharedPref = new SharedPref(getActivity());
            String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
            String routeInfoJson = mSharedPref.getString(Constants.KEY_ROUTE_INFO, "");
            PostOffice postOffice = null;
            RouteInfo routeInfo = null;
            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
            if (!userJson.isEmpty()) {
                userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            }
            if (!postOfficeJson.isEmpty()) {
                postOffice = ApiService.getGson().fromJson(postOfficeJson, PostOffice.class);
            }

            if (!routeInfoJson.isEmpty()) {
                routeInfo = ApiService.getGson().fromJson(routeInfoJson, RouteInfo.class);
            }
            String user = userInfo1.getChatUserName();
            String pass = userInfo1.getChatPassword();
            if (!user.isEmpty() && !pass.isEmpty()) {
                try {
                    if (!RingmeOttSdk.isLoggedIn())
                        RingmeOttSdk.login(user, pass, new RingmeChatLoginListener() {
                            @Override
                            public void onLoginProcessError(int i, @Nullable Exception e) {
//                        Log.d("onLoginProcessError", e != null ? e.getMessage() : null);
                            }

                            @Override
                            public void onLoginSuccessful() {
                                Log.d("onLoginSuccessful", "THANHCONG");
                            }

                        });
                } catch (Exception e) {
                    Toast.showToast(getViewContext(), "RingMe");
                }

            }
            if (routeInfo != null) {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    getActivity().startActivity(intent);
                }
            } else {
                if (postOffice != null) {
                    List<RouteInfo> routeInfos = postOffice.getRoutes();
                    if (routeInfos.size() > 0) {
                        if (routeInfos.size() == 1) {
                            sharedPref.putString(Constants.KEY_ROUTE_INFO, ApiService.getGson().toJson(routeInfos.get(0)));
                            if (getActivity() != null) {
                                // showUIShift();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                getActivity().finish();
                                getActivity().startActivity(intent);

                            }
                        } else {
                            showDialog(routeInfos);
                        }
                    }
                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    getActivity().startActivity(intent);
                }
            }
        } catch (Exception e) {
            Toast.showToast(getViewContext(), e
                    .getMessage());
        }

    }

    @Override
    public void showThatBai() {

    }

    @Override
    public void showSMS() {
        llLogin.setVisibility(View.GONE);
        llSms.setVisibility(View.VISIBLE);
        llXacthuc.setVisibility(View.GONE);
        edtSdt.setText("");
    }

    @Override
    public void showXacThuc() {
        llLogin.setVisibility(View.GONE);
        llSms.setVisibility(View.GONE);
        llXacthuc.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLogin() {
        edtSms.setText("");
        tvLogin.setText(mPhone);
        llLogin.setVisibility(View.VISIBLE);
        llXacthuc.setVisibility(View.GONE);
        llSms.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mPhone)) {
            tvTitle.setText("Xác thực ứng dụng lần đầu sử dụng");
        } else {
            tvTitle.setText("Truy cập để tiếp tục sử dụng");
        }
    }

    @Override
    public void gotoLogin() {
        String values = mSharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
        if (TextUtils.isEmpty(values)) {
            showXacThuc();
        } else {
            String mobileNumber = values.split(";")[0];
            String signCode = values.split(";")[1];
            if (TextUtils.isEmpty(mobileNumber)) {
                showErrorToast("Không tìm thấy thông tin số điện thoại.");
                return;
            }
            if (!NumberUtils.checkMobileNumber(mobileNumber)) {
                showErrorToast("Số điện thoại không hợp lệ.");
                return;
            }
            mPresenter.ddLogin(mobileNumber, signCode);
        }
    }

    @Override
    public void showVersion(List<GetVersionResponse> list) {
        if (isVersion) {
            int isKtr = 0;
            int version = Integer.parseInt(BuildConfig.VERSION_NAME.replaceAll("\\.", ""));
            String v = BuildConfig.VERSION_NAME;
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    if (Integer.parseInt(list.get(i).getVersion().replaceAll("\\.", "")) == version) {
                        break;
                    }
                }
                if (Integer.parseInt(list.get(i).getVersion().replaceAll("\\.", "")) != version) {
                    isKtr++;
                    v = list.get(i).getVersion();
                    break;
                }
            }
            if (isKtr > 0) {
                new DialogVersion(getViewContext(), v, new DialogVersionCallback() {
                    @Override
                    public void onPhienBanCu() {
                        gotoLogin();
                        isVersion = false;
                    }

                    @Override
                    public void onPhienBanMoi() {
                        hideProgress();
                        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(list.get(0).getUrlDownload()));
                        startActivity(viewIntent);

                    }
                }).show();
            } else gotoLogin();
        } else {
            gotoLogin();
        }
    }

    void showDialog(List<RouteInfo> routeInfos) {
        new RouteDialog(getActivity(), routeInfos, new RouteOptionCallBack() {
            @Override
            public void onRouteOptionResponse(Item routeInfo, RouteInfo itemRouteInfo) {
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(itemRouteInfo));
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    getActivity().startActivity(intent);

                }
            }
        }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
//        llLogin.setVisibility(View.VISIBLE);
//        llXacthuc.setVisibility(View.GONE);
//        llSms.setVisibility(View.GONE);
//        String values = mSharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
//        Log.d("1212", "a: " + values);
//        if (!TextUtils.isEmpty(values)) {
//            String mobileNumber = values.split(";")[0];
//            if (!TextUtils.isEmpty(mobileNumber)) {
//                tvLogin.setText(mobileNumber);
//                tvLogin.setVisibility(View.VISIBLE);
//                tvTitle.setText("Truy cập để tiếp tục sử dụng");
//            } else {
//                tvLogin.setVisibility(View.GONE);
//                tvTitle.setText("Xác thực ứng dụng lần đầu sử dụng");
//            }
//        }
    }


    //    @OnClick(R.id.btn_login)
//    public void onViewClicked() {
//        if (NetworkUtils.isNoNetworkAvailable(getActivity())) {
//            SharedPref sharedPref = new SharedPref(getActivity());
//            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//            if (!userJson.isEmpty()) {
//                UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//                if (userInfo != null) {
//                    if (userInfo.getIsEms() != null && "Y".equals(userInfo.getIsEms())) {
//                        Constants.HEADER_NUMBER = "tel:159";
//                    } else {
//                        Constants.HEADER_NUMBER = "tel:18002009";
//                    }
//                    gotoHome();
//                }
//            }
//        } else {
//            mPresenter.getVersion();
//        }
//
//    }
//
    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            new DialogLogin(getViewContext(), message).show();
        }
    }

//
//    @Override
//    public void gotoHome() {
//        try {
//            SharedPref sharedPref = new SharedPref(getActivity());
//            String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
//            String routeInfoJson = mSharedPref.getString(Constants.KEY_ROUTE_INFO, "");
//            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//            if (!userJson.isEmpty()) {
//                userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//            }
//            PostOffice postOffice = null;
//            RouteInfo routeInfo = null;
//            if (!postOfficeJson.isEmpty()) {
//                postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
//            }
//
//            if (!routeInfoJson.isEmpty()) {
//                routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
//            }
//
//            String user = userInfo1.getChatUserName();
//            String pass = userInfo1.getChatPassword();
//            if (!user.isEmpty() && !pass.isEmpty()) {
//                try {
//                    if (!RingmeOttSdk.isLoggedIn())
//                        RingmeOttSdk.login(user, pass, new RingmeChatLoginListener() {
//                            @Override
//                            public void onLoginProcessError(int i, @Nullable Exception e) {
////                        Log.d("onLoginProcessError", e != null ? e.getMessage() : null);
//                            }
//
//                            @Override
//                            public void onLoginSuccessful() {
//                                Log.d("onLoginSuccessful", "THANHCONG");
//                            }
//
//                        });
//                } catch (Exception e) {
//                    Toast.showToast(getViewContext(), "RingMe");
//                }
//
//            }
//
//
//            if (routeInfo != null) {
//                if (getActivity() != null) {
//                    showProgress();
//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    getActivity().finish();
//                    getActivity().startActivity(intent);
//                    hideProgress();
//                }
//            } else {
//                if (postOffice != null) {
//                    List<RouteInfo> routeInfos = postOffice.getRoutes();
//                    if (routeInfos.size() > 0) {
//                        if (routeInfos.size() == 1) {
//                            sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfos.get(0)));
//                            if (getActivity() != null) {
//                                // showUIShift();
//                                showProgress();
//                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                getActivity().finish();
//                                getActivity().startActivity(intent);
//                                hideProgress();
//
//                            }
//                        } else {
//                            showDialog(routeInfos);
//                        }
//                    }
//                } else {
//                    showProgress();
//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    getActivity().finish();
//                    getActivity().startActivity(intent);
//                    hideProgress();
//                }
//            }
//            isVersion = true;
//        } catch (Exception e) {
//
//        }
//
//
//    }
//
//    @Override
//    public void showThanhCong() {
//        String values = mSharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
//        if (TextUtils.isEmpty(values)) {
//            mPresenter.gotoValidation();
//        } else {
//            String mobileNumber = values.split(";")[0];
//            String signCode = values.split(";")[1];
//            if (TextUtils.isEmpty(mobileNumber)) {
//                showError("Không tìm thấy thông tin số điện thoại.");
//                return;
//            }
//            if (!NumberUtils.checkMobileNumber(mobileNumber)) {
//                showError("Số điện thoại không hợp lệ.");
//                return;
//            }
//            mPresenter.login(mobileNumber, signCode);
//        }
//    }
//
//    @Override
//    public void showVersionV1(List<GetVersionResponse> list) {
//        if (isVersion) {
//            int isKtr = 0;
//            int version = Integer.parseInt(BuildConfig.VERSION_NAME.replaceAll("\\.", ""));
//            String v = BuildConfig.VERSION_NAME;
//            for (int i = 0; i < list.size(); i++) {
//                if (i == 0) {
//                    if (Integer.parseInt(list.get(i).getVersion().replaceAll("\\.", "")) == version) {
//                        break;
//                    }
//                }
//                if (Integer.parseInt(list.get(i).getVersion().replaceAll("\\.", "")) != version) {
//                    isKtr++;
//                    v = list.get(i).getVersion();
//                    break;
//                }
//            }
//            if (isKtr > 0) {
//                new DialogVersion(getViewContext(), v, new DialogVersionCallback() {
//                    @Override
//                    public void onPhienBanCu() {
//                        showThanhCong();
//                        isVersion = false;
//                    }
//
//                    @Override
//                    public void onPhienBanMoi() {
//                        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(list.get(0).getUrlDownload()));
//                        startActivity(viewIntent);
//                    }
//                }).show();
//            } else showThanhCong();
//        } else {
//            showThanhCong();
//        }
//    }
//
//    SharedPref sharedPref;
//
//
//    @Override
//    public void showMessage(String message) {
//        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).
//                setConfirmText("OK").setTitleText("Thông báo").
//                setContentText(message).
//                setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        // Di toi view Validation
//                        mPresenter.gotoValidation();
//                    }
//                }).show();
//    }


}
