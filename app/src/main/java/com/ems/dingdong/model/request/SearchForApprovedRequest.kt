package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class SearchForApprovedRequest(
    @SerializedName("LadingCode")
    val ladingCode: String,
    @SerializedName("FromDate")
    val fromDate: String,
    @SerializedName("ToDate")
    val toDate: String,
    @SerializedName("PostmanId")
    val postmanId: String,
    @SerializedName("RouteId")
    val routeId: String,
    @SerializedName("POCode")
    val pOCode: String,
    @SerializedName("StatusCode")
    val statusCode: String,
    @SerializedName("FromRouteId")
    val fromRouteId: Int
)