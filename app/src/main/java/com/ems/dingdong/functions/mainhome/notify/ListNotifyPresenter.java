package com.ems.dingdong.functions.mainhome.notify;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyPresenter;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListNotifyPresenter extends Presenter<ListNotifyContract.View, ListNotifyContract.Interactor> implements ListNotifyContract.Presenter {

    public ListNotifyPresenter(ContainerView containerView) {
        super(containerView);
    }

    List<TicketNotifyRespone> list1;

    @Override
    public void start() {

    }

    @Override
    public ListNotifyContract.Interactor onCreateInteractor() {
        return new ListNotifyInteractor(this);
    }

    @Override
    public ListNotifyContract.View onCreateView() {
        return ListNotifyFragment.getInstance();
    }

    @Override
    public void getListTicket(TicketNotifyRequest request) {
        mView.showProgress();
        mInteractor.getListTicket(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        TicketNotifyRespone[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), TicketNotifyRespone[].class);
                        List<TicketNotifyRespone> list1 = Arrays.asList(list);
                        this.list1 = list1;
                        mView.hideProgress();
                        mView.showListNotifi(list1);
                    } else {
                        mView.hideProgress();
                        mView.showListNotifi(new ArrayList<>());
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                });
    }

    @Override
    public void showDetail(String ticket) {
        new DetailNotifyPresenter(mContainerView).setCodeTicket(ticket).pushView();
    }

    @Override
    public void isSeen(List<String> list, String ticket, int type) {
        mView.hideProgress();
        mInteractor.isSeen(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.hideProgress();
                        if (list.size() == 1 && type != 1)
                            showDetail(ticket);
                        else {
                            mView.refesht();
                        }
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }
}
