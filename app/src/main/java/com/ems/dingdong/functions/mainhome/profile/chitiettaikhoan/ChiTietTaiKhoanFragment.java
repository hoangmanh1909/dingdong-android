package com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankActivite;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.dialog.DiaLogMatDinh;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.dialog.DiaLogSoDu;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.DialogOTP;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankInquiryBalanceRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class ChiTietTaiKhoanFragment extends ViewFragment<ChiTietTaiKhoanContract.Presenter> implements ChiTietTaiKhoanContract.View {

    @BindView(R.id.tv_mabuuta)
    TextView tvMabuuta;
    @BindView(R.id.tv_hovaten)
    TextView tvHovaten;
    @BindView(R.id.tv_sodienthoai)
    TextView tvSodienthoai;
    @BindView(R.id.tv_loaigiayto)
    TextView tvLoaigiayto;
    @BindView(R.id.tv_sogttt)
    TextView tvSogttt;
    //    @BindView(R.id.tv_ngaycap)
//    TextView tvNgaycap;
//    @BindView(R.id.tv_noicap)
//    TextView tvNoicap;
    @BindView(R.id.tv_hanmuc)
    TextView tvHanmuc;
    @BindView(R.id.tv_ngayhethan)
    TextView tvNgayhethan;
    @BindView(R.id.tv_mabuucuc)
    TextView tvMabuucuc;
    @BindView(R.id.tv_sotktc)
    TextView tvSotktc;

    @BindView(R.id.btn_huy_matmacdinh)
    Button btnHuyMatmacdinh;
    //    @BindView(R.id.tv_trangthailienket)
//    TextView tvTrangthailienket;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;

    int type = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chitiett;
    }

    public static ChiTietTaiKhoanFragment getInstance() {
        return new ChiTietTaiKhoanFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
//        tvBuucuc.setText();
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");

//        if (SipCmc.getAccountInfo() != null) {
//            tvCtel.setText("Số máy lẻ : " + SipCmc.getAccountInfo().getName());
//        }
        Log.d("asdhjg23123", new Gson().toJson(userInfo));

        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
            tvMabuucuc.setText(String.format("%s - %s", postOffice.getCode(), postOffice.getName()));
        }
        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
//                tvMabuucuc.setText(userInfo.getSmartBankLink().get(i).getPOCode() + " - " + userInfo.get);
                tvMabuuta.setText(userInfo.getSmartBankLink().get(i).getPostmanCode() + " - " + userInfo.getFullName());
                tvHovaten.setText(userInfo.getSmartBankLink().get(i).getBankAccountName());
                tvSotktc.setText(userInfo.getSmartBankLink().get(i).getBankAccountNumber());
                tvSodienthoai.setText(userInfo.getMobileNumber());
                tvLoaigiayto.setText(userInfo.getSmartBankLink().get(i).getPIDType());
                tvSogttt.setText(userInfo.getSmartBankLink().get(i).getPIDNumber());
                tvHanmuc.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(userInfo.getSmartBankLink().get(i).getBankAccountLimit())))));
                tvNgayhethan.setText(userInfo.getSmartBankLink().get(i).getBankAccountLimitExpired());
                if (userInfo.getSmartBankLink().get(i).getIsDefaultPayment()) {
                    btnHuyMatmacdinh.setText("Hủy mặc định");
                } else {
                    btnHuyMatmacdinh.setText("Đặt mặc định");
                }
            }
        }

    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @OnClick({R.id.img_back, R.id.btn_huy_lienket, R.id.btn_huy_matmacdinh, R.id.btn_xemsodu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_huy_lienket:
                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                    if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                        String title = "";
                        String tilteMess = "";
                        if (userInfo.getSmartBankLink().get(i).getStatus().equals("WAITING_CANCEL")) {
                            CallOTP callOTP = new CallOTP();
                            callOTP.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                            callOTP.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
                            callOTP.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                            mPresenter.ddCallOTP(callOTP);
                        } else {
                            SmartBankRequestCancelLinkRequest smartBankRequestCancelLinkRequest = new SmartBankRequestCancelLinkRequest();
                            smartBankRequestCancelLinkRequest.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                            smartBankRequestCancelLinkRequest.setPIDNumber(userInfo.getSmartBankLink().get(i).getPIDNumber());
                            smartBankRequestCancelLinkRequest.setPIDType(userInfo.getSmartBankLink().get(i).getPIDType());
                            smartBankRequestCancelLinkRequest.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
                            smartBankRequestCancelLinkRequest.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                            smartBankRequestCancelLinkRequest.setSeABankAccount(userInfo.getSmartBankLink().get(i).getBankAccountNumber());
                            smartBankRequestCancelLinkRequest.setSeABankAccountLimit(userInfo.getSmartBankLink().get(i).getBankAccountLimit());

                            title = "HỦY LIÊN KẾT";
                            tilteMess = "Bạn có chắc chắn muốn hủy liên kết tài khoản không?";

                            DiaLogMatDinh otpDialog = new DiaLogMatDinh(getViewContext(),
                                    tilteMess, title
                                    , new DiaLogMatDinh.OnPaymentCallback() {
                                @Override
                                public void onPaymentClick(String otp) {
                                    mPresenter.ddHuyLienKet(smartBankRequestCancelLinkRequest);
                                }
                            });
                            otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            otpDialog.show();
                        }
                    }
                }
                break;
            case R.id.btn_huy_matmacdinh:
                TaiKhoanMatDinh taiKhoanMatDinh = new TaiKhoanMatDinh();
                String title = "";
                String tilteMess = "";
                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                    if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                        if (!userInfo.getSmartBankLink().get(i).getIsDefaultPayment()) {
//                            taiKhoanMatDinh.setBankCode(null);
                            taiKhoanMatDinh.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                            taiKhoanMatDinh.setAccountNumber(userInfo.getSmartBankLink().get(i).getBankAccountNumber());
                            taiKhoanMatDinh.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                            taiKhoanMatDinh.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
                            title = "ĐẶT LÀM MẶC ĐỊNH";
                            tilteMess = "Bạn có muốn đặt tài khoản này làm tài khoản thanh toán măc định?";
                            type = 1;
                        } else {
                            taiKhoanMatDinh.setAccountNumber(userInfo.getSmartBankLink().get(i).getBankAccountNumber());
                            taiKhoanMatDinh.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                            taiKhoanMatDinh.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
                            title = "HỦY ĐẶT MẶC ĐỊNH";
                            tilteMess = "Bạn có muốn hủy đặt mặc định tài khoản này?";
                            type = 2;
                        }

                        DiaLogMatDinh otpDialog = new DiaLogMatDinh(getViewContext(),
                                tilteMess, title
                                , new DiaLogMatDinh.OnPaymentCallback() {
                            @Override
                            public void onPaymentClick(String otp) {
                                mPresenter.ddTaiKhoanMacDinh(taiKhoanMatDinh);
                            }
                        });
                        otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        otpDialog.show();
                    }
                }
                break;
            case R.id.btn_xemsodu:
                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                    if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                        SmartBankInquiryBalanceRequest smartBankInquiryBalanceRequest = new SmartBankInquiryBalanceRequest();
                        smartBankInquiryBalanceRequest.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                        smartBankInquiryBalanceRequest.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                        smartBankInquiryBalanceRequest.setSeABankAccount(userInfo.getSmartBankLink().get(i).getBankAccountNumber());
                        mPresenter.ddTruyVanSodu(smartBankInquiryBalanceRequest);
                    }
                }
                break;
        }
    }

    @Override
    public void showOTP() {
        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                userInfo.getSmartBankLink().get(0).setStatus("WAITING_CANCEL");
                sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
                SmartBankConfirmCancelLinkRequest smartBankConfirmLinkRequest = new SmartBankConfirmCancelLinkRequest();
                smartBankConfirmLinkRequest.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                smartBankConfirmLinkRequest.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
                smartBankConfirmLinkRequest.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                DialogOTP otpDialog = new DialogOTP(getViewContext(), "Vui lòng nhập OTP đã được gửi về SĐT " + userInfo.getMobileNumber()
                        , new DialogOTP.OnPaymentCallback() {
                    @Override
                    public void onPaymentClick(String otp, int type) {
                        smartBankConfirmLinkRequest.setOTP(otp);
                        mPresenter.ddXacnhanhuy(smartBankConfirmLinkRequest);
                    }

                    @Override
                    public void onCallOTP() {
                        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                                CallOTP callOTP = new CallOTP();
                                callOTP.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                                callOTP.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
                                callOTP.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                                mPresenter.ddCallOTP(callOTP);
                            }
                        }
                    }
                });
                otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                otpDialog.show();
            }
        }
    }

    @Override
    public void huyLKThanhCong() {
        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                userInfo.getSmartBankLink().remove(i);
                sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
            }
        }
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showSoDu(String sodu) {
        DiaLogSoDu otpDialog = new DiaLogSoDu(getViewContext(), sodu);
        otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        otpDialog.show();
    }

    @Override
    public void capNhatMacDinh() {

        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                if (type == 2) {
                    userInfo.getSmartBankLink().get(i).setIsDefaultPayment(false);
                    sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
                } else if (type == 1) {
                    userInfo.getSmartBankLink().get(i).setIsDefaultPayment(true);
                    sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
                }
                if (userInfo.getSmartBankLink().get(i).getIsDefaultPayment()) {
                    btnHuyMatmacdinh.setText("Hủy mặc định");
                } else {
                    btnHuyMatmacdinh.setText("Đặt mặc định");
                }
            }
        }


    }
}
