package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketManagementTotalRequest;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.network.api.ApiService;

import io.reactivex.Observable;
import io.reactivex.Single;

public class StatisticTicketInteractor extends Interactor<StatisticTicketContract.Presenter>
        implements StatisticTicketContract.Interactor {


    public StatisticTicketInteractor(StatisticTicketContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> ddStatisticTicket(STTTicketManagementTotalRequest data) {
        return NetWorkControllerGateWay.ddStatisticTicket(data);
    }

    @Override
    public Observable<SimpleResult> ddTicketDen(TicketManagementTotalRequest loginRequest) {
        return ApiService.ddTicketDen(loginRequest);
    }
}
