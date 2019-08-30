package com.ems.dingdong.functions.mainhome.chihobtxh.payment

import com.core.base.viper.Interactor
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.response.IdentifyCationResponse
import com.ems.dingdong.network.NetWorkController

/**
 * The PaymentChiHoBtxh interactor
 */
internal class PaymentChiHoBtxhInteractor(presenter: PaymentChiHoBtxhContract.Presenter) : Interactor<PaymentChiHoBtxhContract.Presenter>(presenter), PaymentChiHoBtxhContract.Interactor {
    override fun getIdentifyCation(callback: CommonCallback<IdentifyCationResponse>) {
        NetWorkController.getIdentifyCation(callback)
    }
}
