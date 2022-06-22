package com.ems.dingdong.functions.mainhome.profile.ewallet.lienket;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.BaseRequestModel;

public class LinkBankPresenter extends Presenter<LinkBankContract.View, LinkBankContract.Interactor>
        implements LinkBankContract.Presenter {

    public LinkBankPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public LinkBankContract.Interactor onCreateInteractor() {
        return new LinkBankInteractor(this);
    }

    @Override
    public LinkBankContract.View onCreateView() {
        return LinkBankFragment.getInstance();
    }

    @Override
    public void getDDsmartBankConfirmLinkRequest(BaseRequestModel x) {

    }
}
