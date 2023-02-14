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
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.CallLogUtils;
import com.ems.dingdong.callback.DialogVersionCallback;
import com.ems.dingdong.callback.RouteOptionCallBack;
import com.ems.dingdong.dialog.DialogLogin;
import com.ems.dingdong.dialog.DialogText;
import com.ems.dingdong.dialog.RouteDialog;
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
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
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

    @BindView(R.id.tv_version)
    CustomTextView tvVersion;
    @BindView(R.id.tv_phone)
    CustomMediumTextView tvPhone;
    @BindView(R.id.tv_status)
    CustomTextView tvStatus;
    private SharedPref mSharedPref;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CONTACTS, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;
    private ItemBottomSheetPickerUIFragment pickerShift;


    private Calendar mCalendarRaCa;
    private Calendar mCalendarVaoCa;
    private String mDateRaCa;
    private String mDateVaoCa;
    String mDataCA;
    UserInfo userInfo1 = null;
    List<CallLogMode> mList = new ArrayList<>();

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    boolean isVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        isVersion = true;
        tvVersion.setText(String.format("V.%s (%s)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
//        CheckCall.checkPermissionCall(getViewContext());

        mSharedPref = new SharedPref(getActivity());
        if (BuildConfig.DEBUG) {
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0969803622;9F44C481A28BA1C5831AC903F4451FE980205595AFAA5134B41E23B1651306A4");//dev EMS
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0973222902;A0F0033A62B4FB523F85F25C0469F41F35AABCCE42165823EB9E11D42C91D427");// pro vinatti
//         mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0947278893;73AA9207BC84142291421BC028955CBC6637BF01CE20948531B259787B5C74CE");// dev vinatti
//         product
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0846043045;8BA5730A24D18630D3B442451CBCE4950BE906606BAA9796D49D238C03A2EF9F");// dev UAT
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0386429889;DA64BB2CC38718125DF731ED355695D88207E78544332F9F9E4B4153A0FED175");// pre UAT
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0935271867;7790BAABB8FFC68592EE7F53045706EC326AC735B52B78339CE1D2D1791013F9");// pre UAT
            // dev vinatti
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0869635326;249DBDE22786D30E4B9420A12B5347565208A39485F90D9029AF793BCC84BC0C"); // dev

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

//        if (getRuntimePermission())
//            loadData();
    }

    public void loadData() {
        CallLogUtils callLogUtils = CallLogUtils.getInstance(getContext());
        List<CallLogInfo> list = callLogUtils.readCallLogs();
        System.out.println("NHUHAN" + new Gson().toJson(list));
        sharedPref = new SharedPref(getActivity());

        mCalendarRaCa = Calendar.getInstance();
        mDataCA = sharedPref.getString(Constants.KEY_RA_VAOV1, "");
        try {
            if (!mDataCA.isEmpty()) {
                mDateRaCa = DateTimeUtils.convertDateToString(mCalendarRaCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT);
//                ModeCA modeCA = NetWorkController.getGson().fromJson(mDataCA, ModeCA.class);
//                mDateVaoCa = modeCA.getNgaygio();
                List<CallLogInfo> modeList = new ArrayList<>();
//                String ml = sharedPref.getString(Constants.KEY_LIST_PHONE, "");
//                if (!ml.isEmpty()) {
//                CallLogMode[] callLogModeList = NetWorkController.getGson().fromJson(ml, CallLogMode[].class);
//                List<CallLogMode> list = Arrays.asList(callLogModeList);
                for (int i = 0; i < list.size(); i++) {
//                    list.get(i).setPostmanCode(modeCA.getPostmanCode());
//                    list.get(i).setFromNumber(modeCA.getFromNumber());
                    long dateFrom = list.get(i).getDate();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                    String date = formatter.format(dateFrom);
                    @SuppressLint("SimpleDateFormat") Date date1 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(date);
                    @SuppressLint("SimpleDateFormat") Date date2 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(mDataCA);
                    @SuppressLint("SimpleDateFormat") Date date3 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(mDateRaCa);
                    if (date1.compareTo(date2) >= 0 && date1.compareTo(date3) <= 0) {
                        modeList.add(list.get(i));
                    }
                }
//                }
                String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                if (!userJson.isEmpty()) {
                    userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                }
                List<CallLogMode> request = new ArrayList<>();
                if (modeList.size() > 0) {
                    for (CallLogInfo info : modeList) {
                        CallLogMode i = new CallLogMode();
                        long dateFrom = info.getDate();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                        String date = formatter.format(dateFrom);
                        i.setDate(date);
                        i.setCallDate(date);
                        i.setCallDuration(String.valueOf(info.getDuration()));
                        i.setCallType(Integer.parseInt(info.getCallType()));
                        i.setPhNumber(info.getNumber());
                        i.setPostmanCode(userInfo1.getUserName());
                        i.setFromNumber(userInfo1.getMobileNumber());
                        request.add(i);
                    }
                    Log.d("NhuHanTHNHKASDASD", new Gson().toJson(request) + mDataCA);
                    mPresenter.getCallLog(request);
                } else {
                    Toast.showToast(getViewContext(), "Bạn không thực hiện cuộc gọi nào (từ " + mDataCA + " đến " + mDateRaCa + ")");
                }
            } else {
                mCalendarVaoCa = Calendar.getInstance();
//                ModeCA modeCA1 = new ModeCA();
//                modeCA1.setNgaygio(DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
//                modeCA1.setPostmanCode(userInfo1.getUserName());
//                modeCA1.setFromNumber(userInfo1.getMobileNumber());
                sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                Log.d("THNHKASDASDLANDAU", DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
            }
        } catch (Exception e) {
            e.getMessage();
            sharedPref.putBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, true);
            Toast.showToast(getViewContext(), "Lỗi không thể ghi nhận được log cuộc gọi. (" + e.getMessage() + ")");
        }

    }

    private boolean getRuntimePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 123);
            return false;
        }
        return true;
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int hasPermission1 = getActivity().checkSelfPermission(Manifest.permission.READ_CALL_LOG);
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
                loadData();
            } else {
                Toast.showToast(getViewContext(), "Bạn đã từ chối quyền " + grantResults[0]);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
//        try {
//            getLoaderManager().initLoader(URL_LOADER, null, this);
//        } catch (Exception ignored) {
//        }

        String values = mSharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
        Log.d("1212", "a: " + values);
        if (!TextUtils.isEmpty(values)) {
            String mobileNumber = values.split(";")[0];
            if (!TextUtils.isEmpty(mobileNumber)) {
                tvPhone.setText(mobileNumber);
                tvPhone.setVisibility(View.VISIBLE);
                tvStatus.setText("Truy cập để tiếp tục sử dụng");

            } else {
                tvPhone.setVisibility(View.GONE);
                tvStatus.setText("Xác thực ứng dụng lần đầu sử dụng");
            }
        }
    }

    @OnClick(R.id.login_layout)
    public void onViewClicked() {
        if (NetworkUtils.isNoNetworkAvailable(getActivity())) {
            SharedPref sharedPref = new SharedPref(getActivity());
            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
            if (!userJson.isEmpty()) {
                UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                if (userInfo != null) {
                    if (userInfo.getIsEms() != null && "Y".equals(userInfo.getIsEms())) {
                        Constants.HEADER_NUMBER = "tel:159";
                    } else {
                        Constants.HEADER_NUMBER = "tel:18002009";
                    }
                    gotoHome();
                }
            }
        } else {
            mPresenter.getVersion();
        }

    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {

            new DialogLogin(getViewContext(), message).show();
//            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).
//                    setConfirmText("OK").setTitleText("Thông báo").
//                    setContentText(message).
//                    setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    sweetAlertDialog.dismiss();
//                }
//            }).show();
        }
    }

    @Override
    public void showVersion(String version, String urlDownload) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Đã có phiên bản mới " + version + " vui lòng cập nhật ứng dụng.");
        builder1.setCancelable(false);
        builder1.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(urlDownload));
                startActivity(viewIntent);
            }
        });
        builder1.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                showThanhCong();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        return;
    }


    @Override
    public void gotoHome() {
        if (getRuntimePermission()) loadData();
        else {
            Toast.showToast(getViewContext(), "Bạn đã từ chối quyền ghi nhận nhật ký cuộc gọi");
        }
        SharedPref sharedPref = new SharedPref(getActivity());
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = mSharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        PostOffice postOffice = null;
        RouteInfo routeInfo = null;
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }


// sdk chij Ly
//        String user = "7jk75dqp5j@vnpost";
//        String pass = "3d74d58168c121430af6abd10bfd786f2e5023dd";
//sdk chi QUyeen
        String user = userInfo1.getChatUserName();
        String pass = userInfo1.getChatPassword();

        try {
            if (!user.isEmpty() && !pass.isEmpty()) {
                try {
                    RingmeOttSdk.login(user, pass, new RingmeChatLoginListener() {
                        @Override
                        public void onLoginProcessError(int i, @Nullable Exception e) {

                        }

                        @Override
                        public void onLoginSuccessful() {
                            Log.d("onLoginSuccessful", "THANHCONG");
                        }


                    });
                } catch (Exception e) {
                    Toast.showToast(getViewContext(), e.getMessage());
                }

            }
        } catch (Exception e) {
            e.getMessage();
        }


        if (routeInfo != null) {
            if (getActivity() != null) {
                // showUIShift();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                getActivity().startActivity(intent);

            }
        } else {
            if (postOffice != null) {
                List<RouteInfo> routeInfos = postOffice.getRoutes();
                if (routeInfos.size() > 0) {
                    if (routeInfos.size() == 1) {
                        sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfos.get(0)));
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
        isVersion = true;

    }

    @Override
    public void showThanhCong() {
        String values = mSharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
        if (TextUtils.isEmpty(values)) {
            mPresenter.gotoValidation();
        } else {
            String mobileNumber = values.split(";")[0];
            String signCode = values.split(";")[1];
            if (TextUtils.isEmpty(mobileNumber)) {
                showError("Không tìm thấy thông tin số điện thoại.");
                return;
            }
            if (!NumberUtils.checkMobileNumber(mobileNumber)) {
                showError("Số điện thoại không hợp lệ.");
                return;
            }
            mPresenter.login(mobileNumber, signCode);
        }
    }

    @Override
    public void showVersionV1(List<GetVersionResponse> list) {
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
                        showThanhCong();
                        isVersion = false;
                    }

                    @Override
                    public void onPhienBanMoi() {
                        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(list.get(0).getUrlDownload()));
                        startActivity(viewIntent);
                    }
                }).show();
            } else showThanhCong();
        } else {
            showThanhCong();
        }
    }

    SharedPref sharedPref;

    @Override
    public void showCallLog(int size) {
        sharedPref = new SharedPref(getActivity());
        try {
            sharedPref.clearRaVao();
        } catch (Exception e) {
        }
        UserInfo userInfo123 = null;
        String userJson = sharedPref.getString(Constants.KEY_NAME_PHONE, "");
        if (!userJson.isEmpty()) {
            userInfo123 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        ModeCA modeCA = new ModeCA();
        mCalendarVaoCa = Calendar.getInstance();
        modeCA.setNgaygio(DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
//        if (userInfo123 != null) {
//            modeCA.setPostmanCode(userInfo123.getUserName());
//            modeCA.setFromNumber(userInfo123.getMobileNumber());
//
//        }
        sharedPref.putBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, false);
        sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
        Toast.showToast(getViewContext(), "Ghi nhận thành công " + size + " cuộc gọi lên hệ thống");
    }


    @Override
    public void showMessage(String message) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setConfirmText("OK").setTitleText("Thông báo").setContentText(message).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                // Di toi view Validation
                mPresenter.gotoValidation();
            }
        }).show();
    }

    void showDialog(List<RouteInfo> routeInfos) {
        new RouteDialog(getActivity(), routeInfos, new RouteOptionCallBack() {
            @Override
            public void onRouteOptionResponse(Item item, RouteInfo itemRouteInfo) {
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(itemRouteInfo));
                if (getActivity() != null) {
                    // showUIShift();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    getActivity().startActivity(intent);

                }
            }
        }).show();
    }

    private static final int URL_LOADER = 1;

//    @NonNull
//    @Override
//    public Loader<Cursor> onCreateLoader(int loaderID, @Nullable Bundle args) {
//        android.util.Log.d("AAAAAAAA", "onCreateLoader() >> loaderID : " + loaderID);
//        switch (loaderID) {
//            case URL_LOADER:
//                // Returns a new CursorLoader
//                return new CursorLoader(getActivity(),   // Parent activity context
//                        CallLog.Calls.CONTENT_URI,        // Table to query
//                        null,     // Projection to return
//                        null,            // No selection clause
//                        null,            // No selection arguments
//                        null             // Default sort order
//                );
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor managedCursor) {
//        mList = new ArrayList<>();
//        SharedPref sharedPref = new SharedPref(getActivity());
//        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//        if (!userJson.isEmpty()) {
//            userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//        }
//        try {
//            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
//            int numberFrm = managedCursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID);
//            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
//            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
//            int DEFAULT_SORT_ORDER = managedCursor.getColumnIndex(CallLog.Calls.DEFAULT_SORT_ORDER);
//            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
//            while (managedCursor.moveToNext()) {
//                String phNumber = managedCursor.getString(number);
//                String callType = managedCursor.getString(type);
//                String callDate = managedCursor.getString(date);
//                String phNumber1 = managedCursor.getString(numberFrm);
//                Date callDayTime = new Date(Long.valueOf(callDate));
//                String callDuration = "0";
//                callDuration = managedCursor.getString(duration);
//                String dir = null;
//                String datea = DateTimeUtils.convertDateToString(callDayTime, DateTimeUtils.DEFAULT_DATETIME_FORMAT);
//                int callTypeCode = Integer.parseInt(callType);
//                Log.d("MEMITOI123123", callDate + " ");
//                CallLogMode mode = new CallLogMode();
//                mode.setCallDate(datea);
//                mode.setPhNumber(phNumber);
//                mode.setCallDuration(callDuration);
//                mode.setCallType(callTypeCode);
//                mode.setDate(datea);
//                mList.add(mode);
//            }
//            Collections.sort(mList, new LoginFragment.NameComparator());
//            Log.d("MEMITOI123123", new Gson().toJson(mList));
//            sharedPref.putString(Constants.KEY_LIST_PHONE, NetWorkController.getGson().toJson(mList));
//            managedCursor.close();
//        } catch (Exception e) {
//            e.getMessage();
//            android.util.Log.d("AAAAAAAAException", e.getMessage());
//        }
//    }

    class NameComparator implements Comparator<CallLogMode> {
        public int compare(CallLogMode s1, CallLogMode s2) {
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(s1.getDate());
                date2 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(s2.getDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date1.compareTo(date2);
        }
    }

//    @Override
//    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//    }
}
