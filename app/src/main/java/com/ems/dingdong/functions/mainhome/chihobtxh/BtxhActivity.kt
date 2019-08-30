package com.ems.dingdong.functions.mainhome.chihobtxh

import com.core.base.viper.ViewFragment
import com.ems.dingdong.base.DingDongActivity
import com.ems.dingdong.functions.mainhome.chihobtxh.check.CheckReferencePresenter

class BtxhActivity : DingDongActivity() {
    override fun onCreateFirstFragment(): ViewFragment<*> {
        return CheckReferencePresenter(this).fragment as ViewFragment<*>
    }
}
