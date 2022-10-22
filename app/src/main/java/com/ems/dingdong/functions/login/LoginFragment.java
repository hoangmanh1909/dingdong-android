package com.ems.dingdong.functions.login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogVersionCallback;
import com.ems.dingdong.callback.RouteOptionCallBack;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.GetVersionResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ringme.ott.sdk.listener.RingmeChatLoginListener;
import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.listener.RingmeChatLoginListener;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;

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
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;
    private ItemBottomSheetPickerUIFragment pickerShift;

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
        mSharedPref = new SharedPref(getActivity());
        if (BuildConfig.DEBUG) {
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0394610790;0BE19E95374396A6B4C64201E7255638ED304A0FDBEACEA542ED2A2150F4FB45");//dev EMS
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0973222902;A0F0033A62B4FB523F85F25C0469F41F35AABCCE42165823EB9E11D42C91D427");// pro vinatti
//         mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0947278893;73AA9207BC84142291421BC028955CBC6637BF01CE20948531B259787B5C74CE");// dev vinatti
//         product
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0846043045;8BA5730A24D18630D3B442451CBCE4950BE906606BAA9796D49D238C03A2EF9F");// dev UAT
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0914532228;FB3839BC60CE623420FC01C4D9BCAA51A3003FABEA0C49965922F3ABDD4DB00D");// pre UAT
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0935271867;7790BAABB8FFC68592EE7F53045706EC326AC735B52B78339CE1D2D1791013F9");// pre UAT
            // dev vinatti
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0934527127;E2B0467E0DA4BB72DF471E37D2B5BD16703755B46154EED603959632B0FAEC30"); // dev

        }
        checkPermissionCall();
//        loginSipCmc();
    }


    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission1 = getActivity().checkSelfPermission(Manifest.permission.READ_CALL_LOG);
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
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED
                    || hasPermission2 != PackageManager.PERMISSION_GRANTED
                    || hasPermission3 != PackageManager.PERMISSION_GRANTED
                    || hasPermission4 != PackageManager.PERMISSION_GRANTED
                    || hasPermission5 != PackageManager.PERMISSION_GRANTED
                    || hasPermission6 != PackageManager.PERMISSION_GRANTED
                    || hasPermission7 != PackageManager.PERMISSION_GRANTED
                    || hasPermission8 != PackageManager.PERMISSION_GRANTED
                    || hasPermission9 != PackageManager.PERMISSION_GRANTED
                    || hasPermission10 != PackageManager.PERMISSION_GRANTED
                    || hasPermission11 != PackageManager.PERMISSION_GRANTED
                    || hasPermission12 != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
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
                    if ("Y".equals(userInfo.getIsEms())) {
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
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void showVersion(String version, String urlDownload) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Đã có phiên bản mới " + version + " vui lòng cập nhật ứng dụng.");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Cập nhật",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(urlDownload));
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
        SharedPref sharedPref = new SharedPref(getActivity());
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = mSharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        PostOffice postOffice = null;
        RouteInfo routeInfo = null;

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }

        RingmeOttSdk.login("ms45j52u35@vnpost"
                , "6aab7b753496d649b1f26d9ea1de107de4a1d3a1", new RingmeChatLoginListener() {
                    @Override
                    public void onLoginSuccessful() {
                        Log.d("onLoginSuccessful", "THANHCONG");
                    }

                    @Override
                    public void onLoginFailed() {
                        Log.d("onLoginFailed", "THANHCONG");
                    }

                    @Override
                    public void onChatNotConnectedYet() {
                        Log.d("onChatNotConnectedYet", "THANHCONG");
                    }

                    @Override
                    public void onLoginProcessError(@NonNull Exception e) {
                        Log.d("onLoginProcessError", e.getMessage());
                    }
                });


//        Log.d("KIEMTRADANGNHAP", RingmeOttSdk.isLoggedIn() + "");
//        RingmeOttSdk.openChatList(getViewContext());
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
            showProgress();
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
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(list.get(0).getUrlDownload()));
                        startActivity(viewIntent);
                    }
                }).show();
            } else showThanhCong();
        } else {
            showThanhCong();
        }
        //                        if (!responses.get(0).getVersion().equals(versionApp)) {
//                            mView.showVersion(responses.get(0).getVersion(), responses.get(0).getUrlDownload());
//                        } else
//                            mView.showThanhCong();
    }

   /* private void showUIShift() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            items.add(new Item(i + "", "Ca " + i));
        }
        if (pickerShift == null) {
            pickerShift = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            Constants.SHIFT = item.getValue();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            getActivity().finish();
                            getActivity().startActivity(intent);
                        }
                    }, 0);
            pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
        } else {
            pickerShift.setData(items, 0);
            if (!pickerShift.isShow) {
                pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
            }
        }
    }*/

    @Override
    public void showMessage(String message) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
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
}
