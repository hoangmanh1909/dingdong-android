package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.detailstatistic;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyMode;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.StatisticTicketFragment;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.detailstatistic.detailladingcode.DetailLadingCodePresenter;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailStatisticTicketPresenter extends Presenter<DetailStatisticTicketContract.View, DetailStatisticTicketContract.Interactor>
        implements DetailStatisticTicketContract.Presenter {


    String code;
    int form;
    int todate;
    STTTicketManagementTotalRespone data;

    public DetailStatisticTicketPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
    }

    public DetailStatisticTicketPresenter setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public DetailStatisticTicketContract.Interactor onCreateInteractor() {
        return new DetailStatisticTicketInteractor(this);
    }

    @Override
    public DetailStatisticTicketContract.View onCreateView() {
        return DetailStatisticTicketFragment.getInstance();
    }


    @Override
    public void ddStatisticTicketDetail(STTTicketManagementTotalRequest data) {
        mView.showProgress();
        mInteractor.ddStatisticTicketDetail(data)
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
    public STTTicketManagementTotalRespone getData() {
        return data;
    }

    @Override
    public int getFormDate() {
        return form;
    }

    @Override
    public int getToDate() {
        return todate;
    }

    @Override
    public void getDetail(String ticket,String trangjthai) {
        mView.showProgress();
        mInteractor.getLadingCodeTicket(ticket)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.hideProgress();
                        DetailNotifyMode detailNotifyModes = NetWorkController.getGson().fromJson(simpleResult.getData(), DetailNotifyMode.class);
                        new DetailLadingCodePresenter(mContainerView).setCode(trangjthai).setData(detailNotifyModes).pushView();
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    public DetailStatisticTicketPresenter setData(STTTicketManagementTotalRespone data1) {
        data = data1;
        return this;
    }

    public DetailStatisticTicketPresenter setFormDate(int data1) {
        form = data1;
        return this;
    }

    public DetailStatisticTicketPresenter setToDate(int data1) {
        todate = data1;
        return this;
    }
}
