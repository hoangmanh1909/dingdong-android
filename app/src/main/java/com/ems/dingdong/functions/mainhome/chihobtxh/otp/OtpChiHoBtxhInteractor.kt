package com.ems.dingdong.functions.mainhome.chihobtxh.otp

import com.core.base.viper.Interactor
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.SimpleResult
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.network.NetWorkController

/**
 * The OtpChiHoBtxh interactor
 */
internal class OtpChiHoBtxhInteractor(presenter: OtpChiHoBtxhContract.Presenter) : Interactor<OtpChiHoBtxhContract.Presenter>(presenter), OtpChiHoBtxhContract.Interactor {
    override fun seaBankPayment(seaBankPaymentRequest: SeaBankPaymentRequest, callback: CommonCallback<SimpleResult>) {
        NetWorkController.seaBankPayment(seaBankPaymentRequest,callback)
    }
}
