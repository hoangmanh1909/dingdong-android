package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di;

import android.content.Context;

import androidx.annotation.NonNull;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic.DetailStatisticTicketPresenter;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketManagementTotalRequest;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.api.ApiService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StatisticTicketPresenter extends Presenter<StatisticTicketContract.View, StatisticTicketContract.Interactor>
        implements StatisticTicketContract.Presenter {


    String code;
    int mType;

    public StatisticTicketPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
    }

    public StatisticTicketPresenter setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public StatisticTicketContract.Interactor onCreateInteractor() {
        return new StatisticTicketInteractor(this);
    }

    @Override
    public StatisticTicketContract.View onCreateView() {
        return StatisticTicketFragment.getInstance();
    }


    @Override
    public void ddGetSubSolution(STTTicketManagementTotalRequest data) {
        mView.showProgress();
        mInteractor.ddStatisticTicket(data)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.hideProgress();

                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            STTTicketManagementTotalRespone[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), STTTicketManagementTotalRespone[].class);
                            List<STTTicketManagementTotalRespone> list1 = Arrays.asList(searchMode);
                            mView.showThanhCong(list1);
                            mView.hideProgress();
                        } else {
                            mView.showThatBai(simpleResult.getMessage());
                            mView.hideProgress();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.showThatBai(e.getMessage());
                    }
                });
    }

    @Override
    public void showStatisticDetail(STTTicketManagementTotalRespone data, int form, int to) {
        new DetailStatisticTicketPresenter(mContainerView).setData(data).setFormDate(form).setToDate(to).setType(data.getListTicketCode()).pushView();
    }

    @Override
    public void ddTicketDen(TicketManagementTotalRequest loginRequest) {
        mView.showProgress();
        mInteractor.ddTicketDen(loginRequest)
                .subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode() != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                STTTicketManagementTotalRespone[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), STTTicketManagementTotalRespone[].class);
                                List<STTTicketManagementTotalRespone> list1 = Arrays.asList(searchMode);
                                mView.showThanhCong(list1);
                                mView.hideProgress();
                            } else {
                                mView.showThatBai(simpleResult.getMessage());
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public StatisticTicketPresenter setType(int status) {
        mType = status;
        return this;
    }

    @Override
    public int getType() {
        return mType;
    }
}
