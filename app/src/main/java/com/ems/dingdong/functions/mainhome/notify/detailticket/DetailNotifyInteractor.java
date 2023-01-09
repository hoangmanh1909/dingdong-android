package com.ems.dingdong.functions.mainhome.notify.detailticket;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.List;

import io.reactivex.Single;

public class DetailNotifyInteractor extends Interactor<DetailNotifyContract.Presenter> implements DetailNotifyContract.Interactor {

    public DetailNotifyInteractor(DetailNotifyContract.Presenter presenter) {
        super(presenter);
    }



    @Override
    public Single<SimpleResult> getListTicket(String ticketMode) {
        return NetWorkControllerGateWay.getDetailTicket(ticketMode);
    }

    @Override
    public Single<SimpleResult> isSeen(List<String> list) {
        return NetWorkControllerGateWay.isSeen(list);
    }
}
