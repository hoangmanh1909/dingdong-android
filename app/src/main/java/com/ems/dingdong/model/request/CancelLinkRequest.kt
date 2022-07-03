package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class CancelLinkRequest(
    @SerializedName("POProvinceCode")
    val pOProvinceCode:String?,
    @SerializedName("PODistrictCode")
    val pODistrictCode:String?,
    @SerializedName("POCode")
    val pOCode :String?,
    @SerializedName("PostmanCode")
    val postmanCode :String?,
    @SerializedName("PostmanId")
    val postmanId :Long?,
    @SerializedName("RouteCode")
    val routeCode :String?,
    @SerializedName("RouteId")
    val routeId :Long?,
    @SerializedName("PostmanTel")
    val postmanTel :String?,
    @SerializedName("Signature")
    var signature  :String? = null,
    @SerializedName("AccountType")
    val accountType :Int?,
    @SerializedName("PaymentToken")
    val paymentToken :String?

)