package com.ems.dingdong.network

import android.util.Log
import com.ems.dingdong.BuildConfig
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.*
import com.ems.dingdong.model.request.*
import com.ems.dingdong.model.thauchi.*
import com.ems.dingdong.utiles.Constants
import com.ems.dingdong.utiles.RSAUtil.Companion.signature
import com.ems.dingdong.utiles.Utils
import com.ems.dingdong.utiles.Utils.getUnsafeOkHttpClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object NetWorkControllerGateWay {
    const val TIMEOUT = 120

    @Volatile
    private var apiBuilderVer: VinattiAPI? = null

    @Volatile
    private var apiRxBuilderVer: VinattiAPI? = null


    @JvmStatic
    fun getGson(): Gson = GsonBuilder()
        .setLenient()
        .create()


    fun getApiBuilder(): VinattiAPI {
        if (apiBuilderVer == null) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_DINGDONG_GATEWAY)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient(TIMEOUT, TIMEOUT, "RGluZ0Rvbmc6R2F0ZXdheUAyMTIyIUAj"))
                .build()
            apiBuilderVer = retrofit.create(VinattiAPI::class.java)
        }
        return apiBuilderVer!!
    }


    fun getApiRxBuilder(): VinattiAPI {
        if (apiRxBuilderVer == null) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_DINGDONG_GATEWAY)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient(TIMEOUT, TIMEOUT, "RGluZ0Rvbmc6R2F0ZXdheUAyMTIyIUAj"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            apiRxBuilderVer = retrofit.create(VinattiAPI::class.java)
        }
        return apiRxBuilderVer!!
    }

    @JvmStatic
    fun searchDeliveryStatistic(fromDate: String, toDate: String, status: String, postmanId: String, routeCode: String, callback: CommonCallback<SimpleResult>){
        val request = SearchDeliveryStatisticRequest(fromDate, toDate, status, postmanId, routeCode)
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_DELIVERY, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilder().commonService(requestObject)
        call.enqueue(callback)

    }

    @JvmStatic
    fun statisticDebitGeneral(postmanID: String, fromDate: String, toDate: String, routeCode: String, callback: CommonCallback<SimpleResult>) {
        val debitGeneralRequest = DebitGeneralRequest(postmanID, fromDate, toDate, routeCode)
        val data = getGson().toJson(debitGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_DEBIT_GENERAL, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilder().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun statisticDebitDetail(postmanID: String, fromDate: String, toDate: String, statusCode: String, routeCode: String, callback: CommonCallback<SimpleResult>) {
        val debitDetailRequest = DebitDetailRequest(postmanID, fromDate, toDate, statusCode, routeCode)
        val data = getGson().toJson(debitDetailRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_DEBIT_DETAILL, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilder().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun statisticDeliveryGeneral(postmanID: String, fromDate: String, toDate: String, isSuccess: Boolean, routeCode: String, callback: CommonCallback<SimpleResult>) {
        val deliveryGeneralRequest = DeliveryGeneralRequest(postmanID, fromDate, toDate, isSuccess, routeCode)
        val data = getGson().toJson(deliveryGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID", "", Constants.STT_DELIVERY_GENERAL, data, Utils.getLocalTime(
                Constants.DATE_FORMAT
            ), "", signature
        )
        val call: Call<SimpleResult> = getApiBuilder().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getLadingStatusGeneral(postmanID: String, fromDate: String, toDate: String, ladingType: Int, routeCode: String, callback: CommonCallback<SimpleResult>) {
        val ladingStatusGeneralRequest = LadingStatusGeneralRequest(postmanID, fromDate, toDate, ladingType, routeCode)
        val data = getGson().toJson(ladingStatusGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_LADING_STATUS_GENERAL, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilder().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun findLocation(ladingCode: String, poCode: String): Observable<CommonObjectResult> {
        val trackTraceLadingRequest = TrackTraceLadingRequest(ladingCode, poCode, Utils.SHA256(ladingCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase())
        val data = getGson().toJson(trackTraceLadingRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_TRACK_TRACE_LADING, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().findLocation(requestObject)
    }

    @JvmStatic
    fun searchStatisticCollect(postmanID: String, fromDate: String, toDate: String, callback: CommonCallback<SimpleResult>) {
        val statisticCollectRequest = StatisticCollectRequest(postmanID, fromDate, toDate)
        val data = getGson().toJson(statisticCollectRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_COLLECT, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilder().commonService(requestObject)
        call.enqueue(callback)
    }

    // start e-wallet
    @JvmStatic
    fun linkEWallet(payLinkRequest: PayLinkRequest): Single<LinkEWalletResult> {
        payLinkRequest.setSignature(
            Utils.SHA256(
                payLinkRequest.postmanTel
                        + payLinkRequest.postmanCode + payLinkRequest.getpOCode()
                        + BuildConfig.E_WALLET_SIGNATURE_KEY
            ).toUpperCase(Locale.ROOT)
        )
        val data = getGson().toJson(payLinkRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.EW_PAYLINK_REQUEST, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().linkEWallet(requestObject)
    }

    @JvmStatic
    fun verifyLinkWithOtp(payLinkConfirm: PayLinkConfirm): Single<SimpleResult> {
        payLinkConfirm.setSignature(Utils.SHA256(payLinkConfirm.requestId
                        + payLinkConfirm.otpCode
                        + payLinkConfirm.postmanTel
                        + payLinkConfirm.postmanCode
                        + payLinkConfirm.getpOCode()
                        + BuildConfig.E_WALLET_SIGNATURE_KEY
            ).toUpperCase()
        )
        val data = getGson().toJson(payLinkConfirm)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.EW_PAYLINK_CONFIRM, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun requestPayment(paymentRequestModel: PaymentRequestModel): Single<SimpleResult?>? {
        paymentRequestModel.signature = Utils.SHA256(
            paymentRequestModel.postmanCode
                    + paymentRequestModel.poCode
                    + paymentRequestModel.routeCode
                    + paymentRequestModel.paymentToken
                    + BuildConfig.E_WALLET_SIGNATURE_KEY
        ).toUpperCase()
        val data = getGson().toJson(paymentRequestModel)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.EW_PAYMENT_REQUEST, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun confirmPayment(paymentConfirmModel: PaymentConfirmModel): Single<SimpleResult> {
        paymentConfirmModel.signature = Utils.SHA256(
            paymentConfirmModel.postmanCode
                    + paymentConfirmModel.poCode
                    + paymentConfirmModel.routeCode
                    + paymentConfirmModel.transId
                    + paymentConfirmModel.otpCode
                    + paymentConfirmModel.retRefNumber
                    + paymentConfirmModel.paymentToken
                    + BuildConfig.E_WALLET_SIGNATURE_KEY
        ).toUpperCase()
        val data = getGson().toJson(paymentConfirmModel)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.EW_PAYMENT_CONFIRM, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDataPayment(serviceCode: String?, fromDate: String?, toDate: String?, poCode: String?, routeCode: String?, postmanCode: String?): Single<SimpleResult> {
        val dataPaymentRequest = GetDataPaymentRequest(serviceCode, fromDate, toDate, poCode, routeCode, postmanCode)
        val data = getGson().toJson(dataPaymentRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.EW_GET_DATA_PAYMENT, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getHistoryPayment(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        val requestObject = RequestObject("ANDROID", "", Constants.EW_GET_DATA_HISTORY, dataRequestPayment.data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }
    @JvmStatic
    fun getDataSuccess(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        val requestObject = RequestObject("ANDROID", "", Constants.EW_GET_DATA_SUCCESS, dataRequestPayment.data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun deletePayment(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        Log.e("TAG", "deletePayment: " + dataRequestPayment.data )
        val requestObject = RequestObject("ANDROID", "", Constants.EW_REMOVE_DATA, dataRequestPayment.data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun cancelPayment(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        Log.e("TAG", "deletePayment: " + dataRequestPayment.data )
        val requestObject = RequestObject("ANDROID", "", Constants.EW_CANCEL_DATA, dataRequestPayment.data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDDsmartBankConfirmLinkRequest(request: BaseRequestModel?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_GET_LIST_BANK_LINK, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    }//SMB010

    @JvmStatic
    fun ddTaiKhoanMacDinh(request: TaiKhoanMatDinh?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_DEFAULT_PAYMENT_BANK, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } // SMB009

    @JvmStatic
    fun ddCallOTP(request: CallOTP?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_RESEND_OTP, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } //SMB008

    @JvmStatic
    fun getDanhSachTaiKhoan(danhSachTaiKhoanRequest: DanhSachTaiKhoanRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(danhSachTaiKhoanRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_GET_LIST_ACCOUNT, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } // SMB007

    @JvmStatic
    fun ddTruyVanSodu(request: SmartBankInquiryBalanceRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_INQUIRY_BALANCE, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } // SMB006

    @JvmStatic
    fun SmartBankConfirmCancelLinkRequest(request: SmartBankConfirmCancelLinkRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_CONFIRM_CANCEL_LINK, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } // SMB005

    @JvmStatic
    fun smartBankRequestCancelLinkRequest(request: SmartBankRequestCancelLinkRequest?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_REQUEST_CANCEL_LINK, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } // SMB004

    @JvmStatic
    fun smartBankConfirmLinkRequest(request: SmartBankConfirmLinkRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_CONFIRM_LINK, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } // SMB003

    @JvmStatic
    fun yeuCauLienKet(request: YeuCauLienKetRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_REQUEST_LINK, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } //SMB002

    @JvmStatic
    fun getDanhSachNganHang(): Single<SimpleResult?>? {
        val data = getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_GET_BANK_LIST, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilder().commonServiceRx(requestObject)
    } //SMB001
    // end e-wallet

}