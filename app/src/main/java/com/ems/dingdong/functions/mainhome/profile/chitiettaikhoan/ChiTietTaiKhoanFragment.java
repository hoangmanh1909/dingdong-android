package com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.enumClass.StateEWallet;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankActivite;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.dialog.DiaLogMatDinh;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.dialog.DiaLogSoDu;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.DialogOTP;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankInquiryBalanceRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.observer.EWalletData;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;
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
    DialogOTP otpDialog;
    int type = 0;
    SmartBankLink s;
    PostOffice postOffice;

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
        s = mPresenter.getSmartBankLink();

        Log.d("asdasdasd", new Gson().toJson(s));
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
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
            tvMabuucuc.setText(String.format("%s - %s", postOffice.getCode(), postOffice.getName()));
        }
//                tvMabuucuc.setText(s.getPOCode() + " - " + userInfo.get);
        tvMabuuta.setText(s.getPostmanCode() + " - " + userInfo.getFullName());
        tvHovaten.setText(s.getBankAccountName());
        tvSotktc.setText(s.getBankAccountNumber());
        tvSodienthoai.setText(userInfo.getMobileNumber());
        tvLoaigiayto.setText(s.getPIDType());
        tvSogttt.setText(s.getPIDNumber());
        tvHanmuc.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(s.getBankAccountLimit())))));
        tvNgayhethan.setText(s.getBankAccountLimitExpired());
        if (s.getIsDefaultPayment()) {
            btnHuyMatmacdinh.setText("Hủy mặc định");
        } else {
            btnHuyMatmacdinh.setText("Đặt mặc định");
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
                if (s.getBankCode().equals("SeABank")) {
                    String title = "";
                    String tilteMess = "";
//                        if (s.getStatus().equals("WAITING_CANCEL")) {
//                            CallOTP callOTP = new CallOTP();
//                            callOTP.setBankCode(s.getBankCode());
//                            callOTP.setPOCode(s.getPOCode());
//                            callOTP.setPostmanCode(s.getPostmanCode());
//                            mPresenter.ddCallOTP(callOTP);
//                        } else {
                    String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
                    String routeCode = "";
                    if (!TextUtils.isEmpty(routeInfoJson)) {
                        routeCode = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteCode();
                    }
                    SmartBankRequestCancelLinkRequest smartBankRequestCancelLinkRequest = new SmartBankRequestCancelLinkRequest();
                    smartBankRequestCancelLinkRequest.setBankCode(s.getBankCode());
                    smartBankRequestCancelLinkRequest.setPIDNumber(s.getPIDNumber());
                    smartBankRequestCancelLinkRequest.setPIDType(s.getPIDType());
                    smartBankRequestCancelLinkRequest.setPOCode(s.getPOCode());
                    smartBankRequestCancelLinkRequest.setPostmanCode(s.getPostmanCode());
                    smartBankRequestCancelLinkRequest.setSeABankAccount(s.getBankAccountNumber());
                    smartBankRequestCancelLinkRequest.setSeABankAccountLimit(s.getBankAccountLimit());
                    smartBankRequestCancelLinkRequest.setPODistrictCode(userInfo.getPODistrictCode());
                    smartBankRequestCancelLinkRequest.setPOProvinceCode(userInfo.getPOProvinceCode());
                    smartBankRequestCancelLinkRequest.setRouteCode(routeCode);
                    smartBankRequestCancelLinkRequest.setRouteId(NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteId());
                    smartBankRequestCancelLinkRequest.setPostmanTel(userInfo.getMobileNumber());
                    smartBankRequestCancelLinkRequest.setPostmanId(userInfo.getiD());
                    smartBankRequestCancelLinkRequest.setPostmanCode(userInfo.getUserName());
                    smartBankRequestCancelLinkRequest.setSignature(Utils.SHA256(userInfo.getMobileNumber() + userInfo.getUserName() + s.getPOCode() + BuildConfig.PRIVATE_KEY).toUpperCase());

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
//                        }
                }
                break;
            case R.id.btn_huy_matmacdinh:
                TaiKhoanMatDinh taiKhoanMatDinh = new TaiKhoanMatDinh();
                String title = "";
                String tilteMess = "";
                if (s.getBankCode().equals("SeABank")) {
                    if (!s.getIsDefaultPayment()) {
//                            taiKhoanMatDinh.setBankCode(null);
                        taiKhoanMatDinh.setBankCode(s.getBankCode());
                        taiKhoanMatDinh.setAccountNumber(s.getBankAccountNumber());
                        taiKhoanMatDinh.setPostmanCode(s.getPostmanCode());
                        taiKhoanMatDinh.setPOCode(s.getPOCode());
                        title = "ĐẶT LÀM MẶC ĐỊNH";
                        tilteMess = "Bạn có muốn đặt tài khoản này làm tài khoản thanh toán măc định?";
                        type = 1;
                    } else {
                        taiKhoanMatDinh.setAccountNumber(s.getBankAccountNumber());
                        taiKhoanMatDinh.setPostmanCode(s.getPostmanCode());
                        taiKhoanMatDinh.setPOCode(s.getPOCode());
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
                break;
            case R.id.btn_xemsodu:
                if (s.getBankCode().equals("SeABank")) {
                    SmartBankInquiryBalanceRequest smartBankInquiryBalanceRequest = new SmartBankInquiryBalanceRequest();
                    smartBankInquiryBalanceRequest.setBankCode(s.getBankCode());
                    smartBankInquiryBalanceRequest.setPostmanCode(s.getPostmanCode());
                    smartBankInquiryBalanceRequest.setSeABankAccount(s.getBankAccountNumber());
                    mPresenter.ddTruyVanSodu(smartBankInquiryBalanceRequest);
                }
                break;
        }
    }

    @Override
    public void showOTP() {
        if (s.getBankCode().equals("SeABank")) {
//                userInfo.getSmartBankLink().get(0).setStatus("WAITING_CANCEL");
//                sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
            SmartBankConfirmCancelLinkRequest smartBankConfirmLinkRequest = new SmartBankConfirmCancelLinkRequest();
            smartBankConfirmLinkRequest.setBankCode(s.getBankCode());
            smartBankConfirmLinkRequest.setPOCode(s.getPOCode());
            smartBankConfirmLinkRequest.setPostmanCode(s.getPostmanCode());
            otpDialog = new DialogOTP(getViewContext(), "Vui lòng nhập OTP đã được gửi về SĐT " + userInfo.getMobileNumber()
                    , new DialogOTP.OnPaymentCallback() {
                @Override
                public void onPaymentClick(String otp, int type) {
                    smartBankConfirmLinkRequest.setOTP(otp);
                    mPresenter.ddXacnhanhuy(smartBankConfirmLinkRequest);
                }

                @Override
                public void onCallOTP() {
                    for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                        if (s.getBankCode().equals("SeABank")) {
                            CallOTP callOTP = new CallOTP();
                            callOTP.setBankCode(s.getBankCode());
                            callOTP.setPOCode(s.getPOCode());
                            callOTP.setPostmanCode(s.getPostmanCode());
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

    @Override
    public void dissOTP() {
        try {
            otpDialog.dismiss();

        } catch (Exception e) {
        }
    }

    @Override
    public void huyLKThanhCong() {
        EWalletData.setMeasurements(StateEWallet.DELETE,mPresenter.getSmartBankLink());
        if (otpDialog != null && otpDialog.isShowing()){
            otpDialog.dismiss();
        }
        mPresenter.back();
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
        s = mPresenter.getSmartBankLink();
        EWalletData.setMeasurements(StateEWallet.UPDATE,s);
        if (s.getIsDefaultPayment()) {
            btnHuyMatmacdinh.setText("Hủy mặc định");
        } else {
            btnHuyMatmacdinh.setText("Đặt mặc định");
        }
    }


}
