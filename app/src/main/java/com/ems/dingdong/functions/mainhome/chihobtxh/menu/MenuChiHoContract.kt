package com.ems.dingdong.functions.mainhome.chihobtxh.menu

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView

/**
 * The PhatHang Contract
 */
interface MenuChiHoContract {

    interface Interactor : IInteractor<Presenter>

    interface View : PresentView<Presenter>

    interface Presenter : IPresenter<View, Interactor> {
    }
}



