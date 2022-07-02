package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.OtpEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LinkEWalletFragment extends ViewFragment<LinkEWalletContract.Presenter>
        implements LinkEWalletContract.View {

    @BindView(R.id.tv_info_link)
    CustomTextView tvInfoLink;

    @BindView(R.id.et_otp)
    OtpEditText otpEditText;

    @BindView(R.id.et_otpvimb)
    OtpEditText otpEditTextViMB;

    @BindView(R.id.ll_from_info)
    LinearLayout linearLayout;

    @BindView(R.id.edt_phone_number)
    CustomEditText edtPhoneNumber;

    @BindView(R.id.id_user)
    CustomEditText edtIdUser;
    @BindView(R.id.tv_chonnagnhang)
    TextView tvChonnagnhang;
    @BindView(R.id.btn_link_wallet)
    CustomTextView btnLinkWallet;

    List<DanhSachNganHangRepsone> danhSachNganHangRepsone;

    int mType = 1;

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
        if (mPresenter != null && !TextUtils.isEmpty(mPresenter.getPhoneNumber()) && !TextUtils.isEmpty(mPresenter.getUserIdApp())) {
            edtPhoneNumber.setText(mPresenter.getPhoneNumber());
            edtIdUser.setText(mPresenter.getUserIdApp());
        } else {
            SharedPref pref = SharedPref.getInstance(getViewContext());
            String mobileNSignCode = pref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
            edtPhoneNumber.setText(mobileNSignCode.split(";")[0]);
            String userJson = pref.getString(Constants.KEY_USER_INFO, "");
            if (!userJson.isEmpty()) {
                edtIdUser.setText(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName());
            }
        }
        linearLayout.setVisibility(View.GONE);
        btnLinkWallet.setVisibility(View.GONE);

    }

    @OnClick({R.id.img_back, R.id.btn_link_wallet, R.id.tv_chonnagnhang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_chonnagnhang:
                pickFilter(tvChonnagnhang);
                break;
            case R.id.btn_link_wallet:
                if (linearLayout.getVisibility() == View.VISIBLE) {
                    mPresenter.linkEWallet(mType);
                } else {
                    if (mType == 1) {
                        if (TextUtils.isEmpty(otpEditText.getText()) || otpEditText.getText().length() != 6) {
                            showErrorToast(getString(R.string.wrong_otp_pattern));
                            return;
                        }
                        SharedPref pref = SharedPref.getInstance(getViewContext());
                        String requestId = pref.getString(Constants.KEY_LINK_REQUEST_ID, "");
                        mPresenter.verifyLinkWithOtp(requestId, otpEditText.getText().toString(), 1);
                    } else {
                        if (TextUtils.isEmpty(otpEditTextViMB.getText())) {
                            showErrorToast("OTP không được bỏ trống");
                            return;
                        }
                        if (otpEditTextViMB.getText().length() != 6) {
                            showErrorToast(getString(R.string.wrong_otp_pattern));
                            return;
                        }
                        SharedPref pref = SharedPref.getInstance(getViewContext());
                        String requestId = pref.getString(Constants.KEY_LINK_REQUEST_ID, "");
                        mPresenter.verifyLinkWithOtp(requestId, otpEditTextViMB.getText().toString(), 2);
                    }

                }
                break;
        }
    }

    @Override
    public void showLinkSuccess(String message) {
        linearLayout.setVisibility(View.GONE);
        tvChonnagnhang.setEnabled(false);
        if (mType == 1) {
            otpEditText.setVisibility(View.VISIBLE);
        } else {
            otpEditTextViMB.setVisibility(View.VISIBLE);
        }
        tvInfoLink.setVisibility(View.VISIBLE);
        tvInfoLink.setText(message);
    }

    @Override
    public void showOtpSuccess(String message) {
        new SweetAlertDialog(getViewContext())
                .setConfirmText("OK")
                .setTitleText(getResources().getString(R.string.notification))
                .setContentText("Liên kết tài khoản ví thành công.")
                .setConfirmClickListener(sweetAlertDialog -> {
                    mPresenter.back();
                    sweetAlertDialog.dismiss();
                }).show();
    }

    @Override
    public void showLinkError(String message) {
        showErrorToast(message);
    }

    @Override
    public void showOtpError(String message) {
        showErrorToast(message);
    }

    @Override
    public void showDanhSach(List<DanhSachNganHangRepsone> list) {
        danhSachNganHangRepsone = new ArrayList<>();
        danhSachNganHangRepsone.addAll(list);
    }

    private void pickFilter(View anchor) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), anchor);
        if (danhSachNganHangRepsone.size() > 0) {
            for (int i = 0; i < danhSachNganHangRepsone.size(); i++) {
                if (danhSachNganHangRepsone.get(i).getGroupType() == 1)
                    popupMenu.getMenu().add(0, i, danhSachNganHangRepsone.get(i).getGroupType(), danhSachNganHangRepsone.get(i).getBankName());
            }
            popupMenu.setOnMenuItemClickListener(item -> {
                tvChonnagnhang.setText(item.getTitle());
                if (item.getTitle().toString().contains("MB"))
                    mType = 2;
                else mType = 1;
                linearLayout.setVisibility(View.VISIBLE);
                btnLinkWallet.setVisibility(View.VISIBLE);
                return true;
            });
            popupMenu.show();
        } else Toast.showToast(getViewContext(), "Chưa có ngân hàng nào để liên kết");
    }
}
