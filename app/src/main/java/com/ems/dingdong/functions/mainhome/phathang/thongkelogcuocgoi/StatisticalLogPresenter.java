package com.ems.dingdong.functions.mainhome.phathang.thongkelogcuocgoi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;

/**
 * The ScannerCode Presenter
 */
public class StatisticalLogPresenter extends Presenter<StatisticalLogContract.View, StatisticalLogContract.Interactor>
        implements StatisticalLogContract.Presenter {


    public StatisticalLogPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public StatisticalLogContract.View onCreateView() {
        return StatisticalLogFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public StatisticalLogContract.Interactor onCreateInteractor() {
        return new StatisticalLogInteractor(this);
    }


}
