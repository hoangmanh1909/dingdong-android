package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import com.core.base.viper.Interactor
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.response.SeaBankInquiryResponse
import com.ems.dingdong.network.NetWorkController

/**
 * The InquiryThuHo interactor
 */
internal class InquiryChiHoInteractor(presenter: InquiryChiHoContract.Presenter) : Interactor<InquiryChiHoContract.Presenter>(presenter), InquiryChiHoContract.Interactor {
    override fun seaBankInquiry(seaBankInquiryRequest: SeaBankInquiryRequest, callback: CommonCallback<SeaBankInquiryResponse>) {
        NetWorkController.seaBankInquiry(seaBankInquiryRequest, callback)
    }
}
