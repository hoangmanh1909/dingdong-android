package com.ems.dingdong.functions.mainhome.address.danhbadichi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.AddressContract;

public class DanhBaDiaChiPresenter extends Presenter<DanhBaDiaChiContract.View, DanhBaDiaChiContract.Interactor>
        implements DanhBaDiaChiContract.Presenter {

    public DanhBaDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public DanhBaDiaChiContract.Interactor onCreateInteractor() {
        return new DanhBaDiaChiInteractor(this);
    }

    @Override
    public DanhBaDiaChiContract.View onCreateView() {
        return DanhBaDiaChiFragment.getInstance();
    }
}
