package com.ems.dingdong.functions.mainhome.chihobtxh.check

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.request.BankAccountNumberRequest
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.BankAccountNumberResponse
import com.ems.dingdong.model.response.SeaBankInquiryModel
import com.ems.dingdong.model.response.SeaBankInquiryResponse

/**
 * The CheckReference Contract
 */
public interface CheckReferenceContract {

    interface Interactor : IInteractor<Presenter> {
        fun getBankAccountNumber(bankAccountNumberRequest: BankAccountNumberRequest, callback: CommonCallback<BankAccountNumberResponse>)


        fun BankInquiry(seaBankInquiryRequest: SeaBankInquiryRequest, callback: CommonCallback<SeaBankInquiryResponse>)
    }

    interface View : PresentView<Presenter> {
        fun clearText()

        fun showOTP(seaBankInquiryModel: SeaBankInquiryModel);
    }

    interface Presenter : IPresenter<View, Interactor> {
        fun getBankAccountNumber(bankAccountNumberRequest: BankAccountNumberRequest)

        fun seaBankInquiry(seaBankInquiryRequest: SeaBankInquiryRequest)


        fun otp(seaBankPaymentRequest: SeaBankPaymentRequest);
    }
}



