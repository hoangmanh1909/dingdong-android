package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LadingStatusDetailRequest(
    @SerializedName("Type")
    @Expose
    val type:Int?,
    @SerializedName("ServiceCode")
    @Expose
    val serviceCode:String?,
    @SerializedName("PostmanId")
    @Expose
    val postmanId:String?,
    @SerializedName("FromDate")
    @Expose
    val fromDate:String?,
    @SerializedName("ToDate")
    @Expose
    val toDate:String?,
    @SerializedName("LadingType")
    @Expose
    val ladingType:Int?,
    @SerializedName("RouteCode")
    @Expose
    val routeCode:String?

)