package com.ems.dingdong.network

import android.util.Log
import com.ems.dingdong.BuildConfig
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.*
import com.ems.dingdong.model.request.*
import com.ems.dingdong.model.response.PaymentRequestResponse
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


    fun getApiBuilderVer(): VinattiAPI {
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


    fun getApiRxBuilderVer(): VinattiAPI {
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
    fun searchDeliveryStatistic(
        fromDate: String,
        toDate: String,
        status: String,
        postmanId: String,
        routeCode: String,
        callback: CommonCallback<SimpleResult>
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
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
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
        callback: CommonCallback<SimpleResult>
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
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getLadingStatusGeneral(
        postmanID: String,
        fromDate: String,
        toDate: String,
        ladingType: Int,
        routeCode: String,
        callback: CommonCallback<SimpleResult>
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
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun findLocation(ladingCode: String, poCode: String): Observable<SimpleResult> {
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
        postmanID: String,
        fromDate: String,
        toDate: String,
        callback: CommonCallback<SimpleResult>
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
                payLinkRequest.postmanTel
                        + payLinkRequest.postmanCode + payLinkRequest.getpOCode()
                        + BuildConfig.E_WALLET_SIGNATURE_KEY
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
                payLinkConfirm.requestId
                        + payLinkConfirm.otpCode
                        + payLinkConfirm.postmanTel
                        + payLinkConfirm.postmanCode
                        + payLinkConfirm.getpOCode()
                        + BuildConfig.E_WALLET_SIGNATURE_KEY
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
            paymentRequestModel.postmanCode
                    + paymentRequestModel.poCode
                    + paymentRequestModel.routeCode
                    + paymentRequestModel.paymentToken
                    + BuildConfig.E_WALLET_SIGNATURE_KEY
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
    ): Single<SimpleResult> {
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
        return getApiRxBuilderVer().commonServiceRx(requestObject)
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
            orderPostmanID,
            orderID,
            postmanID,
            status,
            fromAssignDate,
            toAssignDate
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
            orderPostmanID,
            employeeID,
            statusCode,
            confirmReason,
            "DD_ANDROID"
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
        request: ArrayList<ConfirmOrderPostman>,
        callback: CommonCallback<SimpleResult>
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
        hoanTatTinRequest: HoanTatTinRequest,
        callback: CommonCallback<SimpleResult>?
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
            orderPostmanID,
            orderID,
            postmanID,
            status,
            fromAssignDate,
            toAssignDate
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
        dataRequestPayment.data = NetWorkController.getGson().toJson(request)
        val data = NetWorkController.getGson().toJson(dataRequestPayment)
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
        poCode: String?,
        routeId: Int,
        routeType: String?,
        callback: CommonCallback<SimpleResult>?
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
        list: List<HoanTatTinRequest?>?,
        callback: CommonCallback<SimpleResult>?
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
            ladingCode,
            fromDate,
            toDate,
            postmanId,
            routeId,
            poCode,
            statusCode,
            fromRouteId
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
            ladingCode,
            fromDate,
            toDate,
            postmanId,
            routeId,
            poCode,
            statusCode,
            fromRouteId
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
}