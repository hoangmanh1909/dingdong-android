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
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.views.CustomMediumTextView;
import com.vinatti.dingdong.views.CustomTextView;

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
      //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "01685537906;1BA4A873023658E7DBCEE6ED5ADFC2382115C6B862F3A3CAE57F2DDB32D5D862");
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0963170164;A6B0A7F34BECAD439B4E2B031D0690F117544CC6D155596C2DB86F42897C76AD");
        //mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "0963170164;E57EF726774F97E0A5E61C850A6BE72BF1646C25139F0591E5774E1F66AEA119");//server dev
       // mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "01685537906;1BA4A873023658E7DBCEE6ED5ADFC2382115C6B862F3A3CAE57F2DDB32D5D862");
        // mSharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
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

    @Override
    public void gotoHome() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().finish();
            getActivity().startActivity(intent);
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
