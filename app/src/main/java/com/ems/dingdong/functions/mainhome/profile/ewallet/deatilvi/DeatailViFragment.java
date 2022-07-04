package com.ems.dingdong.functions.mainhome.profile.ewallet.deatilvi;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankAdapter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.dialog.DiaLogMatDinh;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.DialogOTP;
import com.ems.dingdong.model.LinkHistory;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DeatailViFragment extends ViewFragment<DeatailViContract.Presenter>
        implements DeatailViContract.View {
    public static DeatailViFragment getInstance() {
        return new DeatailViFragment();
    }

    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.tv_loaivi)
    TextView tvLoaivi;
    @BindView(R.id.tv_trangthai)
    TextView tvTrangthai;
    @BindView(R.id.tv_thanhtoan)
    TextView tvThanhtoan;
    @BindView(R.id.ll_lichsu)
    LinearLayout llLichsu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SharedPref sharedPref;
    private UserInfo userInfo;
    String userJson;
    DialogOTP otpDialog;
    SmartBankLink s;
    PostOffice postOffice;
    @BindView(R.id.btn_huy_matmacdinh)
    Button btnHuyMatmacdinh;

    @BindView(R.id.btn_huy_lienket)
    Button btn_huy_lienket;
    @BindView(R.id.btn_lichsu)
    Button btn_lichsu;
    List<DeatailMode> mList;
    DeatailHistoryAdapter mAdapter;
    List<SmartBankLink> mListVi;
    SmartBankLink smartBankLink;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_linkbank;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        smartBankLink = mPresenter.getSmartBankLink();
        tvTitle.setText("Thông tin liên kết");
        tvLoaivi.setText(smartBankLink.getBankName());
        if (smartBankLink.getIsDefaultPayment())
            tvThanhtoan.setText("Mặc định");
        else tvThanhtoan.setText("Không mặc định");

        tvTrangthai.setText("Đã liên kết");
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (smartBankLink.getIsDefaultPayment()) {
            btnHuyMatmacdinh.setText("Hủy mặc định");
        } else {
            btnHuyMatmacdinh.setText("Đặt mặc định");
        }
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
//        tvBuucuc.setText();
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");


        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        mList = new ArrayList<>();
        mAdapter = new DeatailHistoryAdapter(getContext(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);

        mListVi = new ArrayList<>();
        mListVi = mPresenter.getList();
        if (mListVi != null || mListVi.size() > 0) {
            for (int i = 0; i < mListVi.size(); i++) {
                if (mListVi.get(i).getBankCode().equals("SeABank")) {
                    mListVi.remove(i);
                }
            }
        }

        for (int i = 0; i < mListVi.size(); i++) {
            if (mListVi.get(i).getBankCode().equals(smartBankLink.getBankCode())) {
                mListVi.remove(i);
            }
        }
    }

    @Override
    public void showOTP() {
        mPresenter.back();
    }

    @Override
    public void dissOTP() {

    }


    @Override
    public void capNhatMacDinh() {
    }

    @Override
    public void showHistory(List<DeatailMode> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        llLichsu.setVisibility(View.VISIBLE);
        btnHuyMatmacdinh.setVisibility(View.GONE);
        btn_lichsu.setVisibility(View.GONE);
        btn_huy_lienket.setVisibility(View.GONE);
    }

    @Override
    public void showErrorHistory(String mess) {
        llLichsu.setVisibility(View.GONE);
        Toast.showToast(getViewContext(), mess);
    }

    @Override
    public void showError(String mess) {
        Toast.showToast(getViewContext(), mess);
    }

    @Override
    public void capnhat(String mess) {
        Toast.showToast(getViewContext(), mess);
        smartBankLink = mPresenter.getSmartBankLink();
        if (smartBankLink.getIsDefaultPayment()) {
            tvThanhtoan.setText("Mặc định");
            btnHuyMatmacdinh.setText("Hủy mặc định");
        } else {
            btnHuyMatmacdinh.setText("Đặt mặc định");
            tvThanhtoan.setText("Không mặc định");
        }
    }

    @OnClick({R.id.img_back, R.id.btn_huy_lienket, R.id.btn_lichsu, R.id.btn_huy_matmacdinh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_huy_matmacdinh:

                String title1 = "";
                String tilteMess1 = "";
                for (int i = 0; i < mListVi.size(); i++) {
                    if (mListVi.get(i).getIsDefaultPayment()) {
                        Toast.showToast(getViewContext(), "Vui lòng hủy mặt định " + mListVi.get(i).getBankName() + " trước");
                        return;
                    }
                }
                if (smartBankLink.getIsDefaultPayment()) {
                    title1 = "HỦY ĐẶT MẶC ĐỊNH";
                    tilteMess1 = "Bạn có muốn hủy đặt mặc định tài khoản này?";
                    DiaLogMatDinh otpDialog = new DiaLogMatDinh(getViewContext(),
                            tilteMess1, title1
                            , new DiaLogMatDinh.OnPaymentCallback() {
                        @Override
                        public void onPaymentClick(String otp) {
                            LinkHistory linkHistory = new LinkHistory();
                            linkHistory.setBankCode(null);
                            linkHistory.setPOCode(smartBankLink.getPOCode());
                            linkHistory.setPostmanCode(userInfo.getUserName());
                            linkHistory.setPostmanTel(userInfo.getMobileNumber());
                            mPresenter.SetDefaultPayment(linkHistory);
                        }
                    });
                    otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    otpDialog.show();
                } else {
                    title1 = "ĐẶT LÀM MẶC ĐỊNH";
                    tilteMess1 = "Bạn có muốn đặt tài khoản này làm tài khoản thanh toán mặc định?";
                    DiaLogMatDinh otpDialog = new DiaLogMatDinh(getViewContext(),
                            tilteMess1, title1
                            , new DiaLogMatDinh.OnPaymentCallback() {
                        @Override
                        public void onPaymentClick(String otp) {
                            LinkHistory linkHistory = new LinkHistory();
                            linkHistory.setBankCode(smartBankLink.getBankCode());
                            linkHistory.setPOCode(smartBankLink.getPOCode());
                            linkHistory.setPostmanCode(userInfo.getUserName());
                            linkHistory.setPostmanTel(userInfo.getMobileNumber());
                            mPresenter.SetDefaultPayment(linkHistory);
                        }
                    });
                    otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    otpDialog.show();
                }
                break;
            case R.id.btn_lichsu:

                LinkHistory linkHistory = new LinkHistory();
                linkHistory.setBankCode(smartBankLink.getBankCode());
                linkHistory.setPOCode(smartBankLink.getPOCode());
                linkHistory.setPostmanCode(userInfo.getUserName());
                linkHistory.setPostmanTel(userInfo.getMobileNumber());
                mPresenter.getHistory(linkHistory);
                break;
            case R.id.btn_huy_lienket:
                String title = "";
                String tilteMess = "";
                String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
                String routeCode = "";
                if (!TextUtils.isEmpty(routeInfoJson)) {
                    routeCode = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteCode();
                }
                int account = 0;
                if (smartBankLink.getBankName().contains("MB")) {
                    account = 2;
                } else account = 1;
                SmartBankRequestCancelLinkRequest smartBankRequestCancelLinkRequest = new SmartBankRequestCancelLinkRequest();
                smartBankRequestCancelLinkRequest.setBankCode(smartBankLink.getBankCode());
                smartBankRequestCancelLinkRequest.setPIDNumber(smartBankLink.getPIDNumber());
                smartBankRequestCancelLinkRequest.setPIDType(smartBankLink.getPIDType());
                smartBankRequestCancelLinkRequest.setPOCode(smartBankLink.getPOCode());
                smartBankRequestCancelLinkRequest.setPODistrictCode(userInfo.getPODistrictCode());
                smartBankRequestCancelLinkRequest.setPOProvinceCode(userInfo.getPOProvinceCode());
                smartBankRequestCancelLinkRequest.setRouteCode(routeCode);
                smartBankRequestCancelLinkRequest.setRouteId(NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteId());
                smartBankRequestCancelLinkRequest.setPostmanTel(userInfo.getMobileNumber());
                smartBankRequestCancelLinkRequest.setPostmanId(userInfo.getiD());
                smartBankRequestCancelLinkRequest.setPostmanCode(userInfo.getUserName());
                smartBankRequestCancelLinkRequest.setAccountType(account);
                smartBankRequestCancelLinkRequest.setPaymentToken(smartBankLink.getPaymentToken());
                smartBankRequestCancelLinkRequest.setSignature(Utils.SHA256(userInfo.getMobileNumber()
                        + userInfo.getUserName()
                        + smartBankLink.getPOCode()
                        + BuildConfig.E_WALLET_SIGNATURE_KEY).toUpperCase());
                title = "HỦY LIÊN KẾT";
                tilteMess = "Bạn có chắc chắn muốn hủy liên kết tài khoản này hay không?";

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
                break;
            default:
        }
    }
}
