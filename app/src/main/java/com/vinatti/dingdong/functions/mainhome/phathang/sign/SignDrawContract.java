package com.vinatti.dingdong.functions.mainhome.phathang.sign;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

/**
 * The SignDraw Contract
 */
interface SignDrawContract {

  interface Interactor extends IInteractor<Presenter> {
  }

  interface View extends PresentView<Presenter> {
  }

  interface Presenter extends IPresenter<View, Interactor> {

    SignDrawPresenter.OnSignChecked getOnSignChecked();

    void adjustScreenOrientation();
  }
}



