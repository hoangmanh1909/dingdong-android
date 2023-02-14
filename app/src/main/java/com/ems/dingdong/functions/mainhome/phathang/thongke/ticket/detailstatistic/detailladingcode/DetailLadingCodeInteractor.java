package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.detailstatistic.detailladingcode;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class DetailLadingCodeInteractor extends Interactor<DetailLadingCodeContract.Presenter>
        implements DetailLadingCodeContract.Interactor {


    public DetailLadingCodeInteractor(DetailLadingCodeContract.Presenter presenter) {
        super(presenter);
    }


}
