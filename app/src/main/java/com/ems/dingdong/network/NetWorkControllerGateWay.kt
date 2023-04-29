package com.ems.dingdong.network

import android.util.Log
import com.ems.dingdong.BuildConfig
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.TimDiachiModel
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.XoaDiaChiModel
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode
import com.ems.dingdong.functions.mainhome.main.data.MainMode
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RouteTypeRequest
import com.ems.dingdong.model.*
import com.ems.dingdong.model.request.*
import com.ems.dingdong.model.request.vietmap.TravelSales
import com.ems.dingdong.model.request.vietmap.UpdateRequest
import com.ems.dingdong.model.response.DeliveryPostmanResponse
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse
import com.ems.dingdong.model.response.PaymentRequestResponse
import com.ems.dingdong.model.response.VerifyAddressRespone
import com.ems.dingdong.model.thauchi.*
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest
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
    const val TIMEOUT = 180

    @Volatile
    private var apiBuilderVer: VinattiAPI? = null

    @Volatile
    private var apiRxBuilderVer: VinattiAPI? = null


    @JvmStatic
    fun getGson(): Gson = GsonBuilder().setLenient().create()


    fun getApiBuilderVer(): VinattiAPI {
        if (apiBuilderVer == null) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_DINGDONG_GATEWAY)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient(TIMEOUT, TIMEOUT, "RGluZ0Rvbmc6R2F0ZXdheUAyMTIyIUAj"))
                .build()
            apiBuilderVer = retrofit.create(VinattiAPI::class.java)
        }
        return apiBuilderVer!!
    }


    fun getApiRxBuilderVer(): VinattiAPI {
        if (apiRxBuilderVer == null) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_DINGDONG_GATEWAY)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient(TIMEOUT, TIMEOUT, "RGluZ0Rvbmc6R2F0ZXdheUAyMTIyIUAj"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            apiRxBuilderVer = retrofit.create(VinattiAPI::class.java)
        }
        return apiRxBuilderVer!!
    }

    // start thống kê
    @JvmStatic
    fun searchDeliveryStatistic(
        fromDate: String,
        toDate: String,
        status: String,
        postmanId: String,
        routeCode: String,
        callback: CommonCallback<CommonObjectListResult>
    ) {
        val request = SearchDeliveryStatisticRequest(fromDate, toDate, status, postmanId, routeCode)
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_DELIVERY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<CommonObjectListResult> =
            getApiBuilderVer().commonServiceCommonObjectListResult(requestObject)
        call.enqueue(callback)

    }

    @JvmStatic
    fun statisticDebitGeneral(
        postmanID: String,
        fromDate: String,
        toDate: String,
        routeCode: String,
        callback: CommonCallback<SimpleResult>
    ) {
        val debitGeneralRequest = DebitGeneralRequest(postmanID, fromDate, toDate, routeCode)
        val data = getGson().toJson(debitGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_DEBIT_GENERAL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun statisticDebitDetail(
        postmanID: String,
        fromDate: String,
        toDate: String,
        statusCode: String,
        routeCode: String,
        callback: CommonCallback<SimpleResult>
    ) {
        val debitDetailRequest =
            DebitDetailRequest(postmanID, fromDate, toDate, statusCode, routeCode)
        val data = getGson().toJson(debitDetailRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_DEBIT_DETAILL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun statisticDeliveryGeneral(
        postmanID: String,
        fromDate: String,
        toDate: String,
        isSuccess: Boolean,
        routeCode: String,
        callback: CommonCallback<StatisticDeliveryGeneralResult>
    ) {
        val deliveryGeneralRequest =
            DeliveryGeneralRequest(postmanID, fromDate, toDate, isSuccess, routeCode)
        val data = getGson().toJson(deliveryGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID", "", Constants.STT_DELIVERY_GENERAL, data, Utils.getLocalTime(
                Constants.DATE_FORMAT
            ), "", signature
        )
        val call: Call<StatisticDeliveryGeneralResult> =
            getApiBuilderVer().commonServiceStatisticDeliveryGeneralResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getLadingStatusGeneral(
        postmanID: String,
        fromDate: String,
        toDate: String,
        ladingType: Int,
        routeCode: String,
        callback: CommonCallback<StatisticDeliveryGeneralResult>
    ) {
        val ladingStatusGeneralRequest =
            LadingStatusGeneralRequest(postmanID, fromDate, toDate, ladingType, routeCode)
        val data = NetWorkController.getGson().toJson(ladingStatusGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_LADING_STATUS_GENERAL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<StatisticDeliveryGeneralResult> =
            getApiBuilderVer().commonServiceStatisticDeliveryGeneralResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getBalance(
        mobileNumber: String, postmanId: String, callback: CommonCallback<SimpleResult>
    ) {
        val validationRequest = BalanceRequest(mobileNumber, postmanId)
        val data = getGson().toJson(validationRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_POSTMAN_GET_BALANCE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun ddGetBalance(request: BalanceModel, callback: CommonCallback<SimpleResult>) {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_POSTMAN_GET_BALANCE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getHomeDataCommonService(
        fromData: String,
        toDate: String,
        postmanCode: String,
        routeCode: String,
        funcRequest: String,
        callback: CommonCallback<SimpleResult>
    ) {
        val mainViewRequest = MainViewRequest(fromData, toDate, postmanCode, routeCode)
        val data = getGson().toJson(mainViewRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            funcRequest,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getListTicket(request: TicketNotifyRequest?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_GET_TICKET_NOTIFY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun statisticDeliveryDetail(
        serviceCode: String,
        typeDelivery: Int,
        postmanID: String,
        fromDate: String,
        toDate: String,
        isSuccess: Boolean,
        routeCode: String,
        callback: CommonCallback<SimpleResult>
    ) {
        val deliveryDetailRequest = DeliveryDetailRequest(
            serviceCode, typeDelivery, postmanID, fromDate, toDate, isSuccess, routeCode
        )
        val data = getGson().toJson(deliveryDetailRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_DELIVERY_DETAILL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getLadingStatusDetail(
        type: Int,
        serviceCode: String?,
        postmanID: String?,
        fromDate: String?,
        toDate: String?,
        ladingType: Int,
        routeCode: String?,
        callback: CommonCallback<SimpleResult>
    ) {
        val ladingStatusDetailRequest = LadingStatusDetailRequest(
            type, serviceCode, postmanID, fromDate, toDate, ladingType, routeCode
        )
        val data = getGson().toJson(ladingStatusDetailRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_LADING_STATUS_DETAIL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun statisticPayment(
        postmanId: String, poCode: String, phoneNumber: String, fromDate: String, toDate: String
    ): Single<StatisticPaymentResult> {
        val request = StatisticPaymentRequest(postmanId, poCode, phoneNumber, fromDate, toDate)
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_PAYMENT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
//        val call: Call<StatisticPaymentResult> = getApiBuilderVer().getBalance(requestObject)
//        call.enqueue(callback)
        return getApiRxBuilderVer().getBalance(requestObject)
    }

//    @JvmStatic
//    fun getListTicket(request: TicketNotifyRequest?): Single<SimpleResult> {
//        val data = getGson().toJson(request)
//        val signature = signature(data)
//        val requestObject = RequestObject(
//            "ANDROID", "", Constants.STT_GET_TICKET_NOTIFY, data,
//            Utils.getLocalTime(Constants.DATE_FORMAT), "", signature
//        )
//        return getApiRxBuilderVer().commonServiceRx(requestObject)
//    }

    @JvmStatic
    fun isSeen(request: List<String>): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_GET_TICKET_NOTIFY_UPDATE_SEEN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDetailTicket(ticketCode: String): Single<SimpleResult> {
        val seenRequest = STTTicketNotifyUpdateSeenRequest(ticketCode)
        val data = NetWorkController.getGson().toJson(seenRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_GET_TICKET_NOTIFY_BY_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun cancelDeliveryStatistic(request: CancelDeliveryStatisticRequest): Observable<CancelDeliveryResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_LADING_CANCEL_DELIVERY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().cancelDeliveryStatistic(requestObject)
    }
    //end thống kê

    @JvmStatic
    fun findLocation(ladingCode: String, poCode: String): Single<SimpleResult> {
        val trackTraceLadingRequest = TrackTraceLadingRequest(
            ladingCode,
            poCode,
            Utils.SHA256(ladingCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase()
        )
        val data = getGson().toJson(trackTraceLadingRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_TRACK_TRACE_LADING,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().findLocation(requestObject)
    }

    @JvmStatic
    fun searchStatisticCollect(
        postmanID: String, fromDate: String, toDate: String, callback: CommonCallback<SimpleResult>
    ) {
        val statisticCollectRequest = StatisticCollectRequest(postmanID, fromDate, toDate)
        val data = NetWorkController.getGson().toJson(statisticCollectRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_COLLECT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    // start e-wallet
    @JvmStatic
    fun linkEWallet(payLinkRequest: PayLinkRequest): Single<LinkEWalletResult> {
        payLinkRequest.setSignature(
            Utils.SHA256(
                payLinkRequest.postmanTel + payLinkRequest.postmanCode + payLinkRequest.getpOCode() + BuildConfig.E_WALLET_SIGNATURE_KEY
            ).toUpperCase(Locale.ROOT)
        )
        val data = getGson().toJson(payLinkRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_PAYLINK_REQUEST,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().linkEWallet(requestObject)
    }

    @JvmStatic
    fun verifyLinkWithOtp(payLinkConfirm: PayLinkConfirm): Single<VerifyLinkOtpResult> {
        payLinkConfirm.setSignature(
            Utils.SHA256(
                payLinkConfirm.requestId + payLinkConfirm.otpCode + payLinkConfirm.postmanTel + payLinkConfirm.postmanCode + payLinkConfirm.getpOCode() + BuildConfig.E_WALLET_SIGNATURE_KEY
            ).toUpperCase()
        )
        val data = getGson().toJson(payLinkConfirm)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_PAYLINK_CONFIRM,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().verifyLinkWithOtp(requestObject)
    }

    @JvmStatic
    fun requestPayment(paymentRequestModel: PaymentRequestModel): Single<PaymentRequestResponse> {
        paymentRequestModel.signature = Utils.SHA256(
            paymentRequestModel.postmanCode + paymentRequestModel.poCode + paymentRequestModel.routeCode + paymentRequestModel.paymentToken + BuildConfig.E_WALLET_SIGNATURE_KEY
        ).toUpperCase()
        val data = getGson().toJson(paymentRequestModel)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_PAYMENT_REQUEST,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().paymentRequest(requestObject)
    }

    @JvmStatic
    fun confirmPayment(paymentConfirmModel: PaymentConfirmModel): Single<SimpleResult> {
        paymentConfirmModel.signature = Utils.SHA256(
            paymentConfirmModel.postmanCode + paymentConfirmModel.poCode + paymentConfirmModel.routeCode + paymentConfirmModel.transId + paymentConfirmModel.otpCode + paymentConfirmModel.retRefNumber + paymentConfirmModel.paymentToken + BuildConfig.E_WALLET_SIGNATURE_KEY
        ).toUpperCase()
        val data = getGson().toJson(paymentConfirmModel)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_PAYMENT_CONFIRM,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDataPayment(
        serviceCode: String?,
        fromDate: String?,
        toDate: String?,
        poCode: String?,
        routeCode: String?,
        postmanCode: String?
    ): Single<EWalletDataResult> {
        val dataPaymentRequest =
            GetDataPaymentRequest(serviceCode, fromDate, toDate, poCode, routeCode, postmanCode)
        val data = getGson().toJson(dataPaymentRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_GET_DATA_PAYMENT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().getDataPayment(requestObject)
    }

    @JvmStatic
    fun getHistoryPayment(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_GET_DATA_HISTORY,
            dataRequestPayment.data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDataSuccess(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_GET_DATA_SUCCESS,
            dataRequestPayment.data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun deletePayment(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_REMOVE_DATA,
            dataRequestPayment.data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun cancelPayment(dataRequestPayment: DataRequestPayment): Single<SimpleResult> {
        val signature = signature(dataRequestPayment.data)
        Log.e("TAG", "deletePayment: " + dataRequestPayment.data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_CANCEL_DATA,
            dataRequestPayment.data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDDsmartBankConfirmLinkRequest(request: BaseRequestModel?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_LIST_BANK_LINK,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }//SMB010

    @JvmStatic
    fun ddTaiKhoanMacDinh(request: TaiKhoanMatDinh?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DEFAULT_PAYMENT_BANK,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } // SMB009

    @JvmStatic
    fun ddCallOTP(request: CallOTP?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_RESEND_OTP,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } //SMB008

    @JvmStatic
    fun getDanhSachTaiKhoan(danhSachTaiKhoanRequest: DanhSachTaiKhoanRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(danhSachTaiKhoanRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_LIST_ACCOUNT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } // SMB007

    @JvmStatic
    fun ddTruyVanSodu(request: SmartBankInquiryBalanceRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_INQUIRY_BALANCE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } // SMB006

    @JvmStatic
    fun SmartBankConfirmCancelLinkRequest(request: SmartBankConfirmCancelLinkRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CONFIRM_CANCEL_LINK,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } // SMB005

    @JvmStatic
    fun smartBankRequestCancelLinkRequest(request: SmartBankRequestCancelLinkRequest?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_REQUEST_CANCEL_LINK,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } // SMB004

    @JvmStatic
    fun smartBankConfirmLinkRequest(request: SmartBankConfirmLinkRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CONFIRM_LINK,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } // SMB003

    @JvmStatic
    fun yeuCauLienKet(request: YeuCauLienKetRequest?): Single<SimpleResult?>? {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_REQUEST_LINK,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } //SMB002

    @JvmStatic
    fun getDanhSachNganHang(): Single<SimpleResult> {
        val data = getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_BANK_LIST,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    } //SMB001

    @JvmStatic
    fun huyLienKetVi(request: SmartBankRequestCancelLinkRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_CANCEL_LINK,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getHistory(request: LinkHistory): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_LINK_HISTORY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun SetDefaultPayment(request: LinkHistory): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.EW_DEFAULT_PAYMENT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }
    // end e-wallet

    // start thu gom

    @JvmStatic
    fun searchOrderPostmanCollect(
        orderPostmanID: String?,
        orderID: String?,
        postmanID: String?,
        status: String?,
        fromAssignDate: String?,
        toAssignDate: String?,
        callback: CommonCallback<SimpleResult>?
    ) {
        val searchOrderPostmanCollectRequest = SearchOrderPostmanCollectRequest(
            orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate
        )
        val data = NetWorkController.getGson().toJson(searchOrderPostmanCollectRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_SEARCH_ORDER_POSTMAN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun confirmOrderPostmanCollect(
        orderPostmanID: String,
        employeeID: String,
        statusCode: String,
        confirmReason: String,
        callback: CommonCallback<SimpleResult>
    ) {
        val collectRequest = ConfirmOrderPostmanCollectRequest(
            orderPostmanID, employeeID, statusCode, confirmReason, "DD_ANDROID"
        )
        val data = NetWorkController.getGson().toJson(collectRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_CONFIRM_ORDER_POSTMAN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun orderChangeRoute(request: OrderChangeRouteInsertRequest?): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ORDER_CHANGE_ROUTE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun confirmAllOrderPostman(
        request: ArrayList<ConfirmOrderPostman>, callback: CommonCallback<SimpleResult>
    ) {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_CONFIRM_ALL_ORDER_POSTMAN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun collectOrderPostmanCollect(
        hoanTatTinRequest: HoanTatTinRequest, callback: CommonCallback<SimpleResult>?
    ) {
        val data = NetWorkController.getGson().toJson(hoanTatTinRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_COLLECT_ORDER_POSTMAN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun searchAllOrderPostmanCollect(
        orderPostmanID: String?,
        orderID: String?,
        postmanID: String?,
        status: String?,
        fromAssignDate: String?,
        toAssignDate: String?,
        callback: CommonCallback<SimpleResult>?
    ) {
        val searchOrderPostmanCollectRequest = SearchOrderPostmanCollectRequest(
            orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate
        )
        val data = NetWorkController.getGson().toJson(searchOrderPostmanCollectRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_SEARCH_ORDER_POSTMAN_COLLECT_ALL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun themTin(request: CreateOrderRequest?): Single<SimpleResult> {
        val dataRequestPayment = DataRequestPayment()
//        dataRequestPayment.data = NetWorkController.getGson().toJson(request)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_CREATE_ORDER,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun searchdaichi(request: PUGetBusinessProfileRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_BP_GET_BY_ID,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun search(request: PUGetBusinessProfileRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_BP_GET_CHILD_BY_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getTinhThanhPho(request: BaseRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_GET_PROVINCES,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDanhMucHCC(request: BaseRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DIC_GET_RETURN_GROUP_PA,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getNhomDanhMucHCC(request: String): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(request)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DIC_GET_RETURN_PA,
            request,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getQuanHuyen(request: BaseRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_GET_DISTRICTS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getXaPhuong(request: BaseRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_GET_WARDS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getVMSEARCHV2(request: TimDiachiModel): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_SEARCH_V2,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getListContractAdress(request: String): Single<SimpleResult> {
        val data = request
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ROUTE_ADDRESS_BOOK_GET_BY_ROUTE_ID,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getDetail(request: String): Single<SimpleResult> {
        val data = request
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ROUTE_ADDRESS_BOOK_GET_BY_ID,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getVMPLACE(request: String): Single<SimpleResult> {
        val data = request
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_PLACE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getStresss(request: String): Single<SimpleResult> {
        val data = request
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_GET_WARD_BY_PORTAL_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getThemDiaChi(request: DICRouteAddressBookCreateRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ROUTE_ADDRESS_BOOK_CREATE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getCapNhatDiachi(request: DICRouteAddressBookCreateRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ROUTE_ADDRESS_BOOK_UPDATE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getXoaDiaChi(request: XoaDiaChiModel): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ROUTE_ADDRESS_BOOK_DELETE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getReasonsHoanTat(callback: CommonCallback<SimpleResult>) {
        val data = NetWorkController.getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_GET_PICKUP_REASON,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getPostmanByRoute(
        poCode: String?, routeId: Int, routeType: String?, callback: CommonCallback<SimpleResult>?
    ) {
        val getRouteRequest = GetPostmanByRouteRequest(poCode, routeId, routeType)
        val data = NetWorkController.getGson().toJson(getRouteRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_POSTMAN_BY_ROUTE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun approveOrder(request: OrderChangeRouteRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ORDER_CHANGE_ROUTE_ACCEPT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun cancelOrder(request: OrderChangeRouteRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ORDER_CHANGE_ROUTE_CANCEL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getChangeRouteOrder(request: OrderChangeRouteDingDongManagementRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ORDER_CHANGE_ROUTE_MANAGE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun rejectOrder(request: OrderChangeRouteRequest): Single<SimpleResult> {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_ORDER_CHANGE_ROUTE_REJECT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun collectAllOrderPostman(
        list: List<HoanTatTinRequest?>?, callback: CommonCallback<SimpleResult>?
    ) {
        val data = NetWorkController.getGson().toJson(list)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_COLLECT_ALL_ORDER_POSTMAN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getReasonsHoanTatMiss(callback: CommonCallback<SimpleResult>) {
        val data = NetWorkController.getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_GET_CANCEL_ORDER_REASON,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun searchForCancel(
        ladingCode: String?,
        fromDate: String?,
        toDate: String?,
        postmanId: String?,
        routeId: String?,
        poCode: String?,
        statusCode: String?,
        fromRouteId: Int?,
        callback: CommonCallback<SimpleResult>?
    ) {
        val request = SearchForCancelRequest(
            ladingCode, fromDate, toDate, postmanId, routeId, poCode, statusCode, fromRouteId
        )
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_OCR_SEARCH_FOR_CANCEL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getRouteLadingDetail(ladingCode: String?, callback: CommonCallback<SimpleResult>?) {
        val request = BaseRequest(null, null, ladingCode, null)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_OCR_GET_DETAIL_BY_LADDING_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getListBuuCucHuyen(poCode: String?): Single<SimpleResult> {
        val request = BaseRequest(null, null, null, poCode)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_GET_POS_IN_DISTRICT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun searchForApproved(
        ladingCode: String?,
        fromDate: String?,
        toDate: String?,
        postmanId: String?,
        routeId: String?,
        poCode: String?,
        statusCode: String?,
        fromRouteId: Int?,
        callback: CommonCallback<SimpleResult>?
    ) {
        val request = SearchForApprovedRequest(
            ladingCode, fromDate, toDate, postmanId, routeId, poCode, statusCode, fromRouteId
        )
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_OCR_SEARCH_FOR_APPROVE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }
    // end thu gome

    //start  phát hàng

    @JvmStatic
    fun ddGetDanhSachBapPhat(
        postmanID: String?, fromDate: String?, toDate: String?, routeCode: String?, searchTpe: Int?
    ): Single<SimpleResult> {
        val deliveryPostmanRequest =
            DeliveryPostmanRequest(postmanID, fromDate, toDate, routeCode, searchTpe)
        val data = getGson().toJson(deliveryPostmanRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_POSTMAN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun searchDeliveryPostman(
        postmanID: String?,
        fromDate: String?,
        toDate: String?,
        routeCode: String?,
        searchTpe: Int?,
        callback: CommonCallback<DeliveryPostmanResponse>
    ): Call<DeliveryPostmanResponse> {
        val deliveryPostmanRequest =
            DeliveryPostmanRequest(postmanID, fromDate, toDate, routeCode, searchTpe)
        val data = getGson().toJson(deliveryPostmanRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_POSTMAN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<DeliveryPostmanResponse> =
            getApiBuilderVer().commonServiceDeliveryPostman(requestObject)
        call.enqueue(callback)
        return call
    }

    @JvmStatic
    fun paymentDelivery(request: PaymentDeviveryRequest?, callback: CommonCallback<SimpleResult>) {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_PAYMENT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun paymentDelivery(request: PaymentDeviveryRequest?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_PAYMENT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun paymentV2(request: DeliveryPaymentV2?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_PAYMENT_V2,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun checkDeliverySuccess(request: DeliverySuccessRequest?): Single<DeliveryCheckAmountPaymentResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_SUCCESS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRxDeliverySuccess(requestObject)
    }

    @JvmStatic
    fun checkAmountPayment(request: List<PaypostPaymentRequest>): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CHECK_AMOUNT_PAYMENT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun pushToPNSDeliveryUnSuccess(
        request: DeliveryUnSuccessRequest?, callback: CommonCallback<SimpleResult>
    ) {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_UN_SUCCESS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun deliveryPartial(request: DeliveryProductRequest?, callback: CommonCallback<SimpleResult>?) {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_PARTIAL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getReasons(callback: CommonCallback<SimpleResult>) {
        val data = getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_REASONS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getDeliveryRoute(poCode: String?, callback: CommonCallback<RouteInfoResult>) {
        val deliveryRouteRequest = DeliveryRouteRequest(poCode)
        val data = getGson().toJson(deliveryRouteRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_ROUTE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<RouteInfoResult> =
            getApiBuilderVer().commonServiceRouteInfoResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getRoute(poCode: String?, callback: CommonCallback<RouteInfoResult>) {
        val getRouteRequest = GetRouteRequest(poCode, "P")
        val data = getGson().toJson(getRouteRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DELIVERY_ROUTE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<RouteInfoResult> =
            getApiBuilderVer().commonServiceRouteInfoResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getSolutionByReasonCode(reasonCode: String?, callback: CommonCallback<SolutionResult>) {
        val reasonCodeRequest = GetSolutionByReasonCodeRequest(reasonCode)
        val data = getGson().toJson(reasonCodeRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DLV_GET_SOLUTIONS_BY_REASON,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SolutionResult> =
            getApiBuilderVer().commonServiceSolutionResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getSolutions(callback: CommonCallback<SimpleResult>) {
        val data = getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_SOLUTIONS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun cancelDivided(
        request: List<DingDongCancelDividedRequest>, callback: CommonCallback<SimpleResult>
    ) {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CANCEL_DIVIDED,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun addNewBD13(bd13Create: Bd13Create?, callback: CommonCallback<SimpleResult>) {
        val data = getGson().toJson(bd13Create)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CREATE_BD13,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun searchCreateBd13(
        deliveryPOCode: String?,
        routePOCode: String?,
        bagNumber: String?,
        chuyenThu: String?,
        createDate: String?,
        shift: String?,
        commonCallback: CommonCallback<SimpleResult>
    ) {
        val searchCreateBd13Request = SearchCreateBd13Request(
            deliveryPOCode, routePOCode, bagNumber, chuyenThu, createDate, shift
        )
        val data = getGson().toJson(searchCreateBd13Request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CREATE_SEARCH_BD13,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(commonCallback)
    }


    @JvmStatic
    fun getCancelDelivery(
        postmanCode: String?,
        routeCode: String?,
        fromDate: String?,
        toDate: String?,
        ladingCode: String?,
        callback: CommonCallback<DingDongGetCancelDeliveryResponse>
    ) {
        val getCancelDeliveryRequest =
            GetCancelDeliveryRequest(postmanCode, routeCode, fromDate, toDate)
        val data = getGson().toJson(getCancelDeliveryRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_CANCEL_DELIVERY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<DingDongGetCancelDeliveryResponse> =
            getApiBuilderVer().commonServiceDingDongGetCancelDeliveryResponse(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun cancelDelivery(
        request: DingDongCancelDeliveryRequest?, callback: CommonCallback<SimpleResult>
    ) {
        val data = getGson().toJson(request)
        val signature = signature(data)
        Log.e("TAG", "cancelDelivery: $data")
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CANCEL_DELIVERY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun updateMobile(
        code: String?,
        type: String?,
        mobileNumber: String?,
        commonCallback: CommonCallback<SimpleResult>?
    ): Call<SimpleResult>? {
        val updateMobileRequest = UpdateMobileRequest(code, type, mobileNumber)
        val data = getGson().toJson(updateMobileRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_UPDATE_MOBILE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(commonCallback)
        return call
    }

    @JvmStatic
    fun searchTu(request: String?): Single<SimpleResult> {
        val dataRequestPayment = DataRequestPayment()
        dataRequestPayment.data = request
        val data = getGson().toJson(dataRequestPayment)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_SML_GET_HUB_BY_POCODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun phatSml(request: SMLRequest?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_SML_DELIVERY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun searchLadingCreatedBd13(
        objRequest: DingDongGetLadingCreateBD13Request?
    ): Single<DeliveryPostmanResponse> {
        val data = getGson().toJson(objRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_LADING_BD13,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
//        val call: Call<DeliveryPostmanResponse> =
//            getApiBuilderVer().commonServiceDeliveryPostmanResponse(requestObject)
        return getApiRxBuilderVer().commonServiceDeliveryPostmanResponse(requestObject)
    }

    @JvmStatic
    fun huySml(request: SMLRequest?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_SML_DELIVERY_CANCEL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun statisticSMLDeliveryFail(request: StatisticSMLDeliveryFailRequest?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_SML_DELIVERY_STATISTIC,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun searchChuaPhanHuong(request: SearchMode?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CREATE_SEARCH_BD13,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun comfirmCreate(request: ComfrimCreateMode?): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CREATE_CONFIRM_BD13,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getPostmanShift(poCode: String?, callback: CommonCallback<SimpleResult>) {
        val baseRequest = BaseRequest(null, null, null, poCode)
        val data = getGson().toJson(baseRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_DIC_GET_POSTMAN_SHIFT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun approvedAgree(
        id: String?,
        ladingCode: String?,
        postmanId: String?,
        postmanCode: String?,
        poCode: String?,
        routeId: String?,
        routeCode: String?,
        callback: CommonCallback<SimpleResult>
    ) {
        val request =
            ApprovedAgreeRequest(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode)
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID", "", Constants.DLV_CHANGE_ROUTE_APPROVE, data, Utils.getLocalTime(
                Constants.DATE_FORMAT
            ), "", signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun approvedDisagree(
        id: String?,
        ladingCode: String?,
        postmanId: String?,
        postmanCode: String?,
        poCode: String?,
        routeId: String?,
        routeCode: String?,
        callback: CommonCallback<SimpleResult>
    ) {
        val request =
            ApprovedAgreeRequest(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode)
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CHANGE_ROUTE_DISAGREE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun saveToaDoPhat(request: List<ReceiverVpostcodeMode>): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_RECEIVER_VPOST_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun saveToaDoGom(request: List<SenderVpostcodeMode>): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_SENDER_VPOST_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getHistoryDelivery(parcelCode: String, callback: CommonCallback<CommonObjectListResult>) {
        val request = HistoryDeliveryRequest(parcelCode)
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_LADDING_JOURNEY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<CommonObjectListResult> =
            getApiBuilderVer().commonServiceCommonObjectListResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun pushToPNSDelivery(request: PushToPnsRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_PUSH_TO_PNS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun pushToPNSDelivery(request: PushToPnsRequest, callback: CommonCallback<SimpleResult>) {

        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_PUSH_TO_PNS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun callForwardCallCenter(
        callerNumber: String,
        calleeNumber: String,
        callForwardType: String,
        hotlineNumber: String,
        ladingCode: String,
        PostmanId: String,
        POCode: String,
        callback: CommonCallback<SimpleResult>
    ): Call<SimpleResult> {
        val request = CallForwardCallCenterRequest(
            callerNumber,
            calleeNumber,
            callForwardType,
            hotlineNumber,
            ladingCode,
            "2",
            PostmanId,
            POCode,
            Utils.SHA256(callerNumber + calleeNumber + BuildConfig.PRIVATE_KEY).toUpperCase()
        )
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CALL_CENTER_FORWARD,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
        return call
    }

    @JvmStatic
    fun callForwardCallCenterEdit(
        callerNumber: String,
        calleeNumber: String,
        callForwardType: String,
        hotlineNumber: String,
        ladingCode: String,
        PostmanId: String,
        POCode: String,
        callback: CommonCallback<SimpleResult>
    ): Call<SimpleResult> {
        val request = CallForwardCallCenterRequest(
            callerNumber,
            calleeNumber,
            callForwardType,
            hotlineNumber,
            ladingCode,
            "2",
            PostmanId,
            POCode,
            Utils.SHA256(callerNumber + calleeNumber + BuildConfig.PRIVATE_KEY).toUpperCase()
        )
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CALL_FORWARD_EDIT_COD,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
        return call
    }

    @JvmStatic
    fun searchCallCenter(
        postmanID: String,
        fromDate: String,
        toDate: String,
        callback: CommonCallback<HistoryCallResult>
    ) {
        val request = SearchCallCenterRequest(
            postmanID,
            fromDate,
            toDate,
            Utils.SHA256(postmanID + fromDate + toDate + BuildConfig.PRIVATE_KEY).toUpperCase()
        )
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CALL_CENTER_SEARCH,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<HistoryCallResult> =
            getApiBuilderVer().commonServiceHistoryCallResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun locationAddNew(
        postmanID: String,
        latitude: String,
        longitude: String,
        callback: CommonCallback<SimpleResult>
    ) {

        val request = LocationAddNewRequest(
            postmanID,
            latitude,
            longitude,
            Utils.SHA256(postmanID + BuildConfig.PRIVATE_KEY).toUpperCase()
        )
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_LOCATION_ADD_NEW,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }
    // end phát hàng

    //start Viet-map

    @JvmStatic
    fun getAddressByLocation(
        longitude: Double, latitude: Double, callback: CommonCallback<XacMinhDiaChiResult>
    ) {
        val addressByLocationRequest = GetAddressByLocationRequest(longitude, latitude)
        val data = getGson().toJson(addressByLocationRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_REVERSE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<XacMinhDiaChiResult> = getApiBuilderVer().getAddressByLocation(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun vietmapSearch(
        text: String?, longitude: Double?, latitude: Double?
    ): Single<XacMinhDiaChiResult> {
        val vietmapSearchRequest = VietmapSearchRequest(text, longitude, latitude)
        val data = getGson().toJson(vietmapSearchRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_SEARCH,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().vietmapSearch(requestObject)
    }

    @JvmStatic
    fun vietmapVerify(
        id: String?, userId: String?, layer: String?, callback: CommonCallback<SimpleResult>
    ) {
        val vietMapVerifyRequest = VietMapVerifyRequest(id, userId, true, layer)
        val data = getGson().toJson(vietMapVerifyRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_VERIFY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }


    @JvmStatic
    fun vietmapUpdate(request: UpdateRequest?, callback: CommonCallback<SimpleResult>) {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_UPDATE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun vietmapRoute(request: List<String>, callback: CommonCallback<XacMinhDiaChiResult>) {
        val vmRouteV2Request = VmRouteV2Request(request, "")
        val data = getGson().toJson(vmRouteV2Request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_ROUTE_V2,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<XacMinhDiaChiResult> = getApiBuilderVer().vietmapRoute(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun vietmapVitriEndCode(longitude: Double, latitude: Double): Single<XacMinhDiaChiResult> {
        val vietmapSearchEncode = GetAddressByLocationRequest(longitude, latitude)
        val data = getGson().toJson(vietmapSearchEncode)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_ENCODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().vietmapSearchEncode(requestObject)
    }

    @JvmStatic
    fun vietmapKhoangCach(request: DLVGetDistanceRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_DISTANCE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun vietmapSearchDecode(decode: String): Single<DecodeDiaChiResult> {
        val vietMapSearchDecodeRequest = VietMapSearchDecodeRequest(decode)
        val data = getGson().toJson(vietMapSearchDecodeRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_DECODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().vietmapSearchDecode(requestObject)
    }

    @JvmStatic
    fun vietmapTravelSalesmanProblem(request: TravelSales): Single<XacMinhDiaChiResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_TRAVEL_SALESMAN_PROBLEM,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().vietmapTravelSalesmanProblemV1(requestObject)
    }


    @JvmStatic
    fun vietmapddVerifyAddress(request: VerifyAddress): Single<VerifyAddressRespone> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_VERIFY_ADDRESS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().ddVerifyAddress(requestObject)
    }

    @JvmStatic
    fun vietmapddCreateVietMapRequest(request: CreateVietMapRequest): Single<XacMinhRespone> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_CREATE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().ddCreateVietMapRequest(requestObject)
    }

    @JvmStatic
    fun getLapBangKeBD13(request: OrderCreateBD13Mode): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_ORDER_BD13,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getLoTrinhXacNhanTin(request: OrderCreateBD13Mode): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_ORDER_BY_COORDINATES,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getXacNhanLoTrinh(request: VM_POSTMAN_ROUTE): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.VM_POSTMAN_ROUTE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddSaveIDVmap(request: SaveIDVmapModel): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_CREATE_VMAP_ID,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getMap(): Single<SimpleResult> {
        val data = getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_GET_PARAMS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    //end Viet-map

    // start-login

    @JvmStatic
    fun loginAuthorized(loginRequest: LoginRequest?, callback: CommonCallback<LoginResult>) {
        val data = getGson().toJson(loginRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_LOGIN,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<LoginResult> = getApiBuilderVer().commonServiceLoginResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun validationAuthorized(mobileNumber: String, callback: CommonCallback<SimpleResult>) {
        val validationRequest = ValidationRequest(
            mobileNumber, Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase()
        )
        val data = NetWorkController.getGson().toJson(validationRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_VALIDATION,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun activeAuthorized(
        mobileNumber: String,
        activeCode: String?,
        codeDeviceActive: String?,
        callback: CommonCallback<ActiveResult>
    ) {
        val activeRequest = ActiveRequest(
            mobileNumber,
            activeCode,
            codeDeviceActive,
            Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase()
        )
        val data = NetWorkController.getGson().toJson(activeRequest)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_ACTIVE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<ActiveResult> = getApiBuilderVer().commonServiceActiveResult(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getPostOfficeByCode(
        code: String?, postmanID: String?, callback: CommonCallback<SimpleResult>
    ) {
        val request = GetPostOfficeByCodeRequest(code, postmanID)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_GET_POST_OFFICE_BY_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getVersion(callback: CommonCallback<SimpleResult>) {
        val request = AuthGetVersionRequest("DD_ANDROID")
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_GET_VERSION,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    //end - login
    @JvmStatic
    fun searchParcelCodeDelivery(
        parcelCode: String?, signature: String?, callback: CommonCallback<CommonObjectResult>
    ) {
        val request = AuthGetVersionRequest("DD_ANDROID")
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_INQUIRY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<CommonObjectResult> =
            getApiBuilderVer().commonServicesearchParcelCodeDelivery(requestObject)
        call.enqueue(callback)
    }


    @JvmStatic
    fun checkLadingCode(
        parcelCode: String?, callback: CommonCallback<SimpleResult>
    ) {
        val signature1 = Utils.SHA256(parcelCode + BuildConfig.PRIVATE_KEY).toUpperCase()
        val request = CheckLadingcode(parcelCode, signature1)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_GET_POST_OFFICE_BY_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getInquiryAmount(
        parcelCode: String?, callback: CommonCallback<InquiryAmountResult>
    ) {
        val signature1 = Utils.SHA256(parcelCode + BuildConfig.PRIVATE_KEY).toUpperCase()
        val request = CheckLadingcode(parcelCode, signature1)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_INQUIRY_AMOUNT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<InquiryAmountResult> =
            getApiBuilderVer().commonServicegetInquiryAmount(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun deliveryGetPaypostError(
        fromDate: String?, toDate: String?, callback: CommonCallback<GachNoResult>
    ) {
        val request = GetPaypostError(fromDate, toDate)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_GET_PAYPOST_ERROR,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<GachNoResult> =
            getApiBuilderVer().commonServicedeliveryGetPaypostError(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun cancelRoute(
        id: Int?, postmanId: Int?, callback: CommonCallback<SimpleResult>
    ) {
        val request = CancelRouteModel(id, postmanId)
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CHANGE_ROUTE_CANCEL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun paymentPaypost(
        request: PaymentPaypostRequest, callback: CommonCallback<SimpleResult>
    ) {
        val data = NetWorkController.getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_PAYMENT_PAYPOST,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun ddCall(request: CallLiveMode): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CALL_DIRECT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getVaoCa(request: MainMode): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_POSTMAN_SHIFT_INPUT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getRaCa(request: String): Single<SimpleResult> {
        val data = request
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_POSTMAN_SHIFT_OUTPUT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getCallLog(request: List<CallLogMode>): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CALL_LOG,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getCallHistory(request: HistoryRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CALL_HISTORY,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun getCallHistoryTotal(request: HistoryRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_CALL_HISTORY_TOTAL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddSreachPhone(request: PhoneNumber): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_ROUTE_ADDRESS_SEARCH_ADDRESS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddGetDichVuMpit(): Single<SimpleResult> {
        val data = getGson().toJson("")
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PU_DIC_GET_SERVICE_CODE_MPITS,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    // start chat
    @JvmStatic
    fun getDanhmuccaccap(request: String): Single<SimpleResult> {
        val data = request
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_DIC_GET_PO_BY_PARENT_CODE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddQueuChat(request: RequestQueuChat): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.PARTNER_CHAT_GET_QUEUE_CHAT,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddGetPhoneBuuta(request: String): Single<SimpleResult> {
        val data = request
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_GET_POSTMAN_BY_MOBILE_NUMBER,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddGetTenBuuTa(request: RouteTypeRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.AUTH_GET_POSTMAN_IN_PO_BY_ROUTE_TYPE,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }
    // end chat

    // start add ticket phat
    @JvmStatic
    fun ddGetSubSolution(request: String): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(request)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DIC_GET_SUB_SOLUTION,
            request,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddStatisticTicket(request: STTTicketManagementTotalRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_TICKET_MANAGEMENT_TOTAL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddStatisticTicketDetail(request: STTTicketManagementTotalRequest): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.STT_TICKET_MANAGEMENT_DETAIL,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

    @JvmStatic
    fun ddDivCreateTicket(request: DivCreateTicketMode): Single<SimpleResult> {
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject(
            "ANDROID",
            "",
            Constants.DLV_TICKET_CREATE_TICKET,
            data,
            Utils.getLocalTime(Constants.DATE_FORMAT),
            "",
            signature
        )
        return getApiRxBuilderVer().commonServiceRx(requestObject)
    }

}