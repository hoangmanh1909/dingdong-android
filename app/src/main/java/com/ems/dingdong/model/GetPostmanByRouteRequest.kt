package com.ems.dingdong.model

import com.google.gson.annotations.SerializedName

data class GetPostmanByRouteRequest(
    @SerializedName("poCode")
    val poCode:String?,
    @SerializedName("routeId")
    val routeId:Int?,
    @SerializedName("routeType")
    val routeType:String?
)