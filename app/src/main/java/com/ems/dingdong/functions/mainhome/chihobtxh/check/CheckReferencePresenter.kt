package com.ems.dingdong.functions.mainhome.chihobtxh.check

import android.app.Activity
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.functions.mainhome.chihobtxh.inquiry.InquiryChiHoPresenter
import com.ems.dingdong.functions.mainhome.chihobtxh.otp.OtpChiHoBtxhPresenter
import com.ems.dingdong.functions.mainhome.chihobtxh.payment.PaymentChiHoBtxhPresenter
import com.ems.dingdong.model.request.BankAccountNumberRequest
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.BankAccountNumberResponse
import com.ems.dingdong.model.response.SeaBankInquiryModel
import com.ems.dingdong.model.response.SeaBankInquiryResponse
import retrofit2.Call
import retrofit2.Response

/**
 * The CheckReference Presenter
 */
class CheckReferencePresenter(containerView: ContainerView) : Presenter<CheckReferenceContract.View, CheckReferenceContract.Interactor>(containerView), CheckReferenceContract.Presenter {


    override fun onCreateView(): CheckReferenceContract.View {
        return CheckReferenceFragment.instance
    }

    override fun start() {
        // Start getting data here
    }

    override fun onCreateInteractor(): CheckReferenceContract.Interactor {
        return CheckReferenceInteractor(this)
    }

    var mSeaBankInquiryModel: SeaBankInquiryModel? = null
    override fun getBankAccountNumber(bankAccountNumberRequest: BankAccountNumberRequest) {
        mView.showProgress()
        mInteractor.getBankAccountNumber(bankAccountNumberRequest, object : CommonCallback<BankAccountNumberResponse>(mContainerView as Activity) {
            override fun onSuccess(call: Call<BankAccountNumberResponse>, response: Response<BankAccountNumberResponse>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if (response.body()?.errorCode == "00") {
                    mView.clearText()
                    InquiryChiHoPresenter(mContainerView).setDataBankAccountNumber(response.body()?.data).pushView()

                } else {
                    mView.showAlertDialog(response.body()?.message)
                }
            }

            override fun onError(call: Call<BankAccountNumberResponse>, message: String) {
                super.onError(call, message)
                mView.hideProgress()
                mView.showAlertDialog(message)
            }
        })
    }

    override fun seaBankInquiry(seaBankInquiryRequest: SeaBankInquiryRequest) {
        mView.showProgress()
        mInteractor.BankInquiry(seaBankInquiryRequest, object : CommonCallback<SeaBankInquiryResponse>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SeaBankInquiryResponse>, response: Response<SeaBankInquiryResponse>) {
                super.onSuccess(call, response)
                if ("00" == response.body()!!.errorCode) {
                    mSeaBankInquiryModel = response.body()!!.data!!
                    mView.showOTP(response.body()!!.data!!)
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

    override fun otp(seaBankPaymentRequest: SeaBankPaymentRequest) {
        OtpChiHoBtxhPresenter(mContainerView).setData(seaBankPaymentRequest, mSeaBankInquiryModel!!,1).pushView()
        mView.clearText()

    }

}
