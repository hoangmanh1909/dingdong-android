package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan.ChiTietTaiKhoanPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.SeabankPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listwallet.ListEWalletPresenter;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.response.DanhSachTaiKhoanRespone;
import com.ems.dingdong.model.response.SmartBankLink;
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
        new ListEWalletPresenter(mContainerView,1).pushView();
    }

    @Override
    public void showBank() {
        new ListEWalletPresenter(mContainerView,2).pushView();
    }


    @Override
    public void showSeaBank(SmartBankLink s) {
        new ChiTietTaiKhoanPresenter(mContainerView).setSmartBankLink(s).pushView();
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
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        } else if (simpleResult.getErrorCode().equals("101"))
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.dissmisOTP();
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
                            mView.showThanhCong();
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void getDDsmartBankConfirmLinkRequest(BaseRequestModel x) {
        mView.showProgress();
        mInteractor.getDDsmartBankConfirmLinkRequest(x)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
//                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.setsmartBankConfirmLink(simpleResult.getData());
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }
}
