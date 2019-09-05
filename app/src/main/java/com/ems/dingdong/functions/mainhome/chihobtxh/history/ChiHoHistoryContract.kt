package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.response.IdentifyCationModel
import com.ems.dingdong.model.response.SeaBankHistoryPaymentModel
import com.ems.dingdong.model.response.SeaBankHistoryPaymentResponse

/**
 * The InquiryThuHo Contract
 */
interface ChiHoHistoryContract {

    interface Interactor : IInteractor<Presenter> {
        fun getHistoryPaymentSeaBank(mobileNumber: String?, fromDate: String?, toDate: String?, callback: CommonCallback<SeaBankHistoryPaymentResponse>)
    }

    interface View : PresentView<Presenter> {
        fun showResponseSuccess(data: List<SeaBankHistoryPaymentModel>?)
    }

    interface Presenter : IPresenter<View, Interactor> {
        fun getHistory(mobileNumber: String?, fromDate: String?, toDate: String?)
    }
}



