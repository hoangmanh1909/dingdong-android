package com.vinatti.dingdong.functions.mainhome.phathang.detail;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.functions.mainhome.phathang.detail.receverpersion.ReceverPersonPresenter;
import com.vinatti.dingdong.model.CommonObject;

/**
 * The BaoPhatBangKeDetail Presenter
 */
public class BaoPhatBangKeDetailPresenter extends Presenter<BaoPhatBangKeDetailContract.View, BaoPhatBangKeDetailContract.Interactor>
        implements BaoPhatBangKeDetailContract.Presenter {

    private CommonObject mBaoPhatBangke;

    public BaoPhatBangKeDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatBangKeDetailContract.View onCreateView() {
        return BaoPhatBangKeDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatBangKeDetailContract.Interactor onCreateInteractor() {
        return new BaoPhatBangKeDetailInteractor(this);
    }

    public BaoPhatBangKeDetailPresenter setBaoPhatBangKe(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    @Override
    public CommonObject getBaoPhatBangke() {
        return mBaoPhatBangke;
    }

    @Override
    public void nextReceverPerson() {
        new ReceverPersonPresenter(mContainerView).setBaoPhatBangKe(mBaoPhatBangke).pushView();
    }
}
