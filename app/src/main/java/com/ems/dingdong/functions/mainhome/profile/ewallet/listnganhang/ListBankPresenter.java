package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan.ChiTietTaiKhoanPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.SeabankPresenter;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.response.DanhSachTaiKhoanRespone;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListBankPresenter extends Presenter<ListBankContract.View, ListBankContract.Interactor> implements ListBankContract.Presenter {

    public ListBankPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ListBankContract.Interactor onCreateInteractor() {
        return new ListBankInteractor(this);
    }

    @Override
    public ListBankContract.View onCreateView() {
        return ListBankFragment.getInstance();
    }

    @Override
    public void showEwallet() {
        new EWalletPresenter(mContainerView).pushView();
    }

    @Override
    public void taikhoanthauchi() {
        new SeabankPresenter(mContainerView).pushView();
    }

    @Override
    public void showSeaBank() {
        new ChiTietTaiKhoanPresenter(mContainerView).pushView();
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
                            mView.showThanhCong();
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }
}
