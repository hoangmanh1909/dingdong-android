package com.ems.dingdong.functions.mainhome.phathang.addticket;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class AddTicketInteractor extends Interactor<AddTicketContract.Presenter>
        implements AddTicketContract.Interactor {


    public AddTicketInteractor(AddTicketContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> ddGetSubSolution(String data) {
        return NetWorkControllerGateWay.ddGetSubSolution(data);
    }

    @Override
    public Single<SimpleResult> ddDivCreateTicket(DivCreateTicketMode data) {
        return NetWorkControllerGateWay.ddDivCreateTicket(data);
    }

    @Override
    public void postImage(String pathMedia, CommonCallback<UploadSingleResult> commonCallback) {
        NetWorkController.postImageSingle(pathMedia, commonCallback);
    }
}
