package com.vinatti.dingdong.functions.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.mainhome.main.MainActivity;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.views.CustomMediumTextView;
import com.vinatti.dingdong.views.CustomTextView;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.PROCESS_OUTGOING_CALLS};
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
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0963170164;F4521A23D3B56BEDED23CF0390E9211C17EF17EA9571A9F646142F848440C410");//dev guest
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0915085986;ECB86B40D283B8749028E035024E2E297905FA59FF09522F0FBE6EFC736DB76B");//dev vinatti
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0911818968;681F7E331A44034C78073BD15F0B31692EFDD45064A96B11B52FF110CF19DC73");// production
        checkPermissionCall();
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission1 = getActivity().checkSelfPermission(Manifest.permission.READ_CALL_LOG);
            int hasPermission2 = getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int hasPermission3 = getActivity().checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS);
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED
                    || hasPermission2 != PackageManager.PERMISSION_GRANTED
                    || hasPermission3 != PackageManager.PERMISSION_GRANTED) {
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

    private void showUIShift() {
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
    }

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
