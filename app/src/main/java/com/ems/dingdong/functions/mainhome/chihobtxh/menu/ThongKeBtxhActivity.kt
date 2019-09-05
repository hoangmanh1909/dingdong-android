package com.ems.dingdong.functions.mainhome.chihobtxh.menu

import com.core.base.viper.ViewFragment
import com.ems.dingdong.base.DingDongActivity
import com.ems.dingdong.functions.mainhome.chihobtxh.check.CheckReferencePresenter
import com.ems.dingdong.functions.mainhome.chihobtxh.inquiry.ChiHoHistoryPresenter

class ThongKeBtxhActivity : DingDongActivity() {
    override fun onCreateFirstFragment(): ViewFragment<*> {
        return ChiHoHistoryPresenter(this).fragment as ViewFragment<*>
    }
}
