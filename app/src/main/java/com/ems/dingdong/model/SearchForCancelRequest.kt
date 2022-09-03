package com.ems.dingdong.model

import com.google.gson.annotations.SerializedName

data class SearchForCancelRequest(
    @SerializedName("LadingCode")
    val ladingCode:String?,
    @SerializedName("FromDate")
    val fromDate:String?,
    @SerializedName("ToDate")
    val toDate:String?,
    @SerializedName("PostmanId")
    val postmanId:String?,
    @SerializedName("RouteId")
    val routeId:String?,
    @SerializedName("POCode")
    val pOCode:String?,
    @SerializedName("StatusCode")
    val statusCode:String?,
    @SerializedName("ToRouteId")
    val toRouteId:Int?
)