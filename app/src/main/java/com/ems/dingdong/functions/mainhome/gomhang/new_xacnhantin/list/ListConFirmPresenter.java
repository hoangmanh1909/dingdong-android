package com.ems.dingdong.functions.mainhome.gomhang.new_xacnhantin.list;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;

public class ListConFirmPresenter extends Presenter<ListConFirmContract.View, ListConFirmContract.Interactor>
        implements ListConFirmContract.Presenter {

    public ListConFirmPresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;
    int mTab;

    @Override
    public void start() {

    }

    @Override
    public ListConFirmContract.Interactor onCreateInteractor() {
        return new ListConFirmInteractor(this);
    }

    @Override
    public ListConFirmContract.View onCreateView() {
        return ListConFirmFragment.getInstance();
    }

    public ListConFirmPresenter setType(int type) {
        mType = type;
        return this;
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    public ListConFirmPresenter setTypeTab(int mTab) {
        this.mTab = mTab;
        return this;
    }

    @Override
    public int getTab() {
        return mTab;
    }


}
