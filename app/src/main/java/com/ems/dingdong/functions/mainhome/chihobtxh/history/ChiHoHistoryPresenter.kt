package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import android.app.Activity
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.functions.mainhome.chihobtxh.payment.PaymentChiHoBtxhPresenter
import com.ems.dingdong.model.response.SeaBankHistoryPaymentResponse
import com.ems.dingdong.model.response.SeaBankInquiryResponse
import retrofit2.Call
import retrofit2.Response

/**
 * The InquiryThuHo Presenter
 */
class ChiHoHistoryPresenter(containerView: ContainerView) : Presenter<ChiHoHistoryContract.View, ChiHoHistoryContract.Interactor>(containerView), ChiHoHistoryContract.Presenter {


    override fun onCreateView(): ChiHoHistoryContract.View {
        return ChiHoHistoryFragment.instance
    }

    override fun start() {
        // Start getting data here
    }

    override fun onCreateInteractor(): ChiHoHistoryContract.Interactor {
        return ChiHoHistoryInteractor(this)
    }

    override fun getHistory(mobileNumber: String?, fromDate: String?, toDate: String?) {
        mView.showProgress()
        mInteractor.getHistoryPaymentSeaBank(mobileNumber, fromDate, toDate, object : CommonCallback<SeaBankHistoryPaymentResponse>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SeaBankHistoryPaymentResponse>, response: Response<SeaBankHistoryPaymentResponse>) {
                super.onSuccess(call, response)
                if ("00" == response.body().errorCode) {
                    mView.showResponseSuccess(response.body().data)
                } else {
                    mView.showAlertDialog(response.body().message)
                }
            }

            override fun onError(call: Call<SeaBankHistoryPaymentResponse>, message: String?) {
                super.onError(call, message)
                mView.showAlertDialog(message)
            }
        })
    }
}
