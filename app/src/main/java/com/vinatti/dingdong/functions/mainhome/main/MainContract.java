package com.vinatti.dingdong.functions.mainhome.main;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.functions.mainhome.gomhang.GomHangPresenter;
import com.vinatti.dingdong.functions.mainhome.home.HomePresenter;
import com.vinatti.dingdong.functions.mainhome.location.LocationPresenter;
import com.vinatti.dingdong.functions.mainhome.phathang.PhatHangPresenter;

/**
 * The Home Contract
 */
interface MainContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        HomePresenter getHomePresenter();

        GomHangPresenter getGomHangPresenter();
        PhatHangPresenter getPhatHangPresenter();

        LocationPresenter getLocationPresenter();
    }
}



