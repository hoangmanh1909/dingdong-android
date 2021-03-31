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
            Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE};//, Manifest.permission.PROCESS_OUTGOING_CALLS
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
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0389302289;E0F56BB3AF3A5918FD3C289EAD94AD83BC67483B5D04F6CAB1CFBEE3C15F19B8");//dev EMS
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0969803622;46B7C8DAA00B6BE227A293FE95A298ABC0422615AB6F8D4A8FE3B21615F2134D");// dev vinatti
        // mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0936236826;8640DD007AB020F4F4C53C69FB64D3D8D907203F8D923EFDAC8D56F101FE38FB");// dev vinatti
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0969803622;BCB33E1DE2C4D12BFD69514F89271DC3745831AA9D5830680934A0CDD96B3D4F");// dev UAT
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0948035226;7C8FD391550599BF0BEFC98F0AD8D50642A624411355DB1ED5B211676C32B89D");// dev UAT
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0969803622;85F82893905E3CD675BBE371E737A45C8BB2E4A5F6CC09A88889A00B2B56581B");// dev UAT
//        mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0963170164;1303D417B8C5D73A58202C14AC278D1533562A96D812119DFC0976B05D6D9638");// dev vinatti

        checkPermissionCall();

        //mPresenter.getVersion();

        //loginSipCmc();

    }

/*    private void loginSipCmc() {
        SharedPref sharedPref = new SharedPref(getContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);

        SipCmc.startService(getContext());
        SipCmc.addCallback(new RegistrationCallback() {
            @Override
            public void registrationOk() {
                super.registrationOk();
                //showSuccessToast("login success");
                Log.d("123123", "login Ctel success");
            }

            @Override
            public void registrationFailed() {
                super.registrationFailed();
                //showErrorToast("login Ctel failure");
                Log.d("123123", "login Ctel failed");
            }
        }, null);

        //nên viết callback trước khi gọi
        SipCmc.loginAccount("28496");
    }*/


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
                        Constants.HEADER_NUMBER = "tel:18002009,";
                    }
                    gotoHome();
                }
            }

        } else {
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
        builder1.setMessage("Đã có phiên bản mới, vui lòng cập nhật ứng dụng trước khi sử dụng.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Cập nhật ứng dụng",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(urlDownload));
                        startActivity(viewIntent);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
            }
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
