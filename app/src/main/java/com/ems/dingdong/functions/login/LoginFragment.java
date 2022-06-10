package com.ems.dingdong.functions.login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.RouteOptionCallBack;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tvVersion.setText(String.format("V.%s (%s)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
        mSharedPref = new SharedPref(getActivity());
        if (BuildConfig.DEBUG) {
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0394610790;0BE19E95374396A6B4C64201E7255638ED304A0FDBEACEA542ED2A2150F4FB45");//dev EMS
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0973222902;A0F0033A62B4FB523F85F25C0469F41F35AABCCE42165823EB9E11D42C91D427");// pro vinatti
//         mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0969803622;46B7C8DAA00B6BE227A293FE95A298ABC0422615AB6F8D4A8FE3B21615F2134D");// dev vinatti
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0919743436;6DF63D7BFF563C2B7B5CFF2F2F20D3C230D60246E78F4628E51CCBC0817B3B26");// dev UAT
            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0918253858;59E5CC326E528BE32E1EB8612C6FFEFD3552DEC7C1A4095949491675D21C17F7");// pre UAT
            // dev vinatti
//            mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0907859321;61BA38010909F754331BE4C4494FFF9EBAF0E17566B1B5F7E574455DD0B4E620"); // dev

        }
        checkPermissionCall();
        //loginSipCmc();
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
