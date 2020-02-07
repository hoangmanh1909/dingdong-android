package com.ems.dingdong.functions.mainhome.gomhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.gomnhieu.ListHoanTatNhieuTinPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.statistic.ListStatisticPresenter;

/**
 * The GomHang Presenter
 */
public class GomHangPresenter extends Presenter<GomHangContract.View, GomHangContract.Interactor>
        implements GomHangContract.Presenter {

    public GomHangPresenter(ContainerView containerView) {
        super(containerView);

    }

    @Override
    public GomHangContract.View onCreateView() {
        return GomHangFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public GomHangContract.Interactor onCreateInteractor() {
        return new GomHangInteractor(this);
    }

    @Override
    public void showListStatistic() {
        new ListStatisticPresenter(mContainerView).pushView();
    }

    @Override
    public void showListHoanTatNhieuTin() {
        new ListHoanTatNhieuTinPresenter(mContainerView).pushView();

    }

    @Override
    public void showXacNhanDiaChiPresenter() {
        new XacNhanDiaChiPresenter(mContainerView).pushView();
    }

}
