package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class ApprovedAgreeRequest(
    @SerializedName("Id")
    val id:String?,
    @SerializedName("LadingCode")
    val ladingCode:String?,
    @SerializedName("PostmanId")
    val postmanId:String?,
    @SerializedName("PostmanCode")
    val postmanCode:String?,
    @SerializedName("POCode")
    val pOCode:String?,
    @SerializedName("RouteId")
    val routeId:String?,
    @SerializedName("RouteCode")
    val routeCode:String?
)