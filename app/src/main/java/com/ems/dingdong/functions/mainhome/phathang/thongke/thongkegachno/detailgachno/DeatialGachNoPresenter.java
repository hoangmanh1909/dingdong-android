package com.ems.dingdong.functions.mainhome.phathang.thongke.thongkegachno.detailgachno;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.thongke.thongkegachno.GachNoContract;

public class DeatialGachNoPresenter extends Presenter<DeatialGachNoContract.View, DeatialGachNoContract.Interactor>
        implements DeatialGachNoContract.Presenter {

    public DeatialGachNoPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public DeatialGachNoContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public DeatialGachNoContract.View onCreateView() {
        return null;
    }
}
