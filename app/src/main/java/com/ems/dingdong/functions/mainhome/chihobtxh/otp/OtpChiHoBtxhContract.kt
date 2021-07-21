package com.ems.dingdong.functions.mainhome.chihobtxh.otp

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.SimpleResult
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.SeaBankInquiryModel

/**
 * The OtpChiHoBtxh Contract
 */
interface OtpChiHoBtxhContract {

    interface Interactor : IInteractor<Presenter> {
        fun seaBankPayment(seaBankPaymentRequest: SeaBankPaymentRequest, callback: CommonCallback<SimpleResult>)
    }

    interface View : PresentView<Presenter>

    interface Presenter : IPresenter<View, Interactor> {
        fun getSeaBankPaymentRequest(): SeaBankPaymentRequest
        fun getSeaBankInquiryModel(): SeaBankInquiryModel
        fun payment(otp: String)

        fun int(): String;
    }
}



