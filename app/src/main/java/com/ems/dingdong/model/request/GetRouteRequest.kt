package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class GetRouteRequest(
    @SerializedName("poCode")
    val poCode:String?,
    @SerializedName("routeType")
    val routeType:String?
)