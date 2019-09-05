package com.ems.dingdong.functions.mainhome.chihobtxh.menu

import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView

/**
 * The PhatHang Presenter
 */
class MenuChiHoPresenter(containerView: ContainerView) : Presenter<MenuChiHoContract.View, MenuChiHoContract.Interactor>(containerView), MenuChiHoContract.Presenter {


    override fun onCreateView(): MenuChiHoContract.View {
        return MenuChiHoFragment.instance
    }

    override fun start() {
        // Start getting data here
    }

    override fun onCreateInteractor(): MenuChiHoContract.Interactor {
        return MenuChiHoInteractor(this)
    }

}
