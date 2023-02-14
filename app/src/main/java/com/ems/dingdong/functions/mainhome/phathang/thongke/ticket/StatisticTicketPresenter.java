package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.detailstatistic.DetailStatisticTicketPresenter;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class StatisticTicketPresenter extends Presenter<StatisticTicketContract.View, StatisticTicketContract.Interactor>
        implements StatisticTicketContract.Presenter {


    String code;

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
    public void showStatisticDetail(STTTicketManagementTotalRespone data,int form,int to) {
        new DetailStatisticTicketPresenter(mContainerView).setData(data).setFormDate(form).setToDate(to).pushView();
    }
}
