package com.ems.dingdong.functions.mainhome.profile.ewallet.lienket;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class LinkBankInteractor extends Interactor<LinkBankContract.Presenter>
        implements LinkBankContract.Interactor {

    public LinkBankInteractor(LinkBankContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> getDDsmartBankConfirmLinkRequest(BaseRequestModel request) {
        return NetWorkController.getDDsmartBankConfirmLinkRequest(request);
    }
}
