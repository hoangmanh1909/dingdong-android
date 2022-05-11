package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class StatisticCollectRequest(
    @SerializedName("PostmanId")
    val postmanId:String,
    @SerializedName("FromDate")
    val fromDate:String,
    @SerializedName("ToDate")
    val toDate:String
)