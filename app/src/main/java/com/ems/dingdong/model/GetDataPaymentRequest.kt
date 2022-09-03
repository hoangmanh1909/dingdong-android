package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class GetDataPaymentRequest(
    @SerializedName("ServiceCode")
    val ServiceCode:String?,
    @SerializedName("fromDate")
    val fromDate:String?,
    @SerializedName("toDate")
    val toDate:String?,
    @SerializedName("poCode")
    val poCode:String?,
    @SerializedName("routeCode")
    val routeCode:String?,
    @SerializedName("postmanCode")
    val postmanCode:String?
)