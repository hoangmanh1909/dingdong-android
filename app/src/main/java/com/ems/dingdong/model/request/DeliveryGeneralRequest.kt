package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeliveryGeneralRequest(
    @SerializedName("PostmanId")
    @Expose
    val postmanId: String?,
    @SerializedName("FromDate")
    @Expose
    val fromDate : String?,
    @SerializedName("ToDate")
    @Expose
    val toDate: String?,
    @SerializedName("IsSuccess")
    @Expose
    val isSuccess : Boolean?,
    @SerializedName("RouteCode")
    @Expose
    val routeCode: String?
)