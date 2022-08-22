package com.ems.dingdong.functions.mainhome.notify;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.List;

import io.reactivex.Single;

public class ListNotifyInteractor extends Interactor<ListNotifyContract.Presenter> implements  ListNotifyContract.Interactor{

    public ListNotifyInteractor(ListNotifyContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getListTicket(TicketNotifyRequest request) {
        return NetWorkControllerGateWay.getListTicket(request);
    }

    @Override
    public Single<SimpleResult> isSeen(List<String> list) {
        return NetWorkControllerGateWay.isSeen(list);
    }
}
