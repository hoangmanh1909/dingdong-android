package com.ems.dingdong.functions.mainhome.chihobtxh.payment

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.IdentifyCationModel
import com.ems.dingdong.model.response.IdentifyCationResponse
import com.ems.dingdong.model.response.SeaBankInquiryModel

/**
 * The PaymentChiHoBtxh Contract
 */
interface PaymentChiHoBtxhContract {

    interface Interactor : IInteractor<Presenter> {
        fun getIdentifyCation(callback: CommonCallback<IdentifyCationResponse>)
    }

    interface View : PresentView<Presenter>

    interface Presenter : IPresenter<View, Interactor> {
        fun getList(): List<IdentifyCationModel>
        fun getSeaBankInquiryModel(): SeaBankInquiryModel
        fun toOtp(seaBankPaymentRequest: SeaBankPaymentRequest)
    }
}



