package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class SearchCreateBd13Request(
    @SerializedName("DeliveryPOCode")
    val deliveryPOCode:String?,
    @SerializedName("RoutePOCode")
    val routePOCode:String?,
    @SerializedName("BagNumber")
    val bagNumber:String?,
    @SerializedName("ChuyenThu")
    val chuyenThu:String?,
    @SerializedName("CreateDate")
    val createDate:String?,
    @SerializedName("Shift")
    val shift:String?
)