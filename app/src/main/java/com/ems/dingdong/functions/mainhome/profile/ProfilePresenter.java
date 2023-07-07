package com.ems.dingdong.functions.mainhome.profile;

import android.annotation.SuppressLint;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.lichsucuocgoi.tabcall.TabCallPresenter;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.MainMode;
import com.ems.dingdong.functions.mainhome.profile.chat.ChatPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankPresenter;
import com.ems.dingdong.functions.mainhome.profile.tienluong.SalaryPresenter;
import com.ems.dingdong.functions.mainhome.profile.trace_log.TraceLogPresenter;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.utiles.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The Profile Presenter
 */
public class ProfilePresenter extends Presenter<ProfileContract.View, ProfileContract.Interactor>
        implements ProfileContract.Presenter {

    public ProfilePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ProfileContract.View onCreateView() {
        return ProfileFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }


    @Override
    public ProfileContract.Interactor onCreateInteractor() {
        return new ProfileInteractor(this);
    }

    @Override
    public void moveToEWallet() {
        new ListBankPresenter(mContainerView).pushView();
//        new EWalletPresenter(mContainerView).pushView();
    }

    @Override
    public void showLichsuCuocgoi() {
        new TabCallPresenter(mContainerView).pushView();
    }

    @Override
    public void showLuong() {
        new SalaryPresenter(mContainerView).pushView();
    }

    @Override
    public void showChat() {
        new ChatPresenter(mContainerView).pushView();
    }

    @Override
    public void showTraceLog() {
        new TraceLogPresenter(mContainerView).pushView();
    }

    @Override
    public void getCallLog(List<CallLogMode> request) {
        mInteractor.getCallLog(request)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showCallLog(request.size());
                        mView.hideProgress();
                    } else {
                        new IOSDialog.Builder(getViewContext())
                                .setTitle("Thông báo")
                                .setMessage(simpleResult.getMessage())
                                .setNegativeButton("Đóng", null).show();
                        mView.hideProgress();
                    }
                },throwable -> {
                    mView.hideProgress();
                    new ApiDisposable(throwable, getViewContext());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getVaoCa(MainMode request) {
        try {
            mView.showProgress();
            mInteractor.getVaoCa(request)
                    .subscribeOn(Schedulers.io())
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showVaoCa(simpleResult.getData());
                            mView.hideProgress();
                        } else {
                            mView.showError(1);
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.hideProgress();
                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void getRaCa(MainMode request) {
        try {
            mView.showProgress();
            mInteractor.getRaCa(request)
                    .subscribeOn(Schedulers.io())
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showRaCa(simpleResult.getData());
                            mView.hideProgress();
                        } else {
                            mView.showError(2);
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.hideProgress();
                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.getMessage();
        }

    }

}
