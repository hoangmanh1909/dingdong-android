package com.ems.dingdong.functions.mainhome.chihobtxh.payment

import android.app.Activity
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.functions.mainhome.chihobtxh.otp.OtpChiHoBtxhPresenter
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.IdentifyCationModel
import com.ems.dingdong.model.response.IdentifyCationResponse
import com.ems.dingdong.model.response.SeaBankInquiryModel
import com.ems.dingdong.model.response.SeaBankInquiryResponse
import retrofit2.Call
import retrofit2.Response

/**
 * The PaymentChiHoBtxh Presenter
 */
class PaymentChiHoBtxhPresenter(containerView: ContainerView) : Presenter<PaymentChiHoBtxhContract.View, PaymentChiHoBtxhContract.Interactor>(containerView), PaymentChiHoBtxhContract.Presenter {


    var mList: List<IdentifyCationModel>? = null
    var mSeaBankInquiryModel: SeaBankInquiryModel? = null
    override fun onCreateView(): PaymentChiHoBtxhContract.View {
        return PaymentChiHoBtxhFragment.instance
    }

    override fun start() {
        getIdentifyCation()
    }

    override fun onCreateInteractor(): PaymentChiHoBtxhContract.Interactor {
        return PaymentChiHoBtxhInteractor(this)
    }

    private fun getIdentifyCation() {
        mView.showProgress()
        mInteractor.getIdentifyCation(object : CommonCallback<IdentifyCationResponse>(mContainerView as Activity) {
            override fun onSuccess(call: Call<IdentifyCationResponse>, response: Response<IdentifyCationResponse>) {
                super.onSuccess(call, response)
                if ("00" == response.body().errorCode) {
                    mList = response.body().data

                } else {
                    mView.showAlertDialog(response.body().message)
                }
            }

            override fun onError(call: Call<IdentifyCationResponse>, message: String?) {
                super.onError(call, message)
                mView.showAlertDialog(message)
            }
        })
    }

    override fun getList(): List<IdentifyCationModel> {
        return mList!!
    }

    override fun getSeaBankInquiryModel(): SeaBankInquiryModel {
        return mSeaBankInquiryModel!!
    }

    fun setSeaBankInquiryModel(seaBankInquiryModel: SeaBankInquiryModel): PaymentChiHoBtxhPresenter {
        mSeaBankInquiryModel = seaBankInquiryModel
        return this
    }

    override fun toOtp(seaBankPaymentRequest: SeaBankPaymentRequest) {
        OtpChiHoBtxhPresenter(mContainerView).setData(seaBankPaymentRequest, mSeaBankInquiryModel!!).pushView()
    }


}
