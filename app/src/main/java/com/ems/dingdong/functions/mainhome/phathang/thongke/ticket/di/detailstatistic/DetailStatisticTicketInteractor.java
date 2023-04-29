package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.network.api.ApiService;

import java.util.List;

import io.reactivex.Observable;
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

    @Override
    public Observable<SimpleResult> ddTicketDen(List<String> strings) {
        return ApiService.ddGetListLadingCoe(strings);
    }
}
