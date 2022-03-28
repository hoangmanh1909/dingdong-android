package com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet.LinkEWalletPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.SeabankPresenter;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.SoDuRequest;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankInquiryBalanceRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.model.thauchi.ThonTinSoTaiKhoanRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChiTietTaiKhoanPresenter extends Presenter<ChiTietTaiKhoanContract.View, ChiTietTaiKhoanContract.Interactor> implements ChiTietTaiKhoanContract.Presenter {

    public ChiTietTaiKhoanPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ChiTietTaiKhoanContract.Interactor onCreateInteractor() {
        return new ChiTietTaiKhoanInteractor(this);
    }

    @Override
    public ChiTietTaiKhoanContract.View onCreateView() {
        return ChiTietTaiKhoanFragment.getInstance();
    }


    @Override
    public void ddHuyLienKet(SmartBankRequestCancelLinkRequest request) {
        mView.showProgress();
        mInteractor.ddHuyLienKet(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showOTP();
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void ddXacnhanhuy(SmartBankConfirmCancelLinkRequest request) {
        mView.showProgress();
        mInteractor.ddXacnhanhuy(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.huyLKThanhCong();
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        } else if (simpleResult.getErrorCode().equals("101")) {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.dissOTP();
                        }
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void ddTruyVanSodu(SmartBankInquiryBalanceRequest request) {
        mView.showProgress();
        mInteractor.ddTruyVanSodu(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            SoDuRequest soDuRequest = NetWorkController.getGson().fromJson(simpleResult.getData(), SoDuRequest.class);
                            mView.showSoDu(soDuRequest.getBalanceAmount());
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
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
                            mView.showOTP();
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void ddTaiKhoanMacDinh(TaiKhoanMatDinh request) {
        mView.showProgress();
        mInteractor.ddTaiKhoanMacDinh(request)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.capNhatMacDinh();

                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());

                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void moveToEWallet() {
        new ListBankPresenter(mContainerView).pushView();
//        new EWalletPresenter(mContainerView).pushView();
    }

}
