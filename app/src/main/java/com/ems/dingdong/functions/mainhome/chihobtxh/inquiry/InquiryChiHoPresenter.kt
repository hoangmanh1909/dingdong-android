package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import android.app.Activity
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.functions.mainhome.chihobtxh.payment.PaymentChiHoBtxhPresenter
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.response.BankAccountNumber
import com.ems.dingdong.model.response.SeaBankInquiryResponse
import retrofit2.Call
import retrofit2.Response

/**
 * The InquiryThuHo Presenter
 */
class InquiryChiHoPresenter(containerView: ContainerView) : Presenter<InquiryChiHoContract.View, InquiryChiHoContract.Interactor>(containerView), InquiryChiHoContract.Presenter {


    var mDataBankAccountNumber: List<BankAccountNumber>? = null
    override fun onCreateView(): InquiryChiHoContract.View {
        return InquiryChiHoFragment.instance
    }

    override fun start() {
        // Start getting data here
    }

    override fun onCreateInteractor(): InquiryChiHoContract.Interactor {
        return InquiryChiHoInteractor(this)
    }

    override fun getListAccount(): List<BankAccountNumber>? {
        return mDataBankAccountNumber
    }

    fun setDataBankAccountNumber(dataBankAccountNumber: List<BankAccountNumber>?): InquiryChiHoPresenter {
        mDataBankAccountNumber = dataBankAccountNumber
        return this
    }

    override fun seaBankInquiry(seaBankInquiryRequest: SeaBankInquiryRequest) {
        mView.showProgress()
        mInteractor.seaBankInquiry(seaBankInquiryRequest, object : CommonCallback<SeaBankInquiryResponse>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SeaBankInquiryResponse>, response: Response<SeaBankInquiryResponse>) {
                super.onSuccess(call, response)
                if ("00" == response.body()!!.errorCode) {
                    PaymentChiHoBtxhPresenter(mContainerView).setSeaBankInquiryModel(response.body()!!.data!!).pushView()
                } else {
                    mView.showAlertDialog(response.body()!!.message)
                }
            }

            override fun onError(call: Call<SeaBankInquiryResponse>, message: String?) {
                super.onError(call, message)
                mView.showAlertDialog(message)
            }
        })
    }

    override fun getList(): List<BankAccountNumber>? {
        return mDataBankAccountNumber
    }
}
