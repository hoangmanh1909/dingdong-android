package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class StatisticPaymentRequest(
    @SerializedName("PostmanId")
    val postmanId:String,
    @SerializedName("POCode")
    val pOCode:String,
    @SerializedName("PostmanMobileNumber")
    val postmanMobileNumber:String,
    @SerializedName("FromDate")
    val fromDate:String,
    @SerializedName("ToDate")
    val toDate:String
)