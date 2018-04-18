package com.vinatti.dingdong.functions.mainhome.phathang.scanner;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.BarCodeCallback;

/**
 * The ScannerCode Presenter
 */
public class ScannerCodePresenter extends Presenter<ScannerCodeContract.View, ScannerCodeContract.Interactor>
        implements ScannerCodeContract.Presenter {

    private BarCodeCallback mDelegate;

    public ScannerCodePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ScannerCodeContract.View onCreateView() {
        return ScannerCodeFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ScannerCodeContract.Interactor onCreateInteractor() {
        return new ScannerCodeInteractor(this);
    }

    @Override
    public BarCodeCallback getDelegate() {
        return mDelegate;
    }
    public ScannerCodePresenter setDelegate(BarCodeCallback delegate)
    {
        this.mDelegate = delegate;
        return this;
    }
}
