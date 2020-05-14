package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.OtpEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class LinkEWalletFragment extends ViewFragment<LinkEWalletContract.Presenter>
        implements LinkEWalletContract.View {

    @BindView(R.id.tv_info_link)
    CustomTextView tvInfoLink;

    @BindView(R.id.et_otp)
    OtpEditText otpEditText;

    @BindView(R.id.ll_from_info)
    LinearLayout linearLayout;

    @BindView(R.id.edt_phone_number)
    CustomEditText edtPhoneNumber;

    @BindView(R.id.id_user)
    CustomEditText edtIdUser;

    SharedPref pref;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_link_e_wallet;
    }

    public static LinkEWalletFragment getInstance() {
        return new LinkEWalletFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        pref = SharedPref.getInstance(getViewContext());
    }

    @Override
    public void showAuthSuccess() {
        mPresenter.linkEWallet(edtPhoneNumber.getText().toString(), edtIdUser.getText().toString());
    }

    @OnClick({R.id.img_back, R.id.btn_link_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_link_wallet:
                if (linearLayout.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(edtPhoneNumber.getText())) {
                        showSuccessToast("Bạn chưa nhập số điện thoại");
                        return;
                    }

                    if (TextUtils.isEmpty(edtIdUser.getText())) {
                        showSuccessToast("Bạn chưa nhập mã người dùng trên ứng dụng Ví Bưu Điện");
                        return;
                    }
                    mPresenter.getEWalletToken();

                } else {
                    if (TextUtils.isEmpty(otpEditText.getText()) || otpEditText.getText().length() != 6) {
                        showSuccessToast("Bạn chưa nhập số điện thoại");
                        return;
                    }
                    String requestId = pref.getString(Constants.KEY_REQUEST_ID, "");
                    mPresenter.verifyLinkWithOtp(requestId, otpEditText.getText().toString());
                }
                break;
        }
    }

    @Override
    public void showLinkSuccess(String message) {
        linearLayout.setVisibility(View.GONE);
        otpEditText.setVisibility(View.VISIBLE);
        tvInfoLink.setVisibility(View.VISIBLE);
        tvInfoLink.setText(message);
    }

    @Override
    public void showOtpSuccess(String message) {
        new SweetAlertDialog(getViewContext())
                .setConfirmText("OK")
                .setTitleText(getResources().getString(R.string.notification))
                .setContentText(message)
                .setConfirmClickListener(sweetAlertDialog -> {
                    mPresenter.back();
                    sweetAlertDialog.dismiss();
                }).show();
    }

    @Override
    public void showAuthError(String message) {
        showErrorToast(message);
    }

    @Override
    public void showLinkError(String message) {
        showErrorToast(message);
    }

    @Override
    public void showOtpError(String message) {
        showErrorToast(message);
    }
}
