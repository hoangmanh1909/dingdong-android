package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import android.content.Context;
import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankActivite;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankPresenter;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.SMB002Mode;
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
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SeabankPresenter extends Presenter<SeabankContract.View, SeabankContract.Interactor> implements SeabankContract.Presenter {

    public SeabankPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
    }

    @Override
    public SeabankContract.Interactor onCreateInteractor() {
        return new SeabankInteractor(this);
    }

    @Override
    public SeabankContract.View onCreateView() {
        return SeabankFragment.getInstance();
    }

    @Override
    public void getDanhSachNganHang() {
        try {
            mView.showProgress();
            mInteractor.getDanhSachNganHang()
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                SharedPref sharedPref = new SharedPref((Context) mContainerView);
                                sharedPref.putString(Constants.KEY_LIST_BANK, simpleResult.getData());
                                ArrayList<DanhSachNganHangRepsone> list = NetWorkController.getGson().fromJson(simpleResult.getData(), new TypeToken<ArrayList<DanhSachNganHangRepsone>>() {
                                }.getType());
                                mView.showDanhSach(list);
                                mView.hideProgress();
                            } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.hideProgress();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void yeuCauLienKet(YeuCauLienKetRequest request) {
//        mView.showProgress();
        mInteractor.yeuCauLienKet(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            SMB002Mode thonTinSoTaiKhoanRespone = NetWorkController.getGson().fromJson(simpleResult.getData(), SMB002Mode.class);
                            SmartBankLink smartBankLink = new SmartBankLink();
                            smartBankLink.setBankAccountLimitExpired(thonTinSoTaiKhoanRespone.getAccountLimitExpired());
                            smartBankLink.setBankAccountLimit(thonTinSoTaiKhoanRespone.getAccountLimit());
                            smartBankLink.setBankAccountNumber(thonTinSoTaiKhoanRespone.getAccountNumber());
                            smartBankLink.setStatus(thonTinSoTaiKhoanRespone.getStatus());
                            smartBankLink.setBankAccountName(thonTinSoTaiKhoanRespone.getAccountName());
                            smartBankLink.setPIDNumber(thonTinSoTaiKhoanRespone.getPIDNumber());
                            smartBankLink.setPIDType(thonTinSoTaiKhoanRespone.getPIDType());
                            smartBankLink.setPOCode(thonTinSoTaiKhoanRespone.getPOCode());
                            smartBankLink.setPostmanCode(thonTinSoTaiKhoanRespone.getPostmanCode());
                            smartBankLink.setPostmanTel(thonTinSoTaiKhoanRespone.getPostmanTel());
                            smartBankLink.setIsDefaultPayment(false);
                            mView.showThongTinTaiKhoan(smartBankLink);
                            mView.hideProgress();
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        }
                        mView.hideProgress();
                    }
                });

    }

    @Override
    public void smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request) {
        mView.showProgress();
        mInteractor.smartBankConfirmLinkRequest(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.showMain();
                        } else if (simpleResult.getErrorCode().equals("101")) {
                            Toast.showToast(getViewContext(), "Bạn đã nhập sai OTP! Vui lòng nhập đúng OTP.");
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.dissmisOTP();
                        }
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void getDanhSachTaiKhoan(DanhSachTaiKhoanRequest danhSachTaiKhoanRequest) {
        mView.showProgress();
        mInteractor.getDanhSachTaiKhoan(danhSachTaiKhoanRequest)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode() != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            DanhSachTaiKhoanRespone[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), DanhSachTaiKhoanRespone[].class);
                            List<DanhSachTaiKhoanRespone> list1 = Arrays.asList(list);
                            mView.showDanhSachTaiKhoan(list1);
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }else{ Toast.showToast(getViewContext(),"Lỗi kết nối hệ thống, Vui lòng liên hệ quản trị viên!");
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void moveToEWallet() {
        new ListBankPresenter(mContainerView).pushView();
//        new EWalletPresenter(mContainerView).pushView();
    }

    @Override
    public void ddCallOTP(CallOTP request) {
        mView.showProgress();
        mInteractor.vnpCallOTP(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            Toast.showToast(getViewContext(), "Yêu cầu gửi lại OTP thành công");
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }
}
