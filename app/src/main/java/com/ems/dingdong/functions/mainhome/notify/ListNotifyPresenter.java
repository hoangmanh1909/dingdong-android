package com.ems.dingdong.functions.mainhome.notify;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyPresenter;
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.NotiCtelPresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
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

    String mess;
    List<TicketNotifyRespone> list1;

    @Override
    public void start() {
        if (mess != null) {
            String k[] = mess.split(";");
            System.out.print("sadasdasdasda" + k[0] + ";" + k[1]);
            if (k.length == 2) {
                List<String> ticketModes = new ArrayList<>();
                ticketModes.add(k[1]);
                isSeen(ticketModes, k[1], 1);
                new DialogTextThanhConhg(getViewContext(), k[0], new DialogCallback() {
                    @Override
                    public void onResponse(String loginRespone) {
                    }
                }).show();
            } else {
                new DialogTextThanhConhg(getViewContext(), k[0], new DialogCallback() {
                    @Override
                    public void onResponse(String loginRespone) {
                    }
                }).show();
            }
        }
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

    public void showCall(String ticket) {
        new NotiCtelPresenter(mContainerView).setCodeTicket(ticket).pushView();
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
                        if (list.size() == 1 && type == 5) {
                            showCall(ticket);
                        } else if (list.size() == 1 && type != 1)
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

    public ListNotifyPresenter setMess(String mess) {
        this.mess = mess;
        return this;
    }
}
