package com.ems.dingdong.network

import com.ems.dingdong.BuildConfig
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.CommonObjectResult
import com.ems.dingdong.model.SimpleResult
import com.ems.dingdong.model.request.*
import com.ems.dingdong.utiles.Constants
import com.ems.dingdong.utiles.RSAUtil.Companion.signature
import com.ems.dingdong.utiles.Utils
import com.ems.dingdong.utiles.Utils.getUnsafeOkHttpClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


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
    fun searchDeliveryStatistic(fromDate: String, toDate: String, status: String, postmanId: String, routeCode: String, callback: CommonCallback<SimpleResult>){
        val request = SearchDeliveryStatisticRequest(fromDate, toDate, status, postmanId, routeCode)
        val data = getGson().toJson(request)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_DELIVERY, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)

    }

    @JvmStatic
    fun statisticDebitGeneral(postmanID: String, fromDate: String, toDate: String, routeCode: String, callback: CommonCallback<SimpleResult>) {
        val debitGeneralRequest = DebitGeneralRequest(postmanID, fromDate, toDate, routeCode)
        val data = getGson().toJson(debitGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_DEBIT_GENERAL, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun statisticDebitDetail(postmanID: String, fromDate: String, toDate: String, statusCode: String, routeCode: String, callback: CommonCallback<SimpleResult>) {
        val debitDetailRequest = DebitDetailRequest(postmanID, fromDate, toDate, statusCode, routeCode)
        val data = getGson().toJson(debitDetailRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_DEBIT_DETAILL, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
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
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun getLadingStatusGeneral(postmanID: String, fromDate: String, toDate: String, ladingType: Int, routeCode: String, callback: CommonCallback<SimpleResult>) {
        val ladingStatusGeneralRequest = LadingStatusGeneralRequest(postmanID, fromDate, toDate, ladingType, routeCode)
        val data = NetWorkController.getGson().toJson(ladingStatusGeneralRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_LADING_STATUS_GENERAL, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }

    @JvmStatic
    fun findLocation(ladingCode: String, poCode: String): Observable<CommonObjectResult> {
        val trackTraceLadingRequest = TrackTraceLadingRequest(ladingCode, poCode, Utils.SHA256(ladingCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase())
        val data = getGson().toJson(trackTraceLadingRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.DLV_TRACK_TRACE_LADING, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        return getApiRxBuilderVer().findLocation(requestObject)
    }

    @JvmStatic
    fun searchStatisticCollect(postmanID: String, fromDate: String, toDate: String, callback: CommonCallback<SimpleResult>) {
        val statisticCollectRequest = StatisticCollectRequest(postmanID, fromDate, toDate)
        val data = NetWorkController.getGson().toJson(statisticCollectRequest)
        val signature = signature(data)
        val requestObject = RequestObject("ANDROID", "", Constants.STT_COLLECT, data, Utils.getLocalTime(Constants.DATE_FORMAT), "", signature)
        val call: Call<SimpleResult> = getApiBuilderVer().commonService(requestObject)
        call.enqueue(callback)
    }


}