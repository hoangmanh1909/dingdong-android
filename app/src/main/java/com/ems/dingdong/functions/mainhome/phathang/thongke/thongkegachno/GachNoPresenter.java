package com.ems.dingdong.functions.mainhome.phathang.thongke.thongkegachno;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class GachNoPresenter extends Presenter<GachNoContract.View, GachNoContract.Interactor>
        implements GachNoContract.Presenter {

    public GachNoPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public void start() {

    }

    @Override
    public GachNoContract.Interactor onCreateInteractor() {
        return new GachNoInteractor(this);
    }

    @Override
    public GachNoContract.View onCreateView() {
        return GachNoFragment.getInstance();
    }
}
