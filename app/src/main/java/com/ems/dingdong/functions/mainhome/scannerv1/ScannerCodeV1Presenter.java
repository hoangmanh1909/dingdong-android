package com.ems.dingdong.functions.mainhome.scannerv1;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;

/**
 * The ScannerCode Presenter
 */
public class ScannerCodeV1Presenter extends Presenter<ScannerCodeV1Contract.View, ScannerCodeV1Contract.Interactor>
        implements ScannerCodeV1Contract.Presenter {

    private BarCodeCallback mDelegate;

    public ScannerCodeV1Presenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ScannerCodeV1Contract.View onCreateView() {
        return ScannerCodeV1Fragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ScannerCodeV1Contract.Interactor onCreateInteractor() {
        return new ScannerCodeV1Interactor(this);
    }

    @Override
    public BarCodeCallback getDelegate() {
        return mDelegate;
    }

    public ScannerCodeV1Presenter setDelegate(BarCodeCallback delegate) {
        this.mDelegate = delegate;
        return this;
    }
}
