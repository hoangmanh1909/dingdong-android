package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.detailstatistic;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class DetailStatisticTicketInteractor extends Interactor<DetailStatisticTicketContract.Presenter>
        implements DetailStatisticTicketContract.Interactor {


    public DetailStatisticTicketInteractor(DetailStatisticTicketContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> ddStatisticTicketDetail(STTTicketManagementTotalRequest data) {
        return NetWorkControllerGateWay.ddStatisticTicketDetail(data);
    }

    @Override
    public Single<SimpleResult> getLadingCodeTicket(String ticketMode) {
        return NetWorkControllerGateWay.getDetailTicket(ticketMode);
    }
}
