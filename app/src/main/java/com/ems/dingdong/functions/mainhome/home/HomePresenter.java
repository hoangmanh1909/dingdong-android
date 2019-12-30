package com.ems.dingdong.functions.mainhome.home;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.gomhang.gomnhieu.ListHoanTatNhieuTinPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.statistic.ListStatisticPresenter;
import com.ems.dingdong.functions.mainhome.setting.SettingPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Presenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.listbd13.ListBd13Presenter;

/**
 * The Home Presenter
 */
public class HomePresenter extends Presenter<HomeContract.View, HomeContract.Interactor>
        implements HomeContract.Presenter {

    public HomePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HomeContract.View onCreateView() {
        return HomeFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public HomeContract.Interactor onCreateInteractor() {
        return new HomeInteractor(this);
    }

  /*  @Override
    public void showViewCreateBd13() {
        new CreateBd13Presenter(mContainerView).pushView();
    }*/

    @Override
    public void showViewListCreateBd13() {
        new ListBd13Presenter(mContainerView).pushView();
    }

    @Override
    public void showSetting() {
        new SettingPresenter(mContainerView).pushView();
    }

    @Override
    public void showStatistic() {
        new ListStatisticPresenter(mContainerView).pushView();
    }

    @Override
    public void showHoanTatNhieuTin() {
        new ListHoanTatNhieuTinPresenter(mContainerView).pushView();
    }
}
