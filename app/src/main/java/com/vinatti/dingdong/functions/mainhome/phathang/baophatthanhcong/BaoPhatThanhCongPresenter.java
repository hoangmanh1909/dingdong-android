package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.scanner.ScannerCodePresenter;

/**
 * The BaoPhatThanhCong Presenter
 */
public class BaoPhatThanhCongPresenter extends Presenter<BaoPhatThanhCongContract.View, BaoPhatThanhCongContract.Interactor>
        implements BaoPhatThanhCongContract.Presenter {

    public BaoPhatThanhCongPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatThanhCongContract.View onCreateView() {
        return BaoPhatThanhCongFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatThanhCongContract.Interactor onCreateInteractor() {
        return new BaoPhatThanhCongInteractor(this);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }
}
