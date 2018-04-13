package com.vinatti.dingdong.functions.mainhome.phathang.detail.sign;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.model.XacNhanTin;

/**
 * The SignDraw Contract
 */
interface SignDrawContract {

  interface Interactor extends IInteractor<Presenter> {
  }

  interface View extends PresentView<Presenter> {
  }

  interface Presenter extends IPresenter<View, Interactor> {
    XacNhanTin getBaoPhatBangke();
    SignDrawPresenter.OnSignChecked getOnSignChecked();

    void adjustScreenOrientation();
  }
}



