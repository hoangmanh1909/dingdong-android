package com.ems.dingdong.functions.mainhome.chihobtxh

import com.core.base.viper.ViewFragment
import com.ems.dingdong.base.DingDongActivity
import com.ems.dingdong.functions.mainhome.chihobtxh.menu.MenuChiHoPresenter

class BtxhActivity : DingDongActivity() {
    override fun onCreateFirstFragment(): ViewFragment<*> {
        return MenuChiHoPresenter(this).fragment as ViewFragment<*>
    }
}
