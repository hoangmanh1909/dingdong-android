package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class GetCancelDeliveryRequest(
    @SerializedName("PostmanCode")
    val postmanCode:String?,
    @SerializedName("RouteCode")
    val routeCode:String?,
    @SerializedName("FromDate")
    val fromDate:String?,
    @SerializedName("ToDate")
    val toDate:String?
)