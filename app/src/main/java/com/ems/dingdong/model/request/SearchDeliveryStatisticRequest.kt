package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class SearchDeliveryStatisticRequest(
    @SerializedName("FromDate")
    val fromDate: String,
    @SerializedName("ToDate")
    val toDate: String,
    @SerializedName("Status")
    val status: String,
    @SerializedName("PostmanId")
    val postmanId: String,
    @SerializedName("RouteCode")
    val routeCode: String
)