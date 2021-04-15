package com.ems.dingdong.functions.mainhome.notify.detailticket;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryPresenter;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailNotifyPresenter extends Presenter<DetailNotifyContract.View, DetailNotifyContract.Interactor> implements DetailNotifyContract.Presenter {

    String codeTicket;

    public DetailNotifyPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public DetailNotifyContract.Interactor onCreateInteractor() {
        return new DetailNotifyInteractor(this);
    }

    @Override
    public DetailNotifyContract.View onCreateView() {
        return DetailNotifyFragment.getInstance();
    }

    @Override
    public String setCodeTicket() {
        return codeTicket;
    }

    @Override
    public void getDetail(String ticket) {
        mView.showProgress();
        mInteractor.getListTicket(ticket)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showInfo(NetWorkController.getGson().fromJson(simpleResult.getData(), DetailNotifyMode.class));
                        mView.hideProgress();
                    } else {
                        Toast.showToast(getViewContext(),simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    public DetailNotifyPresenter setCodeTicket(String codeTicket) {
        this.codeTicket = codeTicket;
        return this;
    }

}
