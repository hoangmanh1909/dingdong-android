package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankActivite;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankFragment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.DanhSachTaiKhoanRequest;
import com.ems.dingdong.model.response.DanhSachTaiKhoanRespone;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.model.thauchi.ThonTinSoTaiKhoanRespone;
import com.ems.dingdong.model.thauchi.YeuCauLienKetRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SeabankFragment extends ViewFragment<SeabankContract.Presenter> implements SeabankContract.View {

    public static SeabankFragment getInstance() {
        return new SeabankFragment();
    }

    private SharedPref sharedPref;
    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;

    String userJson;
    String postOfficeJson;
    String routeInfoJson;
    String maNganHang;
    List<DanhSachNganHangRepsone> danhSachNganHangRepsone;
    SmartBankLink thonTinSoTaiKhoanRespone;

    int idDanhSach;
    @BindView(R.id.tv_chonnagnhang)
    TextView tvChonnagnhang;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.constraintLayout_thongtin)
    ConstraintLayout constraintLayoutThongtin;
    @BindView(R.id.btn_yeucaulienket)
    Button btnYeucaulienket;
    @BindView(R.id.edt_sotaikhoan)
    TextView edtSotaikhoan;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_tennganhang)
    TextView tvTennganhang;
    @BindView(R.id.tv_tenchitietnganhang)
    TextView tvTenchitietnganhang;
    @BindView(R.id.tv_tennganhanglienket)
    TextView tvTennganhanglienket;
    @BindView(R.id.edt_loaigiayto)
    TextView edtLoaigiayto;
    //    @BindView(R.id.tv_mabuuta)
//    TextView tvMabuuta;
//    @BindView(R.id.tv_mabuucuc)
//    TextView tvMabuucuc;
//    @BindView(R.id.tv_hovaten)
//    TextView tvHovaten;
//    @BindView(R.id.tv_sodienthoai)
//    TextView tvSodienthoai;
//    @BindView(R.id.tv_loaigiayto)
//    TextView tvLoaigiayto;
//    @BindView(R.id.tv_sogttt)
//    TextView tvSogttt;
//    @BindView(R.id.tv_ngaycap)
//    TextView tvNgaycap;
//    @BindView(R.id.tv_noicap)
//    TextView tvNoicap;
//    @BindView(R.id.tv_hanmuc)
//    TextView tvHanmuc;
//    @BindView(R.id.tv_ngayhethan)
//    TextView tvNgayhethan;
//    @BindView(R.id.tv_trangthaibuuta)
//    TextView tvTrangthaibuuta;
//    @BindView(R.id.tv_trangthailienket)
//    TextView tvTrangthailienket;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<DanhSachTaiKhoanRespone> mList;
    SeabankAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lienketseabank;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        constraintLayout.setVisibility(View.GONE);
        constraintLayoutThongtin.setVisibility(View.GONE);
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }

        mList = new ArrayList<>();
        mAdapter = new SeabankAdapter(getViewContext(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);


        edtSotaikhoan.setText(userInfo.getPIDNumber());
        edtLoaigiayto.setText(userInfo.getPIDType());
    }

    @OnClick({R.id.img_back, R.id.btn_yeucaulienket, R.id.tv_chonnagnhang, R.id.btn_huy, R.id.btn_tieptuc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_huy:
                mPresenter.back();
                break;
            case R.id.btn_tieptuc:
                boolean isCheck = false;
                for (int i = 0; i < mList.size(); i++)
                    if (mList.get(i).isIscheck()) {
                        isCheck = true;
                        break;
                    } else isCheck = false;

                if (!isCheck) {
                    new SweetAlertDialog(getViewContext())
                            .setConfirmText("Đóng")
                            .setTitleText(getResources().getString(R.string.notification))
                            .setContentText("Vui lòng chọn tài khoản liên kết")
                            .setConfirmClickListener(sweetAlertDialog -> {
                                sweetAlertDialog.dismiss();
                            }).show();
                } else if (checkbox.isChecked()) {
                    YeuCauLienKetRequest yeuCauLienKetRequest = new YeuCauLienKetRequest();
                    yeuCauLienKetRequest.setBankCode(maNganHang);
                    yeuCauLienKetRequest.setPIDNumber(userInfo.getPIDNumber());
                    yeuCauLienKetRequest.setPIDType(userInfo.getPIDType());
                    yeuCauLienKetRequest.setPOCode(userInfo.getUnitCode());
                    yeuCauLienKetRequest.setPostmanCode(userInfo.getUserName());
                    String account = "";
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).isIscheck())
                            account = mList.get(i).getAccountNumber();
                    }
                    yeuCauLienKetRequest.setSeABankAccount(account);
                    mPresenter.yeuCauLienKet(yeuCauLienKetRequest);

                    thonTinSoTaiKhoanRespone = new SmartBankLink();

                    showProgress();
                } else {
                    new SweetAlertDialog(getViewContext())
                            .setConfirmText("Đóng")
                            .setTitleText(getResources().getString(R.string.notification))
                            .setContentText("Vui lòng tích chọn đồng ý với điều khoản và điều kiện sử dụng")
                            .setConfirmClickListener(sweetAlertDialog -> {
                                sweetAlertDialog.dismiss();
                            }).show();
                }
                break;
            case R.id.tv_chonnagnhang:
                pickFilter(tvChonnagnhang);
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_yeucaulienket:
                guiYeuCauLienKet();
                break;
        }
    }


    private void guiYeuCauLienKet() {
        TypeGTTT gttt = new TypeGTTT();
        int typegttt;
        DanhSachTaiKhoanRequest danhSachTaiKhoanRequest = new DanhSachTaiKhoanRequest();
//        /// Mã bc của bưu tá
//        yeuCauLienKetRequest.setPOCode(postOffice.getCode());
//        /// Mã bưu tá
//        yeuCauLienKetRequest.setPostmanCode(userInfo.getUserName());

        if (userInfo.getPIDNumber().isEmpty()) {
            Toast.showToast(getViewContext(), "Vui lòng khai báo số GTTT");
            return;
        }
        danhSachTaiKhoanRequest.setPIDNumber(userInfo.getPIDNumber());
        danhSachTaiKhoanRequest.setBankCode(maNganHang);
        danhSachTaiKhoanRequest.setPIDType(userInfo.getPIDType());
//        if (edtSotaikhoan.getText().toString().trim().isEmpty()) {
//            Toast.showToast(getViewContext(), "Vui lòng nhập số tài khoản thấu chi");
//            return;
//        }
        mPresenter.getDanhSachTaiKhoan(danhSachTaiKhoanRequest);
    }

    private void pickFilter(View anchor) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), anchor);
        if (danhSachNganHangRepsone.size() >= 0) {
            for (int i = 0; i < danhSachNganHangRepsone.size(); i++) {
                popupMenu.getMenu().add(0, i, i, danhSachNganHangRepsone.get(i).getBankName());
            }
            popupMenu.setOnMenuItemClickListener(item -> {
                constraintLayout.setVisibility(View.VISIBLE);
                idDanhSach = item.getItemId();
                maNganHang = danhSachNganHangRepsone.get(idDanhSach).getBankCode();
                tvTenchitietnganhang.setText(danhSachNganHangRepsone.get(idDanhSach).getBankName());
                tvTennganhang.setText(danhSachNganHangRepsone.get(idDanhSach).getBankCode());
                Glide.with(this).load(danhSachNganHangRepsone.get(idDanhSach).getLogo()).into(imgLogo);
                tvTennganhanglienket.setText("Liên kết tài khoản " + danhSachNganHangRepsone.get(idDanhSach).getBankCode());
                return true;
            });
            popupMenu.show();
        } else Toast.showToast(getViewContext(), "Chưa có ngân hàng nào để liên kết");
    }

    @Override
    public void showDanhSach(List<DanhSachNganHangRepsone> list) {
        danhSachNganHangRepsone = new ArrayList<>();
        danhSachNganHangRepsone = list;

//        Glide.with(this).load(danhSachNganHangRepsone.get).into(imgLogo);
    }

    @Override
    public void showThongTinTaiKhoan(SmartBankLink respone) {
        thonTinSoTaiKhoanRespone = new SmartBankLink();
        thonTinSoTaiKhoanRespone = respone;
        btnYeucaulienket.setVisibility(View.GONE);
        constraintLayoutThongtin.setVisibility(View.VISIBLE);
        respone.setBankCode("SeABank");
        respone.setPostmanTel(userInfo.getMobileNumber());

        String account = "";
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isIscheck())
                account = mList.get(i).getAccountNumber();
        }
//        respone.setBankAccountNumber(account);
//        respone.setBankAccountLimit("0");
//        respone.setBankAccountLimitExpired("0");
//        links.add(respone);
        if (userInfo.getSmartBankLink() == null) {
            List<SmartBankLink> links = new ArrayList<>();
            userInfo.setSmartBankLink(links);
        }
        userInfo.getSmartBankLink().add(respone);
        sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));

        SmartBankConfirmLinkRequest smartBankConfirmLinkRequest = new SmartBankConfirmLinkRequest();
        smartBankConfirmLinkRequest.setBankCode(maNganHang);
        smartBankConfirmLinkRequest.setPIDNumber(userInfo.getPIDNumber());
        smartBankConfirmLinkRequest.setPIDType(userInfo.getPIDType());
        smartBankConfirmLinkRequest.setPOCode(userInfo.getUnitCode());
        smartBankConfirmLinkRequest.setPostmanCode(userInfo.getUserName());
        smartBankConfirmLinkRequest.setSeABankAccount(account);
        DialogOTP otpDialog = new DialogOTP(getViewContext(), "Vui lòng nhập OTP đã được gửi về SĐT " + respone.getPostmanTel(),
                new DialogOTP.OnPaymentCallback() {
                    @Override
                    public void onPaymentClick(String otp, int type) {

                        smartBankConfirmLinkRequest.setOTP(otp);
                        mPresenter.smartBankConfirmLinkRequest(smartBankConfirmLinkRequest);

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

    @Override
    public void showDanhSachTaiKhoan(List<DanhSachTaiKhoanRespone> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter = new SeabankAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.cbSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < mList.size(); i++) {
                            if (i == position) {
                                mListFilter.get(i).setIscheck(true);
                            } else {
                                mListFilter.get(i).setIscheck(false);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        btnYeucaulienket.setVisibility(View.GONE);
        constraintLayoutThongtin.setVisibility(View.VISIBLE);
    }

    @Override
    public void showThanhcong() {
        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                userInfo.getSmartBankLink().get(i).setStatus("WAITING");
                sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
            }
        }
    }

    @Override
    public void showMain() {
        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                userInfo.getSmartBankLink().get(i).setStatus("ACTIVE");
                sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
                Intent intent = new Intent(getViewContext(), ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }
}
