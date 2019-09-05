package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import com.core.base.viper.Interactor
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.response.SeaBankHistoryPaymentResponse
import com.ems.dingdong.network.NetWorkController

/**
 * The InquiryThuHo interactor
 */
internal class ChiHoHistoryInteractor(presenter: ChiHoHistoryContract.Presenter) : Interactor<ChiHoHistoryContract.Presenter>(presenter), ChiHoHistoryContract.Interactor {
    override fun getHistoryPaymentSeaBank(mobileNumber: String?, fromDate: String?, toDate: String?, callback: CommonCallback<SeaBankHistoryPaymentResponse>) {
        NetWorkController.getHistoryPaymentSeaBank(mobileNumber, fromDate, toDate,callback)
    }
}
