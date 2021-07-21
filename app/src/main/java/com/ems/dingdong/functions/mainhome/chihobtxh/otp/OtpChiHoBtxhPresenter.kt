package com.ems.dingdong.functions.mainhome.chihobtxh.otp

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.ems.dingdong.BuildConfig
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.functions.mainhome.chihobtxh.payment.PaymentChiHoBtxhPresenter
import com.ems.dingdong.model.SimpleResult
import com.ems.dingdong.model.UserInfo
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.SeaBankInquiryModel
import com.ems.dingdong.model.response.SeaBankInquiryResponse
import com.ems.dingdong.network.NetWorkController
import com.ems.dingdong.utiles.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

/**
 * The OtpChiHoBtxh Presenter
 */
class OtpChiHoBtxhPresenter(containerView: ContainerView) : Presenter<OtpChiHoBtxhContract.View, OtpChiHoBtxhContract.Interactor>(containerView), OtpChiHoBtxhContract.Presenter {


    private lateinit var mSeaBankPaymentRequest: SeaBankPaymentRequest
    private lateinit var mSeaBankInquiryModel: SeaBankInquiryModel
    private lateinit var type :String
    override fun onCreateView(): OtpChiHoBtxhContract.View {
        return OtpChiHoBtxhFragment.instance
    }

    override fun start() {
        // Start getting data here
    }

    override fun onCreateInteractor(): OtpChiHoBtxhContract.Interactor {
        return OtpChiHoBtxhInteractor(this)
    }

    fun setData(seaBankPaymentRequest: SeaBankPaymentRequest, seaBankInquiryModel: SeaBankInquiryModel, int: Int): OtpChiHoBtxhPresenter {
        mSeaBankPaymentRequest = seaBankPaymentRequest
        mSeaBankInquiryModel = seaBankInquiryModel
        type = int.toString();
        Log.d("tasdasdad",type);
        return this
    }

    override fun getSeaBankPaymentRequest(): SeaBankPaymentRequest {
        return mSeaBankPaymentRequest
    }

    override fun getSeaBankInquiryModel(): SeaBankInquiryModel {
        return mSeaBankInquiryModel
    }

    override fun payment(otp: String) {
//        mSeaBankPaymentRequest.ProviderACNTCode ="2205"

        mSeaBankPaymentRequest.apply {
            OTPNumber = otp
            Signature = Utils.SHA256(MobileNumber + OTPNumber + BuildConfig.PRIVATE_KEY).toUpperCase(Locale.getDefault())
        }
        mView.showProgress()
        mInteractor.seaBankPayment(mSeaBankPaymentRequest, object : CommonCallback<SimpleResult>(mContainerView as Activity) {
            override fun onSuccess(call: Call<SimpleResult>, response: Response<SimpleResult>) {
                super.onSuccess(call, response)
                if ("00" == response.body()!!.errorCode) {
                    Toast.showToast(mContainerView as Context, response.body()!!.message)
                    back()
                    back()
                    back()
                } else {
                    mView.showAlertDialog(response.body()!!.message)
                }
            }

            override fun onError(call: Call<SimpleResult>, message: String?) {
                super.onError(call, message)
                mView.showAlertDialog(message)
            }
        })
    }

    override fun int(): String {
        return type;
    }


    fun setSeaBankInquiryModel(seaBankInquiryModel: SeaBankInquiryModel): OtpChiHoBtxhPresenter {
        mSeaBankInquiryModel = seaBankInquiryModel
        return this
    }
}
