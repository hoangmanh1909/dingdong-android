package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.detail;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.model.CommonObject;

/**
 * The BaoPhatThanhCongDetail Presenter
 */
public class BaoPhatThanhCongDetailPresenter extends Presenter<BaoPhatThanhCongDetailContract.View, BaoPhatThanhCongDetailContract.Interactor>
        implements BaoPhatThanhCongDetailContract.Presenter {

    private CommonObject mBaoPhatThanhCong;

    public BaoPhatThanhCongDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatThanhCongDetailContract.View onCreateView() {
        return BaoPhatThanhCongDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatThanhCongDetailContract.Interactor onCreateInteractor() {
        return new BaoPhatThanhCongDetailInteractor(this);
    }

    public BaoPhatThanhCongDetailPresenter setBaoPhatThanhCong(CommonObject commonObject) {
        this.mBaoPhatThanhCong = commonObject;
        return this;
    }

    @Override
    public CommonObject getBaoPhatThanhCong() {
        return mBaoPhatThanhCong;
    }
}
