package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

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
}
