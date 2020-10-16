package com.ems.dingdong.functions.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

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
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE};//, Manifest.permission.PROCESS_OUTGOING_CALLS
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
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0911818968;B03684261C101EC3F323BDDAE6247C3FD92278EF78334365CC6ECA729551E70A");//production
        // mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0888862106;DBC8F3B595A448DAE9279CD6B290982C0BF4DB80F585EA48F3B29F5B210CD285");//production
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0915085986;ECB86B40D283B8749028E035024E2E297905FA59FF09522F0FBE6EFC736DB76B");//dev guest
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0915085986;ECB86B40D283B8749028E035024E2E297905FA59FF09522F0FBE6EFC736DB76B");// dev vinatti
        checkPermissionCall();
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission1 = getActivity().checkSelfPermission(Manifest.permission.READ_CALL_LOG);
            int hasPermission2 = getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int hasPermission3 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasPermission4 = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED
                    || hasPermission2 != PackageManager.PERMISSION_GRANTED
                    || hasPermission3 != PackageManager.PERMISSION_GRANTED
                    || hasPermission4 != PackageManager.PERMISSION_GRANTED
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
       String  token = FirebaseInstanceId.getInstance().getToken();
        if (NetworkUtils.isNoNetworkAvailable(getActivity())) {
            SharedPref sharedPref = new SharedPref(getActivity());
            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
            if (!userJson.isEmpty()) {
                UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                if (userInfo != null) {
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
    public void gotoHome() {
        if (getActivity() != null) {
            // showUIShift();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().finish();
            getActivity().startActivity(intent);

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
}
