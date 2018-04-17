package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.receverpersion;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.sign.SignDrawPresenter;
import com.vinatti.dingdong.model.CommonObject;

/**
 * The ReceverPerson Presenter
 */
public class ReceverPersonPresenter extends Presenter<ReceverPersonContract.View, ReceverPersonContract.Interactor>
        implements ReceverPersonContract.Presenter {

    private CommonObject mBaoPhatBangke;

    public ReceverPersonPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ReceverPersonContract.View onCreateView() {
        return ReceverPersonFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ReceverPersonContract.Interactor onCreateInteractor() {
        return new ReceverPersonInteractor(this);
    }

    public ReceverPersonPresenter setBaoPhatBangKe(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    @Override
    public CommonObject getBaoPhatBangke() {
        return mBaoPhatBangke;
    }

    @Override
    public void nextViewSign() {
        new SignDrawPresenter(mContainerView).setBaoPhatBangKe(mBaoPhatBangke).pushView();
    }
}
