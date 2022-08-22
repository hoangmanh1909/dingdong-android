package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainViewRequest(
    @SerializedName("FromDate")
    @Expose
    val fromDate: String?,
    @SerializedName("ToDate")
    @Expose
    val toDate: String?,
    @SerializedName("PostmanCode")
    @Expose
    val postmanCode: String?,
    @SerializedName("RouteCode")
    @Expose
    val routeCode: String?
)