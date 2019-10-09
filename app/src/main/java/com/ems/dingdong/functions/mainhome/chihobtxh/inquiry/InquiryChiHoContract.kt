package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.response.BankAccountNumber
import com.ems.dingdong.model.response.SeaBankInquiryResponse

/**
 * The InquiryThuHo Contract
 */
 interface InquiryChiHoContract {

    interface Interactor : IInteractor<Presenter>{
        fun seaBankInquiry(seaBankInquiryRequest: SeaBankInquiryRequest, callback: CommonCallback<SeaBankInquiryResponse>)
    }

    interface View : PresentView<Presenter>

    interface Presenter : IPresenter<View, Interactor> {
        fun getListAccount(): List<BankAccountNumber>?
        fun seaBankInquiry(seaBankInquiryRequest: SeaBankInquiryRequest)
        fun getList(): List<BankAccountNumber>?
    }
}



